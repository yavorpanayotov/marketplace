package com.panayotov.marketplace.journal;

import com.panayotov.marketplace.domain.Offer;

import java.util.ArrayList;
import java.util.List;

import static com.panayotov.marketplace.utils.Immutable.immutable;
import static java.util.Arrays.asList;

public class OfferJournal {

    private final List<Offer> offers = new ArrayList<>();

    public List<Offer> offers(String userId) {
        return immutable(offers, offer -> offer.userId().equals(userId));
    }

    public void add(Offer... offers) {
        this.offers.addAll(asList(offers));
    }

    public List<Offer> offers() {
        return immutable(offers);
    }

    public void remove(Offer offer) {
        offers.remove(offer);
    }

    public void reduce(Offer offer, int quntity) {
        offers.stream()
                .filter(current -> current.equals(offer))
                .findFirst()
                .ifPresent(current -> {
                    if (current.quantity() == quntity) offers.remove(current);
                    else amend(current, quntity);
                });
    }

    private void amend(Offer offer, int quntity) {
        int index = offers.indexOf(offer);

        offers.set(index, new Offer(offer.itemId(), offer.userId(), offer.quantity() - quntity, offer.pricePerUnit()));
    }
}
