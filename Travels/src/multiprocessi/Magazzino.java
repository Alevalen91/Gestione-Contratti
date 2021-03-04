package multiprocessi;

public class Magazzino {
	
	private int numProdotti;
	private int idProdotto;
	
	public synchronized void put(int idProd) {
		this.idProdotto = idProd;
		numProdotti++;
		stampaSituazione("Prodotto " + idProd);
	}

	public synchronized int get() {
		numProdotti--;
		stampaSituazione("Consumato " + idProdotto);
		return idProdotto;
	}
	
	private synchronized void stampaSituazione(String	msg) {
		System.out.println(msg +"\n" + numProdotti + " prodotti in magazzino");
	}

}
