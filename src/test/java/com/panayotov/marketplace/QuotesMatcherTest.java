package com.panayotov.marketplace;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ZohhakRunner.class)
public class QuotesMatcherTest {

    private final QuotesMatcher matcher = new QuotesMatcher();

    @TestWith({
            "google, google, buyer1, seller1, 1, 1, 5.5, 5.5, true",
            "google, google, buyer1, seller1, 1, 1, 5.6, 5.5, true",
            "google, google, buyer1, seller1, 1, 1, 5.4, 5.5, false",
            "google, google, buyer1, seller1, 1, 2, 5.5, 5.5, true",
            "google, google, buyer1, seller1, 2, 1, 5.5, 5.5, false",
            "google, abcinc, buyer1, seller1, 1, 1, 5.5, 5.5, false",
    })
    public void matchesQuotes(String bidItemId, String offerItemId,
                              String bidUserId, String offerUserId,
                              int bidQuantity, int offerQuantity,
                              BigDecimal bidPricePerUnit, BigDecimal offerPricePerUnit,
                              boolean expected) {

        Bid bid = new Bid(bidItemId, bidUserId, bidQuantity, bidPricePerUnit);
        Offer offer = new Offer(offerItemId, offerUserId, offerQuantity, offerPricePerUnit);

        assertThat(matcher.matches(bid, offer), is(expected));
    }
}