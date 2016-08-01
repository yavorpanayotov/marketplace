package com.panayotov.marketplace.domain;

import java.math.BigDecimal;

public class Order extends MarketEntity {

    private final String buyerId;
    private final String sellerId;

    public Order(String itemId, String buyerId, String sellerId, int quantity, BigDecimal pricePerUnit) {
        super(itemId, quantity, pricePerUnit);
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }

    public String buyerId() {
        return buyerId;
    }

    public String sellerId() {
        return sellerId;
    }
}
