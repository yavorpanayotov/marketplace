package com.panayotov.marketplace.domain;

import java.math.BigDecimal;

public abstract class Quote extends MarketEntity {

    private final String userId;

    public Quote(String itemId, String userId, int quantity, BigDecimal pricePerUnit) {
        super(itemId, quantity, pricePerUnit);
        this.userId = userId;
    }

    public String userId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (quantity != quote.quantity) return false;
        if (itemId != null ? !itemId.equals(quote.itemId) : quote.itemId != null) return false;
        if (pricePerUnit != null ? !pricePerUnit.equals(quote.pricePerUnit) : quote.pricePerUnit != null) return false;
        return userId != null ? userId.equals(quote.userId) : quote.userId == null;

    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + quantity;
        result = 31 * result + (pricePerUnit != null ? pricePerUnit.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
