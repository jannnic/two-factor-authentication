<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" media="screen" href="style/style.css" />
<title>Willkommen im geschützten Bereich</title>
</head>
<body>
<div id="mainBox">
	<h1>Willkommen im geschützten Bereich</h1>
	<p>Dieser Content ist streng geheim</p>
	<a href="<%= request.getContextPath() + "/logout.jsp" %>">Logout</a>
</div>
</body>
</html>