package com.boyoi.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boyoi.core.utils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.boyoi.base.dao.ZtreeDao;
import com.boyoi.base.domain.Ztree;
import com.boyoi.base.service.ZtreeService;



@SuppressWarnings("unused")
@Service
public class ZtreeServiceImpl implements ZtreeService {

	@Autowired
	private ZtreeDao dao;

	private HashMap<String, Object> parameterMap = new HashMap<String,Object>(0);

	@Override
	public List<Ztree> findAll(String model) {
		parameterMap.put("model", model);
		List<Ztree> list = dao.findAll(parameterMap);
		if(list.isEmpty()){
			list = new ArrayList<Ztree>();
		}
		return list;
	}


	@Override
	public void addZtree(Ztree ztree,String model) {
        if ("department".equals(model)) {
            // 删除缓存
            RedisUtil.delByPattern(DepartmentServiceImpl.class.getName() + "*");
            parameterMap.put("model", model);
            parameterMap.put("ztree", ztree);
        }
		dao.add(parameterMap);
	}

	@Override
	public void updateZtree(Ztree ztree,String model) {
        if ("department".equals(model)) {
            // 删除缓存
            RedisUtil.delByPattern(DepartmentServiceImpl.class.getName() + "*");
            RedisUtil.del(ztree.getId());
        }
        parameterMap.put("model", model);
        parameterMap.put("ztree", ztree);
        dao.update(parameterMap);
	}
	
	@Override
	public void deleteZtree(List<String> array,String model) {
        if ("department".equals(model)){
            RedisUtil.delByPattern(DepartmentServiceImpl.class.getName() + "*");
            for(String guid : array)
                RedisUtil.del(guid);
        }
        parameterMap.put("model", model);
		parameterMap.put("list", array);
		dao.delete(parameterMap);
	}
	@Override
	public boolean checkNode(String name,String model) {
		parameterMap.put("model", model);
		parameterMap.put("name", name);
		Ztree ztree = dao.checkNode(parameterMap);
		if(null != dao.checkNode(parameterMap)){
			return false;
		}
		return true;
	}


	@Override
	public List<Ztree> findByIds(List<String> pids) {
		List<Ztree> list = dao.findByIds(pids);
		if(list.isEmpty()){
			list = new ArrayList<Ztree>();
		}
		return list;
	}
}
