package com.panayotov.marketplace.store;

import com.panayotov.marketplace.domain.Order;

import java.util.ArrayList;
import java.util.List;

import static com.panayotov.marketplace.utils.Immutable.immutable;
import static java.util.Arrays.asList;

public class OrderStore {

    private final List<Order> orders = new ArrayList<>();

    public List<Order> ordersForBuyer(String buyerId) {
        return immutable(orders, order -> order.buyerId().equals(buyerId));
    }

    public List<Order> ordersForSeller(String sellerId) {
        return immutable(orders, order -> order.sellerId().equals(sellerId));
    }

    public void add(Order... orders) {
        this.orders.addAll(asList(orders));
    }
}
