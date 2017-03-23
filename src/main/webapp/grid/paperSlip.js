
$(function () {
    $("#photo").click(function () {
        wx.chooseImage({
            count: 1,
            success: function (res) {
                var img1 = res.localIds[0];
                window.location.href = ctx + "/paperSlip/photographUI?imgId=" + img1;
            }
        });
    });

    $("#save").click(function () {
        var imgs = new Array();
        $("input[name='imgId']").each(function (index) {
            var thisObj = $(this);
            wx.uploadImage({
                localId: $(this).val(), // 需要上传的图片的本地ID，由chooseImage接口获得
                isShowProgressTips: 1, // 默认为1，显示进度提示
                success: function (res) {
                    var photo={};
                    photo.path=res.serverId;
                    photo.stockTime=$(thisObj).next().val();
                    imgs.push(photo); // 返回图片的服务器端ID
                    if(index==$("input[name='imgId']").length-1){
                        $.ajax({
                            url: ctx + "/paperSlip/addImgs",
                            type: "post",
                            dataType: "json",
                            data: JSON.stringify(imgs),
                            contentType : 'application/json;charset=utf-8',
                            success: function (data) {
                                if (data.status) {
                                    window.location.href = ctx + "/paperSlip/list";
                                }
                            }
                        });
                    }
                }
            });
        });

    })
});