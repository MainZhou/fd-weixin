<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Area.areaname.label' /></label>
                <label class="form-control">${detail.areaname}</label>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Area.parentid.label' /></label>
                <label class="form-control">${detail.parentid}</label>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Area.levels.label' /></label>
                <label class="form-control">${detail.levels}</label>
            </div>
            </div>
    </div>
</div>