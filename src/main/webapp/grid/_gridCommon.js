//dataGrid的ID,全局
var dataGridGloableId = 'gridContainer';
//服务器验证返回的msg,显示的dom ID
var serverValidateReturnMsgId = "errorMsg";
var _pageSize; // 默认显示20页
var _height=400; //dataGrid高度
var _width=900; //dataGrid宽度
//文档准备好就,就重新设置dataGrid高度和宽度
$(function(){
    _height= jQuery(document).height() - 170; //页面高度减去
    _width = jQuery(".myContent").width();//重新获得datagrid宽度
    _pageSize = parseInt((_height - 70) / 25); // 获得与浏览器相匹配的页数
});

// 重新设置dataGrid高宽度
function changeDataGridSize(){
    var sizeObj = null;
    if (_width && _width > 0) {
        if (sizeObj == null) {
            sizeObj = {};
        }
        if(jQuery(".main-sidebar").width() == 50)
            sizeObj.width = jQuery(window).width() - 230 - 80;
        else
            sizeObj.width = jQuery(window).width() - 50 - 80;
    }
    if (_height && _height > 0) {
        if (sizeObj == null) {
            sizeObj = {};
        }
        sizeObj.height = jQuery(document).height() - 230;
    }
    jQuery('#' + dataGridGloableId).datagrid('resize',sizeObj);
}

//建立筛选列右键菜单
function createColumnMenu(tableId){
    tableId = tableId || 'gridContainer';
    var tmenu = jQuery('<div id="tmenu" style="width:200px;"></div>').appendTo('body');
    var fields = jQuery('#'+tableId).datagrid('getColumnFields');
    for(var i=0; i<fields.length; i++){
        var fieldOption = jQuery('#'+tableId).datagrid('getColumnOption',fields[i]);
        if (fieldOption) {
            if (fieldOption.hidden) {
                jQuery('<div id="_field_'+fields[i]+'" iconCls="icon-empty"/>').html(fieldOption.title).appendTo(tmenu);
            } else {
                jQuery('<div id="_field_'+fields[i]+'" iconCls="icon-ok"/>').html(fieldOption.title).appendTo(tmenu);
            }
        }
    }
    tmenu.menu({
        onClick: function(item){
            var fieldId = item.target.id.replace(/_field_/, "");
            if (item.iconCls=='icon-ok'){
                if (jQuery("#tmenu > .menu-item > .icon-ok").length > 1) {
                    jQuery('#'+tableId).datagrid('hideColumn', fieldId);
                    tmenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-empty'
                    });
                }
            } else {
                jQuery('#'+tableId).datagrid('showColumn', fieldId);
                tmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        }
    });
}

/**
 * 获得dataGrid第一个选择的行，返回row
 */
function getSelected(dataGridId){
    return jQuery('#' + dataGridId).datagrid('getSelected');
}

/**
 * 获得所有的选择的行，返回row[]
 */
function getSelections(dataGridId){
    //if (!dataGridId)
    //    return jQuery('#' + dataGridId).datagrid('getSelections');
    //else
    return jQuery('.moduleGridContainer').datagrid('getSelections');
}

/**
 * 获得一行数据,如用户未选择,提示请选择
 * 返回row对象
 */
function getSelectedOne(){
    var rows = getSelections(dataGridGloableId);
    if(rows.length != 1){
        BootstrapDialog.show({
            title: "提示",
            type: BootstrapDialog.TYPE_DANGER,
            message: "请选择一行操作数据",
            buttons: [{
                label: '关闭',
                action: function(dialog){
                    dialog.close()
                }
            }]
        });
        flushDataGrid(dataGridGloableId);
        return null;
    }else{
        return rows[0];
    }
}

/**
 * 显示链接
 */
function url(val, row, index){
    return "<a target='_blank' href=" + val + ">查看链接"+"</a>";
}

/**
 * 显示图片
 */
function image(val ,row, index){
    return "<img src='" + ctx + val + "' height=60/>";
}

/**
 * 自定义对话框(提交表单)
 * @param title 打开时的标题
 * @param openUrl 加载内容的URL
 * @param buttons 按钮
 */
