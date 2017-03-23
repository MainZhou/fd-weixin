<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>
<div class="subTabs-tag">
    <ul class="nav nav-tabs">
        <li class="active"><a aria-expanded="true" href="#logLogin_tab" data-toggle="tab"><spring:message code="LogLogin.func.name"/></a></li>
        <li class=""><a aria-expanded="false" href="#logOpt_tab" data-toggle="tab" data-request-url="${ctx}/logOpt/list"><spring:message code="LogOpt.func.name"/></a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="logLogin_tab">
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
                <div>
                    <table class="gridContainer"></table>
                </div>
            </div>

            <!-- 高级搜索 -->
            <div id="advancedSearchDialogContainer" style="display: none;">
                <div class="form-horizontal advancedSearchDialogContainerCss">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><spring:message code='User.realName.label' /></label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="tuser.realName" placeholder="<spring:message code="global.please.input"/><spring:message code='User.realName.label' /> ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">登录时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="loginTime" name="loginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'loginTime2\')}'})" placeholder="请选择登录开始时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">~</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="loginTime2" name="loginTime2" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'loginTime\')}'})" placeholder="请选择登录结束时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">退出时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="logoutTime" name="logoutTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'logoutTime2\')}'})" placeholder="请选择退出开始时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">~</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="logoutTime2" name="logoutTime2" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="请选择退出结束时间">
                        </div>
                    </div>
                </div>
                <div class="form-group pull-right">
                    <button id="closeAdvBtn" class="btn btn-default ">关闭</button>
                    <button id="doAdvBtn" class="btn btn-primary">搜索</button>
                </div>
            </div><!-- end search -->
        </div>
    </div>
</div>

<script type="text/javascript">
    var i18n_guid = '<spring:message code='LogLogin.guid.label' />';
    var i18n_user_loginName = '<spring:message code='User.loginName.label' />';
    var i18n_user_realName = '<spring:message code='User.realName.label' />';
    var i18n_loginDate = '<spring:message code='LogLogin.loginDate.label' />';
    var i18n_browser = '<spring:message code='LogLogin.browser.label' />';
    var i18n_ip = '<spring:message code='LogLogin.ip.label' />';
    var i18n_logoutDate = '<spring:message code='LogLogin.logoutDate.label' />';
    var i18n_leaveTime = '<spring:message code='LogLogin.leaveTime.label' />';
    
</script>
<script type="text/javascript" src="${ctx}/grid/logLogin.js"></script>
<script type="text/javascript" src="${ctx}/static/My97DatePicker/WdatePicker.js"></script>
