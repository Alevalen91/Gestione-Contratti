package gestionale_ale;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.Date;


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




@WebServlet("/LinkemInserimenti")
public class LinkemInserimenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String aggiorna_nota;
	private String account_nota;
	private Connection conn;
	
	public ResultSet rs;
	public ResultSet rs2;
	private String sql;
	private String sql2;
	private java.sql.Statement stm;
	private String account;
	private String agente;
	private String tipologia;
	private String email;
	private String codice_agente;
	private String nome;
	private String cognome;
	
	
	private PrintWriter writer;
	private CallableStatement cstm;
	private java.sql.CallableStatement cstm2;
	
	private String delete;
	private String account_del;
	private String cap;
	private String telefono;
	private String indirizzo;

	LocalDate ld = LocalDate.parse( "1960-01-23" ) ;
	
	  



	 
       // Open Db connection 
	
	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://ilmar.crqnoawq1chg.eu-south-1.rds.amazonaws.com:3306/Ilmar","IlmarUser","Ilmar0282135149");
		return conn;
	}
	// Close DB Connection 
	
	private void closeConn() throws SQLException {
		conn.close();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Set filtering, sorting and paging for JQWidgets grid
		
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
			catch (NumberFormatException nfe) {response.sendRedirect("Error.html");}
					
		}
		
		Integer pagesize = 1000;
		if (null != request.getParameter("pagesize"))
		{
			try
			{
				pagesize = Integer.parseInt(request.getParameter("pagesize"));
			}
			catch (NumberFormatException nfe) {
				
				response.sendRedirect("Error.html");
			}
					
		}
		
		Integer start = pagesize * pagenum;
		
	
		try {
		
		getConn();
		// retrieve necessary records from database
		stm = conn.createStatement();
		ResultSet totalContratti = stm.executeQuery("SELECT COUNT(*) AS Count FROM Linkem"	+ where);
		String totalRecords = "";
		while (totalContratti.next()) {
			totalRecords = totalContratti.getString("Count");
		}
		totalContratti.close();
		String sql = "SELECT Account, Nome, Cognome, Contratto, Indirizzo, CAP, Telefono, Email, Giorno, Agente, Codice_agente FROM Linkem " + where + " " + orderby + " LIMIT ?,?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, start);
		stmt.setInt(2, pagesize);
		
		ResultSet contratti = stmt.executeQuery();
		
		boolean totalRecordsAdded = false;
		
		JSONArray recordsArray = new JSONArray();
		while ( contratti.next()) {
			JSONObject currentRecord = new JSONObject();
			
			 
		
			 currentRecord.put("Account",	contratti.getObject("Account"));
			currentRecord.put("Nome", contratti.getObject("Nome"));
			currentRecord.put("Cognome", contratti.getObject("Cognome"));
			currentRecord.put("Contratto", contratti.getObject("Contratto"));
			currentRecord.put("Indirizzo", contratti.getObject("Indirizzo"));
			currentRecord.put("CAP", contratti.getObject("CAP"));
			currentRecord.put("Telefono", contratti.getObject("Telefono"));
			currentRecord.put("Email", contratti.getObject("Email"));
			currentRecord.put("Giorno", contratti.getObject("Giorno"));
			currentRecord.put("Agente", contratti.getObject("Agente"));
			currentRecord.put("Codice Agente", contratti.getObject("Codice_agente"));
			
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
	
		

	
		
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Delete, update, activation and insert method for records
		
		delete = request.getParameter("elimina");
		account_del = request.getParameter("account_del");
		aggiorna_nota = request.getParameter("aggiorna_nota");
		account_nota= request.getParameter("account_nota");
		
	if(delete != null) {
			
			try {
			
				writer=response.getWriter();
				getConn();
				sql = "{call Ilmar.DeleteLinkem (?) }";
				cstm2 = conn.prepareCall(sql);
				cstm2.setString(1, account_del);
				cstm2.execute();
				cstm2.close();
				response.sendRedirect("Linkem.html");
				writer.close();
				closeConn();
			} catch (ClassNotFoundException | SQLException e) {
				response.sendRedirect("Error.html");
				e.printStackTrace();
			}
			
		}
	

else if (aggiorna_nota != null) {
	
	try {
		
		writer=response.getWriter();
		getConn();
		sql = "{call Ilmar.aggiorna_nota (?, ?) }";
		cstm2 = conn.prepareCall(sql);
		cstm2.setString(1, account_nota);
		cstm2.setString(2, aggiorna_nota);
		cstm2.execute();
		cstm2.close();
		response.sendRedirect("grid.html");
		writer.close();
		closeConn();
	} catch (ClassNotFoundException | SQLException e) {
		response.sendRedirect("Error.html");
		e.printStackTrace();
	}
	
}
		else {
		

		
		telefono = request.getParameter("Telefono");
		indirizzo = request.getParameter("Indirizzo");
		cap = request.getParameter("Cap");	
		account = request.getParameter("account_par");
		agente = request.getParameter("Agente");
		tipologia = request.getParameter("Tipologia");
		nome= request.getParameter("nome_par");
		cognome = request.getParameter("cognome_par");
		
		codice_agente = request.getParameter("codice_agente_par");
		email = request.getParameter("Email");
	
	
		try {
			ld = LocalDate.parse(request.getParameter("Data"));
		
			getConn();
		

			
			
			sql = "{ call SelectLinkem}";
			cstm = (CallableStatement) conn.prepareCall(sql);
			
			cstm.execute();
			rs = cstm.getResultSet();

			
			
			while (rs.next()){
				
				
					if((rs.getString("Account").equals(account))) {
						sql2 = "{ call Ilmar.UpdateLinkem (?,?,?,?,?,?,?,?,?,?,?)}";
						break;
						 
			}
					else{
						sql2 = "{ call Ilmar.InsertLinkem (?,?,?,?,?,?,?,?,?,?, ?)}";
					}
				
			}
				 
				
						
						cstm2 = conn.prepareCall(sql2);
						cstm2.setString(1,account);
						cstm2.setString(2, nome);
						cstm2.setString(3, cognome);
						cstm2.setString(4, tipologia);
						cstm2.setString(5, indirizzo);
						cstm2.setString(6, cap);					
						cstm2.setString(7, telefono);
						cstm2.setString(8, agente);
						cstm2.setString(9, codice_agente);
						cstm2.setObject (10,ld);
						cstm2.setObject (11, email);
						
						cstm2.execute();
						cstm2.close();
					
						response.sendRedirect("Linkem.html");
						//writer.close();
						closeConn();
						
			}
		catch (SQLException e) {
			response.sendRedirect("Error.html");
			
			e.printStackTrace();
		}
	 catch (ClassNotFoundException e) {
		 response.sendRedirect("Error.html");
				
			}
		}
		}		
		

		
	

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
		
		
		
	}

}
