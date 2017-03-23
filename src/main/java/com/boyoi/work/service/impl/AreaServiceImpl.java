package com.boyoi.work.service.impl;

import com.boyoi.core.service.impl.BaseServiceImpl;
import com.boyoi.core.utils.WebUtils;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import com.boyoi.work.dao.AreaDao;
import com.boyoi.work.domain.Area;
import com.boyoi.work.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域 service层实现类
 *
 * @author zhoujl
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<AreaDao> implements AreaService {

    @Autowired
    private AreaDao dao;

    @SuppressWarnings("rawtypes")
    @Cacheable(value = "area", keyGenerator = "gridRequestKeyGen", condition = "#gridRequest.mapSize == 0")
    @Override
    public EasyUiPage.Page findByGridRequest(EasyGridRequest gridRequest) {
        EasyUiPage page = dao.findByGridRequest(gridRequest);
        return page.getPage();
    }

    @CacheEvict(value = "area", allEntries = true)
    @Override
    public int add(Area area) {
        area.setGuid(WebUtils.generateID());
        return dao.add(area);
    }

    @CacheEvict(value = "area", allEntries = true)
    @Override
    public int addBatch(List<Area> areas) {
        return dao.addBatch(areas);
    }

    @Caching(evict = {@CacheEvict(value = "area", allEntries = true),
            @CacheEvict(value = "area_guids", key = "#area.guid")})
    @Override
    public int update(Area area) {
        return dao.update(area);
    }

    @Caching(evict = {@CacheEvict(value = "area", allEntries = true),
            @CacheEvict(value = "area_guids", key = "#area.guid")})
    @Override
    public int updateNotEmpty(Area area) {
        return dao.updateNotEmpty(area);
    }

    @Caching(evict = {@CacheEvict(value = "area", allEntries = true),
            @CacheEvict(value = "area_guids", allEntries = true)})
    @Override
    public int delBatch(List<String> guids) {
        return dao.delBatch(guids);
    }

    @Cacheable(value = "area_guids", key = "#guid")
    @Override
    public Area findByGuid(String guid) {
        return dao.findByGuid(guid);
    }

    @Override
    public List<Area> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Area> findByLevel(Area area) {
        return dao.findByLevel(area);
    }

    @Override
    public List<Area> findByChlie(String areaid) {
        Area area = dao.findByGuid(areaid);
        List<Area> parentList = new ArrayList<>();
        parentList = findParentList(area, parentList);
        parentList.add(area);
        return parentList;
    }

    /**
     * 递归查询所有父级
     *
     * @param area       当前子级
     * @param parentList 父级集合
     * @return 父级集合
     */
    private List<Area> findParentList(Area area, List<Area> parentList) {

        if (area != null && !"0".equals(area.getParentid())) {
            String ids = checkId(area);
            if (ids == null) {
                return parentList;
            } else {
                Area parentArea = dao.findByGuid(ids);
                parentList.add(parentArea);
                findParentList(parentArea, parentList);
            }
        }
        return parentList;
    }

    /**
     * 获得当前区域的父级ID
     *
     * @param area 当前区域
     * @return 父级ID
     */
    private String checkId(Area area) {
        String pid = null;
        if (area.getParentid() != null) {
            pid = area.getParentid();
        }
        return pid;
    }
}