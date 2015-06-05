<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" media="screen" href="style/style.css" />
<title>Login</title>
</head>
<body>
<div id="mainBox">
<% if (request.getSession().getAttribute("message") != null) { %>
	<h1>Info</h1>
	<%= request.getSession().getAttribute("message") %>
	<a href="<%=request.getContextPath() + "/LogoutServlet" %>">Zum Start</a>
	<% request.getSession().removeAttribute("message"); 
} %>
	<h1>Login</h1>
	<p style="font-size: smaller;">Geben Sie hier bitte die Tan aus der GoogleAuthenticator-App ein</p>
	<form method="POST" action="SecurityServlet">
	<table>
			<tr>
				<td><label for="otp">TAN:</label></td>
				<td><input type="password" name="j_tan" id="j_tan"/></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
