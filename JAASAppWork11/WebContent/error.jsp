<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.dhbw.otp.filters.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" media="screen" href="style/style.css" />
<title>Fehler</title>
</head>
<body>
<div id="mainBox">
	<h1>Fehler!</h1>
	<% if (Constants.ERR_MSG != null) { %>
	<p><%= Constants.ERR_MSG %></p>
	<% Constants.ERR_MSG = null; 
	} else { %>
	<p>Es gab einen schweren Fehler. Bitte versuchen Sie es erneut!</p>
	<% } %>
	<p><a href="<%=request.getContextPath() + "/index.jsp" %>">Zum Start</a></p>
</div>
</body>
</html>