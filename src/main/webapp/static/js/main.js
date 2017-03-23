var ctx2;
if (ctx == ''){
    ctx2 = '/';
}else{
    ctx2 = ctx;
}
require.config({
    baseUrl : ctx2,
    paths : {
        jquery : ['static/js/jquery-2.2.3.min'],
        jqueryForm : ['static/js/jquery.form'],
        easyUi : ['static/easyui/columnMoving'],
        easyUiResizable : ['static/easyui/plugins/jquery.resizable'],
        easyUiLang : ['static/easyui/locale/easyui-lang-zh_CN'],
        easyUiMain: ['static/easyui/jquery.easyui.min'],
        easyUiDataGrid: ['static/easyui/datagrid_diy'],
        bootstrap : ['static/bootstrap/js/bootstrap'],
        echarts : ['static/echarts/echarts.min'],
        addTabs : ['static/addTabs/bootstrap-addtabs'],
        popover : ['static/popover/jquery.webui-popover.min'],
        placeholder: ['static/placeholder/placeholder'],
        common: ['static/js/GridCommon'],
        bootstrapDialog: ['static/bootstrap3-dialog/js/bootstrap-dialog'],
        bootstrapTable_MAIN: ['static/bootstrap-table/bootstrap-table'],
        bootstrapTable_LANG: ['static/bootstrap-table/locale/bootstrap-table-zh-CN.min'],
        bootstrapTable: ['static/bootstrap-table/bootstrap-table-custom'],
        adminlte:['static/adminlte/js/app.min'],
        validator:['static/validator/local/zh-CN'],
		ztree_excheck:['static/zTree/js/jquery.ztree.excheck'],
		ztree_core:['static/zTree/js/jquery.ztree.core'],
		ztree_exedit:['static/zTree/js/jquery.ztree.exedit'],
		ztree:['static/js/TreeCommon'],
		adjust:['static/js/termina/adjust'], //核算
		measure:['static/js/termina/measure'],//测算
		amap:['http://webapi.amap.com/maps?v=1.3&key=a46fd745b5a80c6781d29e756aed0536&plugin=AMap.Autocomplete'],
		select2_main:['static/select2/js/select2.full'],
		select2:['static/select2/js/i18n/zh-CN'],
		jsUtil:['static/js/jsutil'],
		pinyin:['static/js/pinyin']
    },
    shim : {
        bootstrap : {
            deps : [ 'jquery' ],
            exports : 'bootstrap'
        },
        addTabs:{
            deps:['jquery','bootstrap'],
            exports:'addTabs'
        },
        popover:{
            deps:['jquery','bootstrap'],
            exports:'popover'
        },
        easyUiMain : {
            deps:['jquery'],
            exports: 'easyUiMain'
        },
        easyUiColumnMoving : {
            deps:['jquery', 'easyUiMain'],
            exports: 'easyUiColumnMoving'
        },
        easyUiResizable : {
            deps:['jquery', 'easyUiMain'],
            exports: 'easyUiResizable'
        },
        easyUiLang : {
            deps:['jquery', 'easyUiMain'],
            exports: 'easyUi'
        },
        easyUiDataGrid : {
            deps:['jquery', 'easyUiMain'],
            exports: 'easyUiDataGrid'
        },
        easyUi : {
            deps:['jquery', 'easyUiLang', 'easyUiResizable','easyUiDataGrid'],
            exports: 'easyUi'
        },
        jqueryForm:{
            deps:['jquery'],
            exports:'jqueryForm'
        },
        bootstrapTable_LANG:{
            deps:['jquery', 'bootstrap', 'bootstrapTable_MAIN'],
            exports:'bootstrapTable_LANG'
        },
        adminlte:{
            deps: ['jquery', 'bootstrap'],
            exports: 'adminlte'
        },
        ztree:{
            deps: ['jquery' ,'ztree_core','ztree_excheck','ztree_exedit'],
            exports: 'ztree'
        },
        ztree_excheck:{
        	deps: ['jquery' ,'ztree_core'],
            exports: 'ztree_excheck'
        },
        ztree_exedit:{
        	deps: ['jquery' ,'ztree_core'],
            exports: 'ztree_exedit'
        },
        pinyin:{
            exports: 'pinyin'
        },
        select2:{
        	deps: ['jquery','select2_main'],
            exports: 'select2'
        },
        select2_main:{
        	deps: ['jquery','pinyin'],
            exports: 'select2_main'
        },
        jsUtil:{
            exports: 'jsUtil'
        }
    }

});
if(ctx == ''){
    // 为root目录。加载上一级
    require(['../static/js/admin']);
}else{
    require(['static/js/admin']);
}
