package com.boyoi.core.service.impl;

import com.boyoi.core.dao.BaseDao;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 公共服务类的实现方法
 * @param <D> 继承至BaseDao的DAO层接口
 * @author Chenj
 */
public abstract class BaseServiceImpl<D extends BaseDao> implements BaseService {
	
	/**
	 * 通过spring,把传来的DAO层的接口实例化
	 */
	@Autowired
	protected D dao;

    /**
     * 通过实体查找, 不为空才查找
     * @param t 实体
     * @return 实体集合
     */
    @Override
    public <T extends BaseDomain> List<T> findByDomain(T t) {
        return dao.findByDomain(t);
    }

}
