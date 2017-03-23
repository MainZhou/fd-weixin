var index;
var checkIndex;
var timeBut;
var checkBut;
$(function () {
    index = GetQueryString("index");
    checkIndex = GetQueryString("checkIndex");
    obtainData(index, checkIndex);
    //判断月份查询
    if (index == 0) {
        $("#frontOneMonth").css("color", "black");
        timeBut = $("#frontOneMonth");
    } else if (index == 1) {
        $("#frontTwoMonth").css("color", "black");
        timeBut = $("#frontTwoMonth");
    } else if (index == 2) {
        $("#frontThreeeMonth").css("color", "black");
        timeBut = $("#frontThreeeMonth");
    }
    //判断类型查询
    if (checkIndex == 0) {
        $("#notCheck").css("color", "#0da522");
        $("#notCheck").parent().css("border-bottom", "1px solid #0da522");
        checkBut = $("#notCheck");
    } else if (checkIndex == 1) {
        $("#checking").css("color", "#0da522");
        $("#checking").parent().css("border-bottom", "1px solid #0da522");
        checkBut = $("#checking");
    } else if (checkIndex == 2) {
        $("#ontChecked").css("color", "#0da522");
        $("#ontChecked").parent().css("border-bottom", "1px solid #0da522");
        checkBut = $("#ontChecked");
    } else if (checkIndex == 3) {
        $("#checked").css("color", "#0da522");
        $("#checked").parent().css("border-bottom", "1px solid #0da522");
        checkBut = $("#checked");
    }
    $("#frontOneMonth").click(function () {
        $(timeBut).css("color", "silver");
        timeBut = this;
        $(this).css("color", "black");
        index = 0;
        obtainData(index, checkIndex);
    });
    $("#frontTwoMonth").click(function () {
        $(timeBut).css("color", "silver");
        timeBut = this;
        $(this).css("color", "black");
        index = 1;
        obtainData(index, checkIndex);
    });
    $("#frontThreeeMonth").click(function () {
        $(timeBut).css("color", "silver");
        timeBut = this;
        $(this).css("color", "black");
        index = 2;
        obtainData(index, checkIndex);
    });
    $("#notCheck").click(function () {
        $(checkBut).css("color", "silver");
        $(checkBut).parent().css("border-bottom", "1px solid #fff");
        checkBut = this;
        $(this).css("color", "#0da522");
        $(this).parent().css("border-bottom", "1px solid #0da522");
        checkIndex = 0;
        obtainData(index, checkIndex);
    });
    $("#checking").click(function () {
        $(checkBut).css("color", "silver");
        $(checkBut).parent().css("border-bottom", "1px solid #fff");
        checkBut = this;
        $(this).css("color", "#0da522");
        $(this).parent().css("border-bottom", "1px solid #0da522");
        checkIndex = 1;
        obtainData(index, checkIndex);
    });
    $("#ontChecked").click(function () {
        $(checkBut).css("color", "silver");
        $(checkBut).parent().css("border-bottom", "1px solid #fff");
        checkBut = this;
        $(this).css("color", "#0da522");
        $(this).parent().css("border-bottom", "1px solid #0da522");
        checkIndex = 2;
        obtainData(index, checkIndex);
    });
    $("#checked").click(function () {
        $(checkBut).css("color", "silver");
        $(checkBut).parent().css("border-bottom", "1px solid #fff");
        checkBut = this;
        $(this).css("color", "#0da522");
        $(this).parent().css("border-bottom", "1px solid #0da522");
        checkIndex = 3;
        obtainData(index, checkIndex);
    });
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
function obtainData(index, checkIndex) {
    $.ajax({
        url: ctx + "/makeup/list",
        type: "post",
        data: {"index": index, "checkIndex": checkIndex},
        dataType: "json",
        success: function (result) {
            if (result.status) {
                if(result.resultMap.status.code=='1'){
                var str = "";
                $.each(result.resultMap.data, function (index) {
                    if (checkIndex == 0 || checkIndex == 2) {
                        str += '  <div class="weui_panel_bd"' +
                            '           style="border: 1px solid silver;width: 90%;margin-left: 5%;background-color: white;margin-top: 5%">' +
                            '           <a href="javascript:;" onclick="changePageTwo(\'' + ctx + '/makeup/updateUI\',\'' + index + '\')">' +
                            '               <div class="weui_media_box weui_media_appmsg">' +
                            '                   <div class="weui_media_hd" style="width: 50%;height: 80px;text-align: left">' +
                            '                       <span class="weui_media_desc" style="font-size: 16px;color: black">' + this.detailData[0].typeName + '</span>' +
                            '                       <span class="weui_media_desc"' +
                            '                           style="font-size: 15px;color: #676767;margin-top: 15px;">重量：' + (this.detailData[0].weight ) + 'kg</span>' +
                            '                       <span class="weui_media_desc"' +
                            '                           style="font-size: 15px;color: #676767;margin-top: 5px;">单价：' + (this.detailData[0].price ) + '元/kg</span>' +
                            '                   </div>';

                    } else {
                        str += '  <div class="weui_panel_bd"' +
                            '           style="border: 1px solid silver;width: 90%;margin-left: 5%;background-color: white;margin-top: 5%">' +
                            '           <a href="javascript:;" onclick="changePageTwo(\'' + ctx + '/makeup/infoUI\',\'' + index + '\')">' +
                            '               <div class="weui_media_box weui_media_appmsg">' +
                            '                   <div class="weui_media_hd" style="width: 50%;height: 80px;text-align: left">' +
                            '                       <span class="weui_media_desc" style="font-size: 16px;color: black">' + this.detailData[0].typeName + '</span>' +
                            '                       <span class="weui_media_desc"' +
                            '                           style="font-size: 15px;color: #676767;margin-top: 15px;">重量：' + (this.detailData[0].weight ) + 'kg</span>' +
                            '                       <span class="weui_media_desc"' +
                            '                           style="font-size: 15px;color: #676767;margin-top: 5px;">单价：' + (this.detailData[0].price ) + '元/kg</span>' +
                            '                   </div>';
                    }
                    var path="";
                    var documentTypeStr="";
                    $.each(this.detailData[0].photoData, function (ind) {
                        if(this.path!="" && this.path!=null){
                            path=this.path;
                            if(this.documentType=="W"){
                                documentTypeStr="无公害农产品证";
                            }else if(this.documentType=="L"){
                                documentTypeStr="绿色食品证";
                            }else if(this.documentType=="Y"){
                                documentTypeStr="有机农产品";
                            }else if(this.documentType=="N"){
                                documentTypeStr="农产品地理标志";
                            }else if(this.documentType=="C"){
                                documentTypeStr="产地证明";
                            }else if(this.documentType=="B"){
                                documentTypeStr="购货凭证";
                            }else if(this.documentType=="A"){
                                documentTypeStr="动物检疫证";
                            }else if(this.documentType=="M"){
                                documentTypeStr="肉品检验证";
                            }else if(this.documentType=="O"){
                                documentTypeStr="其他凭证";
                            }else{
                                documentTypeStr="检测报告";
                            }
                        }
                    });
                    if(path==""){
                        str +=
                            '                   <div class="weui_media_bd" style="width: 50%;height: 100px;text-align: center;">' +
                            '                       <div style="font-size: 15px;margin-top: 25px">' +
                            '                           <span>已提交市场检测</span><br>' +
                            '                           <span style="color: red">(无质量合格证明)</span>' +
                            '                       </div>' +
                            '                   </div>' +
                            '               </div>' +
                            '           </a>' +
                            '       </div>';
                    }else{
                        str +=
                            '                   <div class="weui_media_bd" style="width: 50%;height: 100px">' +
                            '                       <div style="height: 80%">' +
                            '                           <figure data-am-widget="figure" class="am am-figure am-figure-default " >' +
                            '                               <img class="weui_media_appmsg_thumb" style="height: 80px" src="' + baseUrl + '/' + path + '" >' +
                            '                           </figure>' +
                            '                       </div>' +
                            '                       <div style="height: 30%;text-align: center;font-size: 14px;color: black">' +
                            '                           <span>'+documentTypeStr+'</span>' +
                            '                       </div>' +
                            '                   </div>' +
                            '               </div>' +
                            '           </a>' +
                            '       </div>';
                    }
                });
                $("#table1").html(str);
                $.AMUI.figure.init();
                }
            }
        }
    })
}
function changePage(url) {
    window.location.href = url + "?index=" + index + "&checkIndex=" + checkIndex ;
}
function changePageTwo(url, dataIndex) {
    window.location.href = url + "?index=" + index + "&checkIndex=" + checkIndex +"&dataIndex="+dataIndex;
}