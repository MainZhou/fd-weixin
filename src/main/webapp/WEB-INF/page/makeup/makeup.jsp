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
                        <div class="am-u-sm-2">
                            <a href="${ctx}/wxAdmin"><span class="am-icon-angle-left am-icon-lg"
                                                           style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                        </div>
                        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>单品建档</span>
                        </div>
                        <div class="am-u-sm-2">
                        </div>
                    </div>
                    <div class="bodyTop" style="clear: none; top:0; left: 0;margin-top: 10px;">
                        <%--<div style="width: 12%;text-align: center;float: left;"><a href="javascript:;"--%>
                        <%--style="text-decoration:none;color: black;"--%>
                        <%--id="nowMonth">当月</a></div>--%>
                        <div style="width: 33%;text-align: center;float: left;"><a href="javascript:;"
                                                                                   style="text-decoration:none;color: silver"
                                                                                   id="frontOneMonth">近一月</a></div>
                        <div style="width: 33%;text-align: center;float: left"><a href="javascript:;"
                                                                                  style="text-decoration:none;color: silver"
                                                                                  id="frontTwoMonth">近二月</a></div>
                        <div style="width: 33%;text-align: center;float: left"><a href="javascript:;"
                                                                                  style="text-decoration:none;color: silver"
                                                                                  id="frontThreeeMonth">近三月</a></div>
                        <%--<div style="width: 22%;text-align: center;float: left"><a href="javascript:;"--%>
                        <%--style="text-decoration:none;color: silver"--%>
                        <%--id="more">|&emsp;更多</a></div>--%>
                    </div>
                    <div class="bodyTab" style="width: 95%;margin-top: 50px;margin-left: 3.5%;font-size: 14px;">
                        <div style="width: 33%;float: left;text-align: center;height: 30px"><a href="javascript:;"
                                                                                               id="notCheck"
                                                                                               style="text-decoration:none;color: silver">未审验</a>
                        </div>
                        <div style="width: 33%;float: left;text-align: center;height: 30px"><a href="javascript:;"
                                                                                               id="ontChecked"
                                                                                               style="text-decoration:none;color: silver">审验未通过</a>
                        </div>
                        <div style="width: 33%;float: left;text-align: center;height: 30px"><a href="javascript:;"
                                                                                               id="checked"
                                                                                               style="text-decoration:none;color: silver">审验通过</a>
                        </div>
                    </div>
                </div>
                <div class="scroll"
                     style="border-top: 1px solid silver;width:100%;position: absolute;overflow:scroll;-webkit-overflow-scrolling: touch;top:127px;bottom: 5px;overflow-x: hidden;background-color: #f6f6f6"
                     id="table1">

                </div>
                <div>
                    <a href="javascript:;" onclick="changePage('${ctx}/makeup/addUI')" style="position: fixed;bottom: 30px;right: 20px"
                       class="am-icon-btn am-success am-icon-plus"></a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/grid/makeup.js"></script>