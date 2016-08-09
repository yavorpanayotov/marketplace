package com.panayotov.marketplace.store;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MarketDataTest {

    private final BidStore bidStore = mock(BidStore.class);
    private final OfferStore offerStore = mock(OfferStore.class);
    private final OrderStore orderStore = mock(OrderStore.class);

    private final MarketData service = new MarketData(bidStore, offerStore, orderStore);

    @Test
    public void addsBidEntry() {
        Bid bid = mock(Bid.class);

        service.add(bid);

        verify(bidStore).add(bid);
    }

    @Test
    public void addsOfferEntry() {
        Offer offer = mock(Offer.class);

        service.add(offer);

        verify(offerStore).add(offer);
    }

    @Test
    public void addsOrderEntry() {
        Order order = mock(Order.class);

        service.add(order);

        verify(orderStore).add(order);
    }

    @Test
    public void retrievesAllBids() {
        List bids = mock(List.class);

        when(bidStore.bids()).thenReturn(bids);

        assertThat(service.bids(), is(bids));
    }

    @Test
    public void retrievesBids_ByUserId() {
        List bids = mock(List.class);

        when(bidStore.bids("buyer1")).thenReturn(bids);

        assertThat(service.bids("buyer1"), is(bids));
    }

    @Test
    public void retrievesAllOffers() {
        List offers = mock(List.class);

        when(offerStore.offers()).thenReturn(offers);

        assertThat(service.offers(), is(offers));
    }

    @Test
    public void retrievesOffers_ByUserId() {
        List offers = mock(List.class);

        when(offerStore.offers("seller1")).thenReturn(offers);

        assertThat(service.offers("seller1"), is(offers));
    }

    @Test
    public void removesBid() {
        Bid bid = mock(Bid.class);

        service.remove(bid);

        verify(bidStore).remove(bid);
    }

    @Test
    public void reducesOffer() {
        Offer offer = mock(Offer.class);

        service.reduce(offer, 3);

        verify(offerStore).reduce(offer, 3);
    }

    @Test
    public void retrievesOrders_ByBuyerId() {
        List orders = mock(List.class);

        when(orderStore.ordersForBuyer("buyer1")).thenReturn(orders);

        assertThat(service.ordersForBuyer("buyer1"), is(orders));
    }

    @Test
    public void retrievesOrders_BySellerId() {
        List orders = mock(List.class);

        when(orderStore.ordersForSeller("seller1")).thenReturn(orders);

        assertThat(service.ordersForSeller("seller1"), is(orders));
    }
}