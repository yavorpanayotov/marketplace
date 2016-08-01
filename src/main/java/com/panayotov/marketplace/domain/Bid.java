package com.panayotov.marketplace.domain;

import java.math.BigDecimal;

public class Bid extends Quote {

    public Bid(String itemId, String userId, int quantity, BigDecimal pricePerUnit) {
        super(itemId, userId, quantity, pricePerUnit);
    }
}
