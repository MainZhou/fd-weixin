package com.boyoi.core.utils;


import java.util.Comparator;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ComparatorString implements Comparator {

    @SuppressWarnings("unchecked")
	@Override
    public int compare(Object o1, Object o2) {
        Map<String,Object> map1 = (Map<String,Object>)o1 ;
        Map<String,Object> map2 = (Map<String,Object>)o2 ;
        String customId1 = map1.get("customId")+"";
        String customId2 = map2.get("customId")+"";
        String productId1 = map1.get("productId")+"";
        String productId2 = map2.get("productId")+"";
        if(customId1.compareTo(customId2)==0){
            return productId1.compareTo(productId2);
        }else{
            return customId1.compareTo(customId2);
        }
    }
}
