<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" media="screen" href="style/style.css" />
<title>Logout</title>
</head>
<body>
<% request.getSession().invalidate(); %>
<div id="mainBox">
	<h1>Logout</h1>
	<% if (request.getSession().getAttribute("message") != null) { %>
	<%= request.getSession().getAttribute("message") %>
	<% request.getSession().removeAttribute("message"); 
	} else { %>
	<p>Sie wurden erfolgreich abgemeldet.</p>
	<% } %>
	<p><a href="<%= request.getContextPath() + "/index.jsp" %>">Zum Start</a></p>
</div>
</body>
</html>