package multiprocessi;

public class StoreIdeale {

	public static void main(String args[]) {
		Magazzino magazz = new Magazzino();
		new Produttore(magazz);
		
		new Consumatore(magazz);
	}

}
