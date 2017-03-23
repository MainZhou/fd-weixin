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
                            <a href="${ctx}/wxLogin"><span class="am-icon-angle-left am-icon-lg"
                                                                          style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                        </div>
                        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>扫码</span>
                        </div>
                        <div class="am-u-sm-2">
                        </div>
                    </div>
                </div>
                <div style="border-top: 1px solid silver;width:100%;position: absolute;top:50px;bottom: 0;background-color: #f6f6f6">
                    <div style="background-color: white;width: 90%;margin-left: 5%;margin-top: 5%">
                        <div style="background-color: #40b951;height: 5px"></div>
                        <div style="text-align: center;margin-top: 5px;border-bottom: 1px dashed  silver"
                             id="marketName"></div>
                        <div style="font-size: 14px;margin-top: 5px;margin-left: 5px;"><img
                                src="${ctx}/static/img/Consumer_-seller-.png">&emsp;销售者：<span
                                id="wholesalerName"></span></div>
                        <div style="font-size: 14px;margin-top: 5px;margin-left: 5px;"><img
                                src="${ctx}/static/img/Consumer_shop-number.png">&emsp;摊位号：<span id="boothNo"></span>
                        </div>
                        <div style="font-size: 14px;margin-top: 5px;margin-bottom: 5px;margin-left: 5px;"><img
                                src="${ctx}/static/img/Consumer_category-.png">&emsp;品&emsp;类：<span id="typeNa"></span>
                        </div>
                        <div style="background-color: #40b951;height: 5px"></div>
                    </div>
                    <div style="margin-left: 5%;margin-top: 10px;font-size: 14px">近3日进货信息</div>
                    <div id="dataHt"></div>
                </div>
                <div>
                    <a href="javascript:;" style="position: fixed;bottom: 10px;right: 43%" onclick="scanCodeFun()">
                        <img src="${ctx}/static/img/scan.png"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="toast" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_safe weui_icon_safe_warn"></i>
        <p class="weui_toast_content">查询失败</p>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/grid/scanCode.js"></script>