package net.iconomi.api.client;

import net.iconomi.generated.ApiClient;
import net.iconomi.generated.api.AssetApi;
import net.iconomi.generated.api.StrategiesApi;
import net.iconomi.generated.api.TradingApi;
import net.iconomi.generated.api.UserApi;
import okhttp3.OkHttpClient;

public class IconomiRestApi {
    private final AssetApi assetApi;
    private final StrategiesApi strategiesApi;
    private final TradingApi tradingApi;
    private final UserApi userApi;

    public IconomiRestApi(String baseURL,
                          String apiKey,
                          String apiSecret) {
        ApiClient apiClient = new ApiClient(
                new OkHttpClient.Builder()
                        .followRedirects(true)
                        .followSslRedirects(false)
                        .addInterceptor(i -> HeaderUtil.addAuthHeaders(i, apiKey, apiSecret))
                        .build());
        apiClient.setBasePath(baseURL);

        this.assetApi = new AssetApi(apiClient);
        this.strategiesApi = new StrategiesApi(apiClient);
        this.tradingApi = new TradingApi(apiClient);
        this.userApi = new UserApi(apiClient);
    }

    public AssetApi getAssetApi() {
        return assetApi;
    }

    public StrategiesApi getStrategiesApi() {
        return strategiesApi;
    }

    public TradingApi getTradingApi() {
        return tradingApi;
    }

    public UserApi getUserApi() {
        return userApi;
    }
}
