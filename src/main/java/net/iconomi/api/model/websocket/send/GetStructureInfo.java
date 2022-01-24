package net.iconomi.api.model.websocket.send;

public class GetStructureInfo extends WebsocketMessage {
    private final String ticker;

    public GetStructureInfo(String ticker) {
        super("structure-info");
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
