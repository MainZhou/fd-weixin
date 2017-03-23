<%@ include file="/WEB-INF/page/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <title>食安互联</title>
    <link href="${ctx}/static/assets/css/amazeui.min.css" rel="stylesheet">
    <link href="${ctx}/static/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/weui.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/example.css" rel="stylesheet">
    <link href="${ctx}/static/validator/jquery.validator.css" rel="stylesheet">
    <script src="${ctx}/static/js/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/validator/jquery.validator.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/validator/local/zh-CN.js" type="text/javascript"></script>
    <!-- 微信调用需要的两个JS -->
    <script src="${ctx}/static/js/jweixin-1.0.0.js"></script>
    <script src="${ctx}/static/js/wxJsapi.js" type="text/javascript"></script>
    <style>
        .loginBut {
            background-color: #0da522;
        }

        .loginBut:active {
            background-color: green;
        }
    </style>
</head>
<body>
<div style="position: relative" id="topHeight">
    <div style="text-align: center">
        <img src="${ctx}/static/img/LOGO2.png" style="margin-top: 30px;width: 150px;">
    </div>
    <form action="${ctx}/wxLoginFun" method="post">

        <div class="am-g am-g-collapse" style="margin-top: 10%">
            <div class="am-u-sm-2 am-u-sm-offset-1">
                <img src="${ctx}/static/img/phone.png">
            </div>
            <div class="am-u-sm-8">
                <input class="weui_input" pattern="[0-9]*" placeholder="请输入手机号" maxlength="11"
                       name="tel"
                       id="loginName"
                       style="border-bottom: 1px solid silver;height: 30px;margin-left: -15px">
            </div>
            <div class="am-u-sm-1" style="display: none">
                <i class="weui_icon_warn"></i>
            </div>
        </div>
        <div class="am-g am-g-collapse" style="margin-top: 5%">
            <div class="am-u-sm-2 am-u-sm-offset-1">
                <img src="${ctx}/static/img/key.png">
            </div>
            <div class="am-u-sm-3">
                <input class="weui_input" pattern="[0-9]*" placeholder="请输入验证码" maxlength="11"
                       name="smsCode"
                       style="border-bottom: 1px solid silver;height: 30px;margin-left: -15px">
            </div>
            <div class="am-u-sm-1" style="display: none">
                <i class="weui_icon_warn"></i>
            </div>
            <div class="am-u-sm-4">
                <button type="button" class="btn btn-default" id="getSmsCode" style="outline:none;margin-left: -15px">
                    获取验证码
                </button>
            </div>
        </div>
        <div class="am-g am-g-collapse">
            <div class="am-u-sm-10 am-u-sm-offset-1">
                <button type="submit" class="am-btn  am-round loginBut"
                        style="width:100%;margin-top: 10%;outline:none;color: white">登录
                </button>
            </div>
        </div>
    </form>
</div>
<div id="whiHeight"></div>
<div class="am-g am-g-collapse">
    <div class="am-u-sm-5 am-u-sm-offset-1">
        <a href="javascript:;" style="text-decoration:none;color: black" onclick="scanCodeUI()">消费者查询</a>
    </div>
    <div class="am-u-sm-2 am-u-sm-offset-1">
        <a href="${ctx}/register" style="text-decoration:none;color: black">注册</a>
    </div>
</div>

<c:if test="${error != null}">
    <div id="toast" style="display: none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <i class="weui_icon_safe weui_icon_safe_warn"></i>
            <p class="weui_toast_content">${error}</p>
        </div>
    </div>
</c:if>

<div id="toast2" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_safe weui_icon_safe_warn"></i>
        <p class="weui_toast_content">查询失败</p>
    </div>
</div>

</body>
</html>
<script>

    // 获得验证码
    $("#getSmsCode").click(function () {
        $.post("${ctx}/sendSMSCode", {tel: $("#loginName").val()}, function (data) {
            alert(data.msg);
        });
    });

    $(function () {
        if ($("#toast").is("div")) {
            $('#toast').show();
            setTimeout(function () {
                $('#toast').hide();
            }, 2000);
        }

        var winHeight = $(window).height();
        var topHeight = $("#topHeight").css("height");
        topHeight = topHeight.replace("px", "");
        var whiHeight = (winHeight - topHeight - 40) + "px";
        $("#whiHeight").css("height", whiHeight);
    });

    // 验证
    $('form').validator({
        stopOnError: false,
        msgMaker: false,
        invalid: function (form, errors) {

            var html = "";
            $.map(errors, function (msg) {
                html += msg;
            });
        },
        fields: {
            "tel": "required;mobile"
            , "smsCode": "required"
        }
    }).on('validation', function (e, current) {
        //字段验证失败后，添加错误高亮
        if (current.isValid) {
            $(current.element).parent().next().eq(0).css("display", "none");
        } else {
            $(current.element).parent().next().eq(0).css("display", "inline-block");
        }
    });

    function scanCodeUI() {
        var s = "http://www.chinasahl.com/purchaseRecords/consumerSearch?QRCode=Cxs62UupPfg5MlmHEwTseQ==";



        wx.scanQRCode({
            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                var result = res.resultStr;
                var i = result.indexOf("QRCode=");
                if(i!=-1){
                    result=result.substring(i+7,result.length);
                }
                window.location.href = baseUrl + "/purchaseRecords/consumerSearch?QRCode=" + result;//WFnwENDWIC97GK734R9FxA==
            }, cancel: function (res) {
                $('#toast2').show();
                setTimeout(function () {
                    $('#toast2').hide();
                }, 1000);
            }
        });

    }
</script>