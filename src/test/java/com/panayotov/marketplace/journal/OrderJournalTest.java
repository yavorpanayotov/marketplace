package com.panayotov.marketplace.journal;

import com.panayotov.marketplace.domain.Order;
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

public class OrderJournalTest {

    private final OrderJournal journal = new OrderJournal();

    @Rule
    public ExpectedException exception = none();

    @Test
    public void returnsNoOrders_ByBuyerId_IfNonePresent() {
        assertThat(journal.ordersForBuyer("buyer1"), is(emptyList()));
    }

    @Test
    public void listsOrders_ByBuyerId() {
        Order order1 = orderWithBuyer("buyer1");
        Order order2 = orderWithBuyer("buyer1");
        Order order3 = orderWithBuyer("buyer3");

        journal.add(order1);
        journal.add(order2);
        journal.add(order3);

        assertThat(journal.ordersForBuyer("buyer1"), is(asList(order1, order2)));
    }

    @Test
    public void listingOrders_ByBuyerId_IsViewOnly() {
        exception.expect(UnsupportedOperationException.class);

        journal.ordersForBuyer("buyer1").add(mock(Order.class));
    }

    @Test
    public void returnsNoOrders_BySellerId_IfNonePresent() {
        assertThat(journal.ordersForSeller("seller1"), is(emptyList()));
    }

    @Test
    public void listsOrders_BySellerId() {
        Order order1 = orderWithSeller("seller1");
        Order order2 = orderWithSeller("seller1");
        Order order3 = orderWithSeller("seller3");

        journal.add(order1, order2, order3);

        assertThat(journal.ordersForSeller("seller1"), is(asList(order1, order2)));
    }

    @Test
    public void listingOrders_BySellerId_IsViewOnly() {
        exception.expect(UnsupportedOperationException.class);

        journal.ordersForSeller("seller1").add(mock(Order.class));
    }

    private Order orderWithBuyer(String buyerId) {
        return new Order("item id", buyerId, "seller x", 1, asDecimal(2));
    }

    private Order orderWithSeller(String sellerId) {
        return new Order("item id", "buyer x", sellerId, 1, asDecimal(2));
    }
}