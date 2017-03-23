package ${columnRequest.packageName}.domain;
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
import com.boyoi.core.domain.BaseDomain;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * ${columnRequest.funcName} 实体对象
 *
 * @author ${columnRequest.devPerson}
 */
public class ${columnRequest.prefix} extends BaseDomain {
    <c:forEach items="${columnRequest.attrs}" var="item"><c:if test="${item.value.javaName != 'guid'}">
    /**
     * ${item.value.cnLabel}
     */
    <c:if test="${true == item.value.required && item.value.javaType == 'Integer'}">@NotNull(message = "{${columnRequest.prefix}.validator.${item.value.javaName}.required}")</c:if><c:if test="${true == item.value.required && item.value.javaType == 'String'}">@NotBlank(message = "{${columnRequest.prefix}.validator.${item.value.javaName}.required}")</c:if><c:if test="${'' != item.value.length && null != item.value.length && item.value.javaType == 'Integer'}">@Max(value=${item.value.length}, message = "{${columnRequest.prefix}.validator.${item.value.javaName}.max}")</c:if><c:if test="${'' != item.value.length && null != item.value.length && item.value.javaType == 'String'}">@Size(max=${item.value.length}, message = "{${columnRequest.prefix}.validator.${item.value.javaName}.max}")</c:if><c:if test="${item.value.javaType == 'java.util.Date'}">@DateTimeFormat(pattern = "yyyy-MM-dd")@JSONField(format = "yyyy-MM-dd")</c:if>
    private ${item.value.javaType} ${item.value.javaName};
    </c:if></c:forEach>

    <c:forEach items="${columnRequest.attrs}" var="item"><c:if test="${item.value.javaName != 'guid'}">
    public ${item.value.javaType} get${fn:toUpperCase(fn:substring(item.value.javaName,0 ,1 ))}${fn:substring(item.value.javaName, 1,fn:length(item.value.javaName) )}() {
        return ${item.value.javaName};
    }

    public void set${fn:toUpperCase(fn:substring(item.value.javaName,0 ,1 ))}${fn:substring(item.value.javaName, 1,fn:length(item.value.javaName) )}(${item.value.javaType} ${item.value.javaName}) {
        this.${item.value.javaName} = ${item.value.javaName};
    }
    </c:if></c:forEach>

}