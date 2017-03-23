require(['jquery','common','ztree'], function(jQuery,common,ztree){
    var domainUrl = "/department";

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
            columns:[[
                {field:'guid',checkbox:true},
                {field:'tableAlias.deptName',title:i18n_deptName,width:120,align:'left',sortable:true,formatter:function(value, row){
                    return row.deptName;
                }},
                //{field:'tableAlias.deptCode',title:i18n_deptCode,width:120,align:'center',sortable:true,formatter:function(value, row){
                //    return row.deptCode;
                //}},
                {field:'tableAlias.deptStatus',title:i18n_deptStatus,width:120,align:'center',sortable:true,formatter:function(value, row){
                    return row.deptStatus;
                }}
                , {field:'parent.deptName',title:i18n_parentId,width:120,align:'left',sortable:true,formatter:function(value, row){
                    if(row.parentDept != null)
                        return row.parentDept.deptName;
                }}
                //, {field:'deptAddr',title:i18n_deptAddr,width:120,align:'left'}
            ]]
        });

        jQuery( common.currentAdd() ).click(function(){
            common.addCommonCustom(
                {
                    url:domainUrl + "/add",
                    title: "添加部门",
                    closeBtnFun:function(){
                        ztree.ztree(domainUrl, "department");
                        return true;
                    }
                }
            );
        });
        jQuery( common.currentEdit() ).click(function(){
            common.editCommonCustom(
                {
                    url:domainUrl + "/update",
                    title: "编辑部门",
                    closeBtnFun:function(){
                        ztree.ztree(domainUrl, "department");
                        return true;
                    }
                }
            );

        });
        // 详情
        jQuery( common.currentDetail() ).click(function(){
            common.detailCommon(domainUrl);
        });
        // 删除
        jQuery( common.currentDel() ).click(function(){
            common.delCommonCustom(
                {
                    url:domainUrl + "/del",
                    delSuccessAfter:function(){
                        ztree.ztree(domainUrl, "department");
                        return true;
                    }
                }
            );
        });
        // 搜索
        common.commonSearch();

        // 初始化树，第一个参数为查询url. 第二个为dom里id
        ztree.ztree(domainUrl, "department");

    });

});

