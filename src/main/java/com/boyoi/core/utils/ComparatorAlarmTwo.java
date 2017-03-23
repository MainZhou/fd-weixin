package com.boyoi.core.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ComparatorAlarmTwo implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Map map1 = (Map) o1;
        Map map2 = (Map) o2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(map1.get("uploadTime").toString());
            date2 = sdf.parse(map2.get("uploadTime").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1.compareTo(date2);
    }
}
