<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 --><!-- testff -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/area/add" commandName="area" method="post">
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Area.areaname.label' /></label> <span class="msg-box" for="areaname"></span>
                    <form:input path="areaname" cssClass="form-control"  placeholder="请输入区域名称" maxlength="32" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Area.parentid.label' /></label> <span class="msg-box" for="parentid"></span>
                    <form:input path="parentid" cssClass="form-control"  placeholder="请输入上级区域" maxlength="6" />
                </div>
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Area.levels.label' /></label> <span class="msg-box" for="levels"></span>
                    <form:input path="levels" cssClass="form-control"  placeholder="请输入区域等级" maxlength="1" />
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
                "areaname":"required;length[~32]"
                ,"parentid":"required;length[~6]"
                ,"levels":"required;length[~1]"
                
            }
        }).on('validation', function(e, current){
            //字段验证失败后，添加错误高亮
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        });
    });
</script>

