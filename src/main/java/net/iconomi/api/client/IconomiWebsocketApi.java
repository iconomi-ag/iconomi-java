package net.iconomi.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kotlin.Pair;
import net.iconomi.api.model.websocket.send.*;
import net.iconomi.api.model.websocket.receive.Subscribed;
import net.iconomi.generated.model.*;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class IconomiWebsocketApi {
    private static final Consumer defaultConsumer = o -> {
    };

    private final ObjectMapper objectMapper;
    private final String baseURL;
    private final String apiKey;
    private final String apiSecret;

    private Consumer<Chart> onChartReceived = defaultConsumer;
    private Consumer<Structure> onStructureReceived = defaultConsumer;
    private Consumer<FittingInfo> onFittingInfoReceived = defaultConsumer;
    private Consumer<AssetInfo> onAssetInfoReceived = defaultConsumer;
    private Consumer<Strategy> onStrategyReceived = defaultConsumer;
    private Consumer<Strategy[]> onStrategyListReceived = defaultConsumer;
    private Consumer<Balance> onBalanceReceived = defaultConsumer;
    private Consumer<Ticker> onTickerReceived = defaultConsumer;
    private Consumer<Subscribed> onSubscribedReceived = defaultConsumer;
    private Consumer<String> onUnknownMessageReceived = defaultConsumer;

    private BiConsumer<Throwable, Response> onFailure = (t, r) -> {
        t.printStackTrace();
    };
    private Runnable onOpen = () -> {
    };
    private BiConsumer<Integer, String> onClosing = (i, s) -> {
    };
    private BiConsumer<Integer, String> onClosed = (i, s) -> {
    };
    private WebSocket webSocket;
    private OkHttpClient client;

    public IconomiWebsocketApi(String baseURL,
                               String apiKey,
                               String apiSecret) {
        this.baseURL = baseURL + "/v1/ws";
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public void open() throws IOException {
        client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(baseURL)
                .build();
        Headers signatureHeaders = HeaderUtil.getSignatureHeaders(request, apiKey, apiSecret, Headers.of());
        Request.Builder properRequest = request.newBuilder();
        for (Pair<? extends String, ? extends String> signatureHeader : signatureHeaders) {
            properRequest.addHeader(signatureHeader.getFirst(), signatureHeader.getSecond());
        }

        webSocket = client.newWebSocket(
                properRequest.build(),
                new WebSocketListener() {
                    @Override
                    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        onClosed.accept(code, reason);
                    }

                    @Override
                    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        onClosing.accept(code, reason);
                    }

                    @Override
                    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                        onFailure.accept(t, response);
                    }

                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                        processMessage(text);
                    }

                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                        processMessage(bytes.toString());
                    }

                    @Override
                    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                        onOpen.run();
                    }
                }
        );
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "");
            webSocket = null;
        }
        if (client != null) {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
            client = null;
        }
    }

    public void registerPriceHistoryReceived(Consumer<Chart> onChartReceived) {
        Objects.requireNonNull(onChartReceived);
        this.onChartReceived = onChartReceived;
    }

    public void registerOnStructureReceived(Consumer<Structure> onStructureReceived) {
        Objects.requireNonNull(onStructureReceived);
        this.onStructureReceived = onStructureReceived;
    }

    public void registerOnFittingInfoReceived(Consumer<FittingInfo> onFittingInfoReceived) {
        Objects.requireNonNull(onFittingInfoReceived);
        this.onFittingInfoReceived = onFittingInfoReceived;
    }

    public void registerOnUnknownMessageReceived(Consumer<String> onUnknownMessageReceived) {
        Objects.requireNonNull(onUnknownMessageReceived);
        this.onUnknownMessageReceived = onUnknownMessageReceived;
    }

    public void registerOnAssetInfoReceived(Consumer<AssetInfo> onAssetInfoReceived) {
        Objects.requireNonNull(onAssetInfoReceived);
        this.onAssetInfoReceived = onAssetInfoReceived;
    }

    public void registerOnStrategyReceived(Consumer<Strategy> onStrategyReceived) {
        Objects.requireNonNull(onStrategyReceived);
        this.onStrategyReceived = onStrategyReceived;
    }

    public void registerOnStrategyListReceived(Consumer<Strategy[]> onStrategyListReceived) {
        Objects.requireNonNull(onStrategyListReceived);
        this.onStrategyListReceived = onStrategyListReceived;
    }

    public void registerOnBalanceReceived(Consumer<Balance> onBalanceReceived) {
        Objects.requireNonNull(onBalanceReceived);
        this.onBalanceReceived = onBalanceReceived;
    }

    public void registerOnTickerReceived(Consumer<Ticker> onTickerReceived) {
        Objects.requireNonNull(onTickerReceived);
        this.onTickerReceived = onTickerReceived;
    }

    public void registerOnSubscribed(Consumer<Subscribed> onSubscribed) {
        Objects.requireNonNull(onSubscribed);
        this.onSubscribedReceived = onSubscribed;
    }

    public void registerOnFailure(BiConsumer<Throwable, Response> onFailure) {
        Objects.requireNonNull(onFailure);
        this.onFailure = onFailure;
    }

    public void registerOnOpen(Runnable onOpen) {
        Objects.requireNonNull(onOpen);
        this.onOpen = onOpen;
    }

    public void registerOnClosing(BiConsumer<Integer, String> onClosing) {
        Objects.requireNonNull(onClosing);
        this.onClosing = onClosing;
    }

    public void registerOnClosed(BiConsumer<Integer, String> onClosed) {
        Objects.requireNonNull(onClosed);
        this.onClosed = onClosed;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void subscribeToPrices(String ticker) {
        send(new Subscribe(ticker, "daa/price"));
    }

    public void getPriceHistory(String ticker, String currency) {
        send(new GetPriceHistory(ticker, currency));
    }

    public void getStrategyStructure(String ticker) {
        send(new GetStructure(ticker));
    }

    public void getStrategyFittingInfo(String ticker) {
        send(new GetStructureInfo(ticker));
    }

    public void getStrategyinfo(String ticker) {
        send(new GetStrategyInfo(ticker));
    }

    public void getStrategyList() {
        send(new GetStrategyList());
    }

    public void getPrice(String ticker, String currency) {
        send(new GetPrice(ticker, currency));
    }

    public void getBalance(String currency) {
        send(new GetBalance(currency));
    }

    public void submitStructure(StructureSubmit structureSubmit) {
        HashMap body = objectMapper.convertValue(structureSubmit, HashMap.class);
        body.put("type", "submit-structure");
        send(body);
    }

    private void send(Object object) {
        try {
            String message = objectMapper.writeValueAsString(object);
            webSocket.send(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode node = jsonNode.get("type");
            if (node != null) {
                String type = node.asText();
                switch (type) {
                    case "chart":
                        Chart chart = objectMapper.convertValue(jsonNode.get("chart"), Chart.class);
                        onChartReceived.accept(chart);
                        break;
                    case "structure":
                        Structure structure = objectMapper.convertValue(jsonNode.get("structure"), Structure.class);
                        onStructureReceived.accept(structure);
                        break;
                    case "fitting-info":
                        FittingInfo fittingInfo = objectMapper.convertValue(jsonNode.get("fitting-info"), FittingInfo.class);
                        onFittingInfoReceived.accept(fittingInfo);
                        break;
                    case "asset-info":
                        if (jsonNode.get("asset-info").size() <= 2) {
                            AssetInfo assetInfo = objectMapper.convertValue(jsonNode.get("asset-info"), AssetInfo.class);
                            onAssetInfoReceived.accept(assetInfo);
                        } else {
                            Strategy strategy = objectMapper.convertValue(jsonNode.get("asset-info"), Strategy.class);
                            onStrategyReceived.accept(strategy);
                        }
                        break;
                    case "strategy-list":
                        Strategy[] strategies = objectMapper.convertValue(jsonNode.get("strategy-list"), Strategy[].class);
                        onStrategyListReceived.accept(strategies);
                        break;
                    case "balance":
                        Balance balance = objectMapper.convertValue(jsonNode.get("balance"), Balance.class);
                        onBalanceReceived.accept(balance);
                        break;
                    case "ticker-data":
                        Ticker ticker = objectMapper.convertValue(jsonNode.get("ticker-data"), Ticker.class);
                        onTickerReceived.accept(ticker);
                        break;
                    case "subscribed-message":
                        Subscribed subscribed = objectMapper.convertValue(jsonNode.get("subscribed-message"), Subscribed.class);
                        onSubscribedReceived.accept(subscribed);
                        break;
                    default:
                        onUnknownMessageReceived.accept(message);
                }
            } else if (jsonNode.get("dataType") == null || !"pong".equals(jsonNode.get("dataType").textValue())) {
                onUnknownMessageReceived.accept(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
