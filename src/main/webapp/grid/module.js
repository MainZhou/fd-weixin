require(['jquery','common'], function(jQuery,common){
        var domainUrl = "/module";

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
                    ,{field: 'moduleName', title: i18n_moduleName,width:250}
                    ,{field: 'intro',title: i18n_intro, sortable: true,width:250}
                    ,{field: 'menu.menuName', title: i18n_menuId,width:200,
                        formatter:function(value, row){
                            if(null != row.menu )
                                return row.menu.menuName;
                        }
                    }
                ]]
            });

            // 添加
            jQuery( common.currentAdd() ).click(function(){
                common.addCommon("添加模块", domainUrl);
            });
            // 编辑
            jQuery( common.currentEdit() ).click(function(){
                common.editCommon("编辑模块", domainUrl);
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


