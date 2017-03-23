require(['jquery','common'], function(jQuery,common){
    var domainUrl = "/logOpt";

    jQuery(function(){
        jQuery(common.currentGrid()).datagrid({
            width: common._width(),     //设置表格宽度
            height: common.subHeight,   //设置表格高度
            pageSize:common.subPageSize, // 默认显示多少页
            url:ctx + domainUrl + '/list', //查询的action地址
            idField:'guid', //指定ID列
            striped:true,
            pageList:[common.subPageSize,30,50,100],
            rownumbers:true, //显示行号
            pagination:true, //是否分页
            //定义表格列(单表头)
            columns:[[
                {field:'guid',checkbox:true},
                {field:'optPerson',title:i18n_userId,width:100,align:'left',sortable:true},
                {field:'optObject',title:i18n_optObject,width:150,align:'left',sortable:true},
                //{field:'optGuid',title:i18n_optGuid,width:250,align:'left',sortable:true},
                {field:'optSuccess',title:i18n_optSuccess,width:100,align:'center',sortable:true,formatter:function(value, row){
                    if(row.optSuccess == '0') return '失败';
                    else return '成功';
                }},
                {field:'optType',title:i18n_optType,width:100,align:'center',sortable:true},
                {field:'optDate',title:i18n_optDate,width:150,align:'center',sortable:true}
            ]]
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

        // 高级搜索
        common.advSearch();



    });

});