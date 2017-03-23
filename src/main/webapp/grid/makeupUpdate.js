$('#makeupForm').validator({
    stopOnError: false,
    msgMaker: false,
    rules: {
        weightStr: [/^([0-9]{1,7}|\d{1,7}\.\d{1,2})$/, "净重输入值范围为:0～9999999.99！"],
        priceStr: [/^([0-9]{1,7}|\d{1,7}\.\d{1,2})$/, "单价输入值范围为:0～9999999.99！"]
    },
    invalid: function (form, errors) {

        var html = "";
        $.map(errors, function (msg) {
            html += msg;
        });
//            alert(html);
    },
    fields: {
        "typeCode": "required;"
        , "weight": "required;weightStr"
        , "price": "required;priceStr"
        , "sourcePlace": "required"
        , "sourcePlaceUnit": "length[~20]"
    }
}).on('validation', function (e, current) {
    //字段验证失败后，添加错误高亮
    if (current.isValid) {
        $(current.element).parent().parent().eq(0).attr("class", "weui_cell");
    } else {
        $(current.element).parent().parent().eq(0).attr("class", "weui_cell weui_cell_warn");
    }
});
var area2 = new LArea();
area2.init({
    'trigger': '#sourcePlace',
    'valueTo': '#sourcePlaceId',
    'keys': {
        id: 'value',
        name: 'text'
    },
    'type': 2,
    'data': [provs_data, citys_data, dists_data]
});
var index;
var checkIndex;
var dataIndex;
$(function () {
    index = GetQueryString("index");
    checkIndex = GetQueryString("checkIndex");
    dataIndex = GetQueryString("dataIndex");
    findType();
    $("#typeCode").select2();
    $("#typeCode").change(function () {
        $("#typeName").val($('#typeCode option:selected').text());
    });
    info();
    //初始化拍照点击事件
    $(".chooseImage").click(function () {
        var thisObj = $(this);
        wx.chooseImage({
            count: 1,
            success: function (res) {
                var imgPath = res.localIds[0];
                $($(thisObj).children("img").get(0)).attr("src",imgPath);
                wx.uploadImage({
                    localId: imgPath.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回图片的服务器端ID
                        $($(thisObj).children("input").get(1)).val(serverId);
                    }
                });
            }, cancel: function (res) {
                $($(thisObj).children("img").get(0)).attr("src",ctx+'/static/img/pic.png');
                $($(thisObj).children("input").get(1)).val("");
            }
        });
    });
});
function findType() {
    $.ajax({
        url: ctx + "/makeup/findType",
        type: "post",
        async: false,
        success: function (data) {
            var str = '<option value="">--请查询品种名--</option>';
            $.each(data.resultMap.typeInfo, function (index) {
                str += '<option value="' + this.typeCode + '">' + this.typeName + '</option>';
            });
            $("#typeCode").html(str);
        }
    })
}
function changePage(url) {
    window.location.href = url + "?index=" + index + "&checkIndex=" + checkIndex;
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
function add() {
    $form = $("#makeupForm");
    $form.unbind("valid.form");//unbind验证,防止重复提交
    $form.bind("valid.form", function (e, form) {//bind验证规则
        saveThis();
    });
    //触发验证
    $form.trigger("validate");

}
function info() {
    $.ajax({
            type: "POST",
            url: ctx + "/makeup/updateInfo?index=" + index + "&checkIndex=" + checkIndex + "&dataIndex=" + dataIndex,
            async: false,
            success: function (data) {
                if (data.status) {
                    if (data.resultMap.verifyStatus == 'N') {
                        $("#verifyState").css("display","inline-block");
                        $("#verifyRemark").html(data.resultMap.verifyRemark);
                    }else{
                        $("#verifyState").css("display","none");
                    }
                    $("#typeCode").val(data.resultMap.detailData[0].typeCode).trigger("change");
                    $("#detailId").val(data.resultMap.detailData[0].detailId);
                    $("#typeName").val(data.resultMap.detailData[0].typeName);
                    $("#weight").val(data.resultMap.detailData[0].weight);
                    $("#price").val(data.resultMap.detailData[0].price);
                    $("#sourcePlace").val(data.resultMap.detailData[0].sourcePlace);
                    $("#sourcePlaceId").val(data.resultMap.detailData[0].sourcePlaceId);
                    $("#sourcePlaceUnit").val(data.resultMap.detailData[0].sourcePlaceUnit);
                    $.each(data.resultMap.detailData[0].photoData, function (index) {
                        if(this.documentType=='W'){
                            $("#img1").attr("src",baseUrl+"/"+this.path);
                            $("#guidW").val(this.guid);
                            $("#oldPathW").val(this.path);
                            $("#pathW").val("-1");
                        }else if(this.documentType=='L'){
                            $("#img2").attr("src",baseUrl+"/"+this.path);
                            $("#guidL").val(this.guid);
                            $("#oldPathL").val(this.path);
                            $("#pathL").val("-1");
                        }else if(this.documentType=='Y'){
                            $("#img3").attr("src",baseUrl+"/"+this.path);
                            $("#guidY").val(this.guid);
                            $("#oldPathY").val(this.path);
                            $("#pathY").val("-1");
                        }else if(this.documentType=='N'){
                            $("#img4").attr("src",baseUrl+"/"+this.path);
                            $("#guidN").val(this.guid);
                            $("#oldPathN").val(this.path);
                            $("#pathN").val("-1");
                        }else if(this.documentType=='C'){
                            $("#img5").attr("src",baseUrl+"/"+this.path);
                            $("#guidC").val(this.guid);
                            $("#oldPathC").val(this.path);
                            $("#pathC").val("-1");
                        }else if(this.documentType=='B'){
                            $("#img6").attr("src",baseUrl+"/"+this.path);
                            $("#guidB").val(this.guid);
                            $("#oldPathB").val(this.path);
                            $("#pathB").val("-1");
                        }else if(this.documentType=='A'){
                            $("#img7").attr("src",baseUrl+"/"+this.path);
                            $("#guidA").val(this.guid);
                            $("#oldPathA").val(this.path);
                            $("#pathA").val("-1");
                        }else if(this.documentType=='M'){
                            $("#img8").attr("src",baseUrl+"/"+this.path);
                            $("#guidM").val(this.guid);
                            $("#oldPathM").val(this.path);
                            $("#pathM").val("-1");
                        }else if(this.documentType=='O'){
                            $("#img9").attr("src",baseUrl+"/"+this.path);
                            $("#guidO").val(this.guid);
                            $("#oldPathO").val(this.path);
                            $("#pathO").val("-1");
                        }else if(this.documentType=='J'){
                            $("#apply").attr("checked","checked");
                        }
                    })
                }
            }

        });
}
function saveThis() {
    var apply;
    var url = ctx + "/makeup/update";
    if ($("#apply").is(':checked')) {
        apply=0;
    } else {
        apply=1;
    }
    url += "?apply="+apply;
    var photoData = new Array();
    $(".chooseImage").each(function (e,index) {
        var path = $($(this).children("input").get(1)).val();
        if(path!='-1'){
            var documentType = $($(this).children("input").get(0)).val();
            var guid = $($(this).children("input").get(2)).val();
            var oldPath = $($(this).children("input").get(3)).val();
            photoData.push({documentType: documentType,path: path,guid:guid,oldPath:oldPath});
        }
    });
    if(photoData.length==0 && apply==1){
        $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
        $("#msg").html("没有产品证明时申请检测必选！");
        $('#toast').show();
        setTimeout(function () {
            $('#toast').hide();
        }, 2000);
        return false;
    }
    var makeupInfo={};
    makeupInfo.typeCode=$("#typeCode").val();
    makeupInfo.detailId=$("#detailId").val();
    makeupInfo.typeName=$("#typeName").val();
    makeupInfo.weight=$("#weight").val();
    makeupInfo.price=$("#price").val();
    makeupInfo.sourcePlace=$("#sourcePlace").val();
    makeupInfo.sourcePlaceId=$("#sourcePlaceId").val();
    makeupInfo.sourcePlaceUnit=$("#sourcePlaceUnit").val();
    makeupInfo.photoData=photoData;
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(makeupInfo),
        async: false,
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        success: function (data) {
            if(data.status){
                if (data.resultMap.status.code == "1") {
                    $("#icon").attr("class", "weui_icon_toast weui-icon-success-no-circle");
                    $("#msg").html("提交成功");
                    $('#toast').show();
                    setTimeout(function () {
                        $('#toast').hide();
                        window.location.href = ctx + "/makeup/list?index=0&checkIndex=0";
                    }, 2000);
                } else {
                    $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
                    $("#msg").html("提交失败");
                    $('#toast').show();
                    setTimeout(function () {
                        $('#toast').hide();
                    }, 2000);
                }
            }else{
                $("#icon").attr("class", "weui_icon_msg weui_icon_warn");
                $("#msg").html("提交失败");
                $('#toast').show();
                setTimeout(function () {
                    $('#toast').hide();
                }, 2000);
            }

        }
    });
}