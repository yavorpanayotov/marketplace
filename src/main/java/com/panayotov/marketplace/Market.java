package com.panayotov.marketplace;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;
import com.panayotov.marketplace.store.MarketData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;

public class Market {

    private final QuotesMatcher quotesMatcher;
    private final OrderGenerator orderGenerator;
    private final MarketData marketData;

    public Market(QuotesMatcher quotesMatcher,
                  OrderGenerator orderGenerator,
                  MarketData marketData) {

        this.quotesMatcher = quotesMatcher;
        this.orderGenerator = orderGenerator;
        this.marketData = marketData;
    }

    public void place(Bid bid) {
        Optional<Offer> matchingOffer = offerFor(bid);

        if (matchingOffer.isPresent()) satisfy(bid, matchingOffer.get());
        else marketData.add(bid);
    }

    public void place(Offer offer) {
        Optional<Bid> matchingBid = bidFor(offer);

        if (matchingBid.isPresent()) satisfy(matchingBid.get(), offer);
        else marketData.add(offer);
    }

    public List<Bid> bids(String userId) {
        return marketData.bids(userId);
    }

    public List<Offer> offers(String userId) {
        return marketData.offers(userId);
    }

    public List<Order> ordersForBuyerId(String buyerId) {
        return marketData.ordersForBuyer(buyerId);
    }

    public List<Order> ordersForSellerId(String sellerId) {
        return marketData.ordersForSeller(sellerId);
    }

    public Optional<BigDecimal> bidPriceFor(String itemId) {
        return marketData.bids().stream()
                .filter(bid -> bid.itemId().equals(itemId))
                .map(Bid::pricePerUnit)
                .max(naturalOrder());
    }

    public Optional<BigDecimal> offerPriceFor(String itemId) {
        return marketData.offers().stream()
                .filter(offer -> offer.itemId().equals(itemId))
                .map(Offer::pricePerUnit)
                .max(reverseOrder());
    }

    private Optional<Offer> offerFor(Bid bid) {
        return marketData.offers().stream()
                .filter(offer -> quotesMatcher.matches(bid, offer))
                .findFirst();
    }

    private Optional<Bid> bidFor(Offer offer) {
        return marketData.bids().stream()
                .filter(bid -> quotesMatcher.matches(bid, offer))
                .findFirst();
    }

    private void satisfy(Bid bid, Offer offer) {
        Order order = orderGenerator.orderFrom(bid, offer);
        marketData.add(order);

        marketData.remove(bid);
        marketData.reduce(offer, bid.quantity());
    }
}
