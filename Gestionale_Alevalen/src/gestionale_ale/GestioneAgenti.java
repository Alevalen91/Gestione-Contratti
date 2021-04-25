package gestionale_ale;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.jdbc.CallableStatement;

@WebServlet("/GestioneAgenti")
public class GestioneAgenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PrintWriter writer;
	private java.sql.CallableStatement cstm;
	public ResultSet rs;
	private String agente;
	private String codice_agente;
	private String delete_agente;
	
	//Open DB connection 
       
	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://ilmar.crqnoawq1chg.eu-south-1.rds.amazonaws.com:3306/Ilmar","IlmarUser","Ilmar0282135149");
		return conn;
	}
	
	// Close Db connection
	
	private void closeConn() throws SQLException {
		conn.close();
	}
  
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get sellers list
		
		JSONArray recordsArray = new JSONArray();
		try {
			getConn();
			
			cstm = conn.prepareCall("{call select_agenti}");
			cstm.execute();
			rs = cstm.getResultSet();
			
			while(rs.next()) {
				
				JSONObject currentRecord = new JSONObject();
				currentRecord.put("Agente", rs.getString("Agente"));
				currentRecord.put("Codice Agente", rs.getString("Codice_Agente"));
				recordsArray.put(currentRecord);
				writer = response.getWriter();
			}
			
			writer.print(recordsArray);
			writer.flush();
			writer.close();
			cstm.close();
	
	
  }
catch (SQLException e) {
			
		} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
			response.sendRedirect("Error.html");
	e.printStackTrace();
}
		
	
		
	try {
		closeConn();
	}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		response.sendRedirect("Error.html");
		e.printStackTrace();
	}
}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Create or delete seller methods
		
		agente = request.getParameter("agente_creato");
		codice_agente = request.getParameter("codice_creato");
		delete_agente = request.getParameter("agente_del");
		
		if(delete_agente != null) {
			
			try {
				
				writer=response.getWriter();
				getConn();
			
				cstm =  conn.prepareCall("{call Ilmar.delete_agente(?)}");
				cstm.setString(1, delete_agente);
				cstm.execute();
				
				response.sendRedirect("grid.html");
				writer.close();
				closeConn();
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		else {
			
			try {

				
				getConn();
			
				cstm = (CallableStatement) conn.prepareCall("{call Ilmar.insert_new_agente(?,?)}");
				cstm.setString(1, agente);
				cstm.setString(2, codice_agente);
				response.sendRedirect("grid.html");
				cstm.execute();
				
		
		
		
	}
			catch (SQLException e) {
				
				response.sendRedirect("Error.html");
				
				e.printStackTrace();
			}
		 catch (ClassNotFoundException e) {
			 response.sendRedirect("Error.html");
					
				}
			try {
				
				closeConn();
			} catch (SQLException e) {
				response.sendRedirect("Error.html");
				
				e.printStackTrace();
			}
			
		}
	}
}
