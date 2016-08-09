package com.panayotov.marketplace.store;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;

import java.util.List;

public class MarketData {

    private final BidStore bidStore;
    private final OfferStore offerStore;
    private final OrderStore orderStore;

    public MarketData(BidStore bidStore, OfferStore offerStore, OrderStore orderStore) {
        this.bidStore = bidStore;
        this.offerStore = offerStore;
        this.orderStore = orderStore;
    }

    public void add(Bid bid) {
        bidStore.add(bid);
    }

    public void add(Offer offer) {
        offerStore.add(offer);
    }

    public void add(Order order) {
        orderStore.add(order);
    }

    public List<Bid> bids() {
        return bidStore.bids();
    }

    public List<Bid> bids(String userId) {
        return bidStore.bids(userId);
    }

    public List<Offer> offers() {
        return offerStore.offers();
    }

    public List<Offer> offers(String userId) {
        return offerStore.offers(userId);
    }

    public List<Order> ordersForBuyer(String buyerId) {
        return orderStore.ordersForBuyer(buyerId);
    }

    public List<Order> ordersForSeller(String sellerId) {
        return orderStore.ordersForSeller(sellerId);
    }

    public void remove(Bid bid) {
        bidStore.remove(bid);
    }

    public void reduce(Offer offer, int quantity) {
        offerStore.reduce(offer, quantity);
    }
}
