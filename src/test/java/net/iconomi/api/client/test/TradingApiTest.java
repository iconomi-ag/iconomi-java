package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.generated.ApiException;
import net.iconomi.generated.api.*;
import net.iconomi.generated.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TradingApiTest {
    static IconomiRestApi restApi;
    private static TradingApi tradingApi;

    @BeforeAll
    public static void beforeAll() {
        restApi = new IconomiRestApi(
                TestData.baseURL,
                TestData.apiKey,
                TestData.apiSecret
        );
        tradingApi = restApi.getTradingApi();
    }

    @Test
    public void allOrders() {
        //Can't really assert anything useful since there may be no orders or they may change
        Assertions.assertDoesNotThrow(() ->
                tradingApi.getAllOrders("COMPLETED", null, null));
        Assertions.assertDoesNotThrow(() ->
                tradingApi.getAllOrders("CANCELLED", null, null));
        Assertions.assertDoesNotThrow(() ->
                tradingApi.getAllOrders("IN_PROGRESS", null, null));

        Assertions.assertDoesNotThrow(() ->
                tradingApi.getAllOrders("COMPLETED", 10, 0));
        Assertions.assertDoesNotThrow(() ->
                tradingApi.getAllOrders("CANCELLED", 10, 0));
        Assertions.assertDoesNotThrow(() ->
                tradingApi.getAllOrders("IN_PROGRESS", 10, 0));
    }

    @Test
    public void orderOfferAndConfirm() throws ApiException {
        OrderOffer orderOffer = new OrderOffer();
        orderOffer.setAmount(new BigDecimal("100"));
        orderOffer.setSide(OrderOffer.SideEnum.SOURCE);
        orderOffer.setSourceTicker("EUR");
        orderOffer.setTargetTicker("BTC");
        TradeOffer tradeOffer = tradingApi.orderOffer(orderOffer);
        TradeConfirm tradeConfirm = tradingApi.confirmOffer(tradeOffer.getOfferId());
        Assertions.assertNull(tradeOffer.getError());
        Assertions.assertNotNull(tradeConfirm.getTransaction());
    }


    @Test
    public void placeOrderStatusCancel() throws ApiException {
        Order order = new Order();
        order.setAmount(new BigDecimal("100"));
        order.setFittingSpeedType(Order.FittingSpeedTypeEnum.FAST);
        order.setSourceTicker("EUR");
        order.setTargetTicker("BTC");
        OrderInfo orderInfo = tradingApi.orderTrade(order);
        OrderInfo status = tradingApi.status(orderInfo.getId());
        OrderInfo cancelOrderInfo = tradingApi.cancelOrder(orderInfo.getId());
    }
}

