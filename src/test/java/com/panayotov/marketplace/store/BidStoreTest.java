package com.panayotov.marketplace.store;

import com.panayotov.marketplace.domain.Bid;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

public class BidStoreTest {

    private final BidStore journal = new BidStore();

    @Rule
    public ExpectedException exception = none();

    @Test
    public void returnsNoBids_IfNonePresent() {
        assertThat(journal.bids("buyer1"), is(emptyList()));
    }

    @Test
    public void listsAllBids() {
        Bid bid1 = aBid("buyer1");
        Bid bid2 = aBid("buyer2");

        journal.add(bid1);
        journal.add(bid2);

        assertThat(journal.bids(), is(asList(bid1, bid2)));
    }

    @Test
    public void listingAllBids_IsViewOnly() {
        exception.expect(UnsupportedOperationException.class);

        journal.bids().add(Mockito.mock(Bid.class));
    }

    @Test
    public void returnsNoBids_ByUserId_IfNonePresent() {
        assertThat(journal.bids("buyer1"), is(emptyList()));
    }

    @Test
    public void listsBids_ByUserId() {
        Bid bid1 = aBid("buyer1");
        Bid bid2 = aBid("buyer1");
        Bid bid3 = aBid("buyer3");

        journal.add(bid1, bid2, bid3);

        assertThat(journal.bids("buyer1"), is(asList(bid1, bid2)));
    }

    @Test
    public void listingBids_ByUserId_IsViewOnly() {
        exception.expect(UnsupportedOperationException.class);

        journal.bids("buyer1").add(Mockito.mock(Bid.class));
    }

    @Test
    public void removesBid() {
        Bid bid = aBid("buyer1");

        journal.add(bid);

        journal.remove(bid);

        assertThat(journal.bids().size(), is(0));
    }

    private Bid aBid(String userId) {
        return new Bid("item id", userId, 1, asDecimal(2));
    }
}