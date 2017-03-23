<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%--ie兼容模式--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--360浏览器默认采用高速模式渲染页面---%>
    <meta name="renderer" content="webkit">
    <%--视口--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录</title>
    <%@include file="../common/common.jsp"%>
    <link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/login.css">
</head>

<body>
<div class="paperBg1">
    <div class="paperBg2">
        <div class="paperBg3">
            <div class="paperBgLBto">
                <div class="paperBgRBto">
                    <div class="logo">
                        <img src="${ctx}/static/img/logo.png" />
                    </div>
                    <div class="loginArea">
                        <div class="logoZi">
                            <img src="${ctx}/static/img/bt.png" />
                        </div>
                        <div class="formArea">
                            <div class="row">
                                <div class="col-md-4 col-md-offset-1" style="margin-top:50px;width:500px;padding-left: 50px">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-user"><b>请登录</b></span> </div>
                                        <div class="panel-body">
                                            <div class="hidden" id="errorMsg"></div>
                                            <form role="form" action="${ctx}/boyoi_security/loginFun" method="post">
                                                <div class="form-group">
                                                    <label class="control-label">用户名</label>
                                                    <input type="text" name="login_name" class="form-control" placeholder="请输入帐号" required autofocus value="${sessionScope['lastUsername']}">
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label">密码</label>
                                                    <input type="password" name="password" class="form-control" placeholder="请输入密码" required >
                                                </div>
                                                <div class="form-group">
                                                    <button type="submit" id="loginBtn" class="btn btn-success col-md-offset-10">登  录</button>
                                                </div>

                                            </form>

                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cpy">
                        技术支持：成都博宇科技有限公司
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var errorMsg = document.getElementById("errorMsg");
    var serverMsg = "${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}";
    if(serverMsg){
        errorMsg.setAttribute("class", "alert alert-danger");
        errorMsg.innerHTML = serverMsg;
    }

    var appVersion = navigator.appVersion;
    if(navigator.appName == "Microsoft Internet Explorer" && appVersion.match(/MSIE 6./i)=="MSIE 6." ||
            appVersion.match(/MSIE 7./i)=="MSIE 7." ||
            appVersion.match(/MSIE 8./i)=="MSIE 8."){
        // 禁止登录
        document.getElementById("loginBtn").disabled = true;
        errorMsg.setAttribute("class", "alert alert-danger");
        errorMsg.innerHTML = "您的浏览器版本过低， 请使用IE9以上的浏览器！ 如使用360等国产浏览器， 请调整为极速模式！ "
    }
</script>
</body>
</html>
