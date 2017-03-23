require(['jquery', 'bootstrap', 'common', 'echarts','bootstrapDialog', 'adminlte', 'addTabs'], function($, bootstrap, common, echarts, bootstrapDialog){

    $(function(){

        // 设置快捷栏的数量
        $.post(ctx + "/quick/quickNum", null, function(data){
            if (data){
                //$("#productInfoNum").html(data.productInfoNum);
                //$("#carInfoNum").html(data.carInfoNum);
                //$("#customInfoNum").html(data.customInfoNum);
                //$("#customFeedbackNum").html(data.customFeedbackNum);
            }
        });

        // 点击显示或隐藏左侧菜单栏时。 重新设置grid尺寸
        $("body [data-toggle='offcanvas']").on("click", function(){
            // 先触发
            $(this).toggleClass("sidebar-toggle2");
            // 重置当前页面grid宽度
            common.resizeGrid(common._width());

        });
        // 风格切换
        $("#style1").click(function(){
            $.post(ctx+ "/user/saveCustomStyle", {customStyle:"1"}, function(data){
               if(data && data.status){
                   window.location.reload();
               } else{
                   alert("更新失败");
               }
            });
        });
        $("#style2").click(function(){
            $.post(ctx+ "/user/saveCustomStyle", {customStyle:"2"}, function(data){
                if(data && data.status){
                    window.location.reload();
                } else{
                    alert("更新失败");
                }
            });
        });
        $("#style3").click(function(){
            $.post(ctx+ "/user/saveCustomStyle", {customStyle:"3"}, function(data){
                if(data && data.status){
                    window.location.reload();
                } else{
                    alert("更新失败");
                }
            });
        });

    });

    // 初始标签页
    $('#tabs').addtabs({monitor:'.sidebar-menu'});
    // 改密码
    $("#changePassword").click(function(){
        common.customSubmit({
            url:ctx + "/user/changePassword?userId=" + $(this).attr("data-user-id"),
            title:'<spring:message code="User.func.change.password"/>'
        });
    });
    // 清除服务器缓存
    $("#clearRedisCache").click(function(){
        bootstrapDialog.show({
            title: "提示",
            type: bootstrapDialog.TYPE_DEFAULT,
            draggable: true,
            closable: false,
            message: "您确定要清除服务器缓存吗?",
            buttons: [{
                label: '取消',
                action: function (dialog) {
                    dialog.close();
                }
            },{
                label: '确定',
                action: function (dialog) {
                    dialog.close();
                    jQuery.post(ctx + "/user/clearCache", function(data){
                        common.customAlert("清除缓存结果", data.msg);
                    });
                }
            }]
        });
    });

});