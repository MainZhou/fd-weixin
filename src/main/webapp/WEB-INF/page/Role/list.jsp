<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>

<div class="box-body">
    <div class="container-fluid">
        <!-- 功能按钮 -->
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="roleName" addPlease="true">角色名称</boyoi:makeSearch>
            </div>
            <div class="col-xs-12 col-md-2">
                <boyoi:makeAdd/>
            </div>
            <div class="col-xs-12 col-md-7">
                <ul class="nav nav-pills nav-padding pull-right">
                    <boyoi:makeButton addBtn="false" editBtn="true" detailBtn="true" delBtn="true"/>
                    <li><a href="javascript:" id="granAuth" title="授权"><span class="glyphicon glyphicon-sunglasses"></span></a></li>
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
    var i18n_guid = '<spring:message code='Role.guid.label' />';
    var i18n_role_name = '<spring:message code='Role.role_name.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/role.js"></script>