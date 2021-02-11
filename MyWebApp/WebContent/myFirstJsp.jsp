
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prima pagina JSP</title>
</head>
<body>

<!-- Commento HTML -->
<%-- commento JSP --%>
Numero random: <%= Math.random() %>

<%
	byte i;
	for (i=1; i<=10; i++) {
%>
	<p> Valore della variabile i: <%= i %></p>
<%
	}
%>

</body>
</html>