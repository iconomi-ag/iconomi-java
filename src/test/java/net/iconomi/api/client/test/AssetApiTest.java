package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.generated.ApiException;
import net.iconomi.generated.api.AssetApi;
import net.iconomi.generated.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AssetApiTest {
    static IconomiRestApi restApi;
    private static AssetApi assetApi;

    @BeforeAll
    public static void beforeAll() {
        restApi = new IconomiRestApi(
                TestData.baseURL,
                TestData.apiKey,
                TestData.apiSecret
        );
        assetApi = restApi.getAssetApi();
    }

    @Test
    public void assetList() throws ApiException {
        List<Asset> assets = assetApi.assetList();
        //This list changes, so can't really assert anything useful except size and that it contains some well known asset
        Assertions.assertFalse(assets.isEmpty());
        Assertions.assertTrue(assets.stream().anyMatch(a -> a.getTicker().equals("BTC")));
    }

    @Test
    public void assetDetails() throws ApiException {
        AssetInfo btc = assetApi.assetDetails("BTC");
        Assertions.assertEquals("BTC", btc.getTicker());
        Assertions.assertEquals("Bitcoin", btc.getName());
    }

    @Test
    public void assetHistory() throws ApiException {
        Chart chart = assetApi.assetPriceHistory("BTC", "EUR", null, null, "HOURLY");
        Assertions.assertEquals("BTC", chart.getTicker());
        Assertions.assertEquals(Chart.CurrencyEnum.EUR, chart.getCurrency());
        Assertions.assertEquals("HOURLY", chart.getGranulation());
        Assertions.assertNotNull(chart.getValues());
        Assertions.assertFalse(chart.getValues().isEmpty());
    }

    @Test
    public void assetStatistics() throws ApiException {
        Statistics statistics = assetApi.assetStatistics("BTC", "EUR");
        Assertions.assertEquals("EUR", statistics.getCurrency());
        Assertions.assertEquals("BTC", statistics.getTicker());
        Assertions.assertNotNull(statistics.getMaxDrawdown());
        Assertions.assertNotNull(statistics.getReturns());
        Assertions.assertNotNull(statistics.getVolatility());
    }

    @Test
    public void assetTicker() throws ApiException {
        Ticker ticker = assetApi.assetTicker("BTC", "EUR");
        Assertions.assertEquals("BTC", ticker.getTicker());
        Assertions.assertEquals("EUR", ticker.getCurrency());
        Assertions.assertNotNull(ticker.getPrice());
        Assertions.assertNotNull(ticker.getAum());
    }
}
