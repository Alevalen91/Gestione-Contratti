package multiprocessi;

public class Magazzino {

	private int numProdotti;
	private int idProdotto;

	// magazzino vuoto
	private boolean mag_vuoto = true; 

	public synchronized void put(int idProd) {

		// Se il magazzino non è vuoto:
		if (!mag_vuoto) 
			try {
				wait(); // fermo il Produttore 
			}
		catch (InterruptedException exc) {
			exc.printStackTrace();
		}

		this.idProdotto = idProd;
		numProdotti++;
		stampaSituazione("Prodotto " + idProd);
		
		mag_vuoto = false; //magazzino pieno
		notify(); //sveglio il Consumatore
	}

	public synchronized int get() {
		
		// Se il magazzino è vuoto:
		if (mag_vuoto) 
			try {
				wait(); // fermo il Consumatore 
			}
		catch (InterruptedException exc) {
			exc.printStackTrace();
		}
		numProdotti--;
		stampaSituazione("Consumato " + idProdotto);

		mag_vuoto = true; //magazzino vuoto
		notify(); //sveglio il Produttore
		
		return idProdotto;
	}

	private synchronized void stampaSituazione(String	msg) {
		System.out.println(msg +"\n" + numProdotti + " prodotti in magazzino");
	}

}
