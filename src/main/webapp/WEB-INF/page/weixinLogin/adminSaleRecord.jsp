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
                        <div class="am-u-sm-12" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>首页</span>
                        </div>
                    </div>
                    <div class="bodyTab" style="width: 95%;margin-top: 20px;margin-left: 3.5%;font-size: 14px;">
                        <div style="width: 50%;float: left;text-align: center;height: 30px">
                            <a href="${ctx}/wxAdmin"
                               style="text-decoration:none;color: silver"
                               id="notCheck">${month}月进货统计</a>
                        </div>
                        <div style="width: 50%;float: left;text-align: center;border-bottom: 1px solid #0da522;height: 30px">
                            <a href="${ctx}/wxAdminSaleRecord"
                               style="text-decoration:none;color: #0da522"
                               id="checking">${month}月销售统计</a>
                        </div>
                    </div>
                </div>
                <div class="scroll"
                     style="width:100%;position: absolute;overflow:scroll;-webkit-overflow-scrolling: touch;top:92px;bottom: 75px;overflow-x: hidden;background-color: #f2f2f2"
                     id="table1">
                    <div style="background-color: #f2f2f2;height: 10px"></div>
                    <div class="am-scrollable-horizontal" style="background-color: white">
                        <div style="text-align: right;margin-right: 20px;margin-top: 10px;background-color: white">
                            <img src="${ctx}/static/img/pay@3x.png" style="width: 15px;margin-top: -5px">合计：<span style="color: #efad4c">￥${result.totalInfo.totalPrice}</span>
                        </div>
                        <table class="am-table  am-text-nowrap" style="border-bottom: 1px solid #eeeeee;width: 95%;margin: auto;border-top: 1px solid #e7e7e7">
                            <thead style="font-size: 14px;">
                            <tr >
                                <td>品名</td>
                                <td style="text-align: center">重量（kg）</td>
                                <td style="text-align: right">总价（元）</td>
                            </tr>
                            </thead>
                            <tbody style="font-size: 15px">
                            <c:forEach items="${result.datas}" var="item">
                                <tr>
                                    <td style="font-weight: bolder;">${item.typeName}</td>
                                    <td style="text-align: center;color: #249cff">${item.weight}</td>
                                    <td style="text-align: right;color: #249cff">￥${item.price*item.weight}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="weui_tabbar">
                <a href="${ctx}/makeup/list?index=0&checkIndex=0" class="weui_tabbar_item" style="text-decoration:none;">
                    <div class="weui_tabbar_icon">
                        <img src="${ctx}/static/img/tab-bar_jinhuobulu_nor.png" alt="">
                    </div>
                    <p class="weui_tabbar_label">进货补录</p>
                </a>
                <a href="${ctx}/paperSlip/list" class="weui_tabbar_item" style="text-decoration:none;">
                    <div class="weui_tabbar_icon">
                        <img src="${ctx}/static/img/tab-bar_zhidanxiaoshou_nor.png" alt="">
                    </div>
                    <p class="weui_tabbar_label">纸单销售</p>
                </a>
                <a href="${ctx}/salesRecords/list?index=0" class="weui_tabbar_item" style="text-decoration:none;">
                    <div class="weui_tabbar_icon">
                        <img src="${ctx}/static/img/tab-bar_xiaoshoujilu_nor.png" alt="">
                    </div>
                    <p class="weui_tabbar_label">销售记录</p>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
//    $(function () {
//        var checkBut = $("#notCheck");
//        $("#notCheck").click(function () {
//            $(checkBut).css("color", "silver");
//            $(checkBut).parent().css("border-bottom", "1px solid #fff");
//            checkBut = this;
//            $(this).css("color", "#0da522");
//            $(this).parent().css("border-bottom", "1px solid #0da522");
//        });
//        $("#checking").click(function () {
//            $(checkBut).css("color", "silver");
//            $(checkBut).parent().css("border-bottom", "1px solid #fff");
//            checkBut = this;
//            $(this).css("color", "#0da522");
//            $(this).parent().css("border-bottom", "1px solid #0da522");
//        });
//    })
</script>