<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../common/header-creator.jsp"%>

<div class="container-fluid" ng-app="step1App" ng-controller="step1Ctrl">
    <div class="row">
        <div class="col-xs-12 col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">请选择一个表</div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <tbody>
                            <tr ng-repeat="table in tables">
                                <td><a href="${ctx}/creator/step2?tableName={{table}}"> {{table}}</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var app = angular.module("step1App",[]);
    app.controller("step1Ctrl",function($scope,$http){
        $http.post("${ctx}/creator/step1").success(function(response){
           $scope.tables = response;
        });
    });
</script>