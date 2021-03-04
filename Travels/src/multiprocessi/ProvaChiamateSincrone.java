package multiprocessi;

public class ProvaChiamateSincrone {

	public static void main(String args[]) {
	
		ThreadTemporizzato attendi = new ThreadTemporizzato();

		new ThreadChiamante(attendi, " lampadina cucina ");
		new ThreadChiamante(attendi, " lampadina bagno ");

		System.out.println("ProvaChiamateSincrone: morto. ");

	}
	
}
