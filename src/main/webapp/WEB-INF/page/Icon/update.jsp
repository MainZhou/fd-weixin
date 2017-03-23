<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/icon/update" commandName="update" method="post">
                <div class="form-group">
                    <form:hidden path="guid" placeholder="GUID" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Icon.css_name.label' /></label> <span class="msg-box" for="cssName"></span>
                    <form:input path="cssName" cssClass="form-control" placeholder="请输入CSS样式名称" maxlength="100" />
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
    $('form').validator({
        stopOnError: false,
        fields: {
        "cssName":"required;length[~100]"
        
    }
    })
    //字段验证失败后，添加错误高亮
    .on('validation', function(e, current){
        $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
    })
</script>

