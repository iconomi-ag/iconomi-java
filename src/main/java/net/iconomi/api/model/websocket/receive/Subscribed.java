package net.iconomi.api.model.websocket.receive;

public class Subscribed {
    private final String subscriptionDataType;
    private final String subscriptionVersion;
    private final String subscriptionFilter;
    private final Integer subscriptionPeriod;

    public Subscribed() {
        this(null, null, null, null);
    }

    public Subscribed(String subscriptionDataType, String subscriptionVersion, String subscriptionFilter, Integer subscriptionPeriod) {
        this.subscriptionDataType = subscriptionDataType;
        this.subscriptionVersion = subscriptionVersion;
        this.subscriptionFilter = subscriptionFilter;
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public String getSubscriptionDataType() {
        return subscriptionDataType;
    }

    public String getSubscriptionVersion() {
        return subscriptionVersion;
    }

    public String getSubscriptionFilter() {
        return subscriptionFilter;
    }

    public Integer getSubscriptionPeriod() {
        return subscriptionPeriod;
    }
}
