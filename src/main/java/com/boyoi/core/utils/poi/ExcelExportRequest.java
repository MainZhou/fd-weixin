package com.boyoi.core.utils.poi;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * excel 导出的 请求参数 实体
 *
 * @author Chenj
 */
public class ExcelExportRequest {

    /**
     * 封装请求参数的Map
     */
    private Map<String, String> map = new HashMap<>();

    /**
     * 存储不带%
     */
    private Map<String, String> map2 = new LinkedHashMap<>();

    /**
     * 排序字段名称
     */
    private String sort;

    /**
     * desc asc
     */
    private String order;


    /**
     * 获得带%的key,value
     * @return map
     */
    public Map<String, String> getMap() {
        //获得关键字时。每个value前后都加上%
        if(!map.isEmpty()){
            Set<Map.Entry<String, String>> entries = map.entrySet();
            String value;
            for (Map.Entry<String, String> entry : entries){
                //去掉前后空格
                value = entry.getValue().trim();
                //字符为空,在map中去掉此关键字
                if ("".equals(value))
                    map.remove(entry.getKey());
                    //前后加上%
                else if(value.indexOf(0) != '%' &&
                        value.indexOf(value.length() - 1) != '%')
                    entry.setValue("%"+value+"%");
            }
        }
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 获得不带%的key,value
     * @return map
     */
    public Map<String, String> getMap2() {
        if(!map.isEmpty()){
            Set<Map.Entry<String, String>> entries = map.entrySet();
            String value;
            for (Map.Entry<String, String> entry : entries){
                value = entry.getValue();

                // 去除前面的所有%
                int prefix = 0;
                for (char c : value.toCharArray()){
                    if (c == '%'){
                        prefix ++;
                    }else {
                        break;
                    }
                }

                // 去除后面的所有%
                int prefixLast;
                for (prefixLast = value.length(); prefixLast > 0; prefixLast--){
                    if (value.charAt(prefixLast - 1) != '%'){
                        break;
                    }
                }
                // 加入到map2中
                map2.put(entry.getKey(), value.substring(prefix,prefixLast));

            }
        }
        return map2;
    }
}
