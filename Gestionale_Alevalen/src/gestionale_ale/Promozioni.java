package gestionale_ale;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

/**
 * Servlet implementation class Promozioni
 */
@WebServlet("/Promozioni")
public class Promozioni extends HttpServlet {
	private String promo_del;
	private static final long serialVersionUID = 1L;
	private String sql2;
	private Connection conn;
	private PrintWriter writer;
	private java.sql.CallableStatement cstm;
	private java.sql.CallableStatement cstm2;
	public ResultSet rs;
	public ResultSet rs2;
	private String promo;
	private String descrizione;
	private String update;
	private String sql;
	private PreparedStatement stm;
	private String paragraph;

	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ilmar?user=root&password=Alevalen91");
		return conn;
	}
	
	private void closeConn() throws SQLException {
		conn.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	promo = request.getParameter("Promozione");
	JSONArray recordsArray = new JSONArray();
	if (promo != null) {
		
		try {
			getConn();
			cstm = (CallableStatement) conn.prepareCall("{call select_promozione(?)}");
			cstm.setString(1, promo);
			cstm.execute();
			rs = cstm.getResultSet();
		
			
			while (rs.next()){
				JSONObject currentRecord = new JSONObject();
				currentRecord.put("Promozione", rs.getString("Promozione"));
				currentRecord.put("Descrizione", rs.getString("Descrizione"));
				recordsArray.put(currentRecord);
				writer = response.getWriter();
				
			}
			
			writer.print(recordsArray);
			writer.flush();
			writer.close();
			
		} 
		
		 catch (SQLException e) {
			
		
		}
		catch (ClassNotFoundException e) {
			
		}
		
	}
	else {
			sql = "{call Ilmar.select_all_promo}";
		
		try {
			getConn();
		
			cstm2 = conn.prepareCall(sql);
			cstm2.execute();
			rs = cstm2.getResultSet();
			
			while(rs.next()) {
				JSONObject currentRecord = new JSONObject();
				currentRecord.put("Promozione", rs.getString("Promozione"));
				currentRecord.put("Descrizione", rs.getString("Descrizione"));
				recordsArray.put(currentRecord);
				writer = response.getWriter();
			}
			
			writer.print(recordsArray);
			writer.flush();
			writer.close();
	
	
  }
catch (SQLException e) {
			
		} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
	}
	
	
	

	try {
		closeConn();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		promo_del = request.getParameter("promo_del");
		
		promo = request.getParameter("Promozione");
		descrizione = request.getParameter("Descrizione");
		
if(promo_del != null) {
			
			try {
			
				writer=response.getWriter();
				getConn();
				sql = "{call Ilmar.delete_promo (?)}";
				cstm =  conn.prepareCall(sql);
				cstm.setString(1, promo_del);
				cstm.execute();
				
				response.sendRedirect("Gestione_promo.html");
				writer.close();
				closeConn();
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		else {
		try {
		
			getConn();
		

			
			
			sql = "{call Ilmar.select_all_promo}";
			cstm =  conn.prepareCall(sql);
			
			cstm.execute();
			rs = cstm.getResultSet();
		

			
			
			while (rs.next()){
				
				
					if((rs.getString("Promozione").equals(promo))) {
						sql2 = "{ call Ilmar.update_promo (?,?)}";
						
						break;
						 
			}
					else{
						sql2 = "{ call Ilmar.inserimento_promo (?,?)}";
						
					}
				
			}
		
						
						cstm2 =  conn.prepareCall(sql2);
						cstm2.setString(1,promo);
						cstm2.setString(2, descrizione);
						cstm2.execute();
						cstm2.close();
						
						
					  
						response.sendRedirect("Gestione_promo.html");
						
						closeConn();
					
				
				
					
			}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
	 catch (ClassNotFoundException e) {
				
			}
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		promo = request.getParameter("Promozione");
		try {
		
			
			getConn();
		
			cstm = (CallableStatement) conn.prepareCall("delete_promo(?)}");
			cstm.setString(1, promo);
			cstm.execute();
			cstm.close();
		
			closeConn();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

		
}	

