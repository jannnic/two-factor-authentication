<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" media="screen" href="/JAASApp/style/style.css" />
<title>New Secret</title>
</head>
<body>
<div id="mainBox">
<h1>Ihr persönlicher secret</h1>
	<form method="POST" action="SecretServlet">
	<table>
		<tr>
			<td><p> Secret:
					<%=request.getSession().getAttribute("secret")%>
			</p></td>
			<td></td>
		</tr>
		<tr>
			<td><a href="<%=request.getSession().getAttribute("qrCode").toString()%>">QRCode</a></td>
			<td></td>
		</tr>
		<tr>
			<td><img src="<%=request.getSession().getAttribute("qrCode").toString()%>"></img></td>
			<td></td>
		</tr>
		<tr>
			<td><input type="submit" name="cancel" value="Abbrechen" /> &nbsp; <input type="submit" name="save" value="Speichern" /></td>
			<td></td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>