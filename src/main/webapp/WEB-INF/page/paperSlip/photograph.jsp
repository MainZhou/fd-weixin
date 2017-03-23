<%@ include file="/WEB-INF/page/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>食安互联</title>
    <link href="${ctx}/static/assets/css/amazeui.min.css" rel="stylesheet">
    <link href="${ctx}/static/dist/css/bootstrap.min.css" rel="stylesheet">
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

        .sumBut {
            background-color: #0da522;
        }

        .sumBut:active {
            background-color: green;
        }
    </style>
</head>
<body>
<div class="top">
    <div class="am-g am-g-collapse" style="margin: auto">
        <div class="am-u-sm-2">
            <a href="${ctx}/paperSlip/list"><span class="am-icon-angle-left am-icon-lg"
                                           style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
        </div>
        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
            <span>销售拍照</span>
        </div>
        <div class="am-u-sm-2">
        </div>
    </div>
</div>
<div class="body">
    <img style="width: 90%;margin-left: 5%;margin-top: 5%;height: 200px"
         src="${ctx}/static/img/pic.png" id="img1">
</div>
<div class="am-g am-g-collapse" style="margin-top: 5%">
    <div class="am-u-sm-1 am-u-sm-offset-1"></div>
    <div class="am-u-sm-3 am-u-sm-offset-1">
        <span style="float: right;margin-top: 3px">销售时间：</span>
    </div>
    <div class="am-u-sm-7 am-u-sm-offset-1">
        <input type="datetime-local"  style="float: left;width: 85%" id="salesTime">
    </div>

</div>

<div class="am-g am-g-collapse">
    <div class="am-u-sm-5 am-u-sm-offset-1">
        <button class="am-btn am-round " style="width:94%;margin-top: 10%;margin-left:3%;outline:none;color: white"><span style="color: green" id="photoAgain">重拍</span></button>
    </div>
    <div class="am-u-sm-5">
        <a href="javascript:;" class="am-btn  am-round sumBut" style="width:94%;margin-top: 10%;margin-left:3%;outline:none;color: white" id="save">提交</a>
    </div>
    <div class="am-u-sm-1">
    </div>
</div>
<div style="height: 50px;"></div>
<div id="toast" style="display: none">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i id="icon" class="weui_icon_toast weui-icon-success-no-circle"></i>
        <p id="msg" class="weui_toast_content">提交成功</p>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/grid/photograph.js"></script>