<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/user/update" commandName="update" method="post">
                <div class="form-group">
                    <form:hidden path="guid" placeholder="guid" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='User.department.label' /></label>
                    <span class="msg-box" for="parentDeptEdit"></span>
                    <div class="input-group">
                        <input type="hidden" id="parentDeptEditHidden" name="department.guid" value="${update.department.guid}">
                        <input type="text" id="parentDeptEdit" class="form-control" readonly="readonly"  placeholder="请选择所属部门" value="${update.department.deptString}">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"> </span></span>
                    </div>
                </div>
                <div id="menuContentEdit" class="form-group" style="display:none;margin-bottom:10px;height:220px;overflow: auto;">
                    <ul id="treeEditDemo" class="ztree"></ul>
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='User.realName.label' /></label> <span class="msg-box" for="realName"></span>
                    <form:input path="realName" cssClass="form-control" placeholder="请输入姓名" maxlength="20" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='User.loginName.label' /></label> <span class="msg-box" for="loginName"></span>
                    <input type="hidden" name="old" value="${update.loginName}">
                    <form:input path="loginName" cssClass="form-control" placeholder="请输入登录名称" maxlength="12" autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label><spring:message code='User.password.label' /></label> <span class="msg-box" for="password"></span>
                    <input type="password" name="password" id="password" class="form-control" placeholder="请输入密码,不输入则不更改!" autocomplete="off" maxlength="20">
                </div>
                <div class="form-group">
                    <label>重复密码</label> <span class="msg-box" for="password2"></span>
                    <input type="password" id="password2" class="form-control" placeholder="请再次输入密码" autocomplete="off" maxlength="20">
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='User.telephone.label' /></label> <span class="msg-box" for="telephone"></span>
                    <form:input path="telephone" cssClass="form-control" placeholder="请输入手机号码" maxlength="13" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='User.userStatus.label' /></label> <span class="msg-box" for="user_status"></span>
                    <form:select path="userStatus" cssClass="form-control">
                        <form:options items="${userStatus}" itemLabel="itemLabel" itemValue="itemValue"/>
                    </form:select>
                </div>
                <div class="form-group">
                    <label><spring:message code='User.postName.label' /></label> <span class="msg-box" for="postName"></span>
                    <form:input path="postName" cssClass="form-control" placeholder="请输入职位" maxlength="10" />
                </div>
                <div class="form-group">
                    <label><spring:message code='User.sex.label' /></label> <span class="msg-box" for="sex"></span>
                    <form:select path="sex" cssClass="form-control">
                        <form:option value="" label="-- 请选择 --"/>
                        <form:option value="0" label="男"/>
                        <form:option value="1" label="女"/>
                    </form:select>
                </div>
                <div class="form-group">
                    <label><spring:message code='User.email.label' /></label> <span class="msg-box" for="email"></span>
                    <form:input path="email" cssClass="form-control" placeholder="请输入电子邮箱" maxlength="20" />
                </div>
                <div class="form-group">
                    <label><spring:message code='User.entryDate.label' /></label> <span class="msg-box" for="entryDate"></span>
                    <form:input path="entryDate" cssClass="form-control" placeholder="请输入出生日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'1900-01-01',maxDate:'%y-%M-%d'})" />
                </div>
                <div class="form-group">
                    <input type="submit" class="hidden" disabled="disabled" id="submit_button">
                </div>
            </form:form>
        </div>
        <!-- 实体内容结束-->
    </div>
</div>

<script type="text/javascript">
    require(['jquery','validator','ztree','select2'], function($) {
        $("#customInfo").select2();

        $('form').validator({
            stopOnError: false,
            fields: {
                "loginName":"required;length[2~20];remote[${ctx}/user/check, guid, loginName]"
                ,"password":"length[3~20]"
                ,"#password2":{
                    rule:"match[password];required(password:filled)",
                    msg:{
                        match:"两次密码输入不一致"
                    }
//                    , tip:"请再次输入密码"
                }
                ,"realName":"required;length[~20]"
                ,"sex":"length[~1]"
                ,"userStatus":"required;length[~1]"
                ,"telephone":"required;mobile"
                ,"email":"email"
                ,"jobNum":"length[~10]"
                ,"manageArea":"length[~20]"
                ,"entryDate":"date"
                ,"post":"length[~10]"
                ,"addr":"length[~20]"
                ,"postName":"length[~10]"
                ,"department":"required;length[~36]"

            }
        })
        //字段验证失败后，添加错误高亮
        .on('validation', function(e, current){
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        });

        // 树
        var setting = {
            async : {
                contentType : "application/json",
                enable : true,
                dataType : "json",
                type : "get",
                url : ctx + "/zTree/loadZtree?model=department",
                dataFilter : null
            },
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey : "id",
                    pIdKey : "pid",
                    rootPId : 0   // 根节点
                }
            },
            callback: {
                onClick: onClick,
                onCheck: onCheck
            }
        };

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeEditDemo");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeEditDemo"),
                    nodes = zTree.getCheckedNodes(true),
                    guid = "",
                    v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                var parentNode = nodes[i].getParentNode();
                while(true){
                    if (null != parentNode){
                        v =parentNode.name + " > " + v;
                    }else{
                        break;
                    }
                    parentNode= parentNode.getParentNode();
                }
                v += nodes[i].name + ",";
                guid += nodes[i].id + ",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            if (guid.length > 0 ) guid = guid.substring(0, guid.length-1);
            // 设值同时验证
            $("#parentDeptEdit").val(v).trigger("validate");
            $("#parentDeptEditHidden").val(guid);
            // 隐藏
            hideMenu();
        }

        function hideMenu() {
            $("#menuContentEdit").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContentEdit" || $(event.target).parents("#menuContentEdit").length>0)) {
                hideMenu();
            }
        }

        $(document).ready(function(){
            $("#parentDeptEdit").click(function(){
                // 选中默认
                var zTree = $.fn.zTree.getZTreeObj("treeEditDemo");
                var initParentDeptGuid = $("#parentDeptEditHidden").val();
                if (initParentDeptGuid){
                    var parentDeptNode = zTree.getNodeByParam("id", initParentDeptGuid, null);
                    zTree.checkNode(parentDeptNode, true, null, null);
                }

                var obj =  $(this);
                var objOffset = $(this).offset();
                $("#menuContentEdit").css({left:objOffset.left + "px", top:objOffset.top + obj.outerHeight() + "px"}).slideDown("fast");

                $("body").bind("mousedown", onBodyDown);
            });
            $.fn.zTree.init($("#treeEditDemo"), setting);
        });

    });

</script>

<script type="text/javascript" src="${ctx}/static/My97DatePicker/WdatePicker.js"></script>
    