<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="hood" 	tagdir="/WEB-INF/tags" %>
<%@taglib prefix="hoodfn" 	uri="/WEB-INF/tags/hood-fn.tld" %>
<hood:page title="Home" script="hood.js">
<jsp:attribute name="head">
</jsp:attribute>
<jsp:body>
<c:choose>
	<c:when test="${empty docs}">
No objects in area
	</c:when>
	<c:otherwise>
<table>
<tr>
    <th>&nbsp;</th>
    <th>Name</th>
    <th>Type</th>
    <th>Description</th>
</tr>
<c:forEach var="doc" items="${docs}">
<tr>
    <td>
        <hood:link href="/app/del?id=${doc.id}">Del</hood:link>
    </td>
    <td>
        <c:out value="${doc.name}"/>
    </td>
	<td>
        <c:out value="${doc.documentType}"/>
    </td>
    <td>
        <c:out value="${doc.description}"/>
    </td>
</tr>
</c:forEach>
</table>
	</c:otherwise>
</c:choose>

</jsp:body>
</hood:page>