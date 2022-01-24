package net.iconomi.api.model.websocket.send;

public class Subscribe extends WebsocketMessage {
    private final String ticker;
    private final String channel;

    public Subscribe(String ticker, String channel) {
        super("subscribe");
        this.ticker = ticker;
        this.channel = channel;
    }

    public String getTicker() {
        return ticker;
    }

    public String getChannel() {
        return channel;
    }
}
