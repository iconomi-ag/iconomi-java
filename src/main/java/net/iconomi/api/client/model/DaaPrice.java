package net.iconomi.api.client.model;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class DaaPrice {

    private String ticker;
    private String currency;
    private String price;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
