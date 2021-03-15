<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.lang.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.google.gson.*"%>
    
    <%
		
		String where = "";
		Integer filterscount = 0;
		if (null != request.getParameter("filterscount"))
		{
			try
			{
				filterscount = Integer.parseInt(request.getParameter("filterscount"));
			}
			catch (NumberFormatException nfe) {}
			{
			}		
		}
		if (filterscount > 0) {
			where = " WHERE (";
			
			String tmpdatafield = "";
			String tmpfilteroperator = "";
			for (Integer i=0; i < filterscount; i++)
			{
				String filtervalue = request.getParameter("filtervalue" + i);
				String filtercondition = request.getParameter("filtercondition" + i);
				String filterdatafield = request.getParameter("filterdatafield" + i);
				filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
				String filteroperator = request.getParameter("filteroperator" + i);
				
				if (tmpdatafield.equals(""))
				{
					tmpdatafield = filterdatafield;			
				}
				else if (!tmpdatafield.equals(filterdatafield))
				{
					where += ")AND(";
				}
				else if (tmpdatafield.equals(filterdatafield))
				{
					if (tmpfilteroperator.equals("0"))
					{
						where += " AND ";
					}
					else where += " OR ";	
				}
					
				// build the "WHERE" clause depending on the filter's condition, value and datafield.
				switch(filtercondition)
				{
					case "CONTAINS":
						where += " " + filterdatafield + " LIKE '%" + filtervalue + "%'";
						break;
					case "CONTAINS_CASE_SENSITIVE":
						where += " " + filterdatafield + " LIKE BINARY '%" + filtervalue + "%'";
						break;
					case "DOES_NOT_CONTAIN":
						where += " " + filterdatafield + " NOT LIKE '%" + filtervalue + "%'";
						break;
					case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
						where += " " + filterdatafield + " NOT LIKE BINARY '%" + filtervalue + "%'";
						break;
					case "EQUAL":
						where += " " + filterdatafield + " = '" + filtervalue + "'";
						break;
					case "EQUAL_CASE_SENSITIVE":
						where += " " + filterdatafield + " LIKE BINARY '" + filtervalue + "'";
						break;
					case "NOT_EQUAL":
						where += " " + filterdatafield + " NOT LIKE '" + filtervalue + "'";
						break;
					case "NOT_EQUAL_CASE_SENSITIVE":
						where += " " + filterdatafield + " NOT LIKE BINARY '" + filtervalue + "'";
						break;
					case "GREATER_THAN":
						where += " " + filterdatafield + " > '" + filtervalue + "'";
						break;
					case "LESS_THAN":
						where += " " + filterdatafield + " < '" + filtervalue + "'";
						break;
					case "GREATER_THAN_OR_EQUAL":
						where += " " + filterdatafield + " >= '" + filtervalue + "'";
						break;
					case "LESS_THAN_OR_EQUAL":
						where += " " + filterdatafield + " <= '" + filtervalue + "'";
						break;
					case "STARTS_WITH":
						where += " " + filterdatafield + " LIKE '" + filtervalue + "%'";
						break;
					case "STARTS_WITH_CASE_SENSITIVE":
						where += " " + filterdatafield + " LIKE BINARY '" + filtervalue + "%'";
						break;
					case "ENDS_WITH":
						where += " " + filterdatafield + " LIKE '%" + filtervalue + "'";
						break;
					case "ENDS_WITH_CASE_SENSITIVE":
						where += " " + filterdatafield + " LIKE BINARY '%" + filtervalue + "'";
						break;
					case "NULL":
						where += " " + filterdatafield + " IS NULL";
						break;
					case "NOT_NULL":
						where += " " + filterdatafield + " IS NOT NULL";
						break;
				}
									
				if (i == filterscount - 1)
				{
					where += ")";
				}
					
				tmpfilteroperator = filteroperator;
				tmpdatafield = filterdatafield;			
			}
		}
		String orderby = "";
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield != null && sortorder != null && (sortorder.equals("asc") || sortorder.equals("desc")))
		{
			sortdatafield = sortdatafield.replaceAll("([^A-Za-z0-9])", "");
		
			orderby = "order by " + sortdatafield + " " + sortorder;
		}
		
		
		Integer pagenum = 0;
		if (null != request.getParameter("pagenum"))
		{
			try
			{
				pagenum = Integer.parseInt(request.getParameter("pagenum"));
			}
			catch (NumberFormatException nfe) {}
			{
			}		
		}
		
		Integer pagesize = 1000;
		if (null != request.getParameter("pagesize"))
		{
			try
			{
				pagesize = Integer.parseInt(request.getParameter("pagesize"));
			}
			catch (NumberFormatException nfe) {}
			{
			}		
		}
		
		Integer start = pagesize * pagenum;
		
		// database connection
		
		// "jdbc:mysql://localhost:3306/northwind" - the database url of the form jdbc:subprotocol:subname
		
		try {
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ilmar?user=root&password=Alevalen91");
		// retrieve necessary records from database
		Statement stm = conn.createStatement();
		ResultSet totalContratti = stm.executeQuery("SELECT COUNT(*) AS Count FROM contratti"	+ where);
		String totalRecords = "";
		while (totalContratti.next()) {
			totalRecords = totalContratti.getString("Count");
		}
		totalContratti.close();
		String sql = "SELECT Account, Nome, Cognome, Tipologia, Agente, Codice_agente, Quarter FROM contratti " + where + " " + orderby + " LIMIT ?,?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, start);
		stmt.setInt(2, pagesize);
		
		ResultSet contratti = stmt.executeQuery();
		//result.replaceAll("\\","");
		boolean totalRecordsAdded = false;
		// format returned ResultSet as a JSON array
		JsonArray recordsArray = new JsonArray();
		while ( contratti.next()) {
			JsonObject currentRecord = new JsonObject();
			 currentRecord.add("Account",	new JsonPrimitive(contratti.getString("Account")));
			currentRecord.add("Nome", new JsonPrimitive(contratti.getString("Nome")));
			currentRecord.add("Cognome", new JsonPrimitive(contratti.getString("Cognome")));
			currentRecord.add("Tipologia", new JsonPrimitive(contratti.getString("Tipologia")));
			currentRecord.add("Agente", new JsonPrimitive(contratti.getString("Agente")));
			currentRecord.add("Codice_agente", new JsonPrimitive(contratti.getString("Codice_agente")));
			currentRecord.add("Quarter", new JsonPrimitive(contratti.getString("Quarter")));
			if (totalRecordsAdded == false) {
				// add the number of filtered records to the first record for client-side use
				currentRecord.add("totalRecords", new JsonPrimitive(totalRecords));
				totalRecordsAdded = true;
			}
			recordsArray.add(currentRecord);
			
			out.print(recordsArray);
			out.flush();
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
%>
	
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>