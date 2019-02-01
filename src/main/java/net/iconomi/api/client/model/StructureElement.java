package net.iconomi.api.client.model;

import java.math.BigDecimal;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class StructureElement {

    private BigDecimal rebalancedWeight;
    private BigDecimal targetWeight;
    private String assetTicker;
    private String assetName;
    private String assetCategory;

    public BigDecimal getRebalancedWeight() {
        return rebalancedWeight;
    }

    public void setRebalancedWeight(BigDecimal rebalancedWeight) {
        this.rebalancedWeight = rebalancedWeight;
    }

    public BigDecimal getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(BigDecimal targetWeight) {
        this.targetWeight = targetWeight;
    }

    public String getAssetTicker() {
        return assetTicker;
    }

    public void setAssetTicker(String assetTicker) {
        this.assetTicker = assetTicker;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }
}
