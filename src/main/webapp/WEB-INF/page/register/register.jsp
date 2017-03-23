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
    <link rel="stylesheet" href="${ctx}/static/css/LArea.css">
    <script src="${ctx}/static/js/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/assets/js/amazeui.min.js"></script>
    <script src="${ctx}/static/validator/jquery.validator.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/validator/local/zh-CN.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/LAreaData1.js"></script>
    <script src="${ctx}/static/js/LAreaData2.js"></script>
    <script src="${ctx}/static/js/LArea.js"></script>
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
            <a href="${ctx}/wxLogin"><span class="am-icon-angle-left am-icon-lg"
                                           style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
        </div>
        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
            <span>注册</span>
        </div>
        <div class="am-u-sm-2">
        </div>
    </div>
</div>
<div class="body">
    <form class="am-form" style="margin-top: -20px;" action="${ctx}/registerData" method="post" id="amForm">
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">姓&nbsp;&emsp;&nbsp;名</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" maxlength="11" name="managerPerson" placeholder="请输入姓名"
                           value="${user.managerPerson}">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">身&nbsp;份&nbsp;证</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" maxlength="18" name="managerCardNo" placeholder="请输入身份证"
                           value="${user.managerCardNo}">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell ">
                <div class="weui_cell_hd"><label class="weui_label">区&nbsp;&nbsp;&emsp;域</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input id="demo1" class="weui_input" name="areaName" readonly="" placeholder="请选择区域"
                           value="${areaName}">
                    <input id="value1" type="hidden" name="areaId" value="${user.areaId}">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手&nbsp;机&nbsp;号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" maxlength="11" name="managerTelephone" placeholder="请输入手机号"
                           value="${user.managerTelephone}">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">验&nbsp;证&nbsp;码</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" name="smsCode" id="smsCode" maxlength="4" placeholder="请输入验证码">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
                <div class="weui_cell_ft">
                    <button type="button" class="btn btn-default" id="getSmsCode" style="outline:none;width: 100px">
                        获取验证码
                    </button>
                </div>
            </div>
        </div>
        <div style="margin-left: 17px">
            <label class="am-checkbox am-success" style="color: #0da522">
                <input type="checkbox" checked name="xy2">食安互联会员服务协议
            </label>
        </div>
        <div class="am-g am-g-collapse">
            <div class="am-u-sm-10 am-u-sm-offset-1">
                <button type="submit" href="javascript:;" class="am-btn  am-round sumBut"
                        style="width:100%;margin-top: 10%;outline:none;color: white" onclick="return checkCode();">下一步
                </button>
            </div>
        </div>
    </form>
</div>
<div id="toast" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_safe weui_icon_safe_warn"></i>
        <p class="weui_toast_content">必须同意协议!</p>
    </div>
</div>
<div id="toast2" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_safe weui_icon_safe_warn"></i>
        <p class="weui_toast_content" id="errorMsg"></p>
    </div>
</div>
</body>
</html>
<script>
    var area2 = new LArea();
    area2.value = [0, 0, 1];//控制初始位置，注意：该方法并不会影响到input的value
    area2.init({
        'trigger': '#demo1',
        'valueTo': '#value1',
        'keys': {
            id: 'value',
            name: 'text'
        },
        'type': 2,
        'data': [provs_data, citys_data, dists_data]
    });
</script>

<script>
    // 获得验证码
    $("#getSmsCode").click(function () {
        $.post("${ctx}/sendSMSCode", {tel: $("input[name='managerTelephone']").val()}, function (data) {
            if (data.status == false) {
                alert(data.msg);
            } else {
                remainTime();
            }
        });
    });
    var i = 60;
    function remainTime() {
        if (i == 0) {
            $("#getSmsCode").html("获取验证码");
            $("#getSmsCode").removeAttr("disabled");
            i = 60;
            return true;
        } else {
            i--;
            $("#getSmsCode").html(i + "秒");
            $("#getSmsCode").attr("disabled", "disabled");
        }
        setTimeout("remainTime()", 1000);
    }
    function checkCode() {
        var smsCode = $("#smsCode").val();
        if (smsCode == "") {
            $("#errorMsg").html("请填写验证码！");
            $('#toast2').show();
            setTimeout(function () {
                $('#toast2').hide();
            }, 1500);
            return false;
        } else {
            var flag = false;
//            //判断验证码是否正确
//            $.ajax({
//                url: ctx + "/checkCode",
//                type: "post",
//                dataType: "json",
//                async: false,
//                data: {"smsCode": smsCode},
//                success: function (data) {
//                    if (data.status == false) {
//                        $("#errorMsg").html(data.msg);
//                        $('#toast2').show();
//                        setTimeout(function () {
//                            $('#toast2').hide();
//                        }, 1500);
//                        flag = false;
//                    } else {
//                        flag = true;
//                    }
//                }
//            })
            flag=true;
            return flag;
        }
    }
    // 验证
    $('form').validator({
        stopOnError: false,
        msgMaker: false,
        rules: {
            card: [/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/, "请输入正确的身份证<br>"]
        },
        invalid: function (form, errors) {
            var html = "";
            $.map(errors, function (msg) {
                html += msg;
            });
        },
        fields: {
            "managerPerson": "required"
            , "managerCardNo": "required;card"
            , "areaId": "required"
            , "managerTelephone": "required;mobile"
            , "smsCode": "required"
            , "xy2": "checked"
        }
    }).on('validation', function (e, current) {
        //字段验证失败后，添加错误高亮
        if (current.isValid) {
            if ($(current.element).attr("name") != "xy2") {
                $(current.element).parent().parent().eq(0).attr("class", "weui_cell");
            }
        } else {
            if ($(current.element).attr("name") == "xy2") {
                $('#toast').show();
                setTimeout(function () {
                    $('#toast').hide();
                }, 1500);
            }
            else {
                $(current.element).parent().parent().eq(0).attr("class", "weui_cell weui_cell_warn");
            }
        }
    });

</script>