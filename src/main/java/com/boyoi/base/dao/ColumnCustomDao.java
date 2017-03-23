package com.boyoi.base.dao;
 
import com.boyoi.core.dao.BaseDao;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import com.boyoi.base.domain.ColumnCustom;

import java.util.List;

/**
 * 列配置 dao层
 *
 * @author Chenj
 */
public interface ColumnCustomDao extends BaseDao {

    /**
    * 通过EasyGridRequest条件查找, 分页插件会拦截此方法, 并重新封装结果
    * @param gridRequest EasyGridRequest请求
    * @return EasyUiPage 特殊类, 此类继承了List, 含有内部类Page, Page封装了total,rows
    */
    @SuppressWarnings("rawtypes")
	EasyUiPage findByGridRequest(EasyGridRequest gridRequest);

    /**
    * 添加
    * @param columnCustom 列配置 实体
    * @return 成功个数
    */
    int add(ColumnCustom columnCustom);

    /**
    * 批量添加
    * @param columnCustoms  列配置 List集合
    * @return 受影响的个数
    */
    int addBatch(List<ColumnCustom> columnCustoms);

    /**
    * 更新实体
    * @param columnCustom 列配置 实体
    * @return 受影响的个数
    */
    int update(ColumnCustom columnCustom);

    /**
    * 条件更新, 不为 null 和 空字符
    * @param columnCustom 实体
    * @return 受影响的个数
    */
    int updateNotEmpty(ColumnCustom columnCustom);

    /**
    * 批量删除
    * @param guids guid的List集合
    * @return 受影响的个数
    */
    int delBatch(List<String> guids);

    /**
     * 清除用户的列习惯
     * @param userId 用户id
     * @param domainUrl 实体url
     * @return 是否成功
     */
    int del(String userId, String domainUrl);

    /**
    * 通过Guid查找
    * @param guid guid
    * @return 列配置 实体
    */
    ColumnCustom findByGuid(String guid);

    /**
     * 查找当前用户某domainUrl下的列习惯
     * @param userId 用户id
     * @param domainUrl 实体url
     * @return ColumnCustom
     */
    ColumnCustom findCurrColumnCustom(String userId, String domainUrl);
}