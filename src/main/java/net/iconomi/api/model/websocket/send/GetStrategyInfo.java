package net.iconomi.api.model.websocket.send;

public class GetStrategyInfo extends WebsocketMessage {
    private final String ticker;

    public GetStrategyInfo(String ticker) {
        super("daa");
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
