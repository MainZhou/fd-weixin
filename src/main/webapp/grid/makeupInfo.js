var index;
var checkIndex;
var dataIndex;
$(function () {
    index = GetQueryString("index");
    checkIndex = GetQueryString("checkIndex");
    dataIndex = GetQueryString("dataIndex");
    obtainData(index,checkIndex,dataIndex);
});
function changePage(url) {
    window.location.href=url+"?index="+index+"&checkIndex="+checkIndex;
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
function obtainData(index,checkIndex,dataIndex) {
    $.ajax({
        url: ctx + "/makeup/info",
        type: "post",
        data: {"index": index, "checkIndex": checkIndex,"dataIndex":dataIndex},
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.status) {
                var str = "";
                var str2="";
                $("#typeName").html(result.resultMap.typeName);
                $("#weight").html(result.resultMap.weight);
                $("#price").html(result.resultMap.price);
                $("#sourcePlace").html(result.resultMap.sourcePlace);
                $("#sourcePlaceUnit").html(result.resultMap.sourcePlaceUnit);
                $.each(result.resultMap.photoData, function (index) {
                    if (this != null && this.imgName != null && this.imgName != '') {
                        if (this.voucherType == 'W') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="无公害农产品证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">无公害农产品证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'L') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="绿色食品证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">绿色食品证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'Y') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="有机农产品" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">有机农产品</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'N') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="农产品地理标志" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">农产品地理标志</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'C') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="产地证明" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">产地证明</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'B') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="购货凭证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">购货凭证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'A') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="动物检疫证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">动物检疫证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'M') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="肉品检验证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">肉品检验证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'O') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgName+'" alt="其他凭证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">其他凭证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'J') {
                            if(this.detectTaskId!=null && this.detectTaskId!=""){
                                $("#deTable").attr("display","inline-block");
                                $.ajax({
                                    url: ctx + "/makeup/detectInfo",
                                    type: "post",
                                    data: {"detectTaskId": this.detectTaskId},
                                    dataType: "json",
                                    async: false,
                                    success: function (result) {
                                        $("#img").attr("src",baseUrl+"/"+result.data.picUrl);
                                        $("#dcName").html(result.data.dcName);
                                        $("#detectTaskCode").html(result.data.detectTaskCode);
                                        $("#typeNameD").html(result.data.typeName);
                                        $("#applyer").html(result.data.applyer);
                                        $("#marketArea").html(result.data.marketArea+result.data.boothNO);
                                        $("#areaname").html(result.data.areaname);
                                        $("#entrancePw").html(result.data.entrancePw);
                                        $("#realName").html(result.data.realName);
                                        $("#detecterName").html(result.data.detecterName);
                                        $("#samplingTime").html(result.data.samplingTime);
                                        $("#detectResult").html(result.data.detectResult);
                                        $("#operateTime").html(result.data.operateTime);
                                    }
                                })
                            }
                        }
                    }
                });
                $("#images").html(str);
            }
        }
    })
}
