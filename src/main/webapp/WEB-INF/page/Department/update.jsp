<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/department/update" commandName="update" method="post">
                <div class="form-group">
                    <form:hidden path="guid" placeholder="GUID" />
                </div>
                <div class="form-group">
                    <label><spring:message code='Department.parentId.label' /></label>
                    <div class="input-group">
                        <input type="text" id="parentDeptEdit" class="form-control" readonly="readonly" value="${update.parentDept.deptName}" placeholder="请选择上级部门">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"> </span></span>
                    </div>
                    <input type="hidden" id="parentDeptEditHidden" name="parentDept.guid" value="${update.parentDept.guid}">
                </div>
                <div id="menuContentEdit" class="form-group" style="display:none;margin-bottom:10px;height:220px;overflow: auto;">
                    <ul id="treeDemoEdit" class="ztree"></ul>
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Department.deptName.label' /></label> <span class="msg-box" for="deptName"></span>
                    <form:input path="deptName" cssClass="form-control" placeholder="请输入部门名称" maxlength="20" />
                </div>
                <%--<div class="form-group">--%>
                    <%--<label><spring:message code='Department.deptCode.label' /></label> <span class="msg-box" for="deptCode"></span>--%>
                    <%--<form:input path="deptCode" cssClass="form-control" placeholder="请输入部门编码" maxlength="10" />--%>
                <%--</div>--%>
                <div class="form-group">
                    <label><spring:message code='Department.deptStatus.label' /></label> <span class="msg-box" for="deptStatus"></span>
                    <form:select path="deptStatus" cssClass="form-control" items="${departmentStatus}" itemLabel="itemLabel" itemValue="itemValue" />
                </div>

                <%--<div class="form-group">--%>
                    <%--<label><spring:message code='Department.deptAddr.label' /></label> <span class="msg-box" for="deptAddr"></span>--%>
                    <%--<form:input path="deptAddr" cssClass="form-control" placeholder="请输入部门地址" maxlength="100" />--%>
                <%--</div>--%>
                <div class="form-group">
                    <input type="submit" class="hidden" disabled="disabled" id="submit_button">
                </div>
            </form:form>
        </div>
        <!-- 实体内容结束-->
    </div>
</div>

<script type="text/javascript">
    require(['validator'], function() {
        $('form').validator({
            stopOnError: false,
            fields: {
                "deptName":"required;length[~20];remote[${ctx}/department/check, guid, deptName, parentDept.guid]"
                ,"deptCode":"length[~10]"
                ,"deptStatus":"required;length[~1]"
                ,"parentDept.guid":"length[~36];validate(deptName)"
                ,"deptAddr":"length[~100]"

            }
        })
        //字段验证失败后，添加错误高亮
        .on('validation', function(e, current){
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        })
    });

    require(['ztree'],function(){
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
            var zTree = $.fn.zTree.getZTreeObj("treeDemoEdit");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemoEdit"),
                    nodes = zTree.getCheckedNodes(true),
                    guid = "",
                    v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
                guid += nodes[i].id + ",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            if (guid.length > 0 ) guid = guid.substring(0, guid.length-1);
            $("#parentDeptEdit").attr("value", v);
            $("#parentDeptEditHidden").attr("value", guid);
            // 隐藏
            hideMenu()
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
                var zTree = $.fn.zTree.getZTreeObj("treeDemoEdit");
                var initParentDeptGuid = $("#parentDeptEditHidden").val();
                if (initParentDeptGuid){
                    var parentDeptNode = zTree.getNodeByParam("id", initParentDeptGuid, null);
                    zTree.checkNode(parentDeptNode, true, null, null);
                }

                // 禁止选中自己为父部门
                var my = zTree.getNodeByParam("id", $("#guid").val(), null);
                zTree.setChkDisabled(my, true);

                var obj =  $(this);
                var objOffset = $(this).offset();
                $("#menuContentEdit").css({left:objOffset.left + "px", top:objOffset.top + obj.outerHeight() + "px"}).slideDown("fast");

                $("body").bind("mousedown", onBodyDown);
            });
            $.fn.zTree.init($("#treeDemoEdit"), setting);

        });

    });

</script>

