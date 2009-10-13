<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="hood" 	tagdir="/WEB-INF/tags" %>
<%@taglib prefix="hoodfn" 	uri="/WEB-INF/tags/hood-fn.tld" %>
<hood:page title="Home" script="hood.js">
<jsp:body>
<form class="clear" action="${pageContext.request.contextPath}/app/del/ok" method="POST">
    <input type="hidden" name="id" value="${doc.id}"/>
    <input type="hidden" name="rev" value="${doc.revision}"/>
    <p>
        Delete object &quot;<c:out value="${doc.name}"/>&quot;?
    </p>
    <p>
        <input id="cancel" class="submit" type="submit" name="cancel" value="Cancel"/>&nbsp;
        <input id="ok" class="submit" type="submit" name="ok" value="Delete"/>
    </p>
</dl>
    
</form>
</jsp:body>
</hood:page>
