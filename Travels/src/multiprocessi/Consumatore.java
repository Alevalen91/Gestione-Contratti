package multiprocessi;

public class Consumatore implements Runnable {

	private Magazzino magazz;
	
	public Consumatore(Magazzino mg) {
		this.magazz = mg;

		// Avvio il Thread
		new Thread(this, "Consumatore").start();
	}
	
	public void run() {
		for (int i = 0; i < 10;) {
			i = magazz.get();
		}
	}
}
