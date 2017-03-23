package ${columnRequest.packageName}.dao;
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
import com.boyoi.core.dao.BaseDao;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import ${columnRequest.packageName}.domain.${columnRequest.prefix};

import java.util.List;

/**
 * ${columnRequest.funcName} dao层
 *
 * @author ${columnRequest.devPerson}
 */
public interface ${columnRequest.prefix}Dao extends BaseDao {

    /**
    * 通过EasyGridRequest条件查找, 分页插件会拦截此方法, 并重新封装结果
    * @param gridRequest EasyGridRequest请求
    * @return EasyUiPage 特殊类, 此类继承了List, 含有内部类Page, Page封装了total,rows
    */
    EasyUiPage findByGridRequest(EasyGridRequest gridRequest);

    /**
    * 添加
    * @param ${columnRequest.prefixFirstCharLow} ${columnRequest.funcName} 实体
    * @return 成功个数
    */
    int add(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow});

    /**
    * 批量添加
    * @param ${columnRequest.prefixFirstCharLow}s  ${columnRequest.funcName} List集合
    * @return 受影响的个数
    */
    int addBatch(List<${columnRequest.prefix}> ${columnRequest.prefixFirstCharLow}s);

    /**
    * 更新实体
    * @param ${columnRequest.prefixFirstCharLow} ${columnRequest.funcName} 实体
    * @return 受影响的个数
    */
    int update(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow});

    /**
    * 条件更新, 不为 null 和 空字符
    * @param ${columnRequest.prefixFirstCharLow} 实体
    * @return 受影响的个数
    */
    int updateNotEmpty(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow});

    /**
    * 批量删除
    * @param guids guid的List集合
    * @return 受影响的个数
    */
    int delBatch(List<String> guids);

    /**
    * 通过Guid查找
    * @param guid guid
    * @return ${columnRequest.funcName} 实体
    */
    ${columnRequest.prefix} findByGuid(String guid);
}