<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>

<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="tableAlias.moduleName" addPlease="true">
                    <spring:message code='Module.moduleName.label' />
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

<!-- dataGrid -->
<div class="box-footer">
    <table class="gridContainer"></table>
</div>

<script type="text/javascript">
    var i18n_guid = '<spring:message code='Module.guid.label' />';
    var i18n_moduleName = '<spring:message code='Module.moduleName.label' />';
    var i18n_intro = '<spring:message code='Module.intro.label' />';
    var i18n_menuId = '<spring:message code='Module.menuId.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/module.js"></script>