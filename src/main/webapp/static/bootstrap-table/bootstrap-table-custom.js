/*
 自定义一些bootstrap table 变量
 */
define(['jquery','exports', 'bootstrapTable_LANG'], function ($, exports) {
    $(function(){
        // 初始化相关数据
        var height= $(window).height() - 215; //页面高度减去
        var width = $(".tab-content").width();//重新获得datagrid宽度
        var pageSize = parseInt((height - 110) / 35); // 获得与浏览器相匹配的页数

        // 修改bootstrap table defaults变量
        $.extend($.fn.bootstrapTable.defaults,
            {
                method: 'post',              // 默认 post 方法
                clickToSelect: true,         // 点击就选择
                sidePagination: 'server',    // 服务端分页
                pagination: true,            // 显示分页工具条
                pageList:[pageSize,30,50], // 页数
                pageSize:pageSize,   // 默认每页显示多少条
                sortOrder: "desc",           // 默认从大到小
                height:height,       // 默认高度
                width:width,          // 默认宽度
                striped:true        // 间隔颜色
            });
        // 点击显示或隐藏左侧菜单栏时。 重新设置grid尺寸, 延迟400毫秒执行
        $("body [data-toggle='offcanvas']").on("click", function(){
            setTimeout('$(\".gridContainer\").bootstrapTable(\'resetView\')',400);
            $(this).toggleClass("sidebar-toggle2");
        });

        $("#style1").click(function(){
            $(".logo").addClass("logoBg");
            $(".bannerWhite").removeClass("bannerWhite").addClass("bannerBlank");
            $(".logo-lg > img").attr("src", ctx + "/static/img/logo-blue.png")
        });
        $("#style2").click(function(){
            $(".logo").removeClass("logoBg");
            $(".bannerBlank").removeClass("bannerBlank").addClass("bannerWhite");
            $(".logo-lg > img").attr("src", ctx + "/static/img/logo-white.png")
        });
        $("#style3").click(function(){
            $(".logo").removeClass("logoBg");
            $(".bannerBlank").removeClass("logoBg").addClass("bannerWhite");
            $(".logo-lg > img").attr("src", ctx + "/static/img/logo-white.png")
        });

        // 导出高度
        exports.height = height;
    });

});