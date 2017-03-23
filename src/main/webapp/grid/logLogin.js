require(['jquery','common'], function(jQuery,common){
    var domainUrl = "/logLogin";

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
                {field:'tuser.loginName',title:i18n_user_loginName,width:100,align:'left',sortable:true,formatter:function(value, row){
                    return row.user.loginName;
                }},
                {field:'tuser.realName',title:i18n_user_realName,width:100,align:'left',sortable:true,formatter:function(value, row){
                    return row.user.realName;
                }},
                {field:'loginDate',title:i18n_loginDate,width:150,align:'center',sortable:true},
                {field:'browser',title:i18n_browser,width:150,align:'left',sortable:true},
                {field:'ip',title:i18n_ip,width:120,align:'left',sortable:true},
                {field:'logoutDate',title:i18n_logoutDate,width:150,align:'center',sortable:true},
                {field:'leaveTime',title:i18n_leaveTime,width:150,align:'center',sortable:true}
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

        // 子标签
        common.subTabs();

    });

});