package com.panayotov.marketplace;

import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;
import com.panayotov.marketplace.journal.JournalService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MarketTest {

    private final QuotesMatcher quotesMatcher = mock(QuotesMatcher.class);
    private final OrderGenerator orderGenerator = mock(OrderGenerator.class);
    private final JournalService journalService = mock(JournalService.class);

    private final Market market = new Market(quotesMatcher, orderGenerator, journalService);

    private final Bid bid = mock(Bid.class);
    private final Offer offer = mock(Offer.class);
    private final Order order = mock(Order.class);

    @Test
    public void placesBid() {
        market.place(bid);

        verify(journalService).add(bid);
        verify(journalService, never()).add(order);
    }

    @Test
    public void satisfiesBid_IfMatchingOfferFound() {
        when(journalService.offers()).thenReturn(asList(offer));
        when(quotesMatcher.matches(bid, offer)).thenReturn(true);
        when(orderGenerator.orderFrom(bid, offer)).thenReturn(order);

        market.place(bid);

        verify(journalService, never()).add(bid);
        verify(journalService).add(order);
        verify(journalService).reduce(offer, bid.quantity());
    }

    @Test
    public void placesOffer() {
        market.place(offer);

        verify(journalService).add(offer);
        verify(journalService, never()).add(order);
    }

    @Test
    public void satisfiesOffer_IfMatchingBidFound() {
        when(journalService.bids()).thenReturn(asList(bid));
        when(quotesMatcher.matches(bid, offer)).thenReturn(true);
        when(orderGenerator.orderFrom(bid, offer)).thenReturn(order);

        market.place(offer);

        verify(journalService, never()).add(offer);
        verify(journalService).add(order);
        verify(journalService).reduce(offer, bid.quantity());
    }

    @Test
    public void listsBids_ByUserId() {
        List bids = mock(List.class);

        when(journalService.bids("buyer1")).thenReturn(bids);

        assertThat(market.bids("buyer1"), is(bids));
    }

    @Test
    public void listsOffers_ByUserId() {
        List offers = mock(List.class);

        when(journalService.offers("seller1")).thenReturn(offers);

        assertThat(market.offers("seller1"), is(offers));
    }

    @Test
    public void listsOrders_ForBuyerId() {
        List orders = mock(List.class);

        when(journalService.ordersForBuyer("buyer1")).thenReturn(orders);

        assertThat(market.ordersForBuyerId("buyer1"), is(orders));
    }

    @Test
    public void listsOrders_ForSellerId() {
        List orders = mock(List.class);

        when(journalService.ordersForSeller("seller1")).thenReturn(orders);

        assertThat(market.ordersForSellerId("seller1"), is(orders));
    }

    @Test
    public void findsHighestBidPriceForItemId() {
        when(journalService.bids()).thenReturn(asList(
                aBid("item1", asDecimal(1)),
                aBid("item1", asDecimal(3)),
                aBid("item1", asDecimal(2)),
                aBid("item2", asDecimal(5))
        ));

        assertThat(market.bidPriceFor("item1"), is(of(asDecimal(3))));
    }

    @Test
    public void findsNothing_IfNoBidForItemId() {
        assertThat(market.bidPriceFor("item1"), is(empty()));
    }

    @Test
    public void findsLowestOfferPriceForItemId() {
        when(journalService.offers()).thenReturn(asList(
                anOffer("item1", asDecimal(3)),
                anOffer("item1", asDecimal(2)),
                anOffer("item1", asDecimal(4)),
                anOffer("item2", asDecimal(1))
        ));

        assertThat(market.offerPriceFor("item1"), is(of(asDecimal(2))));
    }

    @Test
    public void findsNothing_IfNoOfferForItemId() {
        assertThat(market.offerPriceFor("item1"), is(empty()));
    }

    private Bid aBid(String itemId, BigDecimal pricePerUnit) {
        return new Bid(itemId, "buyer x", 1, pricePerUnit);
    }

    private Offer anOffer(String itemId, BigDecimal pricePerUnit) {
        return new Offer(itemId, "seller x", 1, pricePerUnit);
    }
}