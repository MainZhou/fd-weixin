<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <div class="alert">当前操作角色: ${role.roleName}</div>
            <div class="hidden alert alert-danger" id="errorMsg"></div>
            <form>
                <div class="col-xs-12 col-md-5 form-group">
                    <label>未授权的模块</label>
                    <select multiple class="form-control" style="height: 350px" id="noGranModule">
                        <c:forEach items="${noGranModule}" var="item">
                            <option value="${item.guid}">${item.moduleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-xs-12 col-md-2 form-group">
                    <div style="margin-top: 35px"></div>
                    <div class="btn-group-vertical">
                        <div class="btn-group" role="group">
                            <button type="button" id="granAuthBtn" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-menu-right" title="授权"></span>
                            </button>
                        </div>
                        <div class="btn-group" role="group">
                            <button type="button" id="undoGranAuthBtn" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-menu-left" title="取消授权"></span>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-5 form-group">
                    <label>已授权的模块</label>
                    <select multiple class="form-control" style="height: 350px" name="granModuleIds" id="granModule">
                        <c:forEach items="${granModule}" var="item">
                        <option value="${item.guid}">${item.moduleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <input type="submit" class="hidden" disabled="disabled" id="submit_button">
                </div>
            </form>
        </div>
        <!-- 实体内容结束-->
    </div>
</div>

<script type="text/javascript">
    jQuery(function(){
        //授权
        jQuery("#granAuthBtn").click(function(){
            var $noGranModule = jQuery("#noGranModule option:selected");
            var $granModule = jQuery("#granModule");
            var postData = [];

            $noGranModule.each(function(){
                postData.push( $(this).val());
            });

            $.ajax({
                type: "POST",
                url: "${ctx}/role/granAuth?roleId=${role.guid}&method=add",
                dataType: "JSON",
                data: {"moduleIds":postData.toString()},
                success: function(msg){
                    if(!msg.status){
                        alert("更改失败！")
                    }
                }
            });

            $granModule.append($noGranModule);
        });

        //取消授权
        jQuery("#undoGranAuthBtn").click(function(){
            var $granModule = jQuery("#granModule option:selected");
            var $noGranModule = jQuery("#noGranModule");
            var postData = [];
            $granModule.each(function(){
                postData.push( $(this).val());
            });

            $.ajax({
                type: "POST",
                url: "${ctx}/role/granAuth?roleId=${role.guid}&method=del",
                dataType: "JSON",
                data: {"moduleIds":postData.toString()},
                success: function(msg){
                    if(!msg.status){
                        alert("更改失败！")
                    }
                }
            });

            $noGranModule.append($granModule);
        });

    });

</script>

