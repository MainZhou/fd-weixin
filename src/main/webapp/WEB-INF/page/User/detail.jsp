<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='User.department.label' /></label>
                <label class="form-control">${detail.department.deptString}</label>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='User.realName.label' /></label>
                <label class="form-control">${detail.realName}</label>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='User.loginName.label' /></label>
                <label class="form-control">${detail.loginName}</label>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='User.telephone.label' /></label>
                <label class="form-control">${detail.telephone}</label>
            </div>
            <div class="form-group">
                <span class="red">*</span><label><spring:message code='User.userStatus.label' /></label>
                <label class="form-control">${detail.userStatus.itemLabel}</label>
            </div>
            <div class="form-group">
                <label>所属客户</label>
                <label class="form-control">${detail.customInfo.customName}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='User.postName.label' /></label>
                <label class="form-control">${detail.postName}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='User.sex.label' /></label>
                <label class="form-control"><c:if test="${detail.sex == 0}">男</c:if> <c:if test="${detail.sex == 1}">女</c:if></label>
            </div>
            <%--<div class="form-group">--%>
                <%--<label><spring:message code='User.jobNum.label' /></label>--%>
                <%--<label class="form-control">${detail.jobNum}</label>--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
                <%--<label><spring:message code='User.manageArea.label' /></label>--%>
                <%--<label class="form-control">${detail.manageArea}</label>--%>
            <%--</div>--%>
            <div class="form-group">
                <label><spring:message code='User.email.label' /></label>
                <label class="form-control">${detail.email}</label>
            </div>
            <div class="form-group">
                <label><spring:message code='User.entryDate.label' /></label>
                <label class="form-control"><fmt:formatDate value='${detail.entryDate}' pattern="yyyy-MM-dd" /></label>
            </div>
            <%--<div class="form-group">--%>
                <%--<label><spring:message code='User.post.label' /></label>--%>
                <%--<label class="form-control">${detail.post}</label>--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
                <%--<label><spring:message code='User.addr.label' /></label>--%>
                <%--<label class="form-control">${detail.addr}</label>--%>
            <%--</div>--%>

            </div>
    </div>
</div>