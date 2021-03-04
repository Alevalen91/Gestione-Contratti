package multiprocessi;

public class ThreadChiamante implements Runnable {

	private String msg;
	private ThreadTemporizzato thTemp;
	
	public ThreadChiamante(ThreadTemporizzato tTemp, String s) {
		thTemp = tTemp;
		msg = s;
		
		// Instanzio un nuovo thread
		// ed eseguo il metodo run()
		// che chiamerà una classe
		// che gestisce un'altro thread
		new Thread(this).start();
	}
	
	public void run() {
		synchronized(thTemp){
			thTemp.temporizza(msg);
		}
	}
}
