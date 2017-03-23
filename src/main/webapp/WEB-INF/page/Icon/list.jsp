<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>
<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="cssName" addPlease="false">
                    请输入样式名称
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
    <div id="content">
        <table class="gridContainer"></table>
    </div>
</div>

<script type="text/javascript">
    var i18n_guid = '<spring:message code='Icon.guid.label' />';
    var i18n_css_name = '<spring:message code='Icon.css_name.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/icon.js"></script>