function customDialog(title, openUrl, buttons){
    if(!buttons){
        buttons = [{
            label: '关闭',
            action: function(dialog) {
                dialog.close();
            }
        }]
    }
    BootstrapDialog.show({
        title: title,
        type: BootstrapDialog.TYPE_PRIMARY,
        draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': openUrl
        },
        buttons: buttons
    });
}
/**
 * 自定义对话框(提交表单)
 * @param title 打开时的标题
 * @param openUrl 加载内容的URL
 * @param buttons 按钮
 */
function customDialog(title, openUrl, buttons){
    if(!buttons){
        buttons = [{
            label: '关闭',
            action: function(dialog) {
                dialog.close();
            }
        }]
    }
    BootstrapDialog.show({
        title: title,
        type: BootstrapDialog.TYPE_PRIMARY,
        draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': openUrl
        },
        buttons: buttons
    });
}
/**
 * 自定义提交
 * @param title 标题
 * @param url url
 */
function customSubmit(title, url){
    BootstrapDialog.show({
        title: title,
        type: BootstrapDialog.TYPE_PRIMARY,
        draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': url
        },
        buttons: [
            {
                label: '关闭',
                action: function(dialog) {
                    dialog.close();                   // 关闭模态框
                    flushDataGrid(dataGridGloableId); // 重新刷新当前dataGrid
                }
            },
            {
                label: '提交',
                //            hotkey: 13, // 快捷键,回车
                cssClass: 'btn-primary',
                action: function(dialog) {
                    $form = $("form:last");
                    $form.unbind("valid.form");//unbind验证,防止重复提交
                    $form.bind("valid.form", function(e, form){//bind验证规则

                        var options = {
                            beforeSubmit:  showRequest,  //提交前处理
                            success:       showResponse,  //处理完成
                            dataType:  'json'
                        };

                        $form.ajaxSubmit(options);//ajax提交

                        /**
                         * 提交前的操作
                         */
                        function showRequest(formData, jqForm, options) {
                            dialog.enableButtons(false);
                            return true;
                        }

                        /**
                         * 提交成功后的操作
                         */
                        function showResponse(responseText, statusText, xhr, $form)  {
                            dialog.enableButtons(true);
                            jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
                            if(!responseText.status){
                                // 提交失败
                                dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
                            }else{
                                // 提交成功,  显示信息
                                dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg">' + responseText.msg + '</div>');
                                // 隐藏提交
                                var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                                jQuery(buttons[1]).hide();
                            }
                        }
                    });
                    //触发验证
                    $form.trigger("validate");

                }
            }
        ]
    });
}

// 自动过滤input text 前后空格
function autoTrim(){
    jQuery("form:last input").each(function(){
        var val = $(this).val();
        $(this).val(jQuery.trim(val));
    });
}

/**
 * 添加的公共部分(正常大小)
 * @param title 标题
 * @param domainUrl 实体url
 */
function addCommon(title, domainUrl){
    BootstrapDialog.show({
        title: title,
        type: BootstrapDialog.TYPE_PRIMARY,
        draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': ctx + domainUrl + '/add'
        },
        buttons: [
            {
                label: '继续添加',
                action: function(dialog) {
                    var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                    jQuery(buttons[0]).hide(); // 隐藏继续添加
                    jQuery(buttons[2]).show(); // 显示提交
                    turnToEdit();              // 转化为可编辑
                }
            },
            {
                label: '关闭',
                action: function(dialog) {
                    dialog.close();                   // 关闭模态框
                    flushDataGrid(dataGridGloableId); // 重新刷新当前dataGrid
                }
            },
            {
                label: '提交',
    //            hotkey: 13, // 快捷键,回车
                cssClass: 'btn-primary',
                action: function(dialog) {
                    $form = $("form:last");
                    autoTrim(); // 自动trim
                    if(beforeAdd()){
                        addAjaxSubmitFun(dialog, $form);//处理提交
                        afterAdd();
                    }
                }
            }
        ],
        onshown:function(){
            var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
            jQuery(buttons[0]).hide();
        }
    });
}

/**
 * 添加方法
 */
