var domainUrl = "/scanCode";
var code;
jQuery(function () {
    code= GetQueryString("wholesalerId");
    obtainData(code);
});
function infoUrl(index) {
    window.location.href=ctx + "/xiaofeiInfo.jsp?wholesalerId="+code+"&index="+index;
}
function scanCodeFun() {
    wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: function (res) {
            var result = res.resultStr;
            var i = result.indexOf("QRCode=");
            if(i!=-1){
                result=result.substring(i+7,result.length);
            }
            obtainData(result);
        }, cancel: function (res) {
            $('#toast').show();
            setTimeout(function () {
                $('#toast').hide();
            }, 1500);
        }
    });
}
function obtainData(wholesalerId) {
    $.ajax({
        url: ctx + domainUrl + "/scanCode?wholesalerId=" + wholesalerId,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.status) {
                $("#marketName").html(result.resultMap.marketName);
                $("#wholesalerName").html(result.resultMap.wholesalerName);
                $("#boothNo").html(result.resultMap.boothNo);
                $("#typeNa").html(result.resultMap.typeNa);
                var str = "";
                $.each(result.resultMap.datas, function (index) {
                    if (this.photoData == null || this.photoData.imgPath == null || this.photoData.imgPath == '') {
                        str += '<div class="weui_panel_bd"' +
                            '       style="border: 1px solid silver;width: 90%;margin-left: 5%;background-color: white;margin-top: 2%">' +
                            '       <a href="javascript:;" onclick="infoUrl('+index+')" >' +
                            '           <div class="weui_media_box weui_media_appmsg">' +
                            '               <div class="weui_media_hd" style="width: 50%;height: 80px;text-align: left">' +
                            '                   <span class="weui_media_desc" style="font-size: 16px;color: black">' + this.typeName + '</span>' +
                            '                   <span class="weui_media_desc"' +
                            '                      style="font-size: 15px;color: #676767;margin-top: 15px;">' + this.stockTime + '</span>' +
                            '                 <span class="weui_media_desc"' +
                            '                     style="font-size: 15px;color: #676767;margin-top: 5px;">' + this.sourcePlace + '</span>' +
                            '               </div>' +
                            '               <div class="weui_media_bd" style="width: 50%;height: 80px;text-align: center;">' +
                            '                   <div style="margin-top: 10px">' +
                            '                       <span>已提交市场检测</span><br>' +
                            '                       <span style="color: red">(无质量合格证明)</span>' +
                            '                   </div>' +
                            '               </div>' +
                            '           </div>' +
                            '       </a>' +
                            '</div>';
                    } else {
                        str += '<div class="weui_panel_bd"' +
                            '       style="border: 1px solid silver;width: 90%;margin-left: 5%;background-color: white;margin-top: 2%">' +
                            '       <a href="javascript:;" onclick="infoUrl('+index+')">' +
                            '           <div class="weui_media_box weui_media_appmsg">' +
                            '               <div class="weui_media_hd" style="width: 50%;height: 80px;text-align: left">' +
                            '                   <span class="weui_media_desc" style="font-size: 16px;color: black">' + this.typeName + '</span>' +
                            '                   <span class="weui_media_desc"' +
                            '                      style="font-size: 15px;color: #676767;margin-top: 15px;">' + this.stockTime + '</span>' +
                            '                 <span class="weui_media_desc"' +
                            '                     style="font-size: 15px;color: #676767;margin-top: 5px;">' + this.sourcePlace + '</span>' +
                            '               </div>' +
                            '               <div class="weui_media_bd" style="width: 50%;height: 80px">' +
                            '                   <div style="height: 70%">' +
                            '                       <img class="weui_media_appmsg_thumb"' +
                            '                           src="'+baseUrl+'/' + this.photoData.imgPath + '">' +
                            '                   </div>' +
                            '                   <div style="height: 30%;text-align: center;font-size: 14px;color: black">';
                        if (this.photoData.voucherType == 'W') {
                            str += '                       <span>无公害农产品证</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        } else if (this.photoData.voucherType == 'L') {
                            str += '                       <span>绿色食品证</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        } else if (this.photoData.voucherType == 'Y') {
                            str += '                       <span>有机农产品</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        } else if (this.photoData.voucherType == 'N') {
                            str += '                       <span>农产品地理标志</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        } else if (this.photoData.voucherType == 'C') {
                            str += '                       <span>产地证明</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        } else if (this.photoData.voucherType == 'J') {
                            str += '                       <span>检测报告</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        } else if (this.photoData.voucherType == 'B') {
                            str += '                       <span>购货凭证</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        }else if (this.photoData.voucherType == 'A') {
                            str += '                       <span>动物检疫证</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        }else if (this.photoData.voucherType == 'M') {
                            str += '                       <span>肉品检验证</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        }else if (this.photoData.voucherType == 'O') {
                            str += '                       <span>其他凭证</span>' +
                                '                   </div>' +
                                '               </div>' +
                                '           </div>' +
                                '       </a>' +
                                '   </div>';
                        }
                    }
                });
                $("#dataHt").html(str);
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
