<%@ include file="/WEB-INF/page/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>食安互联</title>
    <link href="${ctx}/static/assets/css/amazeui.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/weui.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/example.css" rel="stylesheet">
    <script src="${ctx}/static/js/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/assets/js/amazeui.min.js"></script>
    <style>
        .top {
            background-color: #40b951;
            color: white;
            height: 50px;
            width: 100%;
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
                            <a href="javascript:;" onclick="infoUrl()"><span class="am-icon-angle-left am-icon-lg"
                                                        style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                        </div>
                        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>质量信息</span>
                        </div>
                        <div class="am-u-sm-2">
                        </div>
                    </div>
                </div>
                <div style="width: 100%;position: absolute;top:50px;bottom:0;background-color: #f1f1f1;font-size: 15px">
                    <div style="width: 90%;margin-left: 5%;margin-top: 10%;background-color: white;border: 1px solid #dedede">
                        <div style="margin-top: 10px;margin-left: 15px;margin-bottom: 10px">
                            品种名：<span id="typeName"></span>
                        </div>
                        <div style="margin-top: 10px;margin-left: 15px;margin-bottom: 10px">
                            单价：<span id="price"></span>
                        </div>
                        <div style="margin-top: 10px;margin-left: 15px;margin-bottom: 10px">
                            产地信息：<span id="sourcePlace"></span>
                        </div>
                        <div style="margin-top: 10px;margin-left: 15px;margin-bottom: 10px">
                            乡镇/村社区/基地/农户：<span id="sourcePlaceUnit"></span>
                        </div>
                        <div style="border-bottom: 1px solid silver"></div>
                        <div style="margin-top: 10px;margin-left: 15px;margin-bottom: 10px">
                            质量信息
                        </div>
                        <ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-avg-md-3 am-avg-md-3 am-gallery-default"
                            data-am-gallery="{ pureview: {weChatImagePreview: false} }" id="images">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/grid/scanCodeInfo.js"></script>