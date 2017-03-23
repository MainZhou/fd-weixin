package com.boyoi.core.utils;


import com.boyoi.base.domain.Ztree;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class ComparatorZtree implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Ztree ztree1=(Ztree)o1;
        Ztree ztree2=(Ztree)o2;
        return ztree1.getId().compareTo(ztree2.getId());
    }
}
