require(['jquery','common'], function(jQuery,common){
        var domainUrl = "/menu";

        jQuery(function(){
            jQuery(common.currentGrid()).datagrid({
                width: common._width(),     //设置表格宽度
                height: common._height,   //设置表格高度
                pageSize:common._pageSize, // 默认显示多少页
                url:ctx + domainUrl + '/list', //查询的action地址
                idField:'guid', //指定ID列
                striped:true,
                pageList:[common._pageSize,30,50,100],
                rownumbers:true, //显示行号
                pagination:true, //是否分页
                //定义表格列(单表头)
                columns:[[
                    {field:'guid',checkbox:true}
                    ,{field:'tableAlias.menuName',title:i18n_menuName,width:150,align:'left',sortable:true,formatter:function(value, row){
                        return row.menuName;
                    }}
                    ,{field:'parent.menuName',title:i18n_parentId,width:150,align:'left',sortable:true,formatter:function(value, row){
                        if(row.parentMenu)
                            return row.parentMenu.menuName;
                    }}
                    ,{field:'tableAlias.sortNum',title:i18n_sortNum,width:100,align:'center',sortable:true,formatter:function(value, row){
                        return row.sortNum;
                    }}
                    ,{field:'tableAlias.hint',title:i18n_hint,width:120,align:'left',sortable:true,formatter:function(value, row){
                        return row.hint;
                    }}
                    ,{field:'iconId',title:i18n_iconId,width:60,align:'center',formatter:function(value, row){
                        if(row.icon){
                            return "<span class='" + row.icon.cssName + "'></span>"
                        }
                    }}
                    ,{field:'tableAlias.entryUrl',title:i18n_entryUrl,width:200,align:'left',sortable:true,formatter:function(value, row){
                        return row.entryUrl;
                    }}
                ]]
            });
            // 添加
            jQuery( common.currentAdd() ).click(function(){
                common.addCommon("添加菜单", domainUrl);
            });
            // 编辑
            jQuery( common.currentEdit() ).click(function(){
                common.editCommon("编辑菜单", domainUrl);
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

    }
);