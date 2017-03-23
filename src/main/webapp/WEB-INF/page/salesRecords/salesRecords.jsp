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
<div class="container">
    <div class="tabbar">
        <div class="weui_tab">
            <div class="weui_tab_bd" style="border-bottom: 1px solid silver">
                <div class="top" style="position:absolute; top:0; left: 0;">
                    <div class="am-g am-g-collapse" style="margin: auto">
                        <div class="am-u-sm-2">
                            <a href="${ctx}/wxAdmin"><span class="am-icon-angle-left am-icon-lg"
                                                           style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                        </div>
                        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>进销台账</span>
                        </div>
                        <div class="am-u-sm-2">
                        </div>
                    </div>
                    <div class="bodyTop" style="clear: none; top:0; left: 0;margin-top: 10px;">
                        <div style="width: 33%;text-align: center;float: left;"><a href="javascript:;"
                                                                                   style="text-decoration:none;color: silver"
                                                                                   id="frontOneMonth">近一月</a></div>
                        <div style="width: 33%;text-align: center;float: left;"><a href="javascript:;"
                                                                                   style="text-decoration:none;color: silver"
                                                                                   id="frontTwoMonth">近二月</a></div>
                        <div style="width: 33%;text-align: center;float: left;"><a href="javascript:;"
                                                                                   style="text-decoration:none;color: silver"
                                                                                   id="frontThreeeMonth">近三月</a></div>
                    </div>
                </div>

                <div class="scroll"
                     style="border-top: 1px solid silver;width:100%;position: absolute;overflow:scroll;-webkit-overflow-scrolling: touch;top:100px;bottom: 50px;overflow-x: hidden;"
                     id="table1">
                    <%--<div class="weui_panel_bd">--%>
                    <%--<div class="weui_media_box weui_media_appmsg">--%>
                    <%--<div class="weui_media_hd" style="width: 40%;height: 100px">--%>
                    <%--<figure data-am-widget="figure" class="am am-figure am-figure-default "--%>
                    <%--data-am-figure="{  pureview: 'true' }">--%>
                    <%--<img class="weui_media_appmsg_thumb" style="height: 80px"--%>
                    <%--src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAB4CAMAAAAOusbgAAAAeFBMVEUAwAD///+U5ZTc9twOww7G8MYwzDCH4YcfyR9x23Hw+/DY9dhm2WZG0kbT9NP0/PTL8sux7LFe115T1VM+zz7i+OIXxhes6qxr2mvA8MCe6J6M4oz6/frr+us5zjn2/fa67rqB4IF13XWn6ad83nxa1loqyirn+eccHxx4AAAC/klEQVRo3u2W2ZKiQBBF8wpCNSCyLwri7v//4bRIFVXoTBBB+DAReV5sG6lTXDITiGEYhmEYhmEYhmEYhmEY5v9i5fsZGRx9PyGDne8f6K9cfd+mKXe1yNG/0CcqYE86AkBMBh66f20deBc7wA/1WFiTwvSEpBMA2JJOBsSLxe/4QEEaJRrASP8EVF8Q74GbmevKg0saa0B8QbwBdjRyADYxIhqxAZ++IKYtciPXLQVG+imw+oo4Bu56rjEJ4GYsvPmKOAB+xlz7L5aevqUXuePWVhvWJ4eWiwUQ67mK51qPj4dFDMlRLBZTqF3SDvmr4BwtkECu5gHWPkmDfQh02WLxXuvbvC8ku8F57GsI5e0CmUwLz1kq3kD17R1In5816rGvQ5VMk5FEtIiWislTffuDpl/k/PzscdQsv8r9qWq4LRWX6tQYtTxvI3XyrwdyQxChXioOngH3dLgOFjk0all56XRi/wDFQrGQU3Os5t0wJu1GNtNKHdPqYaGYQuRDfbfDf26AGLYSyGS3ZAK4S8XuoAlxGSdYMKwqZKM9XJMtyqXi7HX/CiAZS6d8bSVUz5J36mEMFDTlAFQzxOT1dzLRljjB6+++ejFqka+mXIe6F59mw22OuOw1F4T6lg/9VjL1rLDoI9Xzl1MSYDNHnPQnt3D1EE7PrXjye/3pVpr1Z45hMUdcACc5NVQI0bOdS1WA0wuz73e7/5TNqBPhQXPEFGJNV2zNqWI7QKBd2Gn6AiBko02zuAOXeWIXjV0jNqdKegaE/kJQ6Bfs4aju04lMLkA2T5wBSYPKDGF3RKhFYEa6A1L1LG2yacmsaZ6YPOSAMKNsO+N5dNTfkc5Aqe26uxHpx7ZirvgCwJpWq/lmX1hA7LyabQ34tt5RiJKXSwQ+0KU0V5xg+hZrd4Bn1n4EID+WkQdgLfRNtvil9SPfwy+WQ7PFBWQz6dGWZBLkeJFXZGCfLUjCgGgqXo5TuSu3cugdcTv/HjqnBTEMwzAMwzAMwzAMwzAMw/zf/AFbXiOA6frlMAAAAABJRU5ErkJggg=="--%>
                    <%--alt="123">--%>
                    <%--</figure>--%>
                    <%--</div>--%>
                    <%--<div class="weui_media_bd">--%>
                    <%--<span class="weui_media_desc" style="font-size: 15px;color: black">成都市食用农产品交易凭证</span>--%>
                    <%--<span class="weui_media_desc" style="font-size: 15px;color: black;margin-top: 5px;">时间：2016-09-14 16:25:00</span>--%>
                    <%--<a href="voucher.jsp" class="am-btn  am-round loginBut"--%>
                    <%--style="width:75%;outline:none;color: white;margin-top: 5px;margin-left: 20px;">--%>
                    <%--图像识别结果--%>
                    <%--</a>--%>
                    <%--<button class="am-btn  am-round "--%>
                    <%--style="width:75%;outline:none;color: black;margin-top: 5px;margin-left: 20px;"--%>
                    <%--disabled>--%>
                    <%--凭证无法识别--%>
                    <%--</button>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
    </div>
    <div class="bd" style="position: fixed;bottom: 0;width: 100%;height: 50px">
        <div class="weui_tab">
            <div class="weui_navbar">
                <div class="weui_navbar_item weui_bar_item_on" id="0">
                    进货记录
                </div>
                <div class="weui_navbar_item" id="1">
                    销售记录
                </div>
                <div class="weui_navbar_item" id="2">
                    电子秤
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
<script>
    $(function () {
        overscroll($("#table1"));
        $(document.body).on('touchmove', function (event) {
            if (boo) {
                event.preventDefault();
            }
        });
    });
    var boo = false;
    function overscroll(el) {
        var lastY;//最后一次y坐标点
        var lastY2;//最后一次y坐标点
        el.on('touchstart', function (event) {
            lastY = $(this).scrollTop();
            lastY2 = event.originalEvent.changedTouches[0].clientY;
        });
        el.on('touchmove', function (event) {
            var y = event.originalEvent.changedTouches[0].clientY;
            var st = $(this).scrollTop();
            if (st <= 0 && lastY != st && y >= lastY2) {
                boo = true;
            } else if (y >= lastY2 && st <= 0) {
                boo = true;
            } else {
                boo = false;
            }
            lastY = st;
            lastY2 = y;
        });
    }
</script>
<script type="text/javascript" src="${ctx}/grid/salesRecords.js"></script>
