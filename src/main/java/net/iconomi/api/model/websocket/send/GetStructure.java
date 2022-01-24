package net.iconomi.api.model.websocket.send;

public class GetStructure extends WebsocketMessage {
    private final String ticker;

    public GetStructure(String ticker) {
        super("structure");
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
