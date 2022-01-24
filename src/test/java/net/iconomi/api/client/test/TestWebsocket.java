package net.iconomi.api.client.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.iconomi.api.client.IconomiWebsocketApi;
import net.iconomi.generated.model.StructureSubmit;
import net.iconomi.generated.model.SubmitStructureElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TestWebsocket {
    private static final Logger logger = LoggerFactory.getLogger(TestWebsocket.class);

    private static final String apiKey = TestData.apiKey;
    private static final String apiSecret = TestData.apiSecret;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static IconomiWebsocketApi websocketApi;

    @BeforeAll
    public static void init() throws IOException {
        websocketApi = new IconomiWebsocketApi(TestData.baseURL, apiKey, apiSecret);
        websocketApi.registerOnUnknownMessageReceived(s -> {
            logger.warn("Received unknown message: {}", s);
        });
        websocketApi.registerOnOpen(() -> {
            logger.info("ON OPEN");
        });
        websocketApi.registerOnFailure((t, r) -> {
            logger.warn("", t);
        });
        websocketApi.registerOnClosed((i, s) -> {
            logger.warn("Closed {}: {}", i, s);
        });
        websocketApi.registerOnClosing((i, s) -> {
            logger.warn("Closing {}: {}", i, s);
        });

        Consumer c = o -> {
            try {
                logger.info("Received object {}, {}", o.getClass().getSimpleName(), objectMapper.writer().writeValueAsString(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };

        websocketApi.registerOnAssetInfoReceived(c);
        websocketApi.registerOnBalanceReceived(c);
        websocketApi.registerOnStrategyListReceived(c);
        websocketApi.registerOnStrategyReceived(c);
        websocketApi.registerOnTickerReceived(c);
        websocketApi.registerPriceHistoryReceived(c);
        websocketApi.open();
    }

    @AfterAll
    public static void deinit() {
        websocketApi.close();
    }

    @Test
    public void prices() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        websocketApi.registerOnSubscribed(s -> {
            latch.countDown();
        });
        websocketApi.subscribeToPrices("BLX");
        websocketApi.subscribeToPrices("BTC");
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void strategyStructure() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerOnStructureReceived(s -> {
            latch.countDown();
        });
        websocketApi.getStrategyStructure(TestData.strategyName);
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void strategyFittingInfo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerOnFittingInfoReceived(s -> {
            latch.countDown();
        });
        websocketApi.getStrategyFittingInfo(TestData.strategyName);
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void strategyInfo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerOnStrategyReceived(s -> {
            latch.countDown();
        });
        websocketApi.getStrategyinfo(TestData.strategyName);
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void strategyList() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerOnStrategyListReceived(s -> {
            latch.countDown();
        });
        websocketApi.getStrategyList();
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void balance() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerOnBalanceReceived(s -> {
            latch.countDown();
        });
        websocketApi.getBalance("USD");
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void price() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerOnTickerReceived(s -> {
            latch.countDown();
        });
        websocketApi.getPrice(TestData.strategyName, "USD");
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void priceHistory() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        websocketApi.registerPriceHistoryReceived(s -> {
            latch.countDown();
        });
        websocketApi.getPriceHistory(TestData.strategyName, "USD");
        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void structureSubmit() {
        StructureSubmit structureSubmit = new StructureSubmit();
        structureSubmit.ticker(TestData.strategyName);
        structureSubmit.setSpeedType(StructureSubmit.SpeedTypeEnum.FAST);
        SubmitStructureElement sel = new SubmitStructureElement();
        sel.setAssetTicker("BTC");
        sel.setRebalancedWeight(BigDecimal.ONE);
        structureSubmit.setValues(Arrays.asList(sel));
        websocketApi.submitStructure(structureSubmit);
        //no return value
    }
}
