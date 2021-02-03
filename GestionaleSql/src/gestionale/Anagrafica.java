package gestionale;

import java.sql.*;

public class Anagrafica {

	private byte idutente;
	private String cognome;
	private String nome;
	private String ruolo;
	private String[] menu;

	public byte getIdUtente() {
		return idutente;
	}
	public String getCognome() {
		return cognome;
	}
	public String getNome() {
		return nome;
	}
	public String getRuolo() {
		return ruolo;
	}
	public String[] getMenu() {
		return menu;
	}
	
	private PreparedStatement pstmt;
	private ResultSet rs;

	// Anagrafica si può "costruire" solo se gli passo un idutente valido
	public Anagrafica(byte idutente) throws SQLException {
		try {
			ConnessioneDB connDB = new ConnessioneDB();
			Connection conn = connDB.openConn();
			pstmt = conn.prepareStatement("SELECT a.cognome, a.nome, r.descrizione, r.id " + 
										  "FROM anagrafica as a " + 
										  "INNER JOIN utente as u ON a.idutente = u.id " + 
										  "INNER JOIN ruolo as r ON u.idruolo = r.id " + 
										  "WHERE a.idutente = ? ");
			pstmt.setByte(1, idutente);
			rs = pstmt.executeQuery();

			this.idutente=idutente;
			if (rs.next()) {
				this.cognome=rs.getString(1);
				this.nome=rs.getString(2);
				this.ruolo=rs.getString(3);
				DammiMenu(rs.getByte(4));
			}
			conn.close();

			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	private void DammiMenu(byte idruolo) throws SQLException {
		try {
			ConnessioneDB connDB = new ConnessioneDB();
			Connection conn = connDB.openConn();
			
			// Verifico il numero di voci di menù
			// per poter settare il numero di elementi
			// dell'array menu.
			pstmt = conn.prepareStatement("SELECT count(m.id) FROM menu as m " + 
										  "WHERE m.idruolo = ? ");
			pstmt.setByte(1, idruolo);
			rs = pstmt.executeQuery();
			byte i = 0;
			if (rs.next()) {
				i = rs.getByte(1);
			}
			
			// Posso selezionare le voci di menù
			// e caricarle nell'array menu
			pstmt = conn.prepareStatement("SELECT m.descrizione FROM menu as m " + 
										  "WHERE m.idruolo = ? ");
			pstmt.setByte(1, idruolo);
			rs = pstmt.executeQuery();
			
			this.menu = new String[i];
			byte k = 0;
			while(rs.next()) {
				this.menu[k]=rs.getString(1);
				++k;
			}  
			
			conn.close();
	
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	}
	
}
