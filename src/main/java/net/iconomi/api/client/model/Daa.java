package net.iconomi.api.client.model;

import java.math.BigDecimal;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class Daa {
    private String ticker;
    private String name;
    private String manager;
    private String type;
    private String managementType;
    private BigDecimal managementFee;
    private BigDecimal performanceFee;
    private BigDecimal entryFee;
    private BigDecimal exitFee;
    private BigDecimal aum;
    private String currency;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManagementType() {
        return managementType;
    }

    public void setManagementType(String managementType) {
        this.managementType = managementType;
    }

    public BigDecimal getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(BigDecimal managementFee) {
        this.managementFee = managementFee;
    }

    public BigDecimal getPerformanceFee() {
        return performanceFee;
    }

    public void setPerformanceFee(BigDecimal performanceFee) {
        this.performanceFee = performanceFee;
    }

    public BigDecimal getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(BigDecimal entryFee) {
        this.entryFee = entryFee;
    }

    public BigDecimal getExitFee() {
        return exitFee;
    }

    public void setExitFee(BigDecimal exitFee) {
        this.exitFee = exitFee;
    }

    public BigDecimal getAum() {
        return aum;
    }

    public void setAum(BigDecimal aum) {
        this.aum = aum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
