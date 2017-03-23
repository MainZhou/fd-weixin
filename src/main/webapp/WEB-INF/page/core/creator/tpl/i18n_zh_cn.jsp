<%@page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="newline" value="<%='\n'%>" />
#  ${columnRequest.funcName} - 资源文件-中文  #
${columnRequest.prefix}.func.name=${columnRequest.funcName}管理
# 标签名 #
<c:forEach items="${columnRequest.attrs}" var="item">
${columnRequest.prefix}.${item.value.javaName}.label=${item.value['cnLabel']}
${newline}
</c:forEach>
# 校验国际化 必须 #
<c:forEach items="${columnRequest.attrs}" var="item">
<c:if test="${ true == item.value['required'] }">
${columnRequest.prefix}.validator.${item.value.javaName}.required=${item.value['cnLabel']}必填!
</c:if>
</c:forEach>
# 校验国际化 最大长度 #
<c:forEach items="${columnRequest.attrs}" var="item">
<c:if test="${'' != item.value.length && null != item.value.length }">
${columnRequest.prefix}.validator.${item.value.javaName}.max=${item.value['cnLabel']}的最大长度不能超过${item.value.length}!
</c:if>
</c:forEach>