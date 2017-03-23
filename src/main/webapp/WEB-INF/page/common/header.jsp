<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page trimDirectiveWhitespaces="true" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%--ie兼容模式--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--360浏览器默认采用高速模式渲染页面---%>
    <meta name="renderer" content="webkit">
    <%--视口--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>安易迅物联网综合运营平台</title>
    <%@include file="common.jsp"%>
    <link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
    <%@include file="common-css.jsp" %>
    <script type="text/javascript" src="${ctx}/static/js/jsutil.js"></script>
    <script src="${ctx}/static/requireJs/require.js" data-main="${ctx}/static/js/main" ></script>

<c:choose>
    <%--风格2--%>
    <c:when test="${sessionScope['LOGIN_SUCCESS'].customStyle == '2'}">
        <body class="hold-transition skin-green sidebar-mini">
    </c:when>
    <%--风格3--%>
    <c:when test="${sessionScope['LOGIN_SUCCESS'].customStyle == '3'}">
        <body class="hold-transition skin-green-light sidebar-mini">
    </c:when>
    <%--默认风格1--%>
    <c:otherwise>
        <body class="hold-transition skin-black-light sidebar-mini">
    </c:otherwise>
</c:choose>
<%--导航条--%>
<%@include file="nav.jsp" %>