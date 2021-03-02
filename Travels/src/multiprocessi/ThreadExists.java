package multiprocessi;


// Questa classe introduce un nuovo elemento,
// tutt'altro che trascurabile: la gestione del tempo.
// Essa gestisce, in qualche modo, la durata dell�applicazione stessa.
public class ThreadExists {
	
	public static void main(String args[]) {
		
		// Viene chiamato il metodo statico currentThread() 
		// della classe Thread.
		// Questo restituisce l'indirizzo dell'oggetto Thread 
		// che sta eseguendo  l'istruzione, 
		// che viene assegnato al reference t.
		
		// t � il reference al nostro "cursore immaginario".
		Thread t = Thread.currentThread();
		t.setName("Thread principale");
		
		// Assegnazione di priorit�: 1 minima, 10 massima.
		// Se non specificato, il valore di default � 5
		t.setPriority(10);
		
		// "Stampare" un thread significa visualizzarne il nome,
		// la priorit�, ed il ThreadGroup di appartenenza che,
		// in questo caso, � "main".
		System.out.println("Thread in esecuzione: " + t);


		try {
			for (int n = 5; n > 0; n--) {
				System.out.println("" + n);
				
				// Poniamo il thread in pausa per 1 secondo
				Thread.sleep(1000);
			}

		// La InterruptedException verrebbe lanciata
		// qual'ora t venisse stoppato da un'altro thread.
		} catch (InterruptedException e) {
			System.out.println("Thread interrotto");
		}
	}
}
