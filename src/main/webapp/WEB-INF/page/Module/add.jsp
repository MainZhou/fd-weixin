<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/module/add" commandName="module" method="post">
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Module.moduleName.label' /></label> <span class="msg-box" for="moduleName"></span>
                    <form:input path="moduleName" cssClass="form-control"  placeholder="请输入模块名称" maxlength="20" />
                </div>
                <div class="form-group">
                    <label><spring:message code='Module.intro.label' /></label> <span class="msg-box" for="intro"></span>
                    <form:textarea path="intro" cssClass="form-control" placeholder="请输入功能简述" maxlength="500" />
                </div>
                <div class="form-group">
                    <label>一级菜单</label> <span class="msg-box" for="menu.parentMenu.id"></span>
                    <form:select path="menu.parentMenu.guid" cssClass="form-control" id="parentMenu">
                        <form:option value="" label="--请选择--"/>
                        <form:options items="${level1MenuList}" itemLabel="menuName" itemValue="guid"/>
                    </form:select>
                </div>
                <div class="form-group">
                    <label>二级菜单</label> <span class="msg-box" for="menu.guid"></span>
                    <select name="menu.guid" class="form-control">
                    </select>
                </div>
                <div class="form-group">
                    <label>功能地址</label> <span class="msg-box" for="urls"></span>
                    <form:textarea path="userPostUrl" rows="6" cssClass="form-control" placeholder="请输入功能地址"/>
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
    require(['validator'], function() {
        $('form').validator({
            stopOnError: false,
            fields: {
                "moduleName":"required;length[~20]"
                ,"intro":"length[~500]"
                ,"menuId":"length[~36]"

            }
        })
        //字段验证失败后，添加错误高亮
        .on('validation', function(e, current){
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        })
    });

</script>

<script type="text/javascript">

    $("#parentMenu").change(function(){
        changeMenu();
    });

    $(function(){
        changeMenu();
    });

    function changeMenu(){
        var parentMenuId = $("#parentMenu option:selected").val();
        $.post("${ctx}/menu/findChildMenu", {"parentId":parentMenuId}, function(data){
            if(data){
                $childMenu = $("select[name='menu.guid']");
                $childMenu.empty();  // 先清空
                var $selectOption = $("<option>");
                $selectOption.val("");
                $selectOption.text("请选择");
                $childMenu.append($selectOption);
                for(var i = 0; i< data.length; i++){
                    var $option = $("<option>");
                    $option.val(data[i].guid);
                    $option.text(data[i].menuName);
                    $childMenu.append($option);
                }
            }
        });
    }
</script>

