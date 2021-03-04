package multiprocessi;

public class ThreadTemporizzato {

	/*synchronized*/
	public void temporizza(String msg) {
		System.out.println("Accesa " + msg);
		
		try {
			Thread.sleep(1000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("Spenta " + msg);
	}
}
