package com.panayotov.marketplace.domain;

import java.math.BigDecimal;

public abstract class MarketEntity {

    protected final String itemId;
    protected final int quantity;
    protected final BigDecimal pricePerUnit;

    public MarketEntity(String itemId, int quantity, BigDecimal pricePerUnit) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String itemId() {
        return itemId;
    }

    public int quantity() {
        return quantity;
    }

    public BigDecimal pricePerUnit() {
        return pricePerUnit;
    }
}
