package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiApiBuilder;
import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.api.client.model.Balance;
import net.iconomi.api.client.model.Daa;
import net.iconomi.api.client.model.DaaChart;
import net.iconomi.api.client.model.StructureElement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
public class TestRestClient {
    private static final String API_KEY = "33e224af6dae6a1317b9722aaeebe4f1a2c07b4c0b541bb5198ab11c3145ca73";
    private static final String API_SECRET = "254288f3d8e22aed1323081452e9cb761d1c179c786a0983acc399a93a01f795";

    /*private static final String API_KEY = "4a5bbaaa421d2bcbcfe857d33bfc31da66a0242e83c5121da72fa9cfd214d2c5";
    private static final String API_SECRET = "ee20ba079cd78b66ac08d73f0a73df593b26f26dd0759d6e18dfaad516d508ce";*/
    private final IconomiRestApi api = new IconomiApiBuilder()
            //.setApiKey("api-key", "secret")
            .setApiKey(API_KEY, API_SECRET)
            //.setBaseApiUrl("https://api-durkadur.iconomi.com")
            .buildRestApi();

    @Test
    public void testApi() throws IOException {
        for (Daa ticker : api.getDaaList()) {
            Daa daa = api.getDaa(ticker.getTicker());

            List<StructureElement> structure = api.getDaaStructure(daa.getTicker());
            assertNotNull(structure);
            System.out.println(String.format("for ticker: '%s' got: '%s', structure: %s", ticker.getTicker(), daa.getName(), structure));
            try {
                BigDecimal price = api.getDaaPrice(ticker.getTicker());
                assertNotNull(price);
                System.out.println(String.format("for ticker: '%s' got price: %s", ticker.getTicker(), price));
            } catch (IOException e) {
                e.printStackTrace();
            }
            DaaChart chart = api.getDaaPriceHistry(daa.getTicker(), Instant.now().minus(10, ChronoUnit.DAYS).toEpochMilli(), Instant.now().minus(10, ChronoUnit.DAYS).toEpochMilli());
            assertNotNull(chart, "history cannot be null");
        }
    }

    @Test
    //  @Disabled
    public void testGetUserBalance() throws IOException {
        for (int i = 0; i < 100; i++) {
            try {
                Balance b = api.getUserBalance();
                System.out.println(String.format("%s got balance", i));
                assertNotNull(b);
            } catch (IOException e) {
                System.out.println("could not get balance " + e.getMessage());
            }
        }
    }


}
