<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>
<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="tableAlias.menuName" addPlease="true">
                    <spring:message code='Menu.menuName.label' />
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
    var i18n_guid = '<spring:message code='Menu.guid.label' />';
    var i18n_menuName = '<spring:message code='Menu.menuName.label' />';
    var i18n_parentId = '<spring:message code='Menu.parentId.label' />';
    var i18n_sortNum = '<spring:message code='Menu.sortNum.label' />';
    var i18n_hint = '<spring:message code='Menu.hint.label' />';
    var i18n_iconId = '<spring:message code='Menu.iconId.label' />';
    var i18n_entryUrl = '<spring:message code='Menu.entryUrl.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/menu.js"></script>