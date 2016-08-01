package com.panayotov.marketplace.domain;

import org.junit.Test;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private final Order order = new Order("item id", "buyer1", "seller1", 1, asDecimal(2));

    @Test
    public void createsOffer() {
        assertThat(order.itemId(), is("item id"));
        assertThat(order.buyerId(), is("buyer1"));
        assertThat(order.sellerId(), is("seller1"));
        assertThat(order.quantity(), is(1));
        assertThat(order.pricePerUnit(), is(asDecimal(2)));
    }
}