package net.iconomi.api.client.model;

import java.util.List;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class DaaChart {

    private String ticker;
    private String currency;
    private long from;
    private long to;
    private String granulation;
    private List<DaaChartPoint> values;

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

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getGranulation() {
        return granulation;
    }

    public void setGranulation(String granulation) {
        this.granulation = granulation;
    }

    public List<DaaChartPoint> getValues() {
        return values;
    }

    public void setValues(List<DaaChartPoint> values) {
        this.values = values;
    }
}
