<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="hood" 	tagdir="/WEB-INF/tags" %>
<%@taglib prefix="hoodfn" 	uri="/WEB-INF/tags/hood-fn.tld" %>
<hood:page title="Home" script="hood.js">
<jsp:attribute name="head">
<meta name="hood_info" content="${hoodfn:json(pageContext.request,hood)}"/>
</jsp:attribute>
<jsp:attribute name="sidebar">
<c:forEach var="curHood" items="${hoods}">
    <c:choose>
        <c:when test="${curHood.id == hood.id}">
        <i title="${hood.description}">${curHood.name}</i>
        </c:when>
        <c:otherwise>
        <hood:link href="/app/hood?id=${curHood.id}" title="${curHood.description}" >${curHood.name}</hood:link>
        </c:otherwise>
    </c:choose>
</c:forEach>
</jsp:attribute>
<jsp:body>
<div id="map">
<p>
<b>Hood:</b> ${hood.name}<br/>
<c:out value="${hood.description}"/>
</p><p>
<ul>
<li>
    <hood:link href="/app/objects?center_lat=${hood.location.latitude}&center_lon=${hood.location.longitude}">List objects around ${hood.name}</hood:link></li>
</li>
<li>
    <hood:link href="/app/new">Create new...</hood:link>
</li>
</ul>
</p>
</div>
</jsp:body>
</hood:page>