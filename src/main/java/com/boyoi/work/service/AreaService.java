package com.boyoi.work.service;

import com.boyoi.core.service.BaseService;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import com.boyoi.work.domain.Area;

import java.util.List;

/**
 * 区域 service层
 *
 * @author zhoujl
 */
public interface AreaService extends BaseService {

    /**
     * 通过EasyGridRequest条件查找
     *
     * @param gridRequest EasyGridRequest请求
     * @return EasyUiPage内部类Page，Page里封装了total,rows
     */
    @SuppressWarnings("rawtypes")
    EasyUiPage.Page findByGridRequest(EasyGridRequest gridRequest);

    /**
     * 添加
     *
     * @param area 区域 实体
     * @return 成功个数
     */
    int add(Area area);

    /**
     * 批量添加
     *
     * @param areas 区域 List集合
     * @return 受影响的个数
     */
    int addBatch(List<Area> areas);

    /**
     * 更新
     *
     * @param area 区域 实体
     * @return 受影响的个数
     */
    int update(Area area);

    /**
     * 条件更新, 不为 null 和 空字符
     *
     * @param area 区域 实体
     * @return 受影响的个数
     */
    int updateNotEmpty(Area area);

    /**
     * 批量删除
     *
     * @param guids guid的List集合
     * @return 受影响的个数
     */
    int delBatch(List<String> guids);

    /**
     * 通过Guid查找
     *
     * @param guid guid
     * @return 区域 实体
     */
    Area findByGuid(String guid);

    /**
     * 查询所有区域
     *
     * @return 区域集合
     */
    List<Area> findAll();

    /**
     * 根据区域等级和父级ID查询
     */
    List<Area> findByLevel(Area area);

    /**
     * 根据子级查询所有父级
     *
     * @param areaid 子级
     * @return 所有父级
     */
    List<Area> findByChlie(String areaid);
}