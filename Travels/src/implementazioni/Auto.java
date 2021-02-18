package implementazioni;

import interfacce.*;
import astrazioni.*;
import enumerazioni.*;

public class Auto extends Veicolo implements Gommabile {

	// Obbligo di override dei metodi della classe astratta
	@Override
	public void accelera() {
		
	}

	// Obbligo di override dei metodi della classe astratta
	public void decelera() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EseguiEquilibraturaPneumatico() {
		// TODO Auto-generated method stub
		
	}
	
	public void InnestaMarcia(Marcia m) {
		switch (m) {
			case PRIMA:
				//Istruzioni per cambiare il rapporto in prima
				break;
			case SECONDA:
				//Istruzioni per cambiare il rapporto in seconda
				break;
		default:
			break;
		}
		
	}

}
