package net.iconomi.api.model.websocket.send;

public class GetBalance extends WebsocketMessage {
    private final String currency;

    public GetBalance(String currency) {
        super("balance");
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
