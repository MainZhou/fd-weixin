package com.boyoi.core.dao;

import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.utils.poi.ExcelExportRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * dao层基类,继承此接口,获得增删改查的基本功能
 * @author Chenj
 */
public interface BaseCrudDao {

    /**
     * 保存一个实体
     * @param t 实体
     * @param <T> 实体泛型
     * @return 受影响的个数
     */
	<T extends BaseDomain> int add(T t);

    /**
     * 批量保存
     * @param list 实体List集合
     * @param <T> 实体泛型
     * @return 受影响的个数
     */
    <T extends BaseDomain> int addBatch(List<T> list);

    /**
     * 删除一个实体
     * @param guid guid
     * @return 受影响的个数
     */
	int del(Serializable guid);

    /**
     * 批量删除实体
     * @param guids guid的List集合
     * @return 受影响的个数
     */
    int delBatch(List<Serializable> guids);

    /**
     * 更新一个实体
     * @param t 实体
     * @param <T> 实体泛型
     * @return 受影响的个数
     */
	<T extends BaseDomain> int update(T t);

    /**
     * 更新一个实体,条件更新,不为 null 和 空字符
     * @param t 实体
     * @param <T> 实体泛型
     * @return 受影响的个数
     */
    <T extends BaseDomain> int updateByNotEmpty(T t);

    /**
     * 通过ID查找实体
     * @param guid guid
     * @param <T> 实体泛型
     * @return 实体
     */
	<T extends BaseDomain> T findById(Serializable guid);

    /**
     * 查找所有实体
     * @param <T> 实体泛型
     * @return 实体集合
     */
	<T extends BaseDomain> List<T> findAll();


    /**
     * 条件查询(EasyGridRequest请求)
     * @param <T> 实体对象
     * @return Page集合
     */
    <T> List<T> findByCondition(EasyGridRequest request);

    /**
     * 通过ExcelExportRequest的请求查找出导出到excel的数据
     * @param exportRequest 请求
     * @return list对应行, map对应列与值之间的关系
     */
    List<Map<String, Object>> findForExcelExport(ExcelExportRequest exportRequest);

    /**
     * 通过实体查找精确查找有多少实体
     * @param t 实体
     * @return 集合
     */
    <T extends BaseDomain> List<T> findByDomain(T t);
}
