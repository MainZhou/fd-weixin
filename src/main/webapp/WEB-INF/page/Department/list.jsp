<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>
<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="tableAlias.deptName" addPlease="true">
                    <spring:message code="Department.deptName.label"/>
                </boyoi:makeSearch>
            </div>
            <div class="col-xs-12 col-md-2">
                <boyoi:makeAdd/>
            </div>
            <div class="col-xs-12 col-md-7">
                <ul class="nav nav-pills nav-padding pull-right">
                    <boyoi:makeButton addBtn="false" editBtn="true" detailBtn="true" delBtn="true"/>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- dataGrid and zTree -->
<div class="box-footer">
    <div class="zTreeContainer">
        <div class="zTreeBackground">
            <div class="zTreeContent">
                <ul class="ztree" data-search-column="guid" id="department"></ul>
            </div>
        </div>
    </div>
    <div class="slider">
        <div class="sliderLeft"></div>
    </div>
    <div class="gridContainerWithTree">
        <table class="gridContainer"></table>
    </div>
</div>
<%--<div id="rMenu">--%>
	<%--<ul>--%>
		<%--<li id="m_add">增加节点</li>--%>
		<%--<li id="m_up">编辑节点</li>--%>
		<%--<li id="m_del">删除节点</li>--%>
	<%--</ul>--%>
<%--</div>--%>
<script type="text/javascript">
    var i18n_guid = '<spring:message code='Department.guid.label' />';
    var i18n_deptName = '<spring:message code='Department.deptName.label' />';
    var i18n_deptCode = '<spring:message code='Department.deptCode.label' htmlEscape="true" />';
    var i18n_deptStatus = '<spring:message code='Department.deptStatus.label' />';
    var i18n_parentId = '<spring:message code='Department.parentId.label' />';
    var i18n_deptAddr = '<spring:message code='Department.deptAddr.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/department.js"></script>