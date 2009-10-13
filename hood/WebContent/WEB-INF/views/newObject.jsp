<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="hood" 	tagdir="/WEB-INF/tags" %>
<%@taglib prefix="hoodfn" 	uri="/WEB-INF/tags/hood-fn.tld" %>
<hood:page title="Home" script="hood.js">
<jsp:body>
<form:form cssClass="clear" action="${pageContext.request.contextPath}/app/new/create" method="POST" modelAttribute="newObject" acceptCharset="UTF-8">
<div id="errorContainer">
<form:errors cssClass="error" path="*"/>
</div>
<ul>
<li><input id="type1" type="radio" name="type" value="PLACE" checked="checked"/> <label for="type1"></label><img src="<c:url value="/image/place.png"/>"/> Place</label></li>
<li><input id="type2" type="radio" name="type" value="PERSON" /> <label for="type2"><img src="<c:url value="/image/person.png"/>"/> Person</label></li>
<li><input id="type3" type="radio" name="type" value="HOOD" /> <label for="type3"><img src="<c:url value="/image/hood.png"/>"/> Hood</label></li>
</ul>
<dl class="clear">
    <c:choose>
        <c:when test="${empty param.ajax}">
    <dt>Latitude</dt>
    <dd><form:input path="lat"/></label><br/></dd>
    <dt>Longitude</</dl>
    <dd><form:input path="lon"/></dd>
        </c:when>
        <c:otherwise>
    <input type="hidden" name="lat" value="${param.lat}"/>
    <input type="hidden" name="lon" value="${param.lon}"/>
        </c:otherwise>
    </c:choose>
    <dt><label for="name">Name</label></dt>
    <dd><form:input id="name" path="name"/></label><br/></dd>
    <dt><label for="description">Description</label></</dl>
    <dd><form:textarea rows="5" cols="60" id="description" path="description"/></dd>
    <dt>&nbsp;</dt>
    <dd>
        <input id="cancel" class="submit" type="submit" value="Cancel" disabled="disabled"/>&nbsp;
        <input id="ok" class="submit" type="submit" value="Create"/>
    </dd>
</dl>
    
</form:form>
</jsp:body>
</hood:page>