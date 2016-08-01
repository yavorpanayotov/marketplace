package com.panayotov.marketplace.domain;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ZohhakRunner.class)
public class QuoteTest {

    private final Quote quote = new Offer("item id", "user id", 1, asDecimal(2));

    @Test
    public void createsQuote() {
        assertThat(quote.itemId(), is("item id"));
        assertThat(quote.userId(), is("user id"));
        assertThat(quote.quantity(), is(1));
        assertThat(quote.pricePerUnit(), is(asDecimal(2)));
    }

    @TestWith({
            "item1, item1, user1, user1, 5, 5, 6, 6, true",
            "item1, item2, user1, user1, 5, 5, 6, 6, false",
            "item1, item1, user1, user2, 5, 5, 6, 6, false",
            "item1, item1, user1, user1, 5, 4, 6, 6, false",
            "item1, item1, user1, user1, 5, 5, 6, 7, false",
    })
    public void overridesHashcodeAndEquals(String quote1ItemId, String quote2ItemId,
                                           String quote1UserId, String quote2UserId,
                                           int quote1Quantity, int quote2Quantity,
                                           BigDecimal quote1PricePerUnit, BigDecimal quote2PricePerUnit,
                                           boolean expected) {

        Quote quote1 = new Offer(quote1ItemId, quote1UserId, quote1Quantity, quote1PricePerUnit);
        Quote quote2 = new Offer(quote2ItemId, quote2UserId, quote2Quantity, quote2PricePerUnit);

        assertThat(quote1.equals(quote2), is(expected));
        assertThat(quote1.hashCode() == quote2.hashCode(), is(expected));
    }
}