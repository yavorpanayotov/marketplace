package com.panayotov.marketplace.journal;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class JournalServiceTest {

    private final BidJournal bidJournal = mock(BidJournal.class);
    private final OfferJournal offerJournal = mock(OfferJournal.class);
    private final OrderJournal orderJournal = mock(OrderJournal.class);

    private final JournalService service = new JournalService(bidJournal, offerJournal, orderJournal);

    @Test
    public void addsBidEntry() {
        Bid bid = mock(Bid.class);

        service.add(bid);

        verify(bidJournal).add(bid);
    }

    @Test
    public void addsOfferEntry() {
        Offer offer = mock(Offer.class);

        service.add(offer);

        verify(offerJournal).add(offer);
    }

    @Test
    public void addsOrderEntry() {
        Order order = mock(Order.class);

        service.add(order);

        verify(orderJournal).add(order);
    }

    @Test
    public void retrievesAllBids() {
        List bids = mock(List.class);

        when(bidJournal.bids()).thenReturn(bids);

        assertThat(service.bids(), is(bids));
    }

    @Test
    public void retrievesBids_ByUserId() {
        List bids = mock(List.class);

        when(bidJournal.bids("buyer1")).thenReturn(bids);

        assertThat(service.bids("buyer1"), is(bids));
    }

    @Test
    public void retrievesAllOffers() {
        List offers = mock(List.class);

        when(offerJournal.offers()).thenReturn(offers);

        assertThat(service.offers(), is(offers));
    }

    @Test
    public void retrievesOffers_ByUserId() {
        List offers = mock(List.class);

        when(offerJournal.offers("seller1")).thenReturn(offers);

        assertThat(service.offers("seller1"), is(offers));
    }

    @Test
    public void removesBid() {
        Bid bid = mock(Bid.class);

        service.remove(bid);

        verify(bidJournal).remove(bid);
    }

    @Test
    public void reducesOffer() {
        Offer offer = mock(Offer.class);

        service.reduce(offer, 3);

        verify(offerJournal).reduce(offer, 3);
    }

    @Test
    public void retrievesOrders_ByBuyerId() {
        List orders = mock(List.class);

        when(orderJournal.ordersForBuyer("buyer1")).thenReturn(orders);

        assertThat(service.ordersForBuyer("buyer1"), is(orders));
    }

    @Test
    public void retrievesOrders_BySellerId() {
        List orders = mock(List.class);

        when(orderJournal.ordersForSeller("seller1")).thenReturn(orders);

        assertThat(service.ordersForSeller("seller1"), is(orders));
    }
}