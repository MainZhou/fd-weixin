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
                            <a href="javascript:;" onclick="changePage('${ctx}/makeup/list')"><span
                                    class="am-icon-angle-left am-icon-lg"
                                    style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                        </div>
                        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                            <span>进货申报</span>
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
                            净重：<span id="weight"></span>kg
                        </div>
                        <div style="margin-top: 10px;margin-left: 15px;margin-bottom: 10px">
                            单价：<span id="price"></span>元/kg
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
                        <ul data-am-widget="gallery"
                            class="am-gallery am-avg-sm-3 am-avg-md-3 am-avg-md-3 am-gallery-default"
                            data-am-gallery="{ pureview: {weChatImagePreview: false} }" id="images">
                        </ul>
                        <div id="deTable" style="display: none">
                            <div style="width: 95%;margin-left: 2.5%;margin-top: 5%">
                                <figure data-am-widget="figure" class="am am-figure am-figure-default "
                                        data-am-figure="{  pureview: 'true' }">
                                    <img style="height: 150px;width: 100%" src="" id="img"/>
                                </figure>
                            </div>

                            <div style="margin-left: 5%;margin-top: 10px;font-size: 14px">检测报告识别</div>
                            <div style="width: 90%;margin-left: 5%;margin-top: 5%">
                                <div style="background-color: #dee7d1;text-align: center;border: 1px solid #a3cc90">
                                    <div style="font-weight: bolder;padding-top: 10px" id="dcName"></div>
                                    <br>
                                    <div style="font-weight: bolder;margin-top: -10px;font-size: 20px;padding-bottom: 5px">
                                        检验报告
                                    </div>
                                </div>
                                <div style="position: relative">
                                    <table class="am-table" style="font-size: 14px;border-left: 1px solid #a3cc90">
                                        <tbody>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检品编号
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="detectTaskCode"></span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检品名称
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="typeNameD"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检验类型
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span>农残快速检测/span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                受检对象
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="applyer"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                经营区域
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="marketArea"></span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检品状态
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span>鲜样</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检品产地
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="areaname"></span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                抽样地点
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="entrancePw"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                抽样者
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="realName"></span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检验依据
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                GB/T 5009.199---2003
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检验者
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="detecterName"></span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                抽样时间
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="samplingTime"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检验项目
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                对食用农产品中的有机磷和氨基甲酸酯类农药残留量的快速检测。
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                检测结论
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="detectResult"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                报告日期
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                                <span id="operateTime"></span>
                                            </td>
                                        </tr>
                                        <tr style="background-color: #dee7d1">
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                                备注
                                            </td>
                                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div>
                                        <img src="${ctx}/static/img/shaxi.png"
                                             style="position: absolute  ;bottom: 0;right: 0;width: 120px"/>
                                    </div>
                                </div>
                                <%--<a href="javascript:;" class="am-btn  am-round loginBut"--%>
                                <%--style="width:70%;margin-top: 10%;outline:none;color: white;margin-left: 15%">打印</a>--%>
                                <div style="height: 20px"></div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/grid/makeupInfo.js"></script>