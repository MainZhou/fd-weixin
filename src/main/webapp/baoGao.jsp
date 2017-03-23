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
            <div class="top">
                <div class="am-g am-g-collapse" style="margin: auto">
                    <div class="am-u-sm-2">
                        <a href="${ctx}/saoMa.jsp"><span class="am-icon-angle-left am-icon-lg"
                                                    style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
                    </div>
                    <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
                        <span>检测报告</span>
                    </div>
                    <div class="am-u-sm-2">
                    </div>
                </div>
            </div>
            <div style="width: 95%;margin-left: 2.5%;margin-top: 5%">
                <figure data-am-widget="figure" class="am am-figure am-figure-default "
                        data-am-figure="{  pureview: 'true' }">
                    <img style="height: 150px;width: 100%"
                         src="http://s.amazeui.org/media/i/demos/pure-1.jpg?imageView2/0/w/640"
                         data-rel="http://s.amazeui.org/media/i/demos/pure-1.jpg"/>
                </figure>
            </div>

            <div style="margin-left: 5%;margin-top: 10px;font-size: 14px">检测报告识别</div>

            <div style="width: 90%;margin-left: 5%;margin-top: 5%">
                <div style="background-color: #dee7d1;text-align: center;border: 1px solid #a3cc90">
                    <div style="font-weight: bolder;padding-top: 10px">成都市沙西农副产品批发市场</div>
                    <br>
                    <div style="font-weight: bolder;margin-top: -10px;font-size: 20px;padding-bottom: 5px">检验报告</div>
                </div>
                <div style="position: relative">
                    <table class="am-table" style="font-size: 14px;border-left: 1px solid #a3cc90">
                        <tbody>
                        <tr>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                检品编号
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                20160802068
                            </td>
                        </tr>
                        <tr style="background-color: #dee7d1">
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                检品名称
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                豆芽
                            </td>
                        </tr>
                        <tr>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                检验类型
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                农残快速检测
                            </td>
                        </tr>
                        <tr style="background-color: #dee7d1">
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                受检对象
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                曹登明（销售者-个人）
                            </td>
                        </tr>
                        <tr>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                经营区域
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                果品3区10号摊位
                            </td>
                        </tr>
                        <tr style="background-color: #dee7d1">
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                检品状态
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                鲜样
                            </td>
                        </tr>
                        <tr>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                检品产地
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                四川省成都市郫县
                            </td>
                        </tr>
                        <tr style="background-color: #dee7d1">
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                抽样地点
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                市场4号预申报通道
                            </td>
                        </tr>
                        <tr>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                抽样者
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                康静
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
                                张华琼
                            </td>
                        </tr>
                        <tr style="background-color: #dee7d1">
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                抽样时间
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center">
                                2016-08-02 12:58:27
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
                                该样品按GB/T 5009.199---2003检验上述项目检测结果为阴性，合格。
                            </td>
                        </tr>
                        <tr>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                报告日期
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center"></td>
                        </tr>
                        <tr style="background-color: #dee7d1">
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: left;width: 25%;font-weight: bolder">
                                备注
                            </td>
                            <td style="border-bottom: 1px solid #a3cc90;border-right: 1px solid #a3cc90;text-align: center"></td>
                        </tr>
                        </tbody>
                    </table>
                    <div>
                        <img src="${ctx}/static/img/shaxi.png" style="position: absolute  ;bottom: 0;right: 0;width: 120px"/>
                    </div>
                </div>
                <a href="javascript:;" class="am-btn  am-round loginBut"
                   style="width:70%;margin-top: 10%;outline:none;color: white;margin-left: 15%">打印</a>
                <div style="height: 20px"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>