<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>
<div class="box-body">
    <div class="container-fluid">
        <!-- 功能按钮 -->
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="realName" addPlease="true">
                    <spring:message code='User.realName.label' />或<spring:message code='User.loginName.label' />
                </boyoi:makeSearch>
            </div>
            <!-- 添加高级搜索 -->
            <div class="col-xs-12 col-md-2">
                <div class="col-xs-12 col-md-2">
                    <boyoi:makeAdvSearch/>
                </div>
                <div class="col-xs-12 col-md-7">
                    <boyoi:makeAdd/>
                </div>
            </div>
            <div class="col-xs-12 col-md-7" >
                <ul class="nav nav-pills nav-padding pull-right">
                    <boyoi:makeButton addBtn="false" editBtn="true" detailBtn="true" delBtn="true"/>
                    <li><a href="javascript:" id="granAuthToUser" title="授权"><span class="glyphicon glyphicon-sunglasses"></span></a></li>
                    <li class="columns-tag"></li>
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
    <div class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='User.realName.label' /></label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="realName" placeholder="<spring:message code="global.please.input"/><spring:message code='User.realName.label' /> ">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='User.telephone.label' /></label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="telephone"  placeholder="<spring:message code="global.please.input"/><spring:message code='User.telephone.label' /> ">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='User.userStatus.label' /></label>
            <div class="col-sm-8">
                <select class="form-control" name="userStatus">
                    <option value="">--请选择--</option>
                    <c:forEach items="${userStatus}" var="item">
                        <option value="${item.itemValue}">${item.itemLabel}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><spring:message code='User.department.label' /></label>
            <div class="col-sm-8">
                <div class="input-group">
                    <input type="hidden" id="parentDeptSearchHidden" name="department.guid">
                    <input type="text" id="parentDeptSearch" class="form-control" readonly="readonly" placeholder="请选择所属部门">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"> </span></span>
                </div>
            </div>
        </div>
        <div id="menuContentSearch" class="form-group" style="display:none;margin-bottom:10px;height:220px;overflow: auto;">
            <div class="col-sm-8 col-sm-offset-4">
                <ul id="treeDemoSearch" class="ztree"></ul>
            </div>
        </div>
    </div>
    <div class="form-group pull-right">
        <button id="closeAdvBtn" class="btn btn-default ">关闭</button>
        <button id="doAdvBtn" class="btn btn-primary">搜索</button>
    </div>
</div><!-- end search -->

<script type="text/javascript">
    var i18n_guid = '<spring:message code='User.guid.label' />';
    var i18n_loginName = '<spring:message code='User.loginName.label' />';
    var i18n_password = '<spring:message code='User.password.label' />';
    var i18n_realName = '<spring:message code='User.realName.label' />';
    var i18n_sex = '<spring:message code='User.sex.label' />';
    var i18n_userStatus = '<spring:message code='User.userStatus.label' />';
    var i18n_telephone = '<spring:message code='User.telephone.label' />';
    var i18n_email = '<spring:message code='User.email.label' />';
    var i18n_jobNum = '<spring:message code='User.jobNum.label' />';
    var i18n_manageArea = '<spring:message code='User.manageArea.label' />';
    var i18n_entryDate = '<spring:message code='User.entryDate.label' />';
    var i18n_post = '<spring:message code='User.post.label' />';
    var i18n_addr = '<spring:message code='User.addr.label' />';
    var i18n_postName = '<spring:message code='User.postName.label' />';
    var i18n_department = '<spring:message code='User.department.label' />';
    var i18n_customName = '<spring:message code='CustomInfo.customName.label' />';

</script>
<script src="${ctx}/static/zTree/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${ctx}/grid/user.js"></script>