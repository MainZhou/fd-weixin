/**
 * Created by Administrator on 2016/9/28.
 */
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
    if(current.isValid){
        $(current.element).parent().parent().eq(0).attr("class","weui_cell");
    }else{
        $(current.element).parent().parent().eq(0).attr("class","weui_cell weui_cell_warn");
    }
});







var index;
var checkIndex;
$(function () {
    index = GetQueryString("index");
    checkIndex = GetQueryString("checkIndex");
    findType();
    $("#typeCode").select2();
    $("#typeCode").change(function () {
        $("#typeName").val($('#typeCode option:selected').text());
    });
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
function changePage(url) {
    window.location.href = url + "?index=" + index + "&checkIndex=" + checkIndex;
}
var area1 = new LArea();
area1.init({
    'trigger': '#demo2',
    'valueTo': '#value2',
    'keys': {
        id: 'value',
        name: 'text'
    },
    'type': 2,
    'data': [provs_data, citys_data, dists_data]
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
function findType() {
    $.ajax({
        url: ctx + "/makeup/findType",
        type: "post",
        success: function (data) {
            var str = '<option value="">--请查询品种名--</option>';
            $.each(data.resultMap.typeInfo, function (index) {
                str += '<option value="' + this.typeCode + '">' + this.typeName + '</option>';
            });
            $("#typeCode").html(str);
        }
    })
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
function saveThis(){
    var apply;
    var url = ctx + "/makeup/makeupAdd";
    if ($("#apply").is(':checked')) {
        apply=0;
    } else {
        apply=1;
    }
    url += "?apply="+apply;
    var photoData = new Array();
    $(".chooseImage").each(function (e,index) {
        var path = $($(this).children("input").get(1)).val();
        if(path!=""){
            var documentType = $($(this).children("input").get(0)).val();
            photoData.push({documentType: documentType,path: path});
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
    makeupInfo.typeName=$("#typeName").val();
    makeupInfo.weight=$("#weight").val();
    makeupInfo.price=$("#price").val();
    makeupInfo.sourcePlace=$("#demo2").val();
    makeupInfo.sourcePlaceId=$("#value2").val();
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