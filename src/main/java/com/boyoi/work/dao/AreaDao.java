package com.boyoi.work.dao;
 
import com.boyoi.core.dao.BaseDao;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import com.boyoi.work.domain.Area;

import java.util.List;

/**
 * 区域 dao层
 *
 * @author zhoujl
 */
public interface AreaDao extends BaseDao {

    /**
    * 通过EasyGridRequest条件查找, 分页插件会拦截此方法, 并重新封装结果
    * @param gridRequest EasyGridRequest请求
    * @return EasyUiPage 特殊类, 此类继承了List, 含有内部类Page, Page封装了total,rows
    */
    @SuppressWarnings("rawtypes")
	EasyUiPage findByGridRequest(EasyGridRequest gridRequest);

    /**
    * 添加
    * @param area 区域 实体
    * @return 成功个数
    */
    int add(Area area);

    /**
    * 批量添加
    * @param areas  区域 List集合
    * @return 受影响的个数
    */
    int addBatch(List<Area> areas);

    /**
    * 更新实体
    * @param area 区域 实体
    * @return 受影响的个数
    */
    int update(Area area);

    /**
    * 条件更新, 不为 null 和 空字符
    * @param area 实体
    * @return 受影响的个数
    */
    int updateNotEmpty(Area area);

    /**
    * 批量删除
    * @param guids guid的List集合
    * @return 受影响的个数
    */
    int delBatch(List<String> guids);

    /**
    * 通过Guid查找
    * @param guid guid
    * @return 区域 实体
    */
    Area findByGuid(String guid);
    /**
     * 查询所有区域
     * @return 区域集合
     */
    List<Area> findAll();
    /**
     * 根据区域等级和父级ID查询
     */
    List<Area> findByLevel(Area area);
}