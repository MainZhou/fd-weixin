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
            <a href="salesRecords.jsp"><span class="am-icon-angle-left am-icon-lg"
                                             style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
        </div>
        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
            <span>凭证信息</span>
        </div>
        <div class="am-u-sm-2">
        </div>
    </div>
</div>
<div class="body">
    <form style="margin-top: -20px;font-size: 13px;font-weight: normal">
        <div class="weui_cells weui_cells_form" style="font-size: 15px;">
            <div class="weui_cell" style="background-color: #f1f1f1;">
                <div style="margin: auto">
                    <span style="font-weight: bolder;">成都食用农产品交易凭证</span>&emsp;<span>(</span>
                    <input class="weui_input" style="width: 35px;" value="2016"
                           maxlength="4"/><span>)</span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">交易时间</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入交易时间" value="2016年9月14日"
                           style="text-align: right;">
                </div>
            </div>
            <div class="weui_cell ">
                <div class="weui_cell_hd"><label class="weui_label">编号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入编号" value="0027551"
                           style="text-align: right;">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">交易市场名称</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <div style="text-align: right">成都市沙西农副产品批发市场</div>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">销售(单位)人</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <div style="text-align: right">尹川</div>
                </div>
            </div>
            <div class="weui_cell ">
                <div class="weui_cell_hd"><label class="weui_label">主体编号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <div style="text-align: right">510102610321</div>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">购货(单位)人</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入购货(单位)人" value="李发财"
                           style="text-align: right;">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">主体编号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input"
                           style="text-align: right;">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">联系电话</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input"
                           style="text-align: right;">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="am-scrollable-horizontal">
                <table class="am-table am-table-compact am-text-nowrap" style="border-bottom: 1px solid silver">
                    <thead style="background-color:#f1f1f1 ">
                    <tr>
                        <th style="text-align: center">品种</th>
                        <th style="text-align: center">品种编号</th>
                        <th style="text-align: center">单价</th>
                        <th style="text-align: center">重量</th>
                        <th style="text-align: center">金额</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <input class="weui_input" placeholder="请输入品种" value="白柳脐橙">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入品种编号" value="20158945">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入单价" value="202.5">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入重量" value="200">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入金额" value="1040.00">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="weui_input" placeholder="请输入品种" value="白柳脐橙">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入品种编号" value="20158945">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入单价" value="202.5">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入重量" value="200">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入金额" value="1040.00">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="weui_input" placeholder="请输入品种" value="白柳脐橙">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入品种编号" value="20158945">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入单价" value="202.5">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入重量" value="200">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入金额" value="1040.00">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="weui_input" placeholder="请输入品种" value="白柳脐橙">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入品种编号" value="20158945">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入单价" value="202.5">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入重量" value="200">
                        </td>
                        <td>
                            <input class="weui_input" placeholder="请输入金额" value="1040.00">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="weui_cell" style="margin-top: -5%">
                <span style="font-weight: bolder;margin-left: 71%">合计：</span>
                <input class="weui_input" style="width: 35px;" value="2016"
                       maxlength="4" disabled/>
            </div>
            <div style="background-color:#f1f1f1;height: 50px;width: 100%"></div>
        </div>
    </form>
</div>
<div class="am-g am-g-collapse">
    <div class="am-u-sm-4">
        <button class="am-btn am-round"
                style="width:90%;margin-top: 10%;outline:none;color: green;margin-left: 5%;display: none" id="saveBut">
            保存
        </button>
        <button class="am-btn am-round" style="width:90%;margin-top: 10%;outline:none;color: green;margin-left: 5%"
                id="updateBut">编辑
        </button>
    </div>
    <div class="am-u-sm-8">
        <a href="voucherInfo.jsp" class="am-btn am-round sumBut"
           style="width:90%;margin-top: 5%;outline:none;color: white;margin-left: 5%">点击生成电子凭证</a>
    </div>
</div>
<div style="height: 20px;"></div>
</body>
</html>
<script>
    $(function () {
        $("#saveBut").click(function () {
            $(this).css("display", "none");
            $("#updateBut").css("display", "inline-block");
            $(".weui_input").each(function (e) {
                $(this).css("color", "black");
                $(this).attr("disabled", true);
            });
        });
        $("#updateBut").click(function () {
            $(this).css("display", "none");
            $("#saveBut").css("display", "inline-block");
            $(".weui_input").each(function (e) {
                $(this).css("color", "#a8a8a8");
                $(this).attr("disabled", "");
            });
        });
    })
</script>