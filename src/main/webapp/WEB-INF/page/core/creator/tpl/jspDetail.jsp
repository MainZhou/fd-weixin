<%@page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:out escapeXml="false" value='<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> '/>
<c:set var="newline" value="<%='\n'%>" />
${newline}
${newline}
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <c:forEach items="${columnRequest.attrs}" var="item">
            <c:if test="${item.value.addEdit != null}">
            <div class="form-group">
                <c:if test="${item.value.required == true}"><span class="red">*</span></c:if><label><spring:message code='${columnRequest.prefix}.${item.value.javaName}.label' /></label>
                <c:choose>
                    <c:when test="${item.value.inputType == '文本区'}">
                        <textarea readonly class="form-control">${fn:escapeXml("${detail.")}${item.value.javaName}}</textarea>
            </c:when>
                    <c:when test="${item.value.inputType == '日期'}">
                        <label class="form-control"><fmt:formatDate value='${fn:escapeXml("${detail.")}${item.value.javaName}}' pattern="yyyy-MM-dd" /></label>
            </c:when>
                    <c:otherwise>
                        <label class="form-control">${fn:escapeXml("${detail.")}${item.value.javaName}}</label>
            </c:otherwise>
                </c:choose>
            </div>
            </c:if>
        </c:forEach>
        </div>
    </div>
</div>