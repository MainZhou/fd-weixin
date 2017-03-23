var domainUrl = "/scanCode";
var code;
var index;
jQuery(function () {
    code = GetQueryString("wholesalerId");
    index = GetQueryString("index");
    obtainData(code, index);

});
function obtainData(wholesalerId, index) {
    $.ajax({
        url: ctx + domainUrl + "/scanCodeInfo?wholesalerId=" + wholesalerId + "&index=" + index,
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.status) {
                var str = "";
                $("#typeName").html(result.resultMap.data.typeName);
                // $("#price").html(result.resultMap.data.);
                $("#sourcePlace").html(result.resultMap.data.sourcePlace);
                $("#sourcePlaceUnit").html(result.resultMap.data.sourcePlaceUnit);
                $.each(result.resultMap.data.photoDatas, function () {
                    if (this != null && this.imgPath != null && this.imgPath != '') {
                        if (this.voucherType == 'W') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="无公害农产品证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">无公害农产品证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'L') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="绿色食品证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">绿色食品证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'Y') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="有机农产品" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">有机农产品</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'N') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="农产品地理标志" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">农产品地理标志</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'C') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="产地证明" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">产地证明</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        } else if (this.voucherType == 'J') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="检测报告" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">检测报告</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'B') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="购货凭证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">购货凭证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'A') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="动物检疫证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">动物检疫证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'M') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="肉品检验证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">肉品检验证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }else if (this.voucherType == 'O') {
                            str += '<li>' +
                                '   <div class="am-gallery-item">' +
                                '       <a href="javascript:;" class="">' +
                                '           <img src="'+baseUrl+'/'+this.imgPath+'" alt="其他凭证" style="height: 80px"/>' +
                                '           <h3 class="am-gallery-title" style="text-align: center">其他凭证</h3>' +
                                '       </a>' +
                                '   </div>' +
                                '</li>';
                        }
                    }
                });
                $("#images").html(str);
            }
            else {
                alert(result.msg)
            }
        }

    })
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
function infoUrl() {
    window.location.href = ctx + "/saoMa.jsp?wholesalerId=" + code;
}