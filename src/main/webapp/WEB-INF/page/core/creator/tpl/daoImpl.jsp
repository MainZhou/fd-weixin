<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${columnRequest.packageName}.dao.${columnRequest.prefix}Dao">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <resultMap type="${columnRequest.prefix}" id="${columnRequest.prefix}Map" autoMapping="true">
        <c:forEach items="${columnRequest.attrs}" var="item"><c:choose><c:when test="${item.value.dbName == 'GUID'}"><id property="${item.value.javaName}" column="GUID" /></c:when><c:otherwise><result property="${item.value.javaName}" column="${item.value.dbName}"/></c:otherwise></c:choose>
        </c:forEach>
    </resultMap>

    <!-- 添加一个实体的公共实现 -->
    <insert id="add" parameterType="${columnRequest.prefix}">
        insert into ${columnRequest.tableName}
        (<c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${!status.last}">${item.value.dbName},</c:when><c:otherwise>${item.value.dbName}</c:otherwise></c:choose></c:forEach>)
        values
        (<c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${!status.last}">${fn:escapeXml("#{")}${item.value.javaName}${fn:escapeXml("}")},</c:when><c:otherwise>${fn:escapeXml("#{")}${item.value.javaName}${fn:escapeXml("}")}</c:otherwise></c:choose></c:forEach>)
    </insert>

    <c:choose><c:when test="${columnRequest.databaseName == 'oracle'}"><!-- 批量添加实体List集合的公共实现 -->
    <insert id="addBatch" parameterType="list">
        insert all
        <foreach collection="list" item="item" index="index" separator=" " >
            into ${columnRequest.tableName} (<c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${!status.last}">${item.value.dbName},</c:when><c:otherwise>${item.value.dbName}</c:otherwise></c:choose></c:forEach>)
            values
            (<c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${!status.last}">${fn:escapeXml("#{item.")}${item.value.javaName}${fn:escapeXml("}")},</c:when><c:otherwise>${fn:escapeXml("#{item.")}${item.value.javaName}${fn:escapeXml("}")}</c:otherwise></c:choose></c:forEach>)
        </foreach>
        SELECT * FROM dual
    </insert>
    </c:when><c:otherwise><!-- 批量添加实体List集合的公共实现 -->
    <insert id="addBatch" parameterType="list">
        insert into ${columnRequest.tableName}
        (<c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${!status.last}">${item.value.dbName},</c:when><c:otherwise>${item.value.dbName}</c:otherwise></c:choose></c:forEach>)
        values
        <foreach collection="list" item="item" index="index" separator="," >
            (<c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${!status.last}">${fn:escapeXml("#{")}${item.value.javaName}${fn:escapeXml("}")},</c:when><c:otherwise>${fn:escapeXml("#{")}${item.value.javaName}${fn:escapeXml("}")}</c:otherwise></c:choose></c:forEach>)
        </foreach>
    </insert>
    </c:otherwise></c:choose>

    <!-- 通过ID查找实体的公共实现 -->
    <select id="findByGuid" resultMap="${columnRequest.prefix}Map">
        select * from ${columnRequest.tableName} tableAlias where tableAlias.GUID=${fn:escapeXml("#{guid}")}
    </select>

    <!-- easyUi DataGrid查询 -->
    <select id="findByGridRequest" resultMap="${columnRequest.prefix}Map">
        select * from ${columnRequest.tableName} tableAlias
        <where>
            <foreach item="item" index="key" collection="map" open=" " separator=" AND " close=" ">
                ${fn:escapeXml("${key}")} like ${fn:escapeXml("#{item}")}
            </foreach>
        </where>
        <if test="null != sort and '' != sort and null != order and '' != order">
            order by ${fn:escapeXml("${sort} ${order}")}
        </if>
    </select>

    <!-- 根据实体进行唯一性判断 -->
    <select id="findByDomain" resultMap="${columnRequest.prefix}Map">
        select tableAlias.guid from ${columnRequest.tableName} tableAlias
        <where>
            <c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:choose><c:when test="${item.value.javaName == 'guid'}"><if test="null != guid and '' != guid">GUID != ${fn:escapeXml("#{guid}")}</if></c:when><c:otherwise><if test="null != ${item.value.javaName} and '' != ${item.value.javaName}"> AND ${item.value.dbName}=${fn:escapeXml("#{")}${item.value.javaName}}</if></c:otherwise>
            </c:choose></c:forEach>
        </where>
    </select>

    <!-- 更新实体的公共实现 -->
    <update id="update" parameterType="${columnRequest.prefix}">
        update ${columnRequest.tableName} tableAlias
        set
        <c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:if test="${item.value.javaName != 'guid'}"><c:choose><c:when test="${!status.last}">${item.value.dbName}=${fn:escapeXml("#{")}${item.value.javaName}},</c:when><c:otherwise>${item.value.dbName}=${fn:escapeXml("#{")}${item.value.javaName}}</c:otherwise>
        </c:choose></c:if></c:forEach>
        where guid=${fn:escapeXml("#{guid}")}
    </update>

    <!-- 更新实体的公共实现(为Null或"" 不更新该字段) -->
    <update id="updateNotEmpty" parameterType="${columnRequest.prefix}">
        update ${columnRequest.tableName} tableAlias
        <set>
            <c:forEach items="${columnRequest.attrs}" var="item" varStatus="status"><c:if test="${item.value.javaName != 'id'}"><if test="null != ${item.value.javaName} and '' != ${item.value.javaName}"><c:choose><c:when test="${!status.last}">${item.value.dbName}=${fn:escapeXml("#{")}${item.value.javaName}},</c:when><c:otherwise>${item.value.dbName}=${fn:escapeXml("#{")}${item.value.javaName}}</c:otherwise></c:choose></if>
            </c:if></c:forEach>
        </set>
        where guid=${fn:escapeXml("#{guid}")}
    </update>

    <!-- 批量删除的公共实现 -->
    <delete id="delBatch" parameterType="list">
        delete from ${columnRequest.tableName}
        where
        guid in
        <foreach collection="list" item="item" open="(" separator="," close=")">
            ${fn:escapeXml("#{item}")}
        </foreach>
    </delete>

</mapper>