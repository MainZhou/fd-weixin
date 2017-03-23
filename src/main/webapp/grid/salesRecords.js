var index;
var typeIndex=0;
jQuery(function () {
    index = GetQueryString("index");
    var checkBut;
    obtainData(index,typeIndex);
    if (index == 0) {
        checkBut = $("#frontOneMonth");
        $(checkBut).css("color", "black");
    } else if (index == 1) {
        checkBut = $("#frontTwoMonth");
        $(checkBut).css("color", "black");
    } else {
        checkBut = $("#frontThreeeMonth");
        $(checkBut).css("color", "black");
    }
    $("#frontOneMonth").click(function () {
        $(checkBut).css("color", "silver");
        checkBut = this;
        $(this).css("color", "black");
        index = 0;
        obtainData(index,typeIndex);
    });
    $("#frontTwoMonth").click(function () {
        $(checkBut).css("color", "silver");
        checkBut = this;
        $(this).css("color", "black");
        index = 1;
        obtainData(index,typeIndex);
    });
    $("#frontThreeeMonth").click(function () {
        $(checkBut).css("color", "silver");
        checkBut = this;
        $(this).css("color", "black");
        index = 2;
        obtainData(index,typeIndex);
    });
    $('.weui_navbar_item').on('click', function () {
        $(this).addClass('weui_bar_item_on').siblings('.weui_bar_item_on').removeClass('weui_bar_item_on');
        typeIndex=$(this).attr("id");
        obtainData(index,typeIndex);
    });
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
function obtainData(index,typeIndex) {
    $.ajax({
        url: ctx + "/salesRecords/list",
        type: "post",
        data: {"index": index,"typeIndex":typeIndex},
        dataType: "json",
        success: function (result) {
            if (result.status) {
                var str = "";
                if(typeIndex==2){
                    str += '<div class="weui_panel_bd">' +
                        '       <div class="weui_media_box weui_media_appmsg">' +
                        '           <div class="weui_media_hd" style="width: 40%;height: 100px">' +
                        '               <figure data-am-widget="figure" class="am am-figure am-figure-default "' +
                        '                   data-am-figure="{  pureview: \'true\' }">'+
                        '                   <img class="weui_media_appmsg_thumb" style="height: 80px"' +
                        '                       src="'+result.resultMap.path+'">' +
                        '               </figure>' +
                        '           </div>' +
                        '           <div class="weui_media_bd">' +
                        '               <span class="weui_media_desc" style="font-size: 15px;color: black">'+result.resultMap.typeName+'</span>' +
                        '               <span class="weui_media_desc" style="font-size: 15px;color: black;margin-top: 5px;">时间：'+result.resultMap.operateTime+'</span>' +
                        '           </div>' +
                        '       </div>' +
                        '   </div>';
                }else{
                    $.each(result.resultMap.opData, function () {
                        var path="";
                        $.each(this.photoData, function () {
                            if(this.path!="" && this.path!=null){
                                path=this.path;
                            }
                        });
                        if(path==""){
                            path=baseUrl+"/weixin/static/img/zanwu.jpg"
                        }else{
                            path=baseUrl+'/'+path;
                        }
                        var typeName=this.typeName;
                        var stockTime=this.stockTime;
                        if(typeName==undefined){
                            typeName="";
                        }
                        if(stockTime==undefined){
                            stockTime="";
                        }
                        str += '<div class="weui_panel_bd">' +
                            '       <div class="weui_media_box weui_media_appmsg">' +
                            '           <div class="weui_media_hd" style="width: 40%;height: 100px">' +
                            '               <figure data-am-widget="figure" class="am am-figure am-figure-default "' +
                            '                   data-am-figure="{  pureview: \'true\' }">'+
                            '                   <img class="weui_media_appmsg_thumb" style="height: 80px"' +
                            '                       src="'+path+'" alt="'+typeName+'">' +
                            '               </figure>' +
                            '           </div>' +
                            '           <div class="weui_media_bd">' +
                            '               <span class="weui_media_desc" style="font-size: 15px;color: black">'+typeName+'</span>' +
                            '               <span class="weui_media_desc" style="font-size: 15px;color: black;margin-top: 5px;">时间：'+stockTime+'</span>' +
                            '           </div>' +
                            '       </div>' +
                            '   </div>';
                    });
                }
                $("#table1").html(str);
                $.AMUI.figure.init();
            }else{
                $("#table1").html("<div style='text-align: center'>没有数据。</div>");
                $.AMUI.figure.init();
            }
        }
    })
}