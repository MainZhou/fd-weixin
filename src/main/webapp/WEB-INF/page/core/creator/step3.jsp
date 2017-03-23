<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../../common/header-creator.jsp"%>

<div class="container-fluid" ng-app="step3App" ng-controller="step3Ctrl">
    <div class="row">
        <div class="col-xs-12 col-md-10 col-md-offset-1">
            <c:forEach items="${msg}" var="m">
                <c:if test="${m.key == true && fn:length(m.value) >0}">
                    <div class="alert alert-success">
                            <c:forEach items="${m.value}" var="success">
                                <p>${success}</p>
                            </c:forEach>
                    </div>
                </c:if>
                <c:if test="${m.key == false && fn:length(m.value) >0}">
                    <div class="alert alert-danger">
                        <c:forEach items="${m.value}" var="danger">
                            <p>${danger}</p>
                        </c:forEach>
                    </div>
                </c:if>
            </c:forEach>
            <nav>
                <ul class="pager">
                    <li class="previous"><a href="javascript:void(0)" onclick="history.back();return false"><span aria-hidden="true">&larr;</span> 返回</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<!-- 底部 -->
<%@include file="../../common/footer.jsp" %>