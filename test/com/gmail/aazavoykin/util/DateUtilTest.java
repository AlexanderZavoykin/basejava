package com.gmail.aazavoykin.util;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void toDateTest() {
        System.out.println(DateUtil.toDate("fkdfkdf"));
        System.out.println(DateUtil.toDate("13-2019"));
        System.out.println(DateUtil.toDate("01-2019"));
    }
}