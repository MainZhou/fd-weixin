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
    <script src="${ctx}/static/assets/js/amazeui.min.js"></script>
    <script src="${ctx}/static/validator/jquery.validator.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/validator/local/zh-CN.js" type="text/javascript"></script>
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
            <a href="javascript:;" onclick="goBackRegister()"><span class="am-icon-angle-left am-icon-lg"
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
    <form style="margin-top: -20px;font-size: 13px;font-weight: normal" id="dataForm" method="post" >
        <div class="weui_cells weui_cells_form" style="font-size: 15px">
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <input type="hidden" name="managerPerson" id="managerPerson" value="${user.managerPerson}"/>
            <input type="hidden" name="managerCardNo" id="managerCardNo" value="${user.managerCardNo}"/>
            <input type="hidden"  id="areaName" value="${areaName}"/>
            <input type="hidden" name="areaId" id="areaId" value="${user.areaId}"/>
            <input type="hidden" name="managerTelephone" id="managerTelephone" value="${user.managerTelephone}"/>
            <div class="weui_cell weui_cell_select ">
                <div class="weui_cell_hd"><label class="weui_label" style="margin-left: 17px;">所属市场</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="parentId" id="parentId">
                        <option value="" selected>--请选择所属市场--</option>
                        <c:forEach var="item" items="${deptList}">
                            <option value="${item.deptId }">${item.deptName }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">摊位号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入摊位号" id="boothNo" name="boothNo" maxlength="10">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div style="width: 100%"><label>经营食用农产品主要品种（可多选）</label></div>
            </div>
            <div class="weui_cell">
                <div class="am-g">
                    <c:forEach var="item" items="${boothList}">
                        <div class="am-u-sm-4">
                            <label class="am-checkbox am-success" style="font-size: 13px">
                                <input type="checkbox" value="${item.code}" name="booth" data-am-ucheck>${item.name}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
        </div>
        <div class="footer" style="margin-left: 17px">
            <label class="am-checkbox am-success" style="color: #0da522">
                <input type="checkbox" name="xy2" id="xy2" checked>食用农产品市场销售质量安全协议书
            </label>
        </div>
        <div class="am-g am-g-collapse">
            <div class="am-u-sm-10 am-u-sm-offset-1">
                <a  class="am-btn  am-round sumBut"
                   style="width:100%;margin-top: 10%;outline:none;color: white" onclick="return add()">注册</a>
            </div>
        </div>
    </form>
</div>
<div id="toast" style="display: none">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i id="icon" class="weui_icon_toast weui-icon-success-no-circle"></i>
        <p id="msg" class="weui_toast_content">提交成功</p>
    </div>
</div>
<div id="toast2" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_safe weui_icon_safe_warn"></i>
        <p class="weui_toast_content" id="errorMsg"></p>
    </div>
</div>
<div style="height: 50px;"></div>
</body>
</html>
<script>
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
            "parentId": "required"
            , "boothNo": "required;"
            , "xy2": "checked"
        }
    }).on('validation', function (e, current) {
        //字段验证失败后，添加错误高亮
        if (current.isValid) {
            if ($(current.element).attr("name") != "xy2") {
                if($(current.element).attr("name") == "parentId"){
                    $(current.element).parent().parent().eq(0).attr("class", "weui_cell weui_cell_select");
                }else{
                    $(current.element).parent().parent().eq(0).attr("class", "weui_cell");
                }

            }
        } else {
            if ($(current.element).attr("name") == "xy2") {
                $("#errorMsg").html("必须同意协议！");
                $('#toast2').show();
                setTimeout(function () {
                    $('#toast2').hide();
                }, 1500);
            } else if($(current.element).attr("name") == "parentId"){
                $(current.element).parent().parent().eq(0).attr("class", "weui_cell weui_cell_select  weui_cell_warn");
            }else{
                $(current.element).parent().parent().eq(0).attr("class", "weui_cell weui_cell_warn");
            }
        }
    });
    function goBackRegister() {
        var managerPerson = $("#managerPerson").val();
        var managerCardNo = $("#managerCardNo").val();
        var areaName = $("#areaName").val();
        var areaId = $("#areaId").val();
        var managerTelephone = $("#managerTelephone").val();
        window.location.href=ctx+"/register?managerPerson="+managerPerson+"&managerCardNo="+managerCardNo+"&areaName="+areaName+"&areaId="+areaId+"&managerTelephone="+managerTelephone;
    }
    function add() {
        var b=true;
        var count = $("input[name='booth']:checked").length;
        if(count==0){
            $("#errorMsg").html("至少选择一项经营品种！");
            $('#toast2').show();
            setTimeout(function () {
                $('#toast2').hide();
            }, 1500);
            b= false;
        }
        var parentId = $("#parentId").val();
        if(parentId ==""){
            $("#parentId").parent().parent().eq(0).attr("class", "weui_cell weui_cell_select  weui_cell_warn");
            b= false;
        }
        var boothNo = $("#boothNo").val();
        if(boothNo ==""){
            $("#boothNo").parent().parent().eq(0).attr("class", "weui_cell weui_cell_warn");
            b= false;
        }
        var boo =$("#xy2").is(':checked');
        if(!boo){
            $("#errorMsg").html("必须同意协议！");
            $('#toast2').show();
            setTimeout(function () {
                $('#toast2').hide();
            }, 1500);
            b= false;
        }
        if(b){
            $.ajax({
                url:ctx+"/register",
                type:"post",
                dataType:"json",
                data:$("#dataForm").serialize(),
                async: false,
                success:function (data) {
                    if(data.status){
                        $('#toast').show();
                        setTimeout(function () {
                            $('#toast').hide();
                        }, 1500);
                        window.location.href=ctx+"/wxLoginFun?tel="+$("#managerTelephone").val();
                    }else{
                        $("#errorMsg").html(data.msg);
                        $('#toast2').show();
                        setTimeout(function () {
                            $('#toast2').hide();
                        }, 1500);
                    }
                }
            })
        }
    }
</script>