function addAjaxSubmitFun(dialog, $form){
    $form.unbind("valid.form");//unbind验证,防止重复提交
    $form.bind("valid.form", function(e, form){//bind验证规则

        var options = {
            beforeSubmit:  showRequest,  //提交前处理
            success:       showResponse,  //处理完成
            dataType:  'json'
        };

        $form.ajaxSubmit(options);//ajax提交

        /**
         * 提交前的操作
         */
        function showRequest(formData, jqForm, options) {
            dialog.enableButtons(false);
            return true;
        }

        /**
         * 提交成功后的操作
         */
        function showResponse(responseText, statusText, xhr, $form)  {
            dialog.enableButtons(true);
            jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
            if(!responseText.status){
                //提交失败
                dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
            }else{
                //提交成功
                // 显示信息
                dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg"> 添加成功!</div>');
                // 转化form 为只读
                turnToRead();
                // 获得关闭按钮, 转化为继续添加
                var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                jQuery(buttons[0]).show(); // 显示继续添加
                jQuery(buttons[2]).hide(); // 隐藏提交

            }
        }
    });
    //触发验证
    $form.trigger("validate");

}


var g_ids = [];
var g_ids_Index = 0;

/**
 * 更新的公共部份
 * @param title 标题
 * @param guids 编辑Ids
 * @param domainUrl 实体url
 */
function editCommon(title, guids, domainUrl){
    //id为空。从dataGrid获取第一个值
    if(!guids){
        // 定义为数组
        var rows = getSelections(dataGridGloableId);
        if(rows.length == 0){
            BootstrapDialog.show({
                title: "提示",
                type: BootstrapDialog.TYPE_DANGER,
                message: "请选择需要操作的行",
                buttons: [{
                    label: '关闭',
                    action: function(dialog){
                        dialog.close()
                    }
                }]
            });
            return;
        }
        g_ids = [];      // 清空
        g_ids_Index = 0; // 重置为0

        // 添加到数组中
        for(var i=0; i<rows.length; i++){
            g_ids[i] = rows[i].guid;
        }
    }

    //加载第一个值
    BootstrapDialog.show({
        title: title,
        type: BootstrapDialog.TYPE_PRIMARY,
        draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': ctx + domainUrl + '/update?guid=' + g_ids[g_ids_Index]
        },
        buttons: [
            {
                label: '编辑上一条',
                cssClass: 'btn-primary',
                action: function(dialog) {
                    var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                    jQuery(buttons[0]).hide(); // 编辑上一条
                    jQuery(buttons[1]).hide(); // 编辑下一条
                    jQuery(buttons[2]).show(); // 关闭
                    jQuery(buttons[3]).show(); // 提交
                    customPriorSubmit();
                }
            },
            {
                label: '编辑下一条',
                cssClass: 'btn-primary',
                action: function(dialog) {
                    var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                    jQuery(buttons[0]).hide(); // 编辑上一条
                    jQuery(buttons[1]).hide(); // 编辑下一条
                    jQuery(buttons[2]).show(); // 关闭
                    jQuery(buttons[3]).show(); // 提交
                    customGoonSubmit();
                }
            },
            {
                label: '关闭',
                action: function(dialog) {
                    dialog.close();                   // 关闭模态框
                    flushDataGrid(dataGridGloableId); // 重新刷新当前dataGrid
                }
            },
            {
                label: '提交',
                cssClass: 'btn-primary',
                action: function(dialog) {
                    $form = $("form:last");
                    autoTrim(); // 自动trim
                    if(beforeUpdate()){
                        updateAjaxSubmitFun(dialog, $form, guids);//处理提交
                        afterUpdate();
                    }
                }
            }
        ],
        onshown: function(){
            var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
            jQuery(buttons[0]).hide();
            jQuery(buttons[1]).hide();
        }
    });

}

//继续编辑（下一条）
function customGoonSubmit () {
    var obj = jQuery("form:last").get(0);
    if (obj) {
        obj.reset();
    }
    turnToEdit();
    if (g_ids_Index >= 0 && g_ids_Index < g_ids.length) {
        g_ids_Index = g_ids_Index + 1;
        loadDataFromServerInner(g_ids[g_ids_Index]);
    }
    return false;
}

