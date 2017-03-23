<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/role/add" commandName="role" method="post">
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Role.role_name.label' /></label>
                    <form:input path="roleName" cssClass="form-control"  placeholder="请输入角色名称" maxlength="20" onkeydown='if(event.keyCode==13) return false;' />
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
            validClass: "has-succes",
            invalidClass: "has-error",
            bindClassTo: ".form-group",
            fields: {
                "roleName":{
                    "rule":"required;length[~20];remote[${ctx}/role/check, roleName]",
                    "must":true// 清除ajax缓存
                }

            }
        })

    });
</script>

