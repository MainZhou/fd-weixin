<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="baseUrl" value="${pageContext.request.servletContext.getAttribute('baseUrl')}"/>
<!DOCTYPE html>
<script type="text/javascript">
    var ctx = '${pageContext.request.contextPath}';
    var baseUrl = '${pageContext.request.servletContext.getAttribute("baseUrl")}';
</script>
