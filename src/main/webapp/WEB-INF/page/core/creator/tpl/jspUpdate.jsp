<%@page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:out escapeXml="false" value='<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> '/>
<c:set var="newline" value="<%='\n'%>" />
${newline}
${newline}
<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="<c:out value='\${ctx}'/>/${columnRequest.prefixFirstCharLow}/update" commandName="update" method="post">
                <c:forEach items="${columnRequest.attrs}" var="item">
                <c:if test="${item.value.addEdit == null}">
                <div class="form-group">
                    <form:hidden path="${item.value.javaName}" placeholder="${item.value.cnLabel}" />
                </div>
                </c:if>

                <c:if test="${item.value.addEdit == true}">
                    <div class="form-group">
                    <c:if test="${item.value.required == true}"><span class="red">*</span></c:if>
                    <label><spring:message code='${columnRequest.prefix}.${item.value.javaName}.label' /></label>
                    <c:choose>
                        <c:when test="${item.value.inputType == '日期'}">
                            <form:input path="${item.value.javaName}" cssClass="form-control" placeholder="请输入${item.value.cnLabel}" onClick="WdatePicker()" />
                </c:when>
                        <c:when test="${item.value.inputType == '文本区'}">
                            <form:textarea path="${item.value.javaName}" cssClass="form-control" placeholder="请输入${item.value.cnLabel}" maxlength="${item.value.length}" />
                </c:when>
                        <c:otherwise>
                            <form:input path="${item.value.javaName}" cssClass="form-control" placeholder="请输入${item.value.cnLabel}" maxlength="${item.value.length}" />
                </c:otherwise>
                </c:choose>
                    </div>
                </c:if>
                </c:forEach>
                <div class="form-group">
                    <input type="submit" class="hidden" disabled="disabled" id="submit_button">
                </div>
            </form:form>
        </div>
        <!-- 实体内容结束-->
    </div>
</div>

<script type="text/javascript">
    require(['validator'], function() {
        $('form').validator({
            stopOnError: false,
            fields: {
            <c:forEach items="${columnRequest.attrs}" var="item" varStatus="index">
            <c:if test="${item.value.addEdit}">
            <c:if test="${index.index > 1}">,</c:if>"${item.value.javaName}":"<c:if test='${item.value.required}'>required;</c:if>${item.value.inputTypeString}"
            </c:if>
            </c:forEach>
            }
        }).on('validation', function(e, current){
            //字段验证失败后，添加错误高亮
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        });
    });
</script>

<c:forEach items="${columnRequest.attrs}" var="item">
    <c:if test="${item.value.inputType=='日期'}">
        <script type="text/javascript" src="<c:out value='\${ctx}'/>/static/My97DatePicker/WdatePicker.js"></script>
    </c:if>
</c:forEach>