package gestionale_ale;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import com. google. gson. *;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.xdevapi.Statement;




@WebServlet("/ContrattiQuery")
public class ContrattiQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	private PreparedStatement pstmt;
	public ResultSet rs;
	public ResultSet rs2;
	private String sql;
	private String sql2;
	private java.sql.Statement stm;
	private String account;
	private String agente;
	private String tipologia;
	private String quarter;
	private String codice_agente;
	private String nome;
	private String cognome;
	private boolean update;
	private String result = "";
	private PrintWriter writer;
	private CallableStatement cstm;
	private java.sql.CallableStatement cstm2;
	private String [] account_array;
	private String delete;
	private String account_del;
	
	java.util.Date date=new java.util.Date();  
	Date quarter1 = new Date();
	
	 
       
	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ilmar?user=root&password=Alevalen91");
		return conn;
	}
	
	private void closeConn() throws SQLException {
		conn.close();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
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
		
		getConn();
		// retrieve necessary records from database
		stm = conn.createStatement();
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
		JSONArray recordsArray = new JSONArray();
		while ( contratti.next()) {
			JSONObject currentRecord = new JSONObject();
			
			 
		
			 currentRecord.put("Account",	contratti.getObject("Account"));
			currentRecord.put("Nome", contratti.getObject("Nome"));
			currentRecord.put("Cognome", contratti.getObject("Cognome"));
			currentRecord.put("Tipologia", contratti.getObject("Tipologia"));
			currentRecord.put("Agente", contratti.getObject("Agente"));
			currentRecord.put("Codice_agente", contratti.getObject("Codice_agente"));
			currentRecord.put("Quarter", contratti.getObject("Quarter"));
			if (totalRecordsAdded == false) {
				// add the number of filtered records to the first record for client-side use
				currentRecord.put("totalRecords", (totalRecords));
				totalRecordsAdded = true;
				
			}
			recordsArray.put(currentRecord);
			response.setContentType("application/json");
			writer = response.getWriter();
			
			
		}
		writer.print(recordsArray);
		writer.flush();
		writer.close();
		closeConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

	
		
		
//		
//		final String account_par = request.getParameter("account");
//		final String tipo_contratto = request.getParameter("tipologia");
//		final String agente = request.getParameter("agente");
//		if (account_par == null && agente == null && tipo_contratto == null ) {
//		
	
//			JSONArray json = new JSONArray();
//			while(rs.next()) {
//				
//
//				JSONObject obj = new JSONObject();
//				obj.put("account", rs.getObject("account"));
//				obj.put("nome", rs.getObject("nome"));
//				obj.put("cognome", rs.getObject("cognome"));
//				obj.put("tipologia", rs.getObject("tipologia"));
//				obj.put("agente", rs.getObject("agente"));
//				obj.put("codice Agente", rs.getObject("codice_agente"));
//				obj.put("Quarter", rs.getObject("quarter"));
//				json.put(obj);
//				
//				
//				
//			//writer.print(rs.getString("account") + " " + rs.getString("cognome") + 
//			//		" " + 
//			//		rs.getString("tipologia") + 
//			//		"" +
//			//		rs.getString("Agente") + 
//			//		" "
//			//		+ rs.getString("codice_agente"));
//			}
//			
//			writer.print(json);
//			writer.close();
//			
//		} catch (ClassNotFoundException | SQLException e) {
//			
//			e.printStackTrace();
//		}
//		
//		}
//		else if (agente != null || account_par != null) {
//			try {
//				getConn();
//				sql = "{ call select_agente (?)}";
//			
//				cstm = (CallableStatement) conn.prepareCall(sql);
//				cstm.setString(1, agente);
//				writer = response.getWriter();
//				cstm.execute();
//				rs = cstm.getResultSet();
//				JSONArray json = new JSONArray();
//				while(rs.next()) {
//					
//					
//					JSONObject obj = new JSONObject();
//					obj.put("Account", rs.getObject("account"));
//					obj.put("Nome", rs.getObject("nome"));
//					obj.put("Cognome", rs.getObject("cognome"));
//					obj.put("Tipologia", rs.getObject("tipologia"));
//					obj.put("Agente", rs.getObject("agente"));
//					obj.put("Codice Agente", rs.getObject("codice_agente"));
//					obj.put("Quarter", rs.getObject("quarter"));
//					json.put(obj);
//					
//					
//				//writer.print(rs.getString("account") + " " + rs.getString("cognome") + 
//					//	" " + 
//					//	rs.getString("tipologia") + 
//						//"" +
//					//	rs.getString("Agente") + 
//						//" "
//						//+ rs.getString("codice_agente"));
//				}
//				writer.print(json);
//				writer.close();
//				
//			} catch (ClassNotFoundException | SQLException e) {
//				
//				e.printStackTrace();
//			}
//			
//		}
//		else if (tipo_contratto != null) {
//			try {
//				getConn();
//				sql = "{ call select_tipologia (?)}";
//			
//				cstm = (CallableStatement) conn.prepareCall(sql);
//				cstm.setString(1, tipo_contratto);
//				writer = response.getWriter();
//				cstm.execute();
//				rs = cstm.getResultSet();
//				JSONArray json = new JSONArray();
//				while(rs.next()) {
//				
//					
//					JSONObject obj = new JSONObject();
//					obj.put("Account", rs.getObject("account"));
//					obj.put("Nome", rs.getObject("nome"));
//					obj.put("Cognome", rs.getObject("cognome"));
//					obj.put("Tipologia", rs.getObject("tipologia"));
//					obj.put("Agente", rs.getObject("agente"));
//					obj.put("Codice Agente", rs.getObject("codice_agente"));
//					obj.put("Quarter", rs.getObject("quarter"));
//					json.put(obj);
//					
//					
//					//writer.print(rs.getString("account") + " " + rs.getString("cognome") + 
//					//	" " + 
//						//rs.getString("tipologia") + 
//						//"" +
//						//rs.getString("Agente") + 
//						//" "
//						//+ rs.getString("codice_agente"));
//				}
//				writer.print(json);
//				writer.close();
//				
//			} catch (ClassNotFoundException | SQLException e) {
//				
//				e.printStackTrace();
//			}
//		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		try {
//			getConn();
//			sql = "{ call select_all}";
//			cstm = (CallableStatement) conn.prepareCall(sql);
//			
//			cstm.executeUpdate();
//			rs = cstm.getResultSet();
//			
//		}
//			
//			catch(SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		catch (ClassNotFoundException e) {
//			
//		}
}

	 
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		Calendar calendar = Calendar.getInstance();
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		
		if (dayOfYear < 90 ) {
			quarter = "1";
		}
		
		else if (dayOfYear > 90 && dayOfYear < 180) {
			
			quarter = "2";
		}
		
		else if (dayOfYear > 180 && dayOfYear <270) {
			quarter = "3";
		}
		
		else {
			quarter = "4";
		}
		
		delete = request.getParameter("elimina");
		account_del = request.getParameter("account_del");
		account = request.getParameter("account_par");
		agente = request.getParameter("Agente");
		tipologia = request.getParameter("Tipologia");
		nome= request.getParameter("nome_par");
		cognome = request.getParameter("cognome_par");
		//quarter = request.getParameter("quarter_par");
		codice_agente = request.getParameter("codice_agente_par");
		
		if(delete != null) {
			
			try {
			
				writer=response.getWriter();
				getConn();
				sql = "{call Ilmar.DELETE (?) }";
				cstm2 = conn.prepareCall(sql);
				cstm2.setString(1, account_del);
				cstm2.execute();
				cstm2.close();
				response.sendRedirect("grid.html");
				writer.close();
				closeConn();
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		else {
		try {
//			delete = request.getParameter("elimina");
//			account_del = request.getParameter("account_del");
//			account = request.getParameter("account_par");
//			agente = request.getParameter("agente_par");
//			tipologia = request.getParameter("tipologia_par");
//			nome= request.getParameter("nome_par");
//			cognome = request.getParameter("cognome_par");
//			quarter = request.getParameter("quarter_par");
//			codice_agente = request.getParameter("codice_agente_par");
//			
			getConn();
		

			
			
			sql = "{ call select_all}";
			cstm = (CallableStatement) conn.prepareCall(sql);
			
			cstm.execute();
			rs = cstm.getResultSet();

			
			
			while (rs.next()){
				
				
					if((rs.getString("Account").equals(account))) {
						sql2 = "{ call Ilmar.UPDATE (?,?,?,?,?,?,?)}";
						break;
						 
			}
					else{
						sql2 = "{ call Ilmar.INSERT (?,?,?,?,?,?,?)}";
					}
				
			}
				 
				
						
						cstm2 = conn.prepareCall(sql2);
						cstm2.setString(1,account);
						cstm2.setString(2, nome);
						cstm2.setString(3, cognome);
						cstm2.setString(4, tipologia);
						cstm2.setString(5, agente);
						cstm2.setString(6, codice_agente);
						cstm2.setString(7, quarter);
						cstm2.execute();
						cstm2.close();
						
						final String state = "Operazione eseguita";
					   // writer = response.getWriter();
					   // writer.print(state);
					    //writer.flush();
					    //response.setContentType("text/html"); 
						//RequestDispatcher rd=request.getRequestDispatcher("grid.html");
						//rd.forward(request, response);
						response.sendRedirect("grid.html");
						//writer.close();
						closeConn();
				
					
				
						
						//rs = cstm.getResultSet();
					
						
						//writer.print(state);
						
						
				
					
				
				
					
			}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
	 catch (ClassNotFoundException e) {
				
			}
		}
		}		
		

		
	

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		account = request.getParameter("account");
		try {
			final String state = account;
			writer=response.getWriter();
			getConn();
			sql = "{call Ilmar.DELETE (?) }";
			cstm2 = conn.prepareCall(sql);
			cstm2.setString(1, account);
			cstm2.execute();
			cstm2.close();
			writer.print(state);
			writer.close();
			closeConn();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
