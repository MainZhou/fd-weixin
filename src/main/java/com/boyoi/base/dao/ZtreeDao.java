package com.boyoi.base.dao;

import java.util.HashMap;
import java.util.List;


import com.boyoi.base.domain.Ztree;

public interface ZtreeDao  {
	/*
	 * 根据模块查询树
	 */
	List<Ztree> findAll(HashMap<String,Object> map);
	/**
	 * 根据父级ID查询数据
	 * @param pids 父级ID集合
	 * @return
	 */
	List<Ztree> findByIds(List<String> pids);
	/*
	 * 根据模块添加树
	 */
	void add(HashMap<String,Object> map);
	/*
	 * 根据模块修改树
	 */
	void update(HashMap<String,Object> map);
	/*
	 * 根据模块删除树
	 */
	void delete(HashMap<String,Object> map);
	/*
	 * 根据模块验证树名字是否存在
	 */
	Ztree checkNode(HashMap<String,Object> map);
}
