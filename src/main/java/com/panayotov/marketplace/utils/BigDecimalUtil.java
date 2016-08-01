package com.panayotov.marketplace.utils;

import java.math.BigDecimal;

public interface BigDecimalUtil {

    static BigDecimal asDecimal(double value) {
        return new BigDecimal(value);
    }
}
