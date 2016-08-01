package com.panayotov.marketplace.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static com.panayotov.marketplace.utils.Immutable.immutable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

public class ImmutableTest {

    @Rule
    public ExpectedException exception = none();

    @Test
    public void returnsImmutableList() {
        List<Integer> immutableList = immutable(new ArrayList<>());

        exception.expect(UnsupportedOperationException.class);

        immutableList.add(1);
    }

    @Test
    public void appliersPredicate() {
        List<Integer> list = range(1, 10).boxed().collect(toList());

        List<Integer> filtered = immutable(list, e -> e > 5);

        assertThat(filtered.size(), is(4));
    }

    @Test
    public void returnsImmutableList_WithPredicate() {
        List<Integer> immutableList = immutable(new ArrayList<>(), e -> e > 0);

        exception.expect(UnsupportedOperationException.class);

        immutableList.add(1);
    }
}