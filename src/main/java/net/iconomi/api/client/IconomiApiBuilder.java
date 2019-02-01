package net.iconomi.api.client;

public final class IconomiApiBuilder {
    String apiKey;
    String apiSecret;
    String baseApiUrl = "https://api.iconomi.net";

    public IconomiRestApi buildRestApi() {
        return new IconomiRestApiImpl(this);
    }

    public IconomiApiBuilder setApiKey(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        return this;
    }

    public IconomiApiBuilder setBaseApiUrl(String string) {
        this.baseApiUrl = string;
        return this;
    }


}
