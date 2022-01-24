package net.iconomi.api.model.websocket.send;

public class GetPrice extends WebsocketMessage {
    private final String ticker;
    private final String currency;

    public GetPrice(String ticker, String currency) {
        super("price");
        this.ticker = ticker;
        this.currency = currency;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCurrency() {
        return currency;
    }
}
