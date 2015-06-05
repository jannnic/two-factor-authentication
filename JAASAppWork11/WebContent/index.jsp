<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" media="screen" href="style/style.css" />
<title>Willkommen</title>
</head>
<body>
<div id="mainBox">
	<h1>Willkommen</h1>
	<% if (request.getSession().getAttribute("message") != null) { %>
	<%= request.getSession().getAttribute("message") %>
	<% request.getSession().removeAttribute("message"); 
	} else { %>
	<!-- <p>Um Zugang zum geschützten Bereich zu haben, benötigen Sie eine TAN. Falls Sie noch keine haben, können Sie eine <a href="/JAASApp/token.jsp">anfordern</a>.</p>  -->
	<p>Um Zugang zum geschützten Breich zu haben, müssen Sie sich anmelden.</p>
	<% } %>
	<p><a href="protected/index.jsp">Zum geschützten Bereich</a></p>
</div>
</body>
</html>