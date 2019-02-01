package net.iconomi.api.client.model;

import java.util.List;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class Balance {
    private String currency;
    private List<BalanceEntry> daaList;
    private List<BalanceEntry> assetList;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<BalanceEntry> getDaaList() {
        return daaList;
    }

    public void setDaaList(List<BalanceEntry> daaList) {
        this.daaList = daaList;
    }

    public List<BalanceEntry> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<BalanceEntry> assetList) {
        this.assetList = assetList;
    }
}