//继续编辑（上一条）
function customPriorSubmit () {
    var obj = jQuery("form:last").get(0);
    if (obj) {
        obj.reset();
    }
    turnToEdit();
    if (g_ids_Index >= 0 && g_ids_Index < g_ids.length) {
        g_ids_Index = g_ids_Index - 1;
        if (g_ids_Index < 0) {
            g_ids_Index = 0;
        }
        loadDataFromServerInner(g_ids[g_ids_Index]);
    }
    return false;
}
// 加载编辑的内容
function loadDataFromServerInner(guid){
    var url = ctx + domainUrl + '/update?guid=' + guid;
    jQuery(".bootstrap-dialog-message").load(url);
}

/**
 * 更新方法
 */
function updateAjaxSubmitFun(dialog, $form, guids){
    $form.unbind("valid.form");//unbind验证,防止重复提交
    $form.bind("valid.form", function(e, form){//bind验证规则

        var options = {
            beforeSubmit:  showRequest,  //提交前处理
            success:       showResponse,  //处理完成
            dataType:  'json'
        };

        $form.ajaxSubmit(options);//ajax提交

        /**
         * 提交前的操作
         */
        function showRequest(formData, jqForm, options) {
            dialog.enableButtons(false);
            return true;
        }

        /**
         * 提交成功后的操作
         */
        function showResponse(responseText, statusText, xhr, $form)  {
            dialog.enableButtons(true);
            jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
            if(!responseText.status){
                //提交失败
                dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
            }else{
                //提交成功
                // 显示信息
                dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg"> 修改成功!</div>');

                var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                if(g_ids[g_ids_Index -1] != undefined){
                    jQuery(buttons[0]).show(); // 显示编辑上一条
                }else{
                    jQuery(buttons[0]).hide(); // 隐藏编辑上一条
                }
                if(g_ids[g_ids_Index + 1] != undefined){
                    jQuery(buttons[1]).show(); // 显示编辑下一条
                }else{
                    jQuery(buttons[1]).hide(); // 隐藏编辑下一条
                }
                jQuery(buttons[3]).hide(); // 隐藏提交按钮

                turnToRead();              // 转化为只读
            }
        }
    });
    //触发验证
    $form.trigger("validate");

}

//转为只读
function turnToRead() {
    try {
        jQuery("form:last input").each(function(index, obj){
            if (obj.type != "hidden" && obj.type != "submit" && obj.type != "checkbox" && obj.type != "radio" && obj.type != "button") {
                if (obj.type == "file") {
                    if (obj.value) {
                        jQuery(obj).after("<label class='form-control _add_hide'>"+obj.value+"</label>");
                    }
                    else {
                        jQuery(obj).after("<label class='form-control _add_hide'>"+ 未选择文件 +"</label>");
                    }
                }
                else {
                    jQuery(obj).after("<label class='form-control _add_hide'>"+obj.value+"</label>");
                }
                jQuery(obj).hide();
            }
            else if(obj.type == "checkbox") {
                jQuery(obj).hide().next().hide();
                if (obj.checked) {
                    jQuery(obj).after("<label class='form-control _add_hide'>"+jQuery(obj).next().html()+"</label>");
                }
            }
            else if(obj.type == "radio") {
                jQuery(obj).hide().next().hide();
                if (obj.checked) {
                    jQuery(obj).after("<label class='form-control _add_hide'>"+jQuery(obj).next(".radioCss").html()+"</label>");
                }
            }
        });
        jQuery("form:last textarea").each(function(index, obj){
            jQuery(obj).after("<label class='form-control _add_hide'>"+obj.value+"</label>");
            jQuery(obj).hide();
        });
        jQuery("form:last select").each(function(index, obj){
            jQuery(obj).after("<label class='form-control _add_hide'>"+jQuery("option:selected", obj).text()+"</label>");
            jQuery(obj).hide();
        });
        jQuery("form:last td img").each(function(index, obj){
            jQuery(obj).hide();
        });

    } catch (e) {
    }
}

