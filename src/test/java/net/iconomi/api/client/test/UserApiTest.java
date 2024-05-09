package net.iconomi.api.client.test;

import net.iconomi.api.client.IconomiRestApi;
import net.iconomi.generated.ApiException;
import net.iconomi.generated.api.UserApi;
import net.iconomi.generated.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserApiTest {
    static IconomiRestApi restApi;
    private static UserApi userApi;

    @BeforeAll
    public static void beforeAll() {
        restApi = new IconomiRestApi(
                TestData.baseURL,
                TestData.apiKey,
                TestData.apiSecret
        );
        userApi = restApi.getUserApi();
    }

    @Test
    public void activities() throws ApiException {
        Activity activities1 = userApi.getActivities("FEES_AND_EARNINGS", 4, 0);
        Activity activities2 = userApi.getActivities("MY_ACTIVITIES", 3, 1);
        Assertions.assertNotNull(activities1);
        Assertions.assertNotNull(activities2);
    }

    @Test
    public void balance() throws ApiException {
        Balance eur = userApi.getUserBalance("EUR");
        Assertions.assertEquals(eur.getCurrency(), "EUR");
        Balance usd = userApi.getUserBalance("USD");
        Assertions.assertEquals(usd.getCurrency(), "USD");
    }

    @Test
    public void depositAddress() throws ApiException {
        Deposit address = userApi.getDepositAddress("BTC");
        Assertions.assertEquals("BTC", address.getCurrency());
    }

    @Test
    public void withdraw() throws ApiException {
        Withdraw withdrawRequest = new Withdraw();
        withdrawRequest.currency("BTC");
        withdrawRequest.address("mpsqws5F3M56TuuXqEpCFe1dqYYNPuA3gK");
        withdrawRequest.setAmount("0.002");
        Transaction withdrawTx = userApi.withdraw(withdrawRequest);
    }

    @Test
    public void userActivity() throws ApiException {
        Activity activities1 = userApi.getActivities("FEES_AND_EARNINGS", 4, 0);
        if (activities1.getTransactions().size() > 0) {
            Transaction transaction = userApi.transactionInfo(activities1.getTransactions().get(0).getTransactionId());
        }
    }

    @Test
    public void userChart() throws ApiException {
        PortfolioHistory userChart = userApi.portfolioHistory("EUR", "ONE_WEEK");
        Assertions.assertEquals("EUR", userChart.getCurrency().name());
        Assertions.assertEquals("ONE_WEEK", userChart.getPeriod().name());
    }
}

