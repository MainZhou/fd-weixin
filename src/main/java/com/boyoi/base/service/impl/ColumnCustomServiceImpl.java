package com.boyoi.base.service.impl;

import com.boyoi.base.dao.ColumnCustomDao;
import com.boyoi.base.domain.ColumnCustom;
import com.boyoi.base.service.ColumnCustomService;
import com.boyoi.core.service.impl.BaseServiceImpl;
import com.boyoi.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 列配置 service层实现类
 *
 * @author Chenj
 */
@Service
public class ColumnCustomServiceImpl extends BaseServiceImpl<ColumnCustomDao> implements ColumnCustomService {

    @Autowired
    private ColumnCustomDao dao;

    @Caching(evict = {@CacheEvict(value = "columnCustom_guids", keyGenerator = "customColumnKeyGen")})
    @Override
    public int addOrUpdate(ColumnCustom columnCustom) {
        // 查询是否已经存在
        ColumnCustom findDomain = new ColumnCustom();
        findDomain.setUserId(columnCustom.getUserId());
        findDomain.setDomainUrl(columnCustom.getDomainUrl());
        List<ColumnCustom> existDomain = dao.findByDomain(findDomain);

        // 判断是否有数据，有就修改
        if (existDomain.size() > 0){
            // 修改
            columnCustom.setGuid(existDomain.get(0).getGuid());
            // 调用经过缓存的update方法
            return update(columnCustom);
        }else{
            // 添加
            columnCustom.setGuid(WebUtils.generateID());
            return dao.add(columnCustom);
        }
    }

    @Cacheable(value="columnCustom_guids", keyGenerator = "customColumnKeyGen")
    @Override
    public ColumnCustom findCurrColumnCustom(String userId, String domainUrl) {
        return dao.findCurrColumnCustom(userId, domainUrl);
    }

    @Caching(evict = {@CacheEvict(value = "columnCustom_guids", keyGenerator = "customColumnKeyGen")})
    @Override
    public int del(String userId, String domainUrl) {
        return dao.del(userId, domainUrl);
    }

    public int update(ColumnCustom columnCustom){
        return dao.updateNotEmpty(columnCustom);
    }

}