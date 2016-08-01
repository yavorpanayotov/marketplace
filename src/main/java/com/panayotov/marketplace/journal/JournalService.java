package com.panayotov.marketplace.journal;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;

import java.util.List;

public class JournalService {

    private final BidJournal bidJournal;
    private final OfferJournal offerJournal;
    private final OrderJournal orderJournal;

    public JournalService(BidJournal bidJournal, OfferJournal offerJournal, OrderJournal orderJournal) {
        this.bidJournal = bidJournal;
        this.offerJournal = offerJournal;
        this.orderJournal = orderJournal;
    }

    public void add(Bid bid) {
        bidJournal.add(bid);
    }

    public void add(Offer offer) {
        offerJournal.add(offer);
    }

    public void add(Order order) {
        orderJournal.add(order);
    }

    public List<Bid> bids() {
        return bidJournal.bids();
    }

    public List<Bid> bids(String userId) {
        return bidJournal.bids(userId);
    }

    public List<Offer> offers() {
        return offerJournal.offers();
    }

    public List<Offer> offers(String userId) {
        return offerJournal.offers(userId);
    }

    public List<Order> ordersForBuyer(String buyerId) {
        return orderJournal.ordersForBuyer(buyerId);
    }

    public List<Order> ordersForSeller(String sellerId) {
        return orderJournal.ordersForSeller(sellerId);
    }

    public void remove(Bid bid) {
        bidJournal.remove(bid);
    }

    public void reduce(Offer offer, int quantity) {
        offerJournal.reduce(offer, quantity);
    }
}
