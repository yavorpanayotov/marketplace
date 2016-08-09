package com.panayotov.marketplace.store;

import com.panayotov.marketplace.domain.Bid;

import java.util.ArrayList;
import java.util.List;

import static com.panayotov.marketplace.utils.Immutable.immutable;
import static java.util.Arrays.asList;

public class BidStore {

    private final List<Bid> bids = new ArrayList<>();

    public List<Bid> bids(String userId) {
        return immutable(bids, bid -> bid.userId().equals(userId));
    }

    public List<Bid> bids() {
        return immutable(bids);
    }

    public void add(Bid... bids) {
        this.bids.addAll(asList(bids));
    }

    public void remove(Bid bid) {
        bids.remove(bid);
    }
}
