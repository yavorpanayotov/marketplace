package com.panayotov.marketplace.store;

import com.panayotov.marketplace.domain.Offer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;

public class OfferStoreTest {

    private final OfferStore journal = new OfferStore();

    @Rule
    public ExpectedException exception = none();

    @Test
    public void returnsNoOffers_IfNonePresent() {
        assertThat(journal.offers(), is(emptyList()));
    }

    @Test
    public void listsAllOffers() {
        Offer offer1 = anOffer("seller1");
        Offer offer2 = anOffer("seller2");

        journal.add(offer1);
        journal.add(offer2);

        assertThat(journal.offers(), is(asList(offer1, offer2)));
    }

    @Test
    public void listingAllOffers_IsViewOnly() {
        exception.expect(UnsupportedOperationException.class);

        journal.offers("seller1").add(mock(Offer.class));
    }

    @Test
    public void returnsNoOffers_ByUserId_IfNonePresent() {
        assertThat(journal.offers("seller1"), is(emptyList()));
    }

    @Test
    public void listsOffers_ByUserId() {
        Offer offer1 = anOffer("seller1");
        Offer offer2 = anOffer("seller1");
        Offer offer3 = anOffer("seller3");

        journal.add(offer1);
        journal.add(offer2);
        journal.add(offer3);

        assertThat(journal.offers("seller1"), is(asList(offer1, offer2)));
    }

    @Test
    public void listingOffers_ByUserId_IsViewOnly() {
        exception.expect(UnsupportedOperationException.class);

        journal.offers("seller1").add(mock(Offer.class));
    }

    @Test
    public void removesOffer() {
        Offer offer = anOffer("seller1");

        journal.add(offer);

        journal.remove(offer);

        assertThat(journal.offers().size(), is(0));
    }

    @Test
    public void reducesOffer() {
        Offer offer = anOffer(2);

        journal.add(offer);

        journal.reduce(offer, 1);

        assertThat(journal.offers().get(0), is(anOffer(1)));
    }

    @Test
    public void reducingOfferToZeroRemovesOffer() {
        Offer offer = anOffer(2);

        journal.add(offer);

        journal.reduce(offer, 2);

        assertThat(journal.offers().size(), is(0));
    }

    private Offer anOffer(int quantity) {
        return new Offer("item id", "seller", quantity, asDecimal(2));
    }

    private Offer anOffer(String userId) {
        return new Offer("item id", userId, 1, asDecimal(2));
    }
}