package astrazioni;

public abstract class Veicolo {
	
	public abstract void accelera() ;
	public abstract void decelera() ;
	
	protected String Rifornimento() {
		return "ok da veicolo";
	}

}
