package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.generated.ApiException;
import net.iconomi.generated.api.StrategiesApi;
import net.iconomi.generated.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class StrategyApiTest {
    static IconomiRestApi restApi;
    private static StrategiesApi strategiesApi;

    @BeforeAll
    public static void beforeAll() {
        restApi = new IconomiRestApi(
                TestData.baseURL,
                TestData.apiKey,
                TestData.apiSecret
        );
        strategiesApi = restApi.getStrategiesApi();
    }

    @Test
    public void charts() throws ApiException {
        Chart chart = strategiesApi.priceHistory(TestData.strategyName, "EUR", null, null, "HOURLY");
        Assertions.assertEquals(TestData.strategyName, chart.getTicker());
        Assertions.assertEquals(Chart.CurrencyEnum.EUR, chart.getCurrency());
        Assertions.assertEquals("HOURLY", chart.getGranulation());
        Assertions.assertNotNull(chart.getValues());
        Assertions.assertFalse(chart.getValues().isEmpty());
    }

    @Test
    public void list() throws ApiException {
        List<Strategy> strategies = strategiesApi.strategyList(null, null);
        Assertions.assertFalse(strategies.isEmpty());
    }

    @Test
    public void price() throws ApiException {
        Ticker daaPrice = strategiesApi.daaPrice(TestData.strategyName, "EUR");
        Assertions.assertEquals(TestData.strategyName, daaPrice.getTicker());
        Assertions.assertEquals("EUR", daaPrice.getCurrency());
        Assertions.assertNotNull(daaPrice.getAum());
        Assertions.assertNotNull(daaPrice.getPrice());
    }

    @Test
    public void fittingInfo() throws ApiException {
        FittingInfo fittingInfo = strategiesApi.fittingInfo(TestData.strategyName);
        Assertions.assertNotNull(fittingInfo);
    }

    @Test
    public void statistics() throws ApiException {
        Statistics statistics = strategiesApi.getStatistics(TestData.strategyName, "EUR");
        Assertions.assertNotNull(statistics);
    }

    @Test
    public void info() throws ApiException {
        Strategy strategy = strategiesApi.details(TestData.strategyName);
        Assertions.assertNotNull(strategy);
    }

    @Test
    public void structure() throws ApiException {
        Structure structure = strategiesApi.structure(TestData.strategyName, "EUR");
        Assertions.assertNotNull(structure);
    }

    @Test
    public void submitStructure() throws ApiException {
        StructureSubmit structureSubmit = new StructureSubmit();
        structureSubmit.ticker(TestData.strategyName);
        structureSubmit.setSpeedType(StructureSubmit.SpeedTypeEnum.FAST);
        SubmitStructureElement sel = new SubmitStructureElement();
        sel.setAssetTicker("ETH");
        sel.setRebalancedWeight(BigDecimal.ONE);
        structureSubmit.setValues(Arrays.asList(sel));
        Structure structure = strategiesApi.submitStructure(TestData.strategyName, structureSubmit);
        Assertions.assertNotNull(structure);
    }

    @Test
    public void posts() throws ApiException {
        Posts posts = strategiesApi.posts(TestData.strategyName, 3, 0);
        Assertions.assertNotNull(posts);
    }

    @Test
    public void createPost() throws ApiException {
        SubmitPost body = new SubmitPost();
        body.setContent("Hello, world!");
        body.setGiphyId("Z6f7vzq3iP6Mw");
        body.setPostToFollowerFunds(Boolean.FALSE);
        Post post = strategiesApi.createPost(TestData.strategyName, body);
        Assertions.assertNotNull(post);
    }

    @Test
    public void updatePost() throws ApiException {
        SubmitPost body = new SubmitPost();
        body.setContent("One!!");
        body.setGiphyId("Z6f7vzq3iP6Mw");
        body.setPostToFollowerFunds(Boolean.FALSE);
        Post post = strategiesApi.createPost(TestData.strategyName, body);
        Assertions.assertNotNull(post);
        String postId = post.getId();
        body.setContent("Two!!");
        post = strategiesApi.updatePost(TestData.strategyName, postId, body);
        Assertions.assertNotNull(post);
        Assertions.assertEquals("Two!!", post.getContent());
    }
}

