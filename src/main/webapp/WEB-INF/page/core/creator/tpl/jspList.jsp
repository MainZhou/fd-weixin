<%@page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:out escapeXml="false" value='<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="boyoi" uri="http://boyoi.com/taglib" %>
<%@ include file="/WEB-INF/page/common/common.jsp" %> '/>
<c:set var="newline" value="<%='\n'%>" />
${newline}
${newline}
<!-- 功能按钮 -->
<div class="box-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-md-3">
                <boyoi:makeSearch name="tableAlias.${columnRequest.normalSearchColumn}" addPlease="true">
                    <spring:message code='${columnRequest.prefix}.${columnRequest.normalSearchColumn}.label' />
                </boyoi:makeSearch>
            </div>
            <div class="col-xs-12 col-md-9">
                <ul class="nav nav-pills nav-padding pull-right">
                    <boyoi:makeButton addBtn="true" editBtn="true" detailBtn="true" delBtn="true"/>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- dataGrid -->
<div class="box-footer">
    <table class="gridContainer"></table>
</div>

<script type="text/javascript">
    <c:forEach items="${columnRequest.attrs}" var="item">var i18n_${item.value.javaName} = '<spring:message code='${columnRequest.prefix}.${item.value.javaName}.label' />';
    </c:forEach>
    ${newline}
</script>
<script type="text/javascript" src="<c:out value='\${ctx}'/>/grid/${columnRequest.prefixFirstCharLow}.js"></script>