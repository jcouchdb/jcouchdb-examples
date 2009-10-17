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
    <th>Map</th>
</tr>
<c:forEach var="doc" items="${docs}">
<tr>
    <td valign="top">
        <hood:link href="/app/del?id=${doc.id}">Del</hood:link>
    </td>
    <td valign="top">
        <c:out value="${doc.name}"/><br/>
        <c:out value="${doc.description}"/>
    </td>
    <td>
        <img width="384" height="384" src="http://maps.google.com/maps/api/staticmap?center=${doc.location.latitude},${doc.location.longitude}&zoom=14&size=384x384&maptype=roadmap&markers=color:blue|${doc.location.latitude},${doc.location.longitude}&sensor=false&key=ABQIAAAA9KHSN9V8JlBG-E7OqIwl9RRtJcnXFm2bRbOk4DF_xY85BAOt6hRQEQiQ9j4QxID_rn_n9nwTUAWi7w"/>
    </td>
</tr>
</c:forEach>
</table>
	</c:otherwise>
</c:choose>

</jsp:body>
</hood:page>