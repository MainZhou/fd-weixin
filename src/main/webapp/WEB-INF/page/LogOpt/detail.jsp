<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %>

<div class="container-fluid">
    <div class="row">
        <!-- 实体内容 -->
        <div class="col-xs-12 col-md-12 sidebar-offcanvas">
            <c:choose>
                <c:when test="${noLog != null}">
                    <div class="form-group">
                        <label style="color:red">${noLog}</label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <label><spring:message code='LogOpt.optObject.label' /></label>
                        <label class="form-control">${detail.optObject}</label>
                    </div>

                    <c:forEach items="${currAllOptLog}" var="item">
                        <div class="form-group">
                            <label><fmt:formatDate value="${item.optDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;由&nbsp;${item.optPerson}&nbsp;${item.optType.itemLabel}</label>

                            <c:choose>
                                <c:when test="${item.compareSize ==0}">
                                    <textarea class="form-control" readonly rows="1">没做任何修改</textarea>
                                </c:when>
                                <c:otherwise>
                                    <textarea class="form-control" readonly rows="${item.compareSize}">${item.compareDesc}</textarea>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </c:forEach>
                </c:otherwise>
                </c:choose>

            </div>
        <!-- 实体内容结束-->
    </div>
</div>

<script type="text/javascript">
   var success = '${detail.optSuccess}';
    if(success === '1')
        jQuery("#opt_success").text("成功");
    else
        jQuery("#opt_success").text("失败");

</script>