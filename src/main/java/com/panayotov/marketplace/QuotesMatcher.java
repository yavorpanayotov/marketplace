package com.panayotov.marketplace;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;

public class QuotesMatcher {

    public boolean matches(Bid bid, Offer offer) {
        return sameItemId(bid, offer)
                && priceIsSatisfied(bid, offer)
                && quantityIsSatisfied(bid, offer);
    }

    private boolean sameItemId(Bid bid, Offer offer) {
        return bid.itemId().equals(offer.itemId());
    }

    private boolean priceIsSatisfied(Bid bid, Offer offer) {
        return bid.pricePerUnit().compareTo(offer.pricePerUnit()) >= 0;
    }

    private boolean quantityIsSatisfied(Bid bid, Offer offer) {
        return bid.quantity() <= offer.quantity();
    }
}
