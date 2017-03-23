package com.boyoi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boyoi.base.domain.Department;
import com.boyoi.base.enums.DepartmentStatusEnum;
import org.junit.Test;

/**
 * Fastjson
 * @author Chenj
 */
public class FastJsonTest {
    @Test
    public void enumTest(){
        Department department = new Department();
        department.setDeptName("部门名称");
        department.setDeptStatus(DepartmentStatusEnum.A);
        String s = JSON.toJSONString(department, SerializerFeature.WriteEnumUsingToString);
        System.out.println(s);
    }
}
