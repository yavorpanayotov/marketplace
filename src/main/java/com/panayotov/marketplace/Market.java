package com.panayotov.marketplace;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;
import com.panayotov.marketplace.journal.JournalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;

public class Market {

    private final QuotesMatcher quotesMatcher;
    private final OrderGenerator orderGenerator;
    private final JournalService journalService;

    public Market(QuotesMatcher quotesMatcher,
                  OrderGenerator orderGenerator,
                  JournalService journalService) {

        this.quotesMatcher = quotesMatcher;
        this.orderGenerator = orderGenerator;
        this.journalService = journalService;
    }

    public void place(Bid bid) {
        Optional<Offer> matchingOffer = offerFor(bid);

        if (matchingOffer.isPresent()) satisfy(bid, matchingOffer.get());
        else journalService.add(bid);
    }

    public void place(Offer offer) {
        Optional<Bid> matchingBid = bidFor(offer);

        if (matchingBid.isPresent()) satisfy(matchingBid.get(), offer);
        else journalService.add(offer);
    }

    public List<Bid> bids(String userId) {
        return journalService.bids(userId);
    }

    public List<Offer> offers(String userId) {
        return journalService.offers(userId);
    }

    public List<Order> ordersForBuyerId(String buyerId) {
        return journalService.ordersForBuyer(buyerId);
    }

    public List<Order> ordersForSellerId(String sellerId) {
        return journalService.ordersForSeller(sellerId);
    }

    public Optional<BigDecimal> bidPriceFor(String itemId) {
        return journalService.bids().stream()
                .filter(bid -> bid.itemId().equals(itemId))
                .map(Bid::pricePerUnit)
                .max(naturalOrder());
    }

    public Optional<BigDecimal> offerPriceFor(String itemId) {
        return journalService.offers().stream()
                .filter(offer -> offer.itemId().equals(itemId))
                .map(Offer::pricePerUnit)
                .max(reverseOrder());
    }

    private Optional<Offer> offerFor(Bid bid) {
        return journalService.offers().stream()
                .filter(offer -> quotesMatcher.matches(bid, offer))
                .findFirst();
    }

    private Optional<Bid> bidFor(Offer offer) {
        return journalService.bids().stream()
                .filter(bid -> quotesMatcher.matches(bid, offer))
                .findFirst();
    }

    private void satisfy(Bid bid, Offer offer) {
        Order order = orderGenerator.orderFrom(bid, offer);
        journalService.add(order);

        journalService.remove(bid);
        journalService.reduce(offer, bid.quantity());
    }
}
