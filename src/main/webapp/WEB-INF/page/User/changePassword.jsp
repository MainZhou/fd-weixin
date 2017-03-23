<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <form action="${ctx}/user/changePassword" method="post" id="changePasswordForm">
                <div class="form-group">
                    <label>帐号</label>
                    <input type="hidden" name="guid" value="${guid}">
                    <input type="text" name="loginName" class="form-control" readonly="true" value="${username}" autocomplete="off">
                </div>
                <div class="form-group">
                    <label>原密码</label> <span class="msg-box" for="passwordOld"></span>
                    <input type="password" name="passwordOld" class="form-control" placeholder="请输入原密码" autocomplete="off">
                </div>
                <div class="form-group">
                    <label>新密码</label> <span class="msg-box" for="password"></span>
                    <input type="password" name="password" class="form-control" placeholder="请输入新密码" autocomplete="off">
                </div>
                <div class="form-group">
                    <label>重复新密码</label> <span class="msg-box" for="password2"></span>
                    <input type="password" id="password2" class="form-control" placeholder="请再次输入新密码" autocomplete="off">
                </div>
                <div class="form-group">
                    <button class="hidden" type="button">保存</button>
                </div>
            </form>
        </div>
        <!-- 实体内容结束-->
    </div>
</div>

<script type="text/javascript">
    require(['validator'], function() {
        $('#changePasswordForm').validator({
            stopOnError: false,
            fields: {
                "passwordOld": "required;length[3~20]",
                "password": "required;length[3~20]"
                ,"#password2":{
                    rule:"required;length[3~20];match[password]",
                    msg:{
                        match:"两次密码输入不一致"
                    }
//                    , tip:"请再次输入密码"
                }
            }
        })
        //字段验证失败后，添加错误高亮
        .on('validation', function(e, current){
            $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
        });
    });

</script>