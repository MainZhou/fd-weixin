<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Role.role_name.label' /></label> <span class="msg-box" for="roleName"></span>
                <label class="form-control">${detail.roleName}</label>
            </div>
            <div class="form-group">
                <input type="submit" class="hidden" disabled="disabled" id="submit_button">
            </div>
            </div>
    </div>
</div>