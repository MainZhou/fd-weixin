package com.boyoi.core.service.impl;

import com.boyoi.core.dao.BaseCrudDao;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.BaseCrudService;
import com.boyoi.core.utils.RedisUtil;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 公共服务类的实现方法
 * 使用此实现类已默认使用Redis缓存
 * @param <D> 继承至BaseDao的DAO层接口
 * @author Chenj
 */
public abstract class BaseRedisServiceImpl<D extends BaseCrudDao> extends BaseCrudServiceImpl<D> implements BaseCrudService {


    //当前具体类的简单类名
    protected final String className = this.getClass().getName();
    // 查找所有的Key
    protected final String classNameAll = className + "_all";

    /**
     * 覆盖添加后的操作, 删除当前类的easyGrid中的缓存
     * @param t 实体
     * @param result 成功更新的个数
     */
    @Override
    protected <T extends BaseDomain> void addAfter(T t, int result) {
        if (result == 1)
            delRedisByEasyGrid();
    }

    /**
     * 批量添加成功后, 删除当前类的easyGrid中的缓存
     * @param list 批量添加的实体
     * @param result 批量添加成功个数
     */
    @Override
    protected <T extends BaseDomain> void addBatchAfter(List<T> list, int result) {
        if (result > 0)
            delRedisByEasyGrid();

    }

    /**
     * 成功更新实体后, 删除当前实体Guid和easyGrid对应的缓存
     * @param t 实体
     * @param result 成功更新的个数
     */
    @Override
    protected <T extends BaseDomain> void updateAfter(T t, int result) {
        if (result == 1){
            delRedisByKeys(t.getGuid());
            delRedisByEasyGrid();
        }
    }

    /**
     * 成功从数据库中删除了数据就删除Guid和easyGrid 对应的缓存
     * @param guid GUID
     * @param result 成功删除的个数
     */
    @Override
    protected void delAfter(Serializable guid, int result) {
        //删除缓存
        if(1 == result){
            delRedisByKeys(guid.toString());
            delRedisByEasyGrid();
        }
    }

    /**
     * 批量删除后从Redis缓存中删除数据
     * @param guids Guid集合
     * @param result 成功删除的个数
     */
    @Override
    protected void delBatchAfter(List<Serializable> guids, int result) {
        if (result > 0){
            delRedisByKeys(guids.toArray(new String[guids.size()]));
            delRedisByEasyGrid();
        }
    }

    /**
     * 通过Guid查找前,先从Redis缓存中获得数据,没有返回null
     * @param guid GUID
     * @return 实体 没有必须返回null
     */
    @Override
    protected <T extends BaseDomain> T findByGuidBefore(Serializable guid) {
        return RedisUtil.get(guid.toString());
    }

    /**
     * 从数据库中成功查找到数据, 添加到缓存中
     * @param t 数据库中的实体
     */
    @Override
    protected <T extends BaseDomain> void findByGuidAfter(T t) {
        RedisUtil.add(t);
    }

    /**
     * 查找所有之前, 先从缓存中获得
     * 不存在, 必须返回null
     * @return 缓存中的实体 或 null
     */
    @Override
    protected <T extends BaseDomain> List<T> findAllBefore() {
        return RedisUtil.get(classNameAll);
    }

    /**
     * 查找所有成功后, 添加到数据库中
     * @param result 所有查出来的数据
     */
    @Override
    protected <T extends BaseDomain> void findAllAfter(List<T> result) {
        RedisUtil.add(classNameAll, result);
    }


    /**
     * 通过easyGrid请求过来,
     * 关键为空, 从缓存中查找
     * 不为空,返回null,从数据库中查找
     * @param easyGridRequest easyGrid发来的请求
     * @return 缓存中的为数据或null
     */
    @Override
    protected <T extends BaseDomain> List<T> findByEasyGridRequestBefore(EasyGridRequest easyGridRequest) {
        //如传来的请求不包含搜索条件，把当前查询，缓存在Redis中
        if(easyGridRequest.getMap().isEmpty()){
            //key为 当前对象简单类名+排序规则+根据什么字段排序+第几页+每页多少行
            String key = className +
                    "-" + easyGridRequest.getOrder() +
                    "-" + easyGridRequest.getSort() +
                    "-" + easyGridRequest.getPage() +
                    "-" + easyGridRequest.getRows();

            //从缓存中获得数据
            return  RedisUtil.get(key);
        }else {
            //包含了搜索条件,返回null, 从数据库中查询!
            return null;
        }
    }

    /**
     * 通过easyGrid请求过来, 从数据库中查得了数据
     * 关键为空, 缓存到Redis
     * 不为空, 什么都不做
     * @param easyGridRequest easyGrid发来的请求
     * @param resultList 结果集合
     */
    @Override
    protected <T extends BaseDomain> void findByEasyGridRequestAfter(EasyGridRequest easyGridRequest, List<T> resultList) {
        if(easyGridRequest.getMap().isEmpty()){
            //key为 当前对象简单类名+排序规则+根据什么字段排序+第几页+每页多少行
            String key = className +
                    "-" + easyGridRequest.getOrder() +
                    "-" + easyGridRequest.getSort() +
                    "-" + easyGridRequest.getPage() +
                    "-" + easyGridRequest.getRows();

            //添加到缓存
            RedisUtil.add(key, resultList);
        }
    }


    /**
     * 批量删除Redis中当前类所有关于easyGrid的缓存
     */
    protected void delRedisByEasyGrid(){
        RedisUtil.delByPattern(className+"*");
    }

    /**
     * 删除Redis中对应的key, 一般为guid
     */
    protected void delRedisByKeys(String... keys){
        RedisUtil.del(keys);
    }

}