package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiApiBuilder;
import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.api.client.model.Balance;
import net.iconomi.api.client.model.Daa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

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
            BigDecimal price = api.getDaaPrice(ticker.getTicker());
            System.out.println(String.format("for ticker: '%s' got price: %s", ticker.getTicker(), price));
        }
    }

    @Test
    @Disabled
    public void testGetUserBalance() throws IOException {
        Balance b = api.getUserBalance();
        Assertions.assertNotNull(b);
    }


}
