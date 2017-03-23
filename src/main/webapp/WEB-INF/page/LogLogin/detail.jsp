<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <label><spring:message code='LogLogin.user.label' /></label>
                <label class="form-control">${detail.user.realName}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='LogLogin.loginDate.label' /></label>
                <label class="form-control"><fmt:formatDate value='${detail.loginDate}' pattern="yyyy-MM-dd HH:mm:ss" /></label>
            </div>
            <div class="form-group">
                <label><spring:message code='LogLogin.browser.label' /></label>
                <label class="form-control">${detail.browser}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='LogLogin.ip.label' /></label>
                <label class="form-control">${detail.ip}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='LogLogin.logoutDate.label' /></label>
                <label class="form-control"><fmt:formatDate value='${detail.logoutDate}' pattern="yyyy-MM-dd HH:mm:ss" /></label>
            </div>
            <div class="form-group">
                <label><spring:message code='LogLogin.leaveTime.label' /></label>
                <label class="form-control">${detail.leaveTime}</label>
            </div>
            </div>
    </div>
</div>