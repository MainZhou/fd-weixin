package com.boyoi.base.service;

import java.util.List;

import com.boyoi.base.domain.Ztree;

public interface ZtreeService {

	List<Ztree> findAll(String model);

	void addZtree(Ztree ztree,String model);
	void updateZtree(Ztree ztree,String model);

	void deleteZtree(List<String> array,String model);
	boolean checkNode(String name,String model);
	/**
	 * 根据父级ID查询数据
	 * @param pids 父级ID集合
	 * @return
	 */
	List<Ztree> findByIds(List<String> pids);
}
