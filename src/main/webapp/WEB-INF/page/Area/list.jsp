<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="tableAlias.areaname" addPlease="true">
                    <spring:message code='Area.areaname.label' />
                </boyoi:makeSearch>
            </div>
            <div class="col-xs-12 col-md-9">
                <ul class="nav nav-pills nav-padding pull-right">
                    <boyoi:makeButton addBtn="true" editBtn="true" detailBtn="true" delBtn="true"/>
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
    var i18n_areaid = '<spring:message code='Area.areaid.label' />';
    var i18n_areaname = '<spring:message code='Area.areaname.label' />';
    var i18n_parentid = '<spring:message code='Area.parentid.label' />';
    var i18n_levels = '<spring:message code='Area.levels.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/area.js"></script>