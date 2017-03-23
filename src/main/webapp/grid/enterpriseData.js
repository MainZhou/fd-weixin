//本地图片地址
var enterpriseIdImg = ""; // 社会信用代码
var idPhoto1Img = ""; // 身份证正面
var idPhoto2Img = ""; // 身份证背面

$(function () {
    $("#enterpriseId").click(function(){
        wx.chooseImage({
            count: 1,
            success: function (res) {
                enterpriseIdImg = res.localIds[0];
                $("#enterpriseIdImg").attr("src", enterpriseIdImg);
                    wx.uploadImage({
                        localId: enterpriseIdImg, // 需要上传的图片的本地ID，由chooseImage接口获得
                        isShowProgressTips: 1, // 默认为1，显示进度提示
                        success: function (res) {
                            $("#enterpriseFromWx").val(res.serverId);// 返回图片的服务器端ID
                        }
                    });
            }, cancel: function () {
                idPhoto2Img = "";
                $("#enterpriseIdImg").attr("src", ctx + '/static/img/pic.png');
                $("#enterpriseFromWx").val("");
            }
        });
    });

    $("#idPhoto1").click(function(){
        wx.chooseImage({
            count: 1,
            success: function (res) {
                idPhoto1Img = res.localIds[0];
                $("#idPhoto1Img").attr("src", idPhoto1Img);
                wx.uploadImage({
                    localId: idPhoto1Img, // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        $("input[name='production']").val(res.serverId)
                        $("#idPhoto1FromWx").val(res.serverId);// 返回图片的服务器端ID
                    }
                });
            }, cancel: function () {
                idPhoto1Img = "";
                $("#idPhoto1Img").attr("src", ctx + '/static/img/pic.png');
                $("#idPhoto1FromWx").val("");
            }
        });
    });

    $("#idPhoto2").click(function(){
        wx.chooseImage({
            count: 1,
            success: function (res) {
                idPhoto2Img = res.localIds[0];
                $("#idPhoto2Img").attr("src", idPhoto2Img);
                wx.uploadImage({
                    localId: idPhoto2Img, // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        $("#idPhoto2FromWx").val(res.serverId);// 返回图片的服务器端ID
                    }
                });
            }, cancel: function () {
                idPhoto2Img = "";
                $("#idPhoto2Img").attr("src", ctx + '/static/img/pic.png');
                $("#idPhoto2FromWx").val("");
            }
        });
    });

});

function add() {
    $form = $("form");
    $form.unbind("valid.form");//unbind验证,防止重复提交
    $form.bind("valid.form", function (e, form) {//bind验证规则
        saveThis();
    });
    //触发验证
    $form.trigger("validate");

}
function saveThis(){
    $.ajax({
        type: "POST",
        url: ctx +"/fillInfo",
        data: $('form').serialize(),// 要提交的表单
        success: function (data) {
            if (data.status == true) {
                $("#icon").attr("class", "weui_icon_toast weui-icon-success-no-circle");
                $("#msg").html("提交成功");
                $('#toast').show();
                setTimeout(function () {
                    $('#toast').hide();
                    window.location.href = ctx + "/makeup/list?index=0&checkIndex=0";
                }, 2000);
            } else {
                $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
                $("#msg").html(data.msg);
                $('#toast').show();
                setTimeout(function () {
                    $('#toast').hide();
                }, 2000);
            }
        }
    });
}


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
        "typeCode": "required;"
        , "weight": "required;weightStr"
        , "price": "priceStr"
        , "sourcePlace": "required"
        , "sourcePlaceUnit": "length[~20]"
    }
}).on('validation', function (e, current) {
    //字段验证失败后，添加错误高亮
    if(current.isValid){
        $(current.element).parent().parent().eq(0).attr("class","weui_cell");
    }else{
        $(current.element).parent().parent().eq(0).attr("class","weui_cell weui_cell_warn");
    }
});