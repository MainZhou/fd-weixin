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
    <link href="${ctx}/static/validator/jquery.validator.css" rel="stylesheet">

    <script src="${ctx}/static/js/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/assets/js/amazeui.min.js"></script>
    <script src="${ctx}/static/validator/jquery.validator.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/validator/local/zh-CN.js" type="text/javascript"></script>
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
            <a href="${ctx}/wxAdmin"><span class="am-icon-angle-left am-icon-lg"
                                       style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
        </div>
        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
            <span>填写资料</span>
        </div>
        <div class="am-u-sm-2">
        </div>
    </div>
</div>
<div class="body">
    <form style="margin-top: -20px;border-bottom: 1px solid silver;font-size: 13px;font-weight: normal">
        <div class="weui_cells weui_cells_form" style="font-size: 15px">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入姓名" name="wholesalerName">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell ">
                <div class="weui_cell_hd"><label class="weui_label">手机号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入手机号" name="mobile">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">身份证号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入身份证号" name="cardNo">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">身份证住址</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入身份证住址" name="address">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <div class="weui_cell weui_cell_select">
                <div class="weui_cell_hd"><label class="weui_label" style="margin-left: 17px;">所属市场</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="wmid">
                        <option value="" selected>--请选择所属市场--</option>
                        <c:forEach var="item" items="${jsonObject.wmInfo}">
                            <option value="${item.wmId}">${item.wmName}</option>
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
                    <input class="weui_input" placeholder="请输入摊位号" name="boothNo">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">主要产地</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入主要产地" name="production">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">主要进货渠道</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入主要进货渠道" name="stockChannel">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-2 am-avg-md-2 am-gallery-default">
                <li>
                    <div class="am-gallery-item">
                        <a href="#" class="" id="idPhoto1">
                            <img src="${ctx}/static/img/pic.png" alt="身份证正面" id="idPhoto1Img"/>
                            <h3 class="am-gallery-title" style="text-align: center">身份证正面</h3>
                            <input type="hidden" id="idPhoto1FromWx" name="idPhoto1FromWx">

                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" class="" id="idPhoto2">
                            <img src="${ctx}/static/img/pic.png" alt="身份证反面" id="idPhoto2Img"/>
                            <h3 class="am-gallery-title" style="text-align: center">身份证反面</h3>
                            <input type="hidden" id="idPhoto2FromWx" name="idPhoto2FromWx">
                        </a>
                    </div>
                </li>
            </ul>
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <div class="weui_cell">
                <div style="width: 100%"><label>经营食用农产品主要品种（可多选）</label></div>
            </div>
            <div class="weui_cell" >
                <div class="am-g" >
                    <div class="am-u-sm-3" >
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="V" name="booth" data-am-ucheck >蔬菜
                        </label>
                    </div>
                    <div class="am-u-sm-4">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="B" name="booth" data-am-ucheck>豆制品
                        </label>
                    </div>
                    <div class="am-u-sm-5">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="W" name="booth" data-am-ucheck>水发产品
                        </label>
                    </div>
                    <div class="am-u-sm-3">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="P" name="booth" data-am-ucheck>猪肉
                        </label>
                    </div>
                    <div class="am-u-sm-4">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="M" name="booth" data-am-ucheck>肉制品
                        </label>
                    </div>
                    <div class="am-u-sm-5">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="L" name="booth" data-am-ucheck>畜禽产品
                        </label>
                    </div>
                    <div class="am-u-sm-3">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="G" name="booth" data-am-ucheck>蛋类
                        </label>
                    </div>
                    <div class="am-u-sm-4">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="A" name="booth" data-am-ucheck>水产品
                        </label>
                    </div>
                    <div class="am-u-sm-5">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="U" name="booth" data-am-ucheck>散装食品
                        </label>
                    </div>
                    <div class="am-u-sm-3">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="C" name="booth" data-am-ucheck>粮油
                        </label>
                    </div>
                    <div class="am-u-sm-4">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="S" name="booth" data-am-ucheck>海产品
                        </label>
                    </div>
                    <div class="am-u-sm-5">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="Y" name="booth" data-am-ucheck>预包装食品
                        </label>
                    </div>
                    <div class="am-u-sm-3">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="F" name="booth" data-am-ucheck>水果
                        </label>
                    </div>
                    <div class="am-u-sm-4">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="E" name="booth" data-am-ucheck>食用菌
                        </label>
                    </div>
                    <div class="am-u-sm-5">
                        <label class="am-checkbox am-success" style="font-size: 13px">
                            <input type="checkbox" value="I" name="booth" data-am-ucheck>进口食用农产品
                        </label>
                    </div>
                </div>
            </div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <div class="weui_cell">
                <span style="margin-top: 2px;font-weight: bolder">所属市场经营区域</span>&emsp;
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入所属市场经营区域">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
        </div>
    </form>
</div>
<div class="am-g am-g-collapse">
    <div class="am-u-sm-10 am-u-sm-offset-1">
        <a href="javascript:;" class="am-btn  am-round sumBut" style="width:100%;margin-top: 10%;outline:none;color: white" onclick="add()">提交</a>
    </div>
</div>
<div id="toast" style="display: none">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i id="icon" class="weui_icon_toast weui-icon-success-no-circle"></i>
        <p id="msg" class="weui_toast_content">提交成功</p>
    </div>
</div>
<div style="height: 50px;"></div>
<script type="text/javascript" src="${ctx}/grid/enterpriseData.js"></script>

</body>
</html>