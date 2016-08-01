package com.panayotov.marketplace;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.panayotov.marketplace.domain.Bid;
import com.panayotov.marketplace.domain.Offer;
import com.panayotov.marketplace.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(ZohhakRunner.class)
public class OrderGeneratorTest {

    private final OrderGenerator generator = new OrderGenerator();

    @Test
    public void generatesOrder() {
        Bid bid = mock(Bid.class);
        Offer offer = mock(Offer.class);

        when(bid.itemId()).thenReturn("item id");
        when(bid.userId()).thenReturn("buyer1");
        when(offer.userId()).thenReturn("seller1");
        when(bid.quantity()).thenReturn(2);
        when(bid.pricePerUnit()).thenReturn(asDecimal(1.1));
        when(offer.pricePerUnit()).thenReturn(asDecimal(1.0));

        Order order = generator.orderFrom(bid, offer);

        assertThat(order, samePropertyValuesAs(new Order("item id", "buyer1", "seller1", 2, asDecimal(1.0))));
    }

    @TestWith({
            "1, 1, 1",
            "1, 2, 1",
            "2, 1, 1",
    })
    public void pricePerUnit_IsTheMinimum_FromBidAndOffer(BigDecimal bidPricePerUnit,
                                                          BigDecimal offerPricePerUnit,
                                                          BigDecimal exptected) {

        Bid bid = new Bid("item id", "user id", 5, bidPricePerUnit);
        Offer offer = new Offer("item id", "user id", 5, offerPricePerUnit);

        assertThat(generator.orderFrom(bid, offer).pricePerUnit(), is(exptected));
    }
}