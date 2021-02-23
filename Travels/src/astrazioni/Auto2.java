package astrazioni;


public class Auto2 extends Veicolo implements Gommabile2 {

	// Obbligo di override dei metodi della classe astratta
	@Override
	public void accelera() {
		
	}

	// Obbligo di override dei metodi della classe astratta
	public void decelera() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String Rifornimento() {
		return "ok da auto2";
	}
	
	@Override
	public void EseguiEquilibraturaPneumatico() {
		// TODO Auto-generated method stub
		
	}
	

}
