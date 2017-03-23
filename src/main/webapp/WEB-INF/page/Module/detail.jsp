<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Module.moduleName.label' /></label>
                <label class="form-control">${detail.moduleName}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Module.intro.label' /></label>
                <textarea readonly class="form-control">${detail.intro}</textarea>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Module.menuId.label' /></label>
                <label class="form-control">${detail.menu.menuName}</label>
            </div>
            </div>
    </div>
</div>