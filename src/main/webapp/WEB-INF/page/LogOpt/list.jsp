<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>
<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="tuser.realName" addPlease="true">
                    <spring:message code='User.realName.label' />或<spring:message code='User.loginName.label' />
                </boyoi:makeSearch>
            </div>
            <div class="col-xs-4 col-md-2">
                <boyoi:makeAdvSearch/>
            </div>
            <div class="col-xs-12 col-md-7">
                <ul class="nav nav-pills nav-padding pull-right">
                    <boyoi:makeButton addBtn="false" editBtn="false" detailBtn="true" delBtn="true"/>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- dataGrid -->
<div class="box-footer">
    <table class="gridContainer"></table>
</div>

<!-- 高级搜索 -->
<div id="advancedSearchDialogContainer" style="display: none;">
    <div class="form-horizontal advancedSearchDialogContainerCss">
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='User.realName.label' /></label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="tuser.realName" placeholder="<spring:message code="global.please.input"/><spring:message code='User.realName.label' />" >
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='LogOpt.optObject.label' /></label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="object" placeholder="<spring:message code="global.please.input"/><spring:message code='LogOpt.optObject.label' />" >
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='LogOpt.optType.label' /></label>
            <div class="col-sm-8">
                <form:select path="optType" cssClass="form-control" name="optType">
                    <form:option value="">--请选择--</form:option>
                    <form:options items="${optType}" itemValue="itemValue" itemLabel="itemLabel"/>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='LogOpt.optSuccess.label' /></label>
            <div class="col-sm-8">
                <select class="form-control" name="optSuccess">
                    <option value="">--请选择--</option>
                    <option value="0">失败</option>
                    <option value="1">成功</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='LogOpt.optDate.label' /></label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="optDate1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="请选择起始<spring:message code='LogOpt.optDate.label' />">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">~</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="optDate2" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="请选择结束<spring:message code='LogOpt.optDate.label' />">
            </div>
        </div>
    </div>
    <div class="form-group pull-right">
        <button id="closeAdvBtn" class="btn btn-default ">关闭</button>
        <button id="doAdvBtn" class="btn btn-primary">搜索</button>
    </div>
</div><!-- end search -->

<script type="text/javascript">
    var i18n_guid = '<spring:message code='LogOpt.guid.label' />';
    var i18n_optObject = '<spring:message code='LogOpt.optObject.label' />';
    var i18n_optGuid = '<spring:message code='LogOpt.optGuid.label' />';
    var i18n_description = '<spring:message code='LogOpt.description.label' />';
    var i18n_optSuccess = '<spring:message code='LogOpt.optSuccess.label' />';
    var i18n_optType = '<spring:message code='LogOpt.optType.label' />';
    var i18n_userId = '<spring:message code='LogOpt.userId.label' />';
    var i18n_optDate = '<spring:message code='LogOpt.optDate.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/logOpt.js"></script>