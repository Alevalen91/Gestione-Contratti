package gestionale;

import java.sql.*;

public class Login {

	private byte idutente;
	public byte getIdutente() {
		return idutente;
	}
	public void setIdutente(byte idutente) {
		this.idutente = idutente;
	}
	
	private PreparedStatement pstmt;
	private ResultSet rs;

	public boolean VerificaLogin(String username, String password) throws LoginException, SQLException  {
		ConnessioneDB connDB = new ConnessioneDB();
		Connection conn = connDB.openConn();
		pstmt = conn.prepareStatement("SELECT id FROM utente " + 
									  "WHERE utente.username = ? " + 
									  "AND utente.userpassword = ? ");
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			setIdutente(rs.getByte(1));
		} else {
			throw new LoginException();
		}
		conn.close();
		return true;
	}

}