//转为可编辑
function turnToEdit() {
    try {
        jQuery("._add_hide").remove(); // 删除只读的状态
        jQuery("._modal_left_msg").remove(); // 删除模态框左下角的提示信息

        var obj = jQuery("form:last").get(0); // 获得dom
        if (obj) {
            obj.reset(); //重置form
        }
        jQuery("form:last input").each(function(index, obj){
            if (obj.type != "hidden" && obj.type != "submit" && obj.type != "checkbox" && obj.type != "radio" && obj.type != "button") {
                jQuery(obj).show();
            }
            else if(obj.type == "checkbox") {
                jQuery(obj).show().next().show();
            }
            else if(obj.type == "radio") {
                jQuery(obj).show().next().show();
            }
        });
        jQuery("form:last textarea").each(function(index, obj){
            jQuery(obj).show();
        });
        jQuery("form:last select").each(function(index, obj){
            jQuery(obj).show();
        });
        jQuery("form:last td img").each(function(index, obj){
            jQuery(obj).show();
        });
    } catch (e) {
    }
}

/**
 * 详情的公共部份
 * @param guid 编辑Id
 * @param domainUrl 实体url
 */
function detailCommon(guid, domainUrl){
    //id为空。从dataGrid获取第一个值
    if(!guid){
        var rows = getSelections(dataGridGloableId);
        //提示用户选择一行
        if (rows.length != 1){
            BootstrapDialog.show({
                title: "提示",
                type: BootstrapDialog.TYPE_DANGER,
                message: "请选择一行操作数据",
                buttons: [{
                    label: '关闭',
                    action: function(dialog){
                        dialog.close()
                    }
                }]
            });
            flushDataGrid(dataGridGloableId);
            return;
        }else{
            guid = rows[0].guid;  // 重新设置id
        }
    }
    //id已经有值了,提交
    BootstrapDialog.show({
        title: '详细信息',
        type: BootstrapDialog.TYPE_PRIMARY,
        draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': ctx + domainUrl + '/detail?guid=' + guid
        },
        buttons: [{
            label: '关闭',
            action: function(dialog) {
                dialog.close();
            }
        }]
    });

}

/**
 * 删除的公共部分
 * @param ids id的数组
 * @param domainUrl
 */
function delCommon(ids, domainUrl){
    //未传值,从dataGrid获得
    if(!ids){
        var rows = getSelections(dataGridGloableId);
        //定义ids为数组
        ids = [];
        //for循环,取出所有的id,并加到数组中
        for(var i=0; i<rows.length; i++){
            ids.push(rows[i].guid);
        }
    }
    //判断取出后的结果是否为空，为空，提示用户选择数据，不为空，删除数据
    if(ids.length > 0){
        //不为空，删除数据
        BootstrapDialog.show({
            title: '提示',
            message: '您确定要删除 <span style="color:red">' + ids.length + ' </span>条数据吗?',
            type: BootstrapDialog.TYPE_DANGER,
            draggable: true,
            buttons: [{
                label: '取消',
                action: function(dialog) {
                    dialog.close();
                }
            }, {
                label: '确定',
                hotkey: 13, // 快捷键,回车
                cssClass: 'btn-danger',
                action: function(dialog) {
                    $.ajax({
                        type:'post',
                        traditional :true,//传统方式的序列化
                        url: ctx + domainUrl + "/del",
                        dataType:'json',
                        data:{'ids': ids},
                        success:function(data){
                            flushDataGrid(dataGridGloableId);//更新dataGrid
                            dialog.close();//关闭对话框
                            //状态为false,提示用户
                            if(data.status == false)
                                BootstrapDialog.alert(data.msg);
                        }
                    });
                }
            }]
        });
    }else{
        //为空，提示用户选择数据
        BootstrapDialog.show({
            title: '提示',
            message: '请选择要删除的行！',
            type: BootstrapDialog.TYPE_INFO,
            buttons: [{
                label: '关闭',
                action: function(dialog) {
                    dialog.close();
                }
            }]
        });
    }
}

/**
 * 刷新dataGrid
 */
function flushDataGrid(dataGridId) {
    if(null == dataGridId || "" == dataGridId)
        dataGridId = dataGridGloableId;
    $dataGrid = $('#' + dataGridId);
    $dataGrid.datagrid('uncheckAll');//刷新前，取消选择
    $dataGrid.datagrid('reload');
}

