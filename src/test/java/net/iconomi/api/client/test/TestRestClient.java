package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiApiBuilder;
import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.api.client.model.Balance;
import net.iconomi.api.client.model.Daa;
import net.iconomi.api.client.model.DaaChart;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
public class TestRestClient {
    private final IconomiRestApi api = new IconomiApiBuilder()
            //.setApiKey("api-key", "secret")
            .buildRestApi();

    @Test
    public void testApi() throws IOException {
        for (Daa ticker : api.getDaaList()) {
            Daa daa = api.getDaa(ticker.getTicker());
            System.out.println(String.format("for ticker: '%s' got daa: %s", ticker.getTicker(), daa));
            assertNotNull(api.getDaaStructure(daa.getTicker()));
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
    @Disabled
    public void testGetUserBalance() throws IOException {
        Balance b = api.getUserBalance();
        assertNotNull(b);
    }


}
