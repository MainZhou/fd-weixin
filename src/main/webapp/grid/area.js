
require(['jquery','common'], function(jQuery,common){
    var domainUrl = "/area";

    jQuery( common.currentGrid()).bootstrapTable({
        url:ctx + domainUrl + '/list', //查询的action地址
        idField:'guid',
        columns:[[
            {field:'guid',checkbox:true},
            {field:'areaname',title:i18n_areaname,width:120,align:'center',sortable:true},
            {field:'parentid',title:i18n_parentid,width:120,align:'center',sortable:true},
            {field:'levels',title:i18n_levels,width:120,align:'center',sortable:true}
        ]]
    });

    jQuery(function(){
        jQuery( common.currentAdd() ).click(function(){
            common.addCommon("区域", domainUrl);
        });
        jQuery( common.currentEdit() ).click(function(){
            common.editCommon("区域", domainUrl);
        });
        // 详情
        jQuery( common.currentDetail() ).click(function(){
            common.detailCommon(domainUrl);
        });
        // 删除
        jQuery( common.currentDel() ).click(function(){
            common.delCommon(domainUrl);
        });
        // 搜索
        common.commonSearch();

    });

});