<%@tag description="page layout"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sf" 		uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="hood" 		tagdir="/WEB-INF/tags" %>
<%@taglib prefix="hoodfn" 	uri="/WEB-INF/tags/hood-fn.tld" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@attribute name="head" fragment="true"%>
<%@attribute name="sidebar" fragment="true" %>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@attribute name="script" required="false" type="java.lang.String"%>
<%@attribute name="stylesheet" required="false" type="java.lang.String"%>
<c:if test="${empty param.ajax}">
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title} :: hood</title>

<link rel="stylesheet" href="<c:url value="/style/reset.css"/>" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="<c:url value="/style/site.css"/>" type="text/css" media="screen, projection" />

<c:forTokens var="item" items="${stylesheet}" delims=",">
<link rel="stylesheet" href="<c:url value="/style/"/>${item}" type="text/css" media="screen, projection" />
</c:forTokens>
<script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAA9KHSN9V8JlBG-E7OqIwl9RRtJcnXFm2bRbOk4DF_xY85BAOt6hRQEQiQ9j4QxID_rn_n9nwTUAWi7w">
</script>
<script type="text/javascript" src="<c:url value="/script/jquery-1.3.2.js"/>">
</script>
<script type="text/javascript" src="<c:url value="/script/json2.js"/>">
</script>
<script type="text/javascript" src="<c:url value="/script/firebug-emu.js"/>">
</script>

<c:forTokens var="item" items="${script}" delims=",">
    <script type="text/javascript"
        src="<c:url value="/script/"/>${item}">
</script>
</c:forTokens>

<meta name="base_url" content="<c:url value="/"/>" />
<%--
      Insert head fragment from page
    --%>
<jsp:invoke fragment="head" />
</head>
<!--[if !IE]><!--> <body> <!--<![endif]-->
<!--[if lt IE 7 ]> <body class="ie6"> <![endif]--> 
<!--[if IE 7 ]>    <body class="ie7"> <![endif]--> 
<!--[if IE 8 ]>    <body class="ie8"> <![endif]--> 

<div id="page">
<div class="clear">
    <div id="sidebar">
        <hood:link class="home" href="/app/home"><span class="text">hood</span></hood:link>
    	<jsp:invoke fragment="sidebar"/>
    </div>
	<div id="content">
</c:if> 
		<jsp:doBody />
<c:if test="${empty param.ajax}">
	 </div>
</div>
<div id="footer" class="clear"><b>Help:</b> double click on map to create new object, click marker for details/delete 
<span class="right">&copy; 2009 Sven Helmberger</span></div>
</div>
</body>
</html>
</c:if> 
