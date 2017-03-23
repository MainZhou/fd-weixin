<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
require(['jquery','common'], function(jQuery,common){
    var domainUrl = "/${columnRequest.prefixFirstCharLow}";

    jQuery(common.currentGrid()).datagrid({
        width: common._width(),         //设置表格宽度
        height: common._height,         //设置表格高度
        pageSize:common._pageSize,      //默认显示多少页
        url:ctx + domainUrl + '/list',  //查询的action地址
        idField:'guid',                 //指定ID列
        striped:true,                   //间隔条纹
        pageList:[common._pageSize,30,50,100],
        rownumbers:true,                //显示行号
        pagination:true,                //是否分页
        //右键菜单（列筛选功能,不需要可删除）
        onHeaderContextMenu: function(e, field){
            common.createColumnMenu(e, domainUrl);
        },
        // 获得动态列配置（不需要可删除）
        onBeforeLoad: function(){
            common.getServerColumnMenu(domainUrl);
        },
        columns:[[
            {field:'guid',checkbox:true},<c:forEach items="${columnRequest.attrs}" var="item" varStatus="index"><c:if test="${item.value.display == true}">
            {field:'${item.value.javaName}',title:i18n_${item.value.javaName},width:${fn:length(item.value.cnLabel) * 30},align:'center'<c:if test="${item.value.sort == true}">,sortable:true</c:if>}<c:if test="${!index.last}">,</c:if></c:if></c:forEach>
        ]]
    });

    jQuery(function(){
        // 添加
        jQuery(common.currentAdd()).click(function(){
            common.addCommon("添加${columnRequest.funcName}", domainUrl);
        });
        // 编辑
        jQuery(common.currentEdit()).click(function(){
            common.editCommon("编辑${columnRequest.funcName}", domainUrl);
        });
        // 详情
        jQuery(common.currentDetail()).click(function(){
            common.detailCommon(domainUrl);
        });
        // 删除
        jQuery(common.currentDel()).click(function(){
            common.delCommon(domainUrl);
        });
        // 搜索
        common.commonSearch();

    });

});