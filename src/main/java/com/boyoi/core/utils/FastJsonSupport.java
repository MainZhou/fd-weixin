package com.boyoi.core.utils;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * fastjson的抽象类，实现其中的接口
 * 过滤名称，传数组
 *
 * @author Chenj
 */
public class FastJsonSupport implements PropertyFilter {
	
	private String[] name;
	
	public FastJsonSupport(String[] name){
		this.name = name;
	}

	@Override
	public boolean apply(Object object, String name, Object value) {
		for(int i=0;i<this.name.length;i++){
			if(this.name[i].equals(name)){
				return false;
			}
		}
		return true;
	}

}
