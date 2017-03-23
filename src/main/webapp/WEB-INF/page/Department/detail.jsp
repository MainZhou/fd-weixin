<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Department.deptName.label' /></label>
                <label class="form-control">${detail.deptName}</label>
            </div>
            <%--<div class="form-group">--%>
                <%--<label><spring:message code='Department.deptCode.label' /></label>--%>
                <%--<label class="form-control">${detail.deptCode}</label>--%>
            <%--</div>--%>
            <div class="form-group">
                <label><spring:message code='Department.deptStatus.label' /></label>
                <label class="form-control">${detail.deptStatus.itemLabel}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Department.parentId.label' /></label>
                <label class="form-control">${detail.parentDept.deptName}</label>
            </div>
            <%--<div class="form-group">--%>
                <%--<label><spring:message code='Department.deptAddr.label' /></label>--%>
                <%--<label class="form-control">${detail.deptAddr}</label>--%>
            <%--</div>--%>
            </div>
    </div>
</div>