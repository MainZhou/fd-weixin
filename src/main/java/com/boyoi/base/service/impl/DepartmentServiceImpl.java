package com.boyoi.base.service.impl;

import com.boyoi.base.dao.DepartmentDao;
import com.boyoi.base.domain.Department;
import com.boyoi.base.service.DepartmentService;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门 service层实现类
 *
 * @author Chenj
 */
@SuppressWarnings("unused")
@Service
public class DepartmentServiceImpl extends BaseRedisServiceImpl<DepartmentDao> implements DepartmentService {

    @Override
    public List<Department> findSubDeptByParentId(String parentId) {
        return dao.findSubDeptByParentId(parentId);
    }

    @Override
    public String getDeptString(Department currDept, List<Department> all) {
        if (null == all)
            all = this.findAll();
        StringBuffer sb = new StringBuffer();
        getParentDepts(sb, currDept, all, false);
        String s = sb.toString();
        if (s.length() > 3)
            return s.substring(0, s.length() - 3);
        return null;
    }

//    @Override
//    protected <T extends BaseDomain> void findByEasyGridRequestAfter(EasyGridRequest easyGridRequest, List<T> resultList) {
//        List<Department> all = this.findAll();
//        for (Department result : (List<Department>)((EasyUiPage)resultList).getRows()){
//            if (null != result.getParentDept()){
//                String deptString = getDeptString(result, all);
//                result.setDeptString(deptString);
//            }
//        }
//
//        // 缓存
//        super.findByEasyGridRequestAfter(easyGridRequest, resultList);
//
//    }



    private void getParentDepts(StringBuffer depts, Department currDept, List<Department> all, boolean first){
        for (Department dept : all){
            if ( dept.getGuid().equals(currDept.getGuid()) ){

                // 判断上级部门是否存在
                if ( null != dept.getParentDept() ){
                    Department curr = getCurr(dept.getParentDept(), all);
                    if (null != curr){
                        getParentDepts(depts, curr, all, false);
                    }
                }

                if (!first){
                    depts.append(dept.getDeptName());
                    depts.append(" > ");
                }
            }
        }
    }

    private Department getCurr(Department currDept, List<Department> all){
        for (Department department : all){
            if (currDept.getGuid().equals(department.getGuid())){
                return department;
            }
        }
        return null;
    }

}