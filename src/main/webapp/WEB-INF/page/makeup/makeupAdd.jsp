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
    <link href="${ctx}/static/select2/css/select2.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/LArea.css" rel="stylesheet">
    <link href="${ctx}/static/validator/jquery.validator.css" rel="stylesheet">
    <script src="${ctx}/static/js/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/assets/js/amazeui.min.js"></script>
    <script src="${ctx}/static/js/LAreaData1.js"></script>
    <script src="${ctx}/static/js/LAreaData2.js"></script>
    <script src="${ctx}/static/js/LArea.js"></script>
    <script src="${ctx}/static/select2/js/select2.min.js" type="text/javascript"></script>
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
            <a onclick="changePage('${ctx}/makeup/list')"><span class="am-icon-angle-left am-icon-lg"
                                                                style="margin-left: 25%;color: white;margin-top: 7px"></span></a>
        </div>
        <div class="am-u-sm-8" style="text-align: center;margin-top: 14px;font-size: 17px;">
            <span>建档申报</span>
        </div>
        <div class="am-u-sm-2">
        </div>
    </div>
</div>
<div class="body">
    <form id="makeupForm" style="margin-top: -20px;border-bottom: 1px solid silver;font-size: 13px;font-weight: normal">
        <div class="weui_cells weui_cells_form" style="font-size: 15px">
            <div class="weui_cell">
                <div class="weui_cell_hd "><label class="weui_label">品种名</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select name="typeCode" class="weui_input" id="typeCode" style="width: 80%">
                        <option value="">--请查询品种名--</option>
                    </select>
                    <input type="hidden" name="typeName" id="typeName"/>
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell ">
                <div class="weui_cell_hd"><label class="weui_label">净重</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入净重（kg）" name="weight" id="weight"
                           onkeyup="value=value.replace(/[^0-9.]/g,'')">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">单价</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" placeholder="请输入单价（元/kg）" name="price" id="price"
                           onkeyup="value=value.replace(/[^0-9.]/g,'')">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">产地信息</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input id="demo2" class="weui_input" readonly="" placeholder="请选择产地信息" name="sourcePlace">
                    <input id="value2" type="hidden" name="sourcePlaceId">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <span style="font-weight: bolder">乡镇/村社区/基地/农户</span>&emsp;
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input"  name="sourcePlaceUnit" maxlength="20" id="sourcePlaceUnit">
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div style="height: 10px;background-color: #f1f1f1"></div>
            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-avg-md-3 am-avg-md-3 am-gallery-default">
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage1" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="无公害农产品" id="img1" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">无公害农产品</h3>
                            <input type="hidden" name="makeupInfo.photoData[0].documentType" value="W">
                            <input type="hidden" id="pathW" name="makeupInfo.photoData[0].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage2" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="绿色食品" id="img2" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">绿色食品</h3>
                            <input type="hidden" name="makeupInfo.photoData[1].documentType" value="L">
                            <input type="hidden" id="pathL" name="makeupInfo.photoData[1].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage3" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="有机农产品" id="img3" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">有机农产品</h3>
                            <input type="hidden" name="makeupInfo.photoData[2].documentType" value="Y">
                            <input type="hidden" id="pathY" name="makeupInfo.photoData[2].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage4" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="农产品地理标志" id="img4" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">农产品地理标志</h3>
                            <input type="hidden" name="makeupInfo.photoData[3].documentType" value="N">
                            <input type="hidden" id="pathN" name="makeupInfo.photoData[3].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage5" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="产地证明" id="img5" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">产地证明</h3>
                            <input type="hidden" name="makeupInfo.photoData[4].documentType" value="C">
                            <input type="hidden" id="pathC" name="makeupInfo.photoData[4].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage6" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="购货凭证" id="img6" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">购货凭证</h3>
                            <input type="hidden" name="makeupInfo.photoData[5].documentType" value="B">
                            <input type="hidden" id="pathB" name="makeupInfo.photoData[5].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage7" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="动物检疫证" id="img7" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">动物检疫证</h3>
                            <input type="hidden" name="makeupInfo.photoData[6].documentType" value="A">
                            <input type="hidden" id="pathA" name="makeupInfo.photoData[6].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage8" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="肉品检疫证" id="img8" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">肉品检疫证</h3>
                            <input type="hidden" name="makeupInfo.photoData[7].documentType" value="M">
                            <input type="hidden" id="pathM" name="makeupInfo.photoData[7].path">
                        </a>
                    </div>
                </li>
                <li>
                    <div class="am-gallery-item">
                        <a href="#" id="chooseImage9" class="chooseImage">
                            <img src="${ctx}/static/img/pic.png" alt="其他合格证明" id="img9" style="height: 80px"/>
                            <h3 class="am-gallery-title" style="text-align: center">其他合格证明</h3>
                            <input type="hidden" name="makeupInfo.photoData[8].documentType" value="O">
                            <input type="hidden" id="pathO" name="makeupInfo.photoData[8].path">
                        </a>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
<div class="am-g am-g-collapse">
    <div class="am-u-sm-4 am-u-sm-offset-4" style="margin-top: 10%">
        <label class="am-checkbox am-success" style="color: #0da522;margin-left: 15px">
            <input type="checkbox" value="1" data-am-ucheck name="apply" checked id="apply">申请检测
        </label>
    </div>
</div>
<div class="am-g am-g-collapse">
    <div class="am-u-sm-10 am-u-sm-offset-1">
        <button type="button" class="am-btn  am-round sumBut"
                style="width:100%;margin-top: 2%;outline:none;color: white" onclick="add()">提交</button>
    </div>
</div>
<div style="height: 50px;"></div>
<div id="toast" style="display: none">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i id="icon" class="weui_icon_toast weui-icon-success-no-circle"></i>
        <p id="msg" class="weui_toast_content">提交成功</p>
    </div>
</div>

</body>
</html>
<script src="${ctx}/grid/makeupAdd.js" type="text/javascript"></script>
