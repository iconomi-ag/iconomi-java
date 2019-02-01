package net.iconomi.api.client;

import net.iconomi.api.client.model.Balance;
import net.iconomi.api.client.model.Daa;
import net.iconomi.api.client.model.DaaChart;
import net.iconomi.api.client.model.DaaList;
import net.iconomi.api.client.model.DaaPrice;
import net.iconomi.api.client.model.DaaStructure;
import net.iconomi.api.client.model.StructureElement;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

class IconomiRestApiImpl implements IconomiRestApi {
    private static final Logger log = LoggerFactory.getLogger(IconomiRestApiImpl.class);
    private final String apiKey;
    private final String apiSecret;
    private final Retrofit retrofit;
    private final IconomiService service;


    IconomiRestApiImpl(IconomiApiBuilder builder) {
        this.apiKey = builder.apiKey;
        this.apiSecret = builder.apiSecret;
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(this::addAuthHeaders)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(builder.baseApiUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        service = retrofit.create(IconomiService.class);
    }

    @Override
    public List<Daa> getDaaList() throws IOException {
        DaaList res = service.listDaa().execute().body();
        if (res == null) {
            return null;
        }
        return res.getDaaList();

    }

    @Override
    public Daa getDaa(String ticker) throws IOException {
        return service.getDaa(ticker).execute().body();
    }

    @Override
    public List<StructureElement> getDaaStructure(String ticker) throws IOException {
        DaaStructure res = service.getDaaStructure(ticker).execute().body();
        if (res == null) {
            return null;
        }
        return res.getValues();
    }

    @Override
    public DaaChart getDaaPriceHistry(String ticker, long from, long to) throws IOException {
        return service.getDaaPriceHistory(ticker, from, to).execute().body();
    }

    @Override
    public BigDecimal getDaaPrice(String ticker) throws IOException {
        DaaPrice daaPrice = service.getDaaPrice(ticker).execute().body();
        if (daaPrice != null) {
            return new BigDecimal(daaPrice.getPrice());
        }
        return null;
    }

    @Override
    public Balance getUserBalance() throws IOException {
        return service.getUserBalance("USD").execute().body();
    }

    private Response addAuthHeaders(Interceptor.Chain chain) throws IOException {
        if (apiKey == null) {
            return chain.proceed(chain.request());
        } else {
            Request original = chain.request();
            Request request = chain.request().newBuilder()
                    .headers(getSignatureHeaders(original))
                    .build();
            return chain.proceed(request);
        }

    }

    private Headers getSignatureHeaders(Request request) {
        long timestamp = Instant.now().toEpochMilli();
        return Headers.of(
                "ICN-API-KEY", apiKey,
                "ICN-SIGN", generateServerDigest("GET", request.url().encodedPath(), timestamp),
                "ICN-TIMESTAMP", String.valueOf(timestamp));
    }

    private String generateServerDigest(String method, String uri, long timestamp) {
        String checkDigestString = method + uri + timestamp;//  "GET+/v1/daa-list+123123123"; //timestamp in epoch milliseconds

        // hash server composited digest with algorithm and apikeys secret
        SecretKeySpec signingKey = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA512");
        Mac mac;
        try {
            mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.warn("Could not ={}", signingKey.getAlgorithm());
            return null;
        }

        return Base64.getEncoder().encodeToString(mac.doFinal(checkDigestString.getBytes()));
    }
}
