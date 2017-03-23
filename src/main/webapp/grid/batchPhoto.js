/**
 * Created by Administrator on 2016/9/29.
 */
//本地图片地址
var imgId;
var index;
$(function () {
    imgId=GetQueryString("imgId");
    index=GetQueryString("index");
    if(index==null || index==""){
        index =-1;
    }
    if(imgId!=null && imgId!=""){
        $("#img1").attr("src", imgId);
    }

    $("#photoAgain").click(function () {
        wx.chooseImage({
            count: 1,
            success: function (res) {
                imgId = res.localIds[0];
                $("#img1").attr("src", imgId);
            }, cancel: function (res) {
                imgId = "";
                $("#img1").attr("src", ctx + '/static/img/pic.png');
            }
        });
    });
    $("#save").click(function () {
        if(imgId=="" || imgId ==null){
            $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
            $("#msg").html("请选择图片！");
            $('#toast').show();
            setTimeout(function () {
                $('#toast').hide();
            }, 1000);
        }else{
            var salesTime = $("#salesTime").val();
            if(salesTime=="" || salesTime ==null){
                $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
                $("#msg").html("请选择进货时间！");
                $('#toast').show();
                setTimeout(function () {
                    $('#toast').hide();
                }, 1000);
            }else{
                $.ajax({
                    url: ctx+"/batchMakeup/saveImg?img="+imgId+"&index="+index+"&salesTime="+salesTime,
                    type:"post",
                    success:function (data) {
                        if (data.status) {
                            $("#icon").attr("class", "weui_icon_toast weui-icon-success-no-circle");
                            $("#msg").html("提交成功");
                            $('#toast').show();
                            setTimeout(function () {
                                $('#toast').hide();
                                window.location.href = ctx + "/batchMakeup/list";
                            }, 1000);
                        } else {
                            $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
                            $("#msg").html("提交失败");
                            $('#toast').show();
                            setTimeout(function () {
                                $('#toast').hide();
                            }, 1000);
                        }
                    }
                });  
            }
        }
    });
});
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
