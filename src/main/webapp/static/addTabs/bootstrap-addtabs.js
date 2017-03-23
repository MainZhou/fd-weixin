/**
 * Website: http://git.oschina.net/hbbcs/bootStrap-addTabs
 *
 * Version : 0.6
 *
 * Created by joe on 2016-2-4.
 */

$.fn.addtabs = function (options) {
    obj = $(this);
    Addtabs.options = $.extend({
        content: '', //直接指定所有页面TABS内容
        close: true, //是否可以关闭
        monitor: 'body', //监视的区域
        iframeUse: true, //使用iframe还是ajax
        iframeHeight: $(document).height() - 107, //固定TAB中IFRAME高度,根据需要自己修改
        method: 'init',
        callback: function () { //关闭后回调函数
        }
    }, options || {});


    $(Addtabs.options.monitor).on('click', '[data-addtab]', function () {
        Addtabs.add({
            id: $(this).attr('data-addtab'),
            title: $(this).attr('title') ? $(this).attr('title') : $(this).html(),
            content: Addtabs.options.content ? Addtabs.options.content : $(this).attr('content'),
            url: $(this).attr('url'),
            ajax: $(this).attr('ajax') ? true : false
        });
    });

    obj.on('click', '.close-tab', function () {
        var id = $(this).prev("a").attr("aria-controls");
        Addtabs.close(id);
    });

    // 双击关闭
    obj.on('dblclick',' [role="presentation"]', function () {
        var id = $(this).find("a").attr("aria-controls");
        // 不关闭首页
        if ("home" != id){
            Addtabs.close(id);
        }
    });

    obj.on('mouseover', '.close-tab', function () {
        $(this).addClass('close');
    });

    obj.on('mouseout', '.close-tab', function () {
        $(this).removeClass('close').addClass('glyphicon-remove');
    });

    $(window).resize(function () {
        obj.find('iframe').attr('height', Addtabs.options.iframeHeight);
        Addtabs.drop();
    });

};

