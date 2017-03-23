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
                        <div class="am-u-sm-12" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>首页</span>
                        </div>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6" style="text-align: center;margin-top: 12%;">
                        <a href="${ctx}/makeup/list?index=0&checkIndex=0">
                            <img src="${ctx}/static/img/danpinjiandang.png" style="width: 50%">
                            <label style="color: black;margin-top: 3%">进货单品建档<br>快速通道</label>
                        </a>
                    </div>
                    <div class="am-u-sm-6" style="text-align: center;margin-top: 12%;">
                        <a href="${ctx}/batchMakeup/list">
                            <img src="${ctx}/static/img/piliangjiandang.png" style="width: 50%">
                            <label style="color: black;margin-top: 3%">进货批量建档<br>快速通道</label>
                        </a>
                    </div>
                    <div class="am-u-sm-12" style="margin-top: 12%">
                        <div style="border-bottom: 1px solid #40b951"></div>
                    </div>
                    <div class="am-u-sm-12" style="text-align: center;margin-top: 12%;">
                        <a href="${ctx}/paperSlip/list">
                            <img src="${ctx}/static/img/xiaoshoujiandang.png" style="width: 25%"/><br>
                            <label style="color: black;margin-top: 3%">销售建档<br>快速通道</label>
                        </a>
                    </div>
                </div>

            </div>
            <div class="weui_tabbar">
                <a href="${ctx}/makeup/list?index=0&checkIndex=0" class="weui_tabbar_item"
                   style="text-decoration:none;">
                    <div class="weui_tabbar_icon">
                        <img src="${ctx}/static/img/tab-bar_jinhuobulu_nor.png" alt="">
                    </div>
                    <p class="weui_tabbar_label">进货</p>
                </a>
                <a href="${ctx}/paperSlip/list" class="weui_tabbar_item" style="text-decoration:none;">
                    <div class="weui_tabbar_icon">
                        <img src="${ctx}/static/img/tab-bar_zhidanxiaoshou_nor.png" alt="">
                    </div>
                    <p class="weui_tabbar_label">销售拍照</p>
                </a>
                <a href="${ctx}/salesRecords/list?index=0" class="weui_tabbar_item" style="text-decoration:none;">
                    <div class="weui_tabbar_icon">
                        <img src="${ctx}/static/img/tab-bar_xiaoshoujilu_nor.png" alt="">
                    </div>
                    <p class="weui_tabbar_label">进销台账</p>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>