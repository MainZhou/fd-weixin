package com.boyoi.base.dao;
 
import com.boyoi.core.dao.BaseCrudDao;
import com.boyoi.core.domain.BaseDomain;

/**
 * 功能模块 dao层
 *
 * @author Chenj
 */
public interface ModuleDao extends BaseCrudDao {
    /**
     * 添加模块-url 到数据库中
     * @param t 模块对象
     * @return 成功个数
     */
    <T extends BaseDomain> int addModuleUrls(T t);

    /**
     * 通过模块删除对应的URL
     * @param t 模块对象
     * @return 成功个数
     */
    <T extends BaseDomain> int delModuleUrls(T t);
}