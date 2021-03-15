package gestionale_ale;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


@WebServlet("/Analisi")
public class Analisi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter writer;
	private String quarter;
	private Connection conn;
	private java.sql.CallableStatement cstm;
	private java.sql.CallableStatement cstm2;
	private String sql;
	private String sql2;
	ResultSet map_agenti;
	private byte i = 0;
	private byte k = 0;
	private byte j = 0;
	private String agente;
	//private byte [] score_agente;
	private ResultSet agenti;
	private byte x ;
	private byte peso;
	private byte start;

	
	
	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ilmar?user=root&password=Alevalen91");
		return conn;
	}
	
	private void closeConn() throws SQLException {
		conn.close();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Byte> map = new HashMap<String, Byte>();
		JSONArray recordsArray = new JSONArray();
		JSONObject currentRecord = new JSONObject();
		quarter = request.getParameter("quarter");
		if (quarter == null) {
			quarter = "1";
		}
		
			
	
		try {
		
		getConn();
		sql2= "{call Ilmar.select_all}";
		
		cstm2 = conn.prepareCall(sql2);
		//cstm2.setString(1, quarter);
		cstm2.execute();
		map_agenti = cstm2.getResultSet();
		while(map_agenti.next()) {
			map.put(map_agenti.getString("agente"), (byte) 0);
		
		}
		
	
		
		sql = "{call Ilmar.agente_analisi (?)}";
		cstm = conn.prepareCall(sql);
		cstm.setString(1, quarter);
		cstm.execute();
		agenti = cstm.getResultSet();
		while(agenti.next()) {
		
		
		if (agenti.getString("Tipologia").equals("Contratto1")) {
			
			map.put(agenti.getString("Agente"), (byte) (map.get(agenti.getString("Agente")) + 1));
		
			
		}
		else if (agenti.getString("Tipologia").equals("Contratto2")) {
			
			map.put(agenti.getString("Agente"), (byte) (map.get(agenti.getString("Agente")) +2));
			
			
		}
		else {
			peso = 3;
			map.put(agenti.getString("Agente"), (byte) (map.get(agenti.getString("Agente")) +3));			
			
		}
			
		
		
		
			
		
		}
	
		for ( String key : map.keySet() ) {
			JSONObject json = new JSONObject();
		  json.put("Agente", key);
		  json.put("Punteggio", map.get(key));
		  recordsArray.put(json);
		
		}
		
		
		closeConn();
		
	
		
		writer = response.getWriter();
		
		writer.print(recordsArray);
		
	   
		writer.flush();
		writer.close();
		
		}
		
		catch (SQLException e) {
			
		}
		catch (ClassNotFoundException e) {
			
		}
		
	
		
		
			
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
//		HashMap<String, Byte> map = new HashMap<String, Byte>();
//		JSONArray recordsArray = new JSONArray();
//		JSONObject currentRecord = new JSONObject();
//		quarter = request.getParameter("quarter");
//		if (quarter == null) {
//			quarter = "1";
//		}
//		
//			
//	
//		try {
//		
//		getConn();
//		sql2= "{call Ilmar.select_all}";
//		
//		cstm2 = conn.prepareCall(sql2);
//		//cstm2.setString(1, quarter);
//		cstm2.execute();
//		map_agenti = cstm2.getResultSet();
//		while(map_agenti.next()) {
//			map.put(map_agenti.getString("agente"), (byte) 0);
//		
//		}
//		
//	
//		
//		sql = "{call Ilmar.agente_analisi (?)}";
//		cstm = conn.prepareCall(sql);
//		cstm.setString(1, quarter);
//		cstm.execute();
//		agenti = cstm.getResultSet();
//		while(agenti.next()) {
//		
//		
//		if (agenti.getString("Tipologia").equals("Contratto1")) {
//			
//			map.put(agenti.getString("Agente"), (byte) (map.get(agenti.getString("Agente")) + 1));
//		
//			
//		}
//		else if (agenti.getString("Tipologia").equals("Contratto2")) {
//			
//			map.put(agenti.getString("Agente"), (byte) (map.get(agenti.getString("Agente")) +2));
//			
//			
//		}
//		else {
//			peso = 3;
//			map.put(agenti.getString("Agente"), (byte) (map.get(agenti.getString("Agente")) +3));			
//			
//		}
//			
//		
//		
//		
//			
//		
//		}
//	
//		for ( String key : map.keySet() ) {
//			JSONObject json = new JSONObject();
//		  json.put("Agente", key);
//		  json.put("Punteggio", map.get(key));
//		  recordsArray.put(json);
//		
//		}
//		
//		
//		closeConn();
//		
//	
//		
//		writer = response.getWriter();
//		response.setContentType("application/json");
//		writer.print(recordsArray);
//		response.sendRedirect("Grafici.html");
//		
//	   
//		writer.flush();
//		writer.close();
//		
//		}
//		
//		catch (SQLException e) {
//			
//		}
//		catch (ClassNotFoundException e) {
//			
//		}
//		
//	
//		
//		
//			
//		
//		
//		
//		
//		
//		
//	}

}
