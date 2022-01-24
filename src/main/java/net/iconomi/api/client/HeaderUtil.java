package net.iconomi.api.client;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class HeaderUtil {

    public static Response addAuthHeaders(Interceptor.Chain chain, String apiKey, String apiSecret) throws IOException {
        if (apiKey == null) {
            return chain.proceed(chain.request());
        } else {
            Request original = chain.request();
            Request request = chain.request().newBuilder()
                    .headers(getSignatureHeaders(original, apiKey, apiSecret, original.headers()))
                    .build();
            return chain.proceed(request);
        }
    }

    public static Headers getSignatureHeaders(Request request, String apiKey, String apiSecret, Headers originalHeaders) throws IOException {
        long timestamp = Instant.now().toEpochMilli();

        String body = "";
        if (request.body() != null) {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            body = buffer.readUtf8();
        }

        String[] headers = new String[originalHeaders.size() * 2 + 6];

        for (int i = 0; i < originalHeaders.size(); i++) {
            headers[i * 2] = originalHeaders.name(i);
            headers[i * 2 + 1] = originalHeaders.value(i);
        }

        headers[headers.length - 6] = "ICN-API-KEY";
        headers[headers.length - 5] = apiKey;
        headers[headers.length - 4] = "ICN-SIGN";
        headers[headers.length - 3] = generateServerDigest(request.method(), request.url().encodedPath(), timestamp, body, apiSecret);
        headers[headers.length - 2] = "ICN-TIMESTAMP";
        headers[headers.length - 1] = String.valueOf(timestamp);

        return Headers.of(headers);
    }

    /**
     * @param method    Http method (POST, GET, ...)
     * @param uri       Part of the url starting with /v1/ without query parameter
     * @param timestamp The timestamp of the request (same as ICN-TIMESTAMP header parameter)
     * @param body      The body of the request or empty string
     * @param apiSecret The api secret
     * @return The request signature
     */
    static String generateServerDigest(String method, String uri, long timestamp, String body, String apiSecret) {
        //return timestamp + request.getMethodValue() + uri + body;
        try {
            String toSign = timestamp + method.toUpperCase() + uri + body;
            SecretKeySpec signingKey = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA512");
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            return Base64.getEncoder().encodeToString(mac.doFinal(toSign.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
