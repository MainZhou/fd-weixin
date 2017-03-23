<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台管理</title>
    <%@include file="../common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="${ctx}/static/js/angular.min.js"></script>
    <style>
        body { padding-top: 70px; }
    </style>
<body>
<!-- 导航条 -->
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/user/admin">代码生成工具-->返回主页</a>
        </div>
    </div>
</nav>