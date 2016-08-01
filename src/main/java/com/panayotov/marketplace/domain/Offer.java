package com.panayotov.marketplace.domain;

import java.math.BigDecimal;

public class Offer extends Quote {

    public Offer(String itemId, String userId, int quantity, BigDecimal pricePerUnit) {
        super(itemId, userId, quantity, pricePerUnit);
    }
}
