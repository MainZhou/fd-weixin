require(['jquery','common'], function(jQuery,common){
    var domainUrl = "/user";

    jQuery(function(){
        var column0 =[
            {field:'guid',checkbox:true},
            {field:'realName',title:i18n_realName,width:120,align:'left',sortable:true},
            {field:'loginName',title:i18n_loginName,width:100,align:'left',sortable:true},
            {field:'department.deptName',title:i18n_department,width:200,align:'left',sortable:true,formatter:function(value, row){
                if (row.department.deptString){
                    return row.department.deptString;
                }else{
                    return row.department.deptName;
                }
            }},
            {field:'sex',title:i18n_sex,width:50,align:'center',sortable:true,formatter:function(value, row){
                if(row.sex == '0') return '男';
                else if(row.sex == '1') return '女';
            }},
            {field:'userStatus',title:i18n_userStatus,width:80,align:'center',sortable:true},
            {field:'postName',title:i18n_postName,width:120,align:'left',sortable:true},
            {field:'telephone',title:i18n_telephone,width:110,align:'center',sortable:true},
            {field:'email',title:i18n_email,width:110,align:'left',sortable:true},
            {field:'entryDate',title:i18n_entryDate,width:100,align:'center',sortable:true}
            //{field:'post',title:i18n_post,width:60,align:'left',sortable:true},
            //{field:'jobNum',title:i18n_jobNum,width:60,align:'center',sortable:true},
            //{field:'manageArea',title:i18n_manageArea,width:100,align:'left',sortable:true},
            //{field:'addr',title:i18n_addr,width:120,align:'left',sortable:true},
        ];
        common.makeCustomColumn({
            requestUrl:domainUrl + '/list',
            grid:{column0:column0}
        });

        jQuery( common.currentAdd() ).click(function(){
            common.addCommon("添加用户", domainUrl);
        });
        jQuery( common.currentEdit() ).click(function(){
            common.editCommon("编辑用户", domainUrl);
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
        common.advSearch({
            // 打开高级搜索后，初始化树
            onshown:function(){
                // 树
                var setting = {
                    async : {
                        contentType : "application/json",
                        enable : true,
                        dataType : "json",
                        type : "get",
                        url : ctx + "/zTree/loadZtree?model=department",
                        dataFilter : null
                    },
                    check: {
                        enable: true,
                        chkStyle: "radio",
                        radioType: "all"
                    },
                    view: {
                        dblClickExpand: false
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey : "id",
                            pIdKey : "pid",
                            rootPId : 0   // 根节点
                        }
                    },
                    callback: {
                        onClick: onClick,
                        onCheck: onCheck
                    }
                };

                function onClick(e, treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj("treeDemoSearch");
                    zTree.checkNode(treeNode, !treeNode.checked, null, true);
                    return false;
                }

                function onCheck(e, treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj("treeDemoSearch"),
                        nodes = zTree.getCheckedNodes(true),
                        guid = "",
                        v = "";
                    for (var i=0, l=nodes.length; i<l; i++) {
                        v += nodes[i].name + ",";
                        guid += nodes[i].id + ",";
                    }
                    if (v.length > 0 ) v = v.substring(0, v.length-1);
                    if (guid.length > 0 ) guid = guid.substring(0, guid.length-1);
                    // 设值同时验证
                    $(".webui-popover #parentDeptSearch:last").val(v);
                    $(".webui-popover #parentDeptSearchHidden:last").val(guid);
                    // 隐藏
                    hideMenu();
                }

                function hideMenu() {
                    $(".webui-popover #menuContentSearch:last").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
                function onBodyDown(event) {
                    if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContentSearch" || $(event.target).parents("#menuContentSearch").length>0)) {
                        hideMenu();
                    }
                }
                $(".webui-popover #parentDeptSearch").click(function(){
                    var obj =  $(this);
                    var objOffset = $(this).offset();
                    $("#menuContentSearch:last").css({left:objOffset.left + "px", top:objOffset.top + obj.outerHeight() + "px"}).slideDown("fast");

                    $("body").bind("mousedown", onBodyDown);
                });
                // 为空才加载
                if ( null == $.fn.zTree.getZTreeObj("treeDemoSearch") ){
                    $.fn.zTree.init($(".webui-popover #treeDemoSearch"), setting);
                }
            }
        });

        //授权
        jQuery("#granAuthToUser").click(function(){
            var row = common.getSelectedOne();
            if(row){
                common.customDialog("授权", ctx + domainUrl + "/granAuth?userId=" + row.guid + "&time=" + new Date().getTime());
            }
        });

    });

});