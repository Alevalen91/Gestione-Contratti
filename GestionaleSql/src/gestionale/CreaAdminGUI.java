package gestionale;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CreaAdminGUI {
	
	private TextField txtUsername;
	private TextField txtCognome;
	private TextField txtNome;
	private Button bottone;
	private Label risultato;
	private PreparedStatement pstmt;

	private void InizializzaComponenti( ) {
		Frame finestra = new Frame("Crea nuovo amministratore"); 

		Label lblUsername=new Label("Username:");  
		lblUsername.setBounds(50,50, 150,20); 
		lblUsername.setBackground(Color.decode("0xcfcfcf"));
		lblUsername.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		lblUsername.setAlignment(Label.RIGHT);

		txtUsername = new TextField();
		txtUsername.setBounds(210,50, 150,20);

		Label lblCognome=new Label("Cognome:");  
		lblCognome.setBounds(50,80, 150,20); 
		lblCognome.setBackground(Color.decode("0xcfcfcf"));
		lblCognome.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		lblCognome.setAlignment(Label.RIGHT);

		txtCognome = new TextField();
		txtCognome.setBounds(210,80, 150,20);

		Label lblNome=new Label("Nome:");  
		lblNome.setBounds(50,110, 150,20); 
		lblNome.setBackground(Color.decode("0xcfcfcf"));
		lblNome.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		lblNome.setAlignment(Label.RIGHT);

		txtNome = new TextField();
		txtNome.setBounds(210,110, 150,20);

		risultato=new Label("-");  
		risultato.setBounds(50,180, 300,20); 
		risultato.setBackground(Color.decode("0xfffec1"));
		risultato.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		risultato.setAlignment(Label.CENTER);

		bottone = new Button("Login");  
		bottone.setBounds(260,140,100,30);

		finestra.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				}
				);
		finestra.setSize(400,230);  
		finestra.setLocationRelativeTo(null);
		finestra.setLayout(null);
		finestra.setBackground(Color.decode("0x9fcfed"));
		finestra.setVisible(true);   
		finestra.add(lblUsername);  
		finestra.add(lblCognome);  
		finestra.add(lblNome);  
		finestra.add(txtUsername);
		finestra.add(txtCognome);
		finestra.add(txtNome);
		finestra.add(bottone);
		finestra.add(risultato);  
	}
	
	public CreaAdminGUI() {
		InizializzaComponenti();

		bottone.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e){ 
					ConnessioneDB connDB = new ConnessioneDB();
					Connection conn = connDB.openConn();
					try {
						pstmt = conn.prepareCall("{CALL CreaAmministratore(?, ?, ?)}");
						pstmt.setString(1, txtUsername.getText());
						pstmt.setString(2, txtCognome.getText());
						pstmt.setString(3, txtNome.getText());
						pstmt.executeQuery();
						risultato.setText("Amministratore " + txtUsername.getText() + " creato." );
						conn.close();
					} catch (SQLException ex) {
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
					}

					
			}  
		});
	}

}