/**
 * 异步加载List页面
 * @param url
 */
function loadList(url){
    if("" != url && "#" != url){
        $.get(url, function(data){
            // 展示内容
            $(".content-wrapper").html(data);
            // 重新加载搜索
            reloadSearch();
            // ie 9 place holder
            require(['placeholder'], function(module) {
                module.init();
            });
        });
    }
}

/**
 * 搜索部份-----------------------------------------------------------------------------
 */
// 重新加载搜索部份
function reloadSearch(){
    var lastKeyWord; // 存放上次搜索的关键字
    // 普通搜索
    $(function(){
        // 输入框变化时, 0.61秒后立即执行搜索
        $("#normalSearch").keyup(function(){
            setTimeout("", 600); // 0.6秒内值改变了才进行搜索
            if($(this).val() != lastKeyWord){
                setTimeout("$('#gridContainer').datagrid('load', populateQueryParameter(0))", 1);
                // 存入关键字
                lastKeyWord = $(this).val();
            }
        });
        // 点击搜索按妞
        $("#normalSearchButton").click(function(){
            $('#gridContainer').datagrid('load',populateQueryParameter(0));
        });
    });

    // 绑定高级搜索内容
    $(function(){
        var $adv = $("#advSearchBtn");

        var $advContent = jQuery("#advancedSearchDialogContainer");
        $adv.webuiPopover('destroy').webuiPopover({
            title: '高级搜索',
            html: true,
            content: $advContent.html(),
            placement:'bottom',
            animation:'pop',
            width:450,
            closeable:true,
            dismissible:false
        }).on("shown.webui.popover", function(e){
            // 移除包含在jsp里的html, 做到唯一性
            $advContent.remove();
            // 使select2 显示
            $(".advancedSearchDialogContainerCss .select222").select2();
        });
    });

}

// 关闭高级搜索框
function closeAdvSearch(){
    var $adv = $("#advSearchBtn");
    $adv.webuiPopover('hide');
}

// 执行高级搜索命令
function doAdvSearch(){
    jQuery('#gridContainer').datagrid('load', populateQueryParameter(1));
    var $adv = $("#advSearchBtn");
    $adv.webuiPopover('hide');
}

/**
 * 封装搜索条件 参数searchType(0:普通搜索 1:高级搜索)
 * 已经自动封装请求参数,只需在list页面配置好，该js会自动转化参数条件
 * map为封装查询的json数据。spring mvc自动转化为Map,mybatis 自动转化查询条件
 **/
function populateQueryParameter(searchType){
    var jsonStr;
    if(searchType === 0){ //普通搜索
        var $normalSearch = jQuery("#normalSearch");
        var normalName = $normalSearch.attr("name");
        var normalVal = $normalSearch.val();
        // 组合普通搜索的JSON数据
        jsonStr = "{\"map\":{\"" + normalName + "\":\"" + normalVal + "\"}}";
        return JSON.parse(jsonStr);
    } else if(searchType === 1){ //高级搜索
        // 搜索高级搜索(实际值已经放在webui-popover-content)下面的所有input标签
        var $inputs = jQuery(".advancedSearchDialogContainerCss :input");
        // 组合搜索条件
        jsonStr = "{\"map\":{";
        var flag = true;
        $inputs.each(function(){
            var keyName = $(this).attr("name");
            var keyVal = $(this).val();
            // 过滤条件为空的, 不提交到服务器
            if(keyVal && flag){  // 第一次了, 前面不加,
                // 组合json字符
                jsonStr += "\"" + keyName + "\":"; // "key":
                jsonStr += "\"" + keyVal  + "\"";  // "value"
                flag = false;
            }else if(keyVal){  // 不是第一次了, 前面加,
                jsonStr += ",";
                jsonStr += "\"" + keyName + "\":"; // "key":
                jsonStr += "\"" + keyVal  + "\"";  // "value"
            }
        });
        jsonStr += "}}";
        // 转化为json, 并返回
        return JSON.parse(jsonStr);
    } else {
        alert("未定义搜索类型");
    }
}
/**
 * 搜索部份结束//////////////////////////////////////////////////////////////////////////
 */