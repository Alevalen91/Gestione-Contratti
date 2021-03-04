package multiprocessi;

public class Produttore implements Runnable {

	private Magazzino magazz;
	
	public Produttore(Magazzino mg) {
		this.magazz = mg;
		new Thread(this, "Produttore").start();
	}
	
	public void run() {
		for (int i = 1; i <= 10; i++) {
			magazz.put(i);
		}
	}
}