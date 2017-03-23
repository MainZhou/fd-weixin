package com.boyoi.base.service;
 
import com.boyoi.base.domain.Department;
import com.boyoi.core.service.BaseCrudService;

import java.util.List;

/**
 * 部门 service层
 *
 * @author Chenj
 */
public interface DepartmentService extends BaseCrudService {
    /**
     * 查找父下面有没有子部门
     * @return 子部门
     */
    List<Department> findSubDeptByParentId(String parentId);

    /**
     * 获得上级>下级
     * @param currDept 当前部门
     * @param all 所有部门
     * @return 上级>下级
     */
    String getDeptString(Department currDept, List<Department> all);
}