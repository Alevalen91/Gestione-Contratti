package gestionale_ale;



import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import org.json.*;

import gestionale_ale.Connessione;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

@WebServlet("/Ruolo")
public class ProvaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	private String idx;
	private String id;
	private String result = "";
	private PrintWriter writer;


	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionale2?user=root&password=Alevalen91");
		return conn;
	}
	private void closeConn() throws SQLException {
		conn.close();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			id = request.getParameter("id");
			String formato = request.getParameter("formato");

			writer = response.getWriter();

			if (!(id == null || formato == null)) {

				if (Integer.parseInt(id) > 0) {
					sql = "SELECT id, descrizione FROM ruolo where id=" + id;
				} else {
					sql = "SELECT id, descrizione FROM ruolo ";
				}
				this.getConn();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				if (formato.equals("html")) {
					result = "<html><head></head><body>";
					while(rs.next()) {
						result +=  "<p>" + rs.getString("descrizione") + "</p>";
					}
					result += "</body></html>";
				}
				if (formato.equals("json")) {
					JSONArray json = new JSONArray();
					while(rs.next()) {
						JSONObject obj = new JSONObject();
						Field changeMap = obj.getClass().getDeclaredField("map");
						changeMap.setAccessible(true);
						changeMap.set(obj, new LinkedHashMap<>());
						changeMap.setAccessible(false);
						obj.put("id", rs.getObject("id"));
						obj.put("descrizione", rs.getObject("descrizione"));
						json.put(obj);
					}

					JSONObject root = new JSONObject();
					root.put("mydata", json);
					result = root.toString();

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
				}
				this.closeConn();
			}
			if (result.length()>0) {
				writer.print(result);
			}
			writer.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}		


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		if (request.getHeader("content-type").equals("application/x-www-form-urlencoded")) {
			// Qui eseguir� le operazioni qual'ora la request
			// sia di formato application/x-www-form-urlencoded
			// id = request.getParameter("id");
			// ecc...
			try {
			idx= request.getParameter("id");
			String ruolox= request.getParameter("descrizione");
			String formatox = request.getParameter("formato");

			writer = response.getWriter();
			if (Integer.parseInt(idx) == 0) {	
				sql = "INSERT INTO Ruolo (descrizione) VALUES ('" + 
					ruolox + "')";
			}
			else if (Integer.parseInt(idx) >0) {
				sql= "UPDATE Ruolo SET descrizione='" + 
						ruolox  + 
						"' WHERE id=" + idx + ";";
				this.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				result= "Operazione eseguita correttamente";
				writer.print(result);
				writer.close();
				this.closeConn();
			}
			}
			catch (Exception e) {
				
			}
		} else if (request.getHeader("content-type").equals("application/json")) {
			try {

				//Supponiamo di inviare un post di tipo json
				//per eseguire una insert se id = 0
				//oppure per eseguire una modifica se id > 0
				//Invieremo i dati al metodo doPost della Servlet
				//nel seguente formato:

				//{ "mydata": [
				//				{
				//					"id": 2,
				//					"descrizione": "Modifica Magazziniere"
				//				}
				//			]
				//}			

				BufferedReader reader = request.getReader();
				StringBuffer sb = new StringBuffer();
				String str = null;
				while ((str = reader.readLine()) != null) 
					sb.append(str);

				JSONObject jObj = new JSONObject(sb.toString());
				JSONArray jArr = jObj.getJSONArray("mydata");

				//				Se ci fossero pi� elementi sull'array json
				//				potremmo eseguire un ciclo
				//				for (int i=0; i<jArr.length(); i++) {
				//					JSONObject JObject = jArr.getJSONObject(i);
				//					writer.println(JObject.getString("descrizione"));
				//				}

				JSONObject JObject = jArr.getJSONObject(0);
				int jid = JObject.getInt("id");
				if (jid==0) {
					sql = "INSERT INTO Ruolo (descrizione) VALUES ('" + 
							JObject.getString("descrizione") + "')";
				} else if (jid>0) {
					sql = "UPDATE Ruolo SET descrizione='" + 
							JObject.getString("descrizione") + 
							"' WHERE id=" + jid + ";";
				}
				this.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				result= "Operazione eseguita correttamente";
				writer.print(result);
				writer.close();
				this.closeConn();

			} catch (Exception e) {
			}
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter writer = response.getWriter();
			int jid = Integer.parseInt(request.getParameter("id")) ;

			if (jid>0) {
				sql = "DELETE FROM Ruolo WHERE id=" + jid + ";";
			}
			this.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			result= "Cancellazione eseguita correttamente";
			writer.print(result);
			writer.close();
			this.closeConn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
