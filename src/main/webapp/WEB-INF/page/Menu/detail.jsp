<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='Menu.menuName.label' /></label>
                <label class="form-control">${detail.menuName}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Menu.parentId.label' /></label>
                <label class="form-control">${detail.parentMenu.menuName}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Menu.sortNum.label' /></label>
                <label class="form-control">${detail.sortNum}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Menu.hint.label' /></label>
                <label class="form-control">${detail.hint}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Menu.entryUrl.label' /></label>
                <label class="form-control">${detail.entryUrl}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='Menu.iconId.label' /></label>
                <label class="form-control"><span class="${detail.icon.cssName}"></span></label>
            </div>
            </div>
    </div>
</div>