package gestionale;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginGUI{

	private static TextField txtUsername;
	private static TextField txtPassword;
	private static Button bottone;
	private static Label risultato;

	private static void InizializzaComponenti( ) {
		Frame finestra = new Frame("Login"); 

		Label lblUsername=new Label("Username:");  
		lblUsername.setBounds(50,50, 150,20); 
		lblUsername.setBackground(Color.decode("0xcfcfcf"));
		lblUsername.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		lblUsername.setAlignment(Label.RIGHT);

		txtUsername = new TextField();
		txtUsername.setBounds(210,50, 150,20);

		Label lblPassword=new Label("Password:");  
		lblPassword.setBounds(50,80, 150,20); 
		lblPassword.setBackground(Color.decode("0xcfcfcf"));
		lblPassword.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		lblPassword.setAlignment(Label.RIGHT);

		txtPassword = new TextField();
		txtPassword.setEchoChar('*');
		txtPassword.setBounds(210,80, 150,20);

		risultato=new Label("-");  
		risultato.setBounds(50,150, 300,20); 
		risultato.setBackground(Color.decode("0xfffec1"));
		risultato.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		risultato.setAlignment(Label.CENTER);

		bottone = new Button("Login");  
		bottone.setBounds(260,110,100,30);

		finestra.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				}
				);
		finestra.setSize(400,200);  
		finestra.setLocationRelativeTo(null);
		finestra.setLayout(null);
		finestra.setBackground(Color.decode("0x8ebd8e"));
		finestra.setVisible(true);   
		finestra.add(lblUsername);  
		finestra.add(lblPassword);  
		finestra.add(txtUsername);
		finestra.add(txtPassword);
		finestra.add(bottone);
		finestra.add(risultato);  
	}

	public static void main(String[] args) {
		InizializzaComponenti();

		bottone.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e){ 
				try {
					Login login = new Login();
					if (login.VerificaLogin(txtUsername.getText(), txtPassword.getText())) {
						@SuppressWarnings("unused")
						GestionaleGUI main_GUI = new GestionaleGUI(login.getIdutente());
					}
				} catch (LoginException | SQLException  loginEx) {
					risultato.setText(loginEx.toString());
				}
			}  
		});
	}

}