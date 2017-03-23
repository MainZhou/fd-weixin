package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Department;
import com.boyoi.base.enums.DepartmentStatusEnum;
import com.boyoi.base.service.DepartmentService;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 部门 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"department", "department" })
public class DepartmentController extends BaseCrudController<Department,DepartmentService> {

    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param department 部门实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(Department department, Model model) {
        model.addAttribute("allDepartment", service.findAll());
        model.addAttribute("departmentStatus", DepartmentStatusEnum.values());
    }

    /**
    * 进入编辑对象前,需要初始化的一些数据
    * @param model 模型
    */
    @Override
    protected void updateUiInit(Model model) {
        // 去除当前操作的部门
        List<Department> allDepartment = service.findAll();
        Department department = (Department) model.asMap().get("update");
        department.getGuid();
        for (Department dept : allDepartment){
            if (dept.getGuid().equals(department.getGuid())){
                allDepartment.remove(dept);
                break;
            }
        }
        model.addAttribute("allDepartment", allDepartment);
        model.addAttribute("departmentStatus", DepartmentStatusEnum.values());
    }

    @RequestMapping(value="del", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public ResponseResult delByArray(String[] ids) {
        for (String id : ids){
            List<Department> subDeptByParentId = service.findSubDeptByParentId(id);
            if (subDeptByParentId.size() > 0){
                responseResult.setStatus(false);
                responseResult.setMsg("不能删除包含有子部门的部门!");
                return responseResult;
            }
        }
        return super.delByArray(ids);
    }

}