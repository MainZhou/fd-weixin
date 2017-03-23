package com.boyoi.core.utils;

import java.util.Comparator;

/**
 * 时间比较器
 * @author Chenj
 */
public class TimeComparator implements Comparator<String> {

    public TimeComparator() {
    }

    @Override
    public int compare(String o1, String o2) {
        return DateTimeUtil.compareDate(o1, o2);
    }
}
