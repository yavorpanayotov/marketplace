package com.panayotov.marketplace.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static com.panayotov.marketplace.utils.BigDecimalUtil.asDecimal;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BigDecimalUtilTest {

    @Test
    public void createsBigDecimalFromDouble() {
        assertThat(asDecimal(1.1), is(new BigDecimal(1.1)));
    }
}