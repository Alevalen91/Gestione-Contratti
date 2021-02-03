package sqlTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestSql {
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	public static void main(String[] args) {

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/testDB?" +
					"user=root&password=Password01@");
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Utente");

			while(rs.next()) System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { }
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) { }
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlEx) { }
				conn = null;
			}
		}
	}
}
