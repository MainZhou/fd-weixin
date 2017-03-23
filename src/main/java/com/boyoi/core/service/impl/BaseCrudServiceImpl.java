package com.boyoi.core.service.impl;

import com.boyoi.core.dao.BaseCrudDao;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.BaseCrudService;
import com.boyoi.core.utils.WebUtils;
import com.boyoi.core.utils.poi.ExcelExportRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共服务类的实现方法
 * @param <D> 继承至BaseDao的DAO层接口
 * @author Chenj
 */
public abstract class BaseCrudServiceImpl<D extends BaseCrudDao> implements BaseCrudService {
	
	/**
	 * 通过spring,把传来的DAO层的接口实例化
	 */
	@Autowired
	protected D dao;

    /**
     * 添加实体
     * @param t 实体
     * @param <T> 继承BaseDomain
     * @return int,1表示添加成功 -1代表没执行
     */
	@Override
	public<T extends BaseDomain> int add(T t) {
        // 设置guid
        if (t.getGuid() == null)
            t.setGuid(WebUtils.generateID());
        int result = -1;
        //添加前要执行的，同一事务下
        if(addBefore(t)){
            //添加到数据库中
            result = dao.add(t);
            //添加后要执行的，同一事务下
            addAfter(t, result);
        }
        return result;
	}

    /**
     * service中添加前要操作的
     * 处于同一事务下
     *
     * @param t 实体
     * @return 是否允许添加数据到数据库中
     */
    protected <T extends BaseDomain> boolean addBefore(T t){
        return true;
    }

    /**
     * service中添加后要操作的
     * 处于同一事务下
     *
     * @param t 实体
     * @param result 成功更新的个数
     */
    protected <T extends BaseDomain> void addAfter(T t, int result){}

    /**
     * 批量添加
     * @param list 实体List集合
     * @return 成功个数 -1代表没执行
     */
    @Override
    public <T extends BaseDomain> int addBatch(List<T> list) {
        int result = -1;
        if (addBatchBefore(list)){
            result = dao.addBatch(list);
            addBatchAfter(list, result);
        }
        return result;
    }

    /**
     * service中批量添加前要操作的
     * 处于同一事务下
     *
     * @param list 批量添加的实体
     */
    protected <T extends BaseDomain> boolean addBatchBefore(List<T> list){
        return true;
    }

    /**
     * service中批量添加后要操作的
     * 处于同一事务下
     *
     * @param list 批量添加的实体
     * @param result 批量添加成功的个数
     */
    protected <T extends BaseDomain> void addBatchAfter(List<T> list, int result){}

    /**
     * 更新单个，并删除缓存
     * @param t 更新的实体
     * @param <T> 更新的实体范型
     * @return 成功删除个数 -1代表没执行
     */
    @Override
    public<T extends BaseDomain> int update(T t) {
        int result = -1;

        if (updateBefore(t)){
            // 数据库中更新
            result = dao.update(t);
            // 更新后要执行的，同一事务下
            updateAfter(t, result);
        }
        return result;
    }

    /**
     * 更新前要执行的
     * @param t 实体
     */
    protected <T extends BaseDomain> boolean updateBefore(T t) {
        return true;
    }

    /**
     * 更新后要执行的
     * @param t 实体
     * @param result 成功更新的个数
     */
    protected <T extends BaseDomain> void updateAfter(T t, int result) {}

    /**
     * 更新一个实体,条件更新,不为 null 和 空字符
     * @param t 实体,可能并不是与数据对应
     * @param <T> 实体泛型
     * @return 受影响的个数
     */
    @Override
    public <T extends BaseDomain> int updateByNotEmpty(T t) {
        int result = -1;
        if (updateBefore(t)){
            result = dao.updateByNotEmpty(t);
            updateAfter(t, result);
        }
        return result;
    }

    /**
     * 通过GUID删除
     * @param guid guid
     * @return 成功删除个数
     */
    @Override
	public int del(Serializable guid) {
        int result = -1;
        if (delBefore(guid)){
            result = dao.del(guid);
            delAfter(guid, result);
        }
        return result;
	}

    /**
     * 删除前要操作的
     * @param guid GUID
     * @return 成功删除的个数 -1代表没执行
     */
    protected boolean delBefore(Serializable guid) {
        return true;
    }

    /**
     * 成功删除后要执行的操作
     * @param guid GUID
     * @param result 成功删除的个数
     */
    protected void delAfter(Serializable guid, int result) {}

    @Override
    public int delBatch(List<Serializable> guids) {
        int result = dao.delBatch(guids);
        delBatchAfter(guids, result);
        return result;
    }

    /**
     * 成功删除后要执行的操作
     * @param guids Guid集合
     * @param result 成功删除的个数
     */
    protected void delBatchAfter(List<Serializable> guids, int result) {}

    /**
     * 通过Guid查找
     * @param guid GUID
     * @param <T> 实体范型
     * @return T
     */
    @Override
	public <T extends BaseDomain> T findByGuid(Serializable guid) {
        // 查找前先执行
        T t = findByGuidBefore(guid);

        // 为空, 从数据库中查, 并执行after
        if (null == t){
            t = dao.findById(guid);
            findByGuidAfter(t);
        }

        return t;
	}

    /**
     * 通过GUID查找后要执行的操作
     * @param guid GUID
     */
    protected <T extends BaseDomain> T findByGuidBefore(Serializable guid) {
        return null;
    }

    /**
     * 通过GUID查找后要执行的操作
     * @param t 数据库中的实体
     */
    protected <T extends BaseDomain> void findByGuidAfter(T t) {}

    /**
     * 查出所有数据
     * @param <T> 实体范型
     * @return 实体T集合
     */
	@Override
	public<T extends BaseDomain> List<T> findAll() {
        List<T> result = findAllBefore();

        // 为空, 从数据库中查
        if (null == result){
            result = dao.findAll();
            findAllAfter(result);
        }
        return result;
	}

    /**
     * 查出所有数据后要执行的操作
     * @return 一般为Redis的缓存
     */
    protected <T extends BaseDomain> List<T> findAllBefore() {
        return null;
    }

    /**
     * 查出所有数据后要执行的操作
     * @param result 所有查出来的数据
     */
    protected <T extends BaseDomain> void findAllAfter(List<T> result) {}

    /**
     * 通过easyGridRequest查找
     * @param easyGridRequest EasyGridRequest
     * @param <T>  实体范型
     * @return 实体T集合
     */
    @Override
    public <T  extends BaseDomain> List<T> findByEasyGridRequest(EasyGridRequest easyGridRequest) {
        List<T> resultList = findByEasyGridRequestBefore(easyGridRequest);
        // 为空, 从数据库中查找, 并执行查找后的操作（如保存到Redis）
        if (null == resultList){
            resultList = dao.findByCondition(easyGridRequest);
            findByEasyGridRequestAfter(easyGridRequest, resultList);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> findForExcelExport(ExcelExportRequest exportRequest) {
        return dao.findForExcelExport(exportRequest);
    }

    /**
     * 查出所有数据前要执行的操作
     * @param easyGridRequest easyGrid发来的请求
     * @return 已经存在的集合
     */
    protected <T extends BaseDomain>  List<T> findByEasyGridRequestBefore(EasyGridRequest easyGridRequest) {
        return null;
    }

    /**
     * 查出所有数据后要执行的操作
     * @param easyGridRequest easyGrid发来的请求
     * @param resultList 结果集合
     */
    protected <T extends BaseDomain> void findByEasyGridRequestAfter(EasyGridRequest easyGridRequest, List<T> resultList) {}

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
