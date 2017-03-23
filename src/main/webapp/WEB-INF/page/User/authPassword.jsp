<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>

<div class="container-fluid">
  <div class="row">
    <!-- 实体内容 -->
    <div class="col-xs-12 col-md-12 sidebar-offcanvas">
      <div class="hidden alert alert-danger" id="errorMsg"></div>
      <form action="${ctx}/user/authPassword" method="post">
        <div class="form-group">
          <input type="hidden" name="userId" value="${userId}" />
        </div>
        <div class="form-group">
            <span class="red">*</span><label>认证密码</label>
            <input type="password" name="authPassword" class="form-control" placeholder="请输入认证密码" maxlength="20" autocomplete="off">
        </div>
        <div class="form-group">
            <span class="red">*</span><label>认证密码</label>
            <input type="password" id="authPassword2" class="form-control" placeholder="请再次输入认证密码" autocomplete="off">
        </div>

        <div class="form-group">
          <input type="submit" class="hidden" disabled="disabled" id="submit_button">
        </div>
      </form>
    </div>
    <!-- 实体内容结束-->
  </div>
</div>

<script type="text/javascript">
  require(['jquery','validator'], function($) {

    $('form').validator({
      stopOnError: false,
      fields: {
        "authPassword":"required;length[3~20]"
        ,"#authPassword2":{
            rule:"required;length[3~20];match[authPassword]",
            msg:{
                match:"两次密码输入不一致"
            }
        }
      }
    })
    //字段验证失败后，添加错误高亮
    .on('validation', function(e, current){
      $(current.element).closest('div')[current.isValid?"removeClass":"addClass"]('has-error');
    });

  });

</script>