window.Addtabs = {
    options:{},
    add: function (opts) {
        var id = 'tab_' + opts.id;
        // 第一行的取消激活
        obj.find('.nav-tabs-custom > .nav-tabs > .active').removeClass('active');
        obj.find('.nav-tabs-custom > .tab-content > .active').removeClass('active');
        // 有下拉菜单的也取消激活
        obj.find('.nav-tabs-custom > .nav-tabs > .dropdown .active').removeClass('active');
        // <激活左侧菜单
        var $sidebarMenu = $(".sidebar-menu [data-addtab='" + opts.id + "']");
        $(".sidebar-menu .active").removeClass("active");
        $sidebarMenu.parent().addClass("active");
        $sidebarMenu.parent().parent().parent().addClass("active");
        // 激活左侧菜单>

        //如果TAB不存在，创建一个新的TAB
        if (!$("#" + id)[0]) {

            //创建新TAB的title
            var title = $('<li>', {
                'role': 'presentation',
                'id': 'tab_' + id
            }).append(
                $('<a>', {
                    'href': '#' + id,
                    'aria-controls': id,
                    'role': 'tab',
                    'data-toggle': 'tab'
                }).html(opts.title.replace(/<(.*)>(.*)<\/(.*)>|<(.*)\/>/,"")) // 去图标
            );

            //是否允许关闭
            if (Addtabs.options.close) {
                title.append(
                    $('<i>',{class:'close-tab glyphicon glyphicon-remove'})
                    //$('<button type="button" class="close close-tab glyphicon glyphicon-remove" aria-label="Close"><span aria-hidden="true">&times;</span></button>')
                );
            }
            //创建新TAB的内容
            var content = $('<div>', {
                'class': 'tab-pane',
                'id': id,
                'role': 'tabpanel'
            });

            //是否指定TAB内容
            if (opts.content) {
                content.append(opts.content);
            } else if (Addtabs.options.iframeUse && !opts.ajax) {//没有内容，使用IFRAME打开链接
                content.append(
                    $('<iframe>', {
                        'class': 'iframeClass',
                        'height': Addtabs.options.iframeHeight,
                        'frameborder': "no",
                        'border': "0",
                        'src': opts.url
                    })
                );
            } else {
                content.load(opts.url);
                //$.get(opts.url, function (data) {
                //    content.append(data);
                //});
            }
            // 删除已经存在的高级搜索弹出框popover
            //$("body > .webui-popover").remove();

            //加入TABS
            obj.find('.nav-tabs-custom > .nav-tabs').append(title);
            obj.find(".nav-tabs-custom > .tab-content").append(content);
        }

        //激活TAB
        $("#tab_" + id).addClass('active');
        $("#" + id).addClass("active");
        Addtabs.drop();
    },
    close: function (id) {
        //如果关闭的是当前激活的TAB，激活他的前一个TAB
        if (obj.find("li.active:not('.dropdown')").attr('id') == "tab_" + id) {
            var first = $("#tab_" + id).prev(":first");
            var next = $("#tab_" + id).next(":first");
            if (first.is("li")){
                first.addClass('active');
                var firstId = first.find('>a').attr("aria-controls");
                $("#" + firstId).addClass('active');
            }else if (next.is('li')){
                next.addClass('active');
                var nextId = next.find('>a').attr("aria-controls");
                $("#" + nextId).addClass('active');
            }

        }
        // 删除已经存在的高级搜索弹出框popover
        $("body > .webui-popover").remove();
        //关闭TAB
        $("#tab_" + id).remove();
        $("#" + id).remove();
        Addtabs.drop();
        Addtabs.options.callback();
    },
    // 返回操作的页签
    back: function (id,activeId) {
        //如果关闭的是当前激活的TAB，激活制定的activeId
        if (obj.find("li.active:not('.dropdown')").attr('id') == "tab_" + id) {
            var first = $("#tab_" + activeId);
            var next = $("#tab_" + id).next(":first");
            if (first.is("li")){
                first.addClass('active');
                var firstId = first.find('>a').attr("aria-controls");
                $("#" + firstId).addClass('active');
            }else if (next.is('li')){
                next.addClass('active');
                var nextId = next.find('>a').attr("aria-controls");
                $("#" + nextId).addClass('active');
            }

        }
        // 删除已经存在的高级搜索弹出框popover
        $("body > .webui-popover").remove();
        //关闭TAB
        $("#tab_" + id).remove();
        $("#" + id).remove();
        Addtabs.drop();
        Addtabs.options.callback();
    },
    drop: function () {
        element = obj.find('.nav-tabs-custom > .nav-tabs');
        //创建下拉标签
        var dropdown = $('<li>', {
            'class': 'dropdown pull-right hide tabdrop'
        }).append(
            $('<a>', {
                'class': 'dropdown-toggle',
                'data-toggle': 'dropdown',
                'href': '#'
            }).append(
                $('<i>', {'class': "glyphicon glyphicon-align-justify"})
            ).append(
                $('<b>', {'class': 'caret'})
            )
        ).append(
            $('<ul>', {'class': "dropdown-menu"})
        );

        //检测是否已增加
        if (!$('.tabdrop').html()) {
            dropdown.prependTo(element);
        } else {
            dropdown = element.find('.tabdrop');
        }
        //检测是否有下拉样式
        if (element.parent().is('.tabs-below')) {
            dropdown.addClass('dropup');
        }
        var collection = 0;

        //检查超过一行的标签页
        element.append(dropdown.find('li'))
            .find('>li')
            .not('.tabdrop')
            .each(function () {
                //this.offsetTop > 0
                if (this.offsetTop > 10 || element.width() - $(this).position().left - $(this).width() < 53) {
                    dropdown.find('ul').append($(this));
                    collection++;
                }
            });

        //如果有超出的，显示下拉标签
        if (collection > 0) {
            dropdown.removeClass('hide');
            if (dropdown.find('.active').length == 1) {
                dropdown.addClass('active');
            } else {
                dropdown.removeClass('active');
            }
        } else {
            dropdown.removeClass('active');
            dropdown.addClass('hide');
            // 查找是否有激活的，激活最后一个菜单
            if (!element.find(" > li.active").is("li")){
                var lastId = element.find(" li > a:last").attr("aria-controls");
                $("#tab_" + lastId).addClass('active');
                $("#" + lastId).addClass('active');
            }
        }
    },
    // 获得激活的tabId
    activeTabId: function(){
        try{
            return obj.find(".nav-tabs-custom > .tab-content > .active").attr('id');
        }catch(e){
            return null;
        }
    },
    //获得地图模式三级tabid
    activeSubTabId:function(){
    	 try{
             return obj.find(".nav-tabs-custom > .tab-content > .tab-content > .active").attr('id');
         }catch(e){
             return null;
         }
    },
    activeTabContent: function(){
        var activeTabId = Addtabs.activeTabId();
        // 如包含了二级标签页，返回当前二级激活的grid
        if ( $('#' + activeTabId + " > div").hasClass("subTabs-tag")){
            var href = $('#' + activeTabId + " .subTabs-tag > ul .active a").attr("href");
            if (href != 'undefined'){
                return '#' + activeTabId + ' .subTabs-tag .tab-content ' + href;
            }
        }else{
            return '#' + activeTabId;
        }
    },
    //地图模式 三级tabContent
    activeSubTabContent:function(){
    	var activeTabId = Addtabs.activeTabId();
		var href=$('#' + activeTabId + " .subTabs-tag > .tab-content ").find(".subTabs-tag > ul .active a").attr("href");
		return '#'+activeTabId+' .subTabs-tag .tab-content .subTabs-tag  .tab-content '+href+' .gridContainer';
	//	return '#'+href;
    },
    activeSubTabContentBack:function(){
    	var activeTabId = Addtabs.activeTabId();
		var href=$('#' + activeTabId + " .subTabs-tag > .tab-content ").find(".subTabs-tag > ul .active a").attr("href");
	//	return '#'+activeTabId+' .subTabs-tag .tab-content .subTabs-tag  .tab-content '+href+' .gridContainer';
		return href;
    }
};