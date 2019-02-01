package net.iconomi.api.client.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class BalanceEntry {
    private final String name;
    private final String ticker;
    private final BigDecimal balance;
    private final BigDecimal value;

    @JsonCreator
    public BalanceEntry(@JsonProperty("name") String name,
                        @JsonProperty("ticker") String ticker,
                        @JsonProperty("balance") BigDecimal balance,
                        @JsonProperty("value") BigDecimal value) {
        this.name = name;
        this.ticker = ticker;
        this.balance = balance;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getValue() {
        return value;
    }
}
