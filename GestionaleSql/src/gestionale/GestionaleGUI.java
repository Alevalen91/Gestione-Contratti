package gestionale;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class GestionaleGUI {
	
	private Anagrafica anagrafica;

	private Label txtCognNome;
	private Label txtRuolo;  
	private MenuBar menubar;
	private Frame finestra;

	private static Button bottone;
	
	private void InizializzaComponenti() {
		finestra= new Frame("Gestionale");  
        
        menubar=new MenuBar();  

        // Esempio di menù
        Menu menu1=new Menu("Menu di esempio");  
        MenuItem elemento1=new MenuItem("Voce n. 1");  

        Menu sottomenu1=new Menu("Voce n. 2");  
        MenuItem sottomenu1_1=new MenuItem("Voce n. 2-1");  
        MenuItem sottomenu1_2=new MenuItem("Voce n. 2-3");  
        sottomenu1.add(sottomenu1_1);  
        sottomenu1.add(sottomenu1_2);  

        MenuItem elemento3=new MenuItem("Voce n. 3");  

        menu1.add(elemento1);  
        menu1.add(sottomenu1);  
        menu1.add(elemento3);  

        Menu menu2=new Menu("Altra voce");  

        menubar.add(menu1);  
        menubar.add(menu2);  
        
		Label lblCognNome=new Label("Cognome e nome:");  
		lblCognNome.setBounds(450,75, 150,20); 
		lblCognNome.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		lblCognNome.setAlignment(Label.RIGHT);
		finestra.add(lblCognNome);

		Label lblRuolo=new Label("Ruolo:");  
		lblRuolo.setBounds(450,95, 150,20); 
		lblRuolo.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		lblRuolo.setAlignment(Label.RIGHT);
		finestra.add(lblRuolo);

		txtCognNome=new Label();  
		txtCognNome.setBounds(610,75, 150,20); 
		txtCognNome.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		txtCognNome.setAlignment(Label.LEFT);
		finestra.add(txtCognNome);

		txtRuolo=new Label();  
		txtRuolo.setBounds(610,95, 150,20); 
		txtRuolo.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		txtRuolo.setAlignment(Label.LEFT);
		finestra.add(txtRuolo);

		bottone = new Button("Nuovo Amministratore");  
		bottone.setBounds(500,130,200,30);
		finestra.add(bottone);
		bottone.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e){ 
				@SuppressWarnings("unused")
				CreaAdminGUI admin_GUI = new CreaAdminGUI();
			}  
		});
		
		finestra.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				}
				);
		
		finestra.setSize(800,600);  
		finestra.setLocationRelativeTo(null);
		finestra.setLayout(null);
		finestra.setBackground(Color.decode("0xf8ee97"));
		finestra.setVisible(true);   
	}

	private void CreaMenuInBaseAlRuolo() {
        Menu menuruolo = new Menu();
        byte k;
        for (k=0; k < this.anagrafica.getMenu().length; ++k) {
        	if ( k==0 ) {
        		menuruolo.setLabel(this.anagrafica.getMenu()[0]);
        	} else {
                MenuItem vocemenu = new MenuItem(this.anagrafica.getMenu()[k]);
                menuruolo.add(vocemenu);
        		
        	}
        }
        menubar.add(menuruolo);
        finestra.setMenuBar(menubar);  
	}
	
	public GestionaleGUI(byte idutente) throws  SQLException {
		try {
			InizializzaComponenti();

			Anagrafica anagrafica = new Anagrafica(idutente);
			this.anagrafica= anagrafica;
			
			txtCognNome.setText(this.anagrafica.getCognome() + " " + this.anagrafica.getNome());  
			txtRuolo.setText(this.anagrafica.getRuolo());  
			
			CreaMenuInBaseAlRuolo();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
}

