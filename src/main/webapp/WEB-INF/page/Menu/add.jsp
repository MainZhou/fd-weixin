<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> 

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form:form action="${ctx}/menu/add" commandName="menu" method="post">
                <div class="form-group">
                    <span class="red">*</span><label><spring:message code='Menu.menuName.label' /></label> <span class="msg-box" for="menuName"></span>
                    <form:input path="menuName" cssClass="form-control"  placeholder="请输入菜单名称" maxlength="10" />
                </div>
                <div class="form-group">
                    <label><spring:message code='Menu.parentId.label' /></label> <span class="msg-box" for="parent_id"></span>
                    <form:select path="parentMenu.guid" cssClass="form-control">
                        <form:option value="" label="--请选择--"/>
                        <form:options items="${parentMenuList}" itemValue="guid" itemLabel="menuName"/>
                    </form:select>
                </div>
                <div class="form-group">
                    <label><spring:message code='Menu.sortNum.label' /></label> <span class="msg-box" for="sortNum"></span>
                    <form:input path="sortNum" cssClass="form-control"  placeholder="请输入同级排序号" maxlength="5" />
                </div>
                <div class="form-group">
                    <label><spring:message code='Menu.hint.label' /></label> <span class="msg-box" for="hint"></span>
                    <form:input path="hint" cssClass="form-control"  placeholder="请输入菜单提示" maxlength="100" />
                </div>
                <div class="form-group">
                    <label><spring:message code='Menu.entryUrl.label' /></label> <span class="msg-box" for="entryUrl"></span>
                    <form:input path="entryUrl" cssClass="form-control"  placeholder="请输入入口地址" maxlength="512" />
                </div>
                <div class="form-group">
                    <label><spring:message code='Menu.iconId.label' /></label> <span class="msg-box" for="icon.guid"></span>
                    <c:forEach items="${icons}" var="item">
                        <label class="checkbox-inline">
                            <input type="radio" name="icon.guid" value="${item.guid}"><span><span class="${item.cssName}"></span></span>
                        </label>
                    </c:forEach>
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
                "menuName":"required;length[~16]"
                ,"sortNum":"digits;range[0~99]"
                ,"hint":"length[~100]"
                ,"iconId":"length[~36]"
                ,"entryUrl":"length[~512]"

            }
        })
        //字段验证失败后，添加错误高亮
        .on('validation', function(e, current){
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        })
    });

</script>

