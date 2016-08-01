package com.panayotov.marketplace;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;

import java.math.BigDecimal;

public class OrderGenerator {

    public Order orderFrom(Bid bid, Offer offer) {
        return new Order(
                bid.itemId(),
                bid.userId(),
                offer.userId(),
                bid.quantity(),
                minPricePerUnit(bid, offer));
    }

    private BigDecimal minPricePerUnit(Bid bid, Offer offer) {
        return bid.pricePerUnit().min(offer.pricePerUnit());
    }
}
