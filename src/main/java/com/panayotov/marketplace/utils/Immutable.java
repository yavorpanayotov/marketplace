package com.panayotov.marketplace.utils;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public interface Immutable {

    static <T> List<T> immutable(List<T> list, Predicate<T> predicate) {
        return unmodifiableList(list.stream()
                .filter(predicate)
                .collect(toList()));
    }

    static <T> List<T> immutable(List<T> list) {
        return unmodifiableList(list.stream()
                .collect(toList()));
    }
}
