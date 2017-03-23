<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../common/header-creator.jsp"%>

<div class="container-fluid" ng-app="step2App" ng-controller="step2Ctrl">
    <div class="row">
        <div class="col-xs-12 col-md-10 col-md-offset-1">
            <c:if test="${pdPath != null }">
            <div class="alert alert-info"><p>当前操作的表是:${tableName}</p><p>当前操作的pd文件是:${pdPath}</p></div>
            </c:if>
            <c:if test="${pdPath == null }">
            <div class="alert alert-danger">没有检测到pd文件,请检查creator.properties文件!</div>
            </c:if>
            <form action="${ctx}/creator/step3" method="post">
                <div class="panel panel-primary">
                    <div class="panel-heading">功能配置</div>
                    <table class="table table-bordered">
                        <tr>
                            <td width="10%">功能名称</td>
                            <td width="40%">
                                <input type="text" name="funcName" class="form-control">
                                <input type="hidden" name="tableName" value="${tableName}">
                                <input type="hidden" name="databaseName" value="${databaseName}">
                            </td>
                            <td width="10%">开发人员</td>
                            <td width="40%">
                                <input type="text" name="devPerson" class="form-control">
                            </td>
                        </tr>
                        <tr>
                            <td width="10%">包名</td>
                            <td width="40%">
                                <input type="text" name="packageName" class="form-control" value="${packageName}">
                            </td>
                            <td width="10%">类名前缀</td>
                            <td width="40%">
                                <input type="text" name="prefix" class="form-control" value="${prefix}">
                            </td>
                        </tr>
                        <tr>
                            <td width="10%">生成绝对路径</td>
                            <td width="40%">
                                <input type="text" name="absPath" class="form-control" value="${absPath}">
                            </td>

                        </tr>
                        <tr>
                            <td width="10%">所属菜单</td>
                            <td width="40%">
                                <select name="parentMenuId" class="form-control">
                                    <option value="">请选择( 不选择默认为一级菜单 )</option>
                                    <c:forEach items="${level1Menu}" var="item">
                                        <option value="${item.guid}">${item.menuName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="10%">生成菜单模块</td>
                            <td width="40%">
                                <div class="checkbox">
                                    <label><input type="checkbox" name="gen_module_menu" checked>是否生成模块和菜单到数据库中,如已存在相同名称,会覆盖!</label>
                                 </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="10%">选择生成类别</td>
                            <td width="40%">
                                <div class="checkbox">
                                    <label><input type="checkbox" name="genType[javaBean]" checked="checked">javaBean</label>
                                    <label><input type="checkbox" name="genType[controller]" checked="checked">controller层</label>
                                    <label><input type="checkbox" name="genType[service]" checked="checked">service层</label>
                                    <label><input type="checkbox" name="genType[dao]" checked="checked">dao层</label>
                                    <label><input type="checkbox" name="genType[jsp]" checked="checked">jsp</label>
                                </div>
                            </td>
                            <td width="10%">覆盖生成</td>
                            <td width="40%">
                                <div class="checkbox">
                                    <label><input type="checkbox" name="cover">慎重选择!不选择会自动备份!</label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div><p><p>

                <div class="panel panel-primary">
                    <div class="panel-heading">字段配置</div>
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th width="7%">数据库字段</th>
                            <th width="7%">Java字段名称</th>
                            <th width="7%">字段类型</th>
                            <th width="8%">中文标签</th>
                            <th width="4%">添加编辑</th>
                            <th width="8%">输入方式</th>
                            <th width="2%">必填</th>
                            <th width="4%">长度限制</th>
                            <th width="4%">列表显示</th>
                            <th width="2%">排序</th>
                            <th width="4%">普通搜索</th>
                            <%--<th width="2%">高搜</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="column in columns">
                            <td>{{column.dbName}}<input type="hidden" name="attrs[{{$index}}].dbName" value="{{column.dbName}}"></td>
                            <td><input type="text" name="attrs[{{$index}}].javaName" class="form-control" value="{{column.javaName}}"></td>
                            <td><input type="text" name="attrs[{{$index}}].javaType" class="form-control" value="{{column.type}}"></td>
                            <td><input type="text" name="attrs[{{$index}}].cnLabel" class="form-control" value="{{column.cnName}}"></td>
                            <td><input type="checkbox" name="attrs[{{$index}}].addEdit" class="form-control" ng-checked="{{$index != 0}}"></td>
                            <td>
                                <select name="attrs[{{$index}}].inputType" class="form-control">
                                    <option ng-repeat="inputType in inputTypes" value="{{inputType}}">{{inputType}}</option>
                                </select>
                            </td>
                            <td><input type="checkbox" name="attrs[{{$index}}].required" class="form-control" ng-checked="{{$index != 0}}"></td>
                            <td><input type="text" name="attrs[{{$index}}].length" class="form-control" value="{{column.size}}"></td>
                            <td><input type="checkbox" name="attrs[{{$index}}].display" class="form-control" ng-checked="{{$index != 0}}"></td>
                            <td><input type="checkbox" name="attrs[{{$index}}].sort" class="form-control" ng-checked="{{$index != 0}}"></td>
                            <td><input type="radio" name="normalSearchColumn" class="form-control" value="{{column.dbName}}" ng-checked="{{$index == 1}}"></td>
                            <%--<td><input type="checkbox" name="attrs[{{$index}}].advSearch" class="form-control"></td>--%>
                        </tr>
                        </tbody>
                    </table>
                </div>
            <nav>
                <ul class="pager">
                    <li class="previous"><a href="javascript:void(0)" onclick="history.back();return false"><span aria-hidden="true">&larr;</span> 返回</a></li>
                    <li class="next"><a href="javascript:$('form').submit()">提交 <span aria-hidden="true">&rarr;</span></a></li>
                </ul>
            </nav>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var app = angular.module("step2App",[]);
    app.controller("step2Ctrl",function($scope,$http){
        //加载table下的column
        $http.post("${ctx}/creator/step2?tableName=${tableName}").success(function(response){
            $scope.columns = response;
        });
        //加载输入类型枚举
        $http.post("${ctx}/creator/inputType").success(function(response){
            $scope.inputTypes = response;
        });
    });
</script>

<!-- 底部 -->
<%@include file="../../common/footer.jsp" %>