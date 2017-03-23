package com.boyoi.base.dao;
 
import com.boyoi.base.domain.Department;
import com.boyoi.core.dao.BaseCrudDao;

import java.util.List;

/**
 * 部门 dao层
 *
 * @author Chenj
 */
public interface DepartmentDao extends BaseCrudDao {
    /**
     * 查找父下面有没有子部门
     * @return 子部门
     */
    List<Department> findSubDeptByParentId(String parentId);
}