<%@ include file="/WEB-INF/page/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <title>食安互联</title>
    <link href="${ctx}/static/assets/css/amazeui.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/weui.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/example.css" rel="stylesheet">
    <script src="${ctx}/static/js/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/assets/js/amazeui.min.js"></script>
    <!-- 微信调用需要的两个JS -->
    <script src="${ctx}/static/js/jweixin-1.0.0.js"></script>
    <script src="${ctx}/static/js/wxJsapi.js" type="text/javascript"></script>
    <style>
        .top {
            background-color: #40b951;
            color: white;
            height: 50px;
            width: 100%;
        }

        .loginBut {
            background-color: #0da522;
        }

        .loginBut:active {
            background-color: green;
        }
    </style>
</head>
<body>
<div class="container" id="container">
    <div class="tabbar">
        <div class="weui_tab">
            <div class="weui_tab_bd">
                <div class="top">
                    <div class="am-g am-g-collapse" style="margin: auto">
                        <div class="am-u-sm-2">
                            <a href="${ctx}/wxAdmin"><span class="am-icon-angle-left am-icon-lg"
                                                           style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                        </div>
                        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>批量建档</span>
                        </div>
                        <div class="am-u-sm-2">
                        </div>
                    </div>
                </div>
                <a href="javascript:;"><img src="${ctx}/static/img/zhidanxiaoshou.png" id="photo"
                                            style="margin-top: 30px;width: 26%;margin-left: 37%">
                    <div style="text-align: center;color: #aeaeae"><span>点击拍照</span></div>
                </a>
                <c:if test="${lists != null}">
                    <div class="scroll"
                         style="border-top: 1px solid silver;width:100%;position: absolute;overflow:scroll;-webkit-overflow-scrolling: touch;top:220px;bottom: 50px;overflow-x: hidden;"
                         id="table1">
                        <c:forEach items="${lists}" var="photo" varStatus="status">
                            <a href="${ctx}/batchMakeup/photographUI?imgId=${photo.img}&index=${status.index}">
                                <div class="weui_panel_bd">
                                    <div class="weui_media_box weui_media_appmsg">
                                        <div class="weui_media_hd" style="width: 35%;height: 100px">
                                            <img class="weui_media_appmsg_thumb"
                                                 src="${photo.img}"
                                                 alt="">
                                        </div>
                                        <input type="hidden" name="imgId" value="${photo.img}">
                                        <input type="hidden" name="salesTime" value="${photo.time}">
                                        <div class="weui_media_bd">
                                        <span class="weui_media_desc"
                                              style="font-size: 15px;color: black;margin-top: 5px;">进货时间：<br>${photo.time}</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${lists != null }">
                    <button class="am-btn  am-round loginBut"
                            style="width:80%;outline:none;color: white;margin-left: 10%;position: absolute;bottom: 10px;"
                            id="save">
                        保存
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/grid/batchMakeup.js"></script>