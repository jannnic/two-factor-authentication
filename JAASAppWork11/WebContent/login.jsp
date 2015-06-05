<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" media="screen" href="/JAASApp/style/style.css" />
<title>Login</title>
</head>
<body>
<div id="mainBox">
<% if (request.getSession().getAttribute("message") != null) { %>
	<h1>Info</h1>
	<%= request.getSession().getAttribute("message") %>
	<a href="<%=request.getContextPath() + "/index.jsp" %>">Zum Start</a>
	<% request.getSession().removeAttribute("message"); 
} %>
	<h1>TAN anfordern</h1>
	<form method="POST" action="j_security_check">
		<table>
			<tr>
				<td><label for="j_username">Benutzername:</label></td>
				<td><input type="text" id="j_username" name="j_username" /></td>
			</tr>
			<tr>
				<td><label for="j_password">Passwort:</label></td>
				<td><input type="password" id="j_password" name="j_password" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="TAN anfordern" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
