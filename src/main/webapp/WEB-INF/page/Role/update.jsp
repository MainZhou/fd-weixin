<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/role/update" commandName="update" method="post">
                <div class="form-group">
                    <form:hidden path="guid" placeholder="GUID" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Role.role_name.label' /></label> <span class="msg-box" for="roleName"></span>
                    <form:input path="roleName" cssClass="form-control" placeholder="请输入角色名称" maxlength="20" onkeydown='if(event.keyCode==13) return false;' />
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
                "roleName":"required;length[~20];remote[${ctx}/role/check, guid, roleName]"

            }
        })
        //字段验证失败后，添加错误高亮
        .on('validation', function(e, current){
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        })
    });

</script>