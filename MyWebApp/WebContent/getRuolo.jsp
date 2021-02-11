
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.lang.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.*"%>
<%
	Class.forName("com.mysql.jdbc.Driver");
	Connection dbConnection = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/gestionale2?user=root&password=Password01@");

	Statement stmt = dbConnection.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT Id, Descrizione FROM Ruolo");

	JSONArray json = new JSONArray();
	while (rs.next()) {
		JSONObject obj = new JSONObject();
		obj.put("id", rs.getObject("Id"));
		obj.put("descrizione", rs.getObject("Descrizione"));
		json.put(obj);
	}
	out.print(json);
	out.flush();
%>