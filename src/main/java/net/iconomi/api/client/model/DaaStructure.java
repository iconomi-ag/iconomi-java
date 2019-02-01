package net.iconomi.api.client.model;

import java.util.List;

/**
 * Copyright (c) 2019 ICONOMI Inc. All rights reserved.
 *
 * @author Tomaž Cerar
 */
public class DaaStructure {

    private String ticker;

    private List<StructureElement> values;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<StructureElement> getValues() {
        return values;
    }

    public void setValues(List<StructureElement> values) {
        this.values = values;
    }
}
