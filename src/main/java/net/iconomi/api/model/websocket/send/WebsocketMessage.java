package net.iconomi.api.model.websocket.send;

public abstract class WebsocketMessage {
    private final String type;

    public String getType() {
        return type;
    }

    public WebsocketMessage(String type) {
        this.type = type;
    }
}
