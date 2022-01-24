package net.iconomi.api.model.websocket.send;

public class GetPriceHistory extends WebsocketMessage {
    private final String ticker;
    private final String currency;

    public GetPriceHistory(String ticker, String currency) {
        super("pricehistory");
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
