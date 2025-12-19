package Jest;

public class ValeurParCarte {

	private Carte c;
	private int valeur;
	private int bonus=0;
	
	public ValeurParCarte(Carte c) {
		this.c = c;
		this.valeur = valeurOriginale();
	}
	
	public int valeurOriginale() {
		if (this.c instanceof CarteClassique) {
			CarteClassique cc =(CarteClassique) c;
			return cc.getNumero();
		} else {
			return 0;
		}
	}

	public void setValeur(int i) {
		this.valeur = i;
		
	}

	public void multiplieVal(int i) {
		this.valeur = this.valeur*i;
		
	}

	public void setBonus(int i) {
		// TODO Auto-generated method stub
		this.bonus=i;
	}
	
	public int getValeur() {
		if (this.valeur<0) {
			return this.valeur-this.bonus;
		} else {
			return this.valeur+this.bonus;
		}
	}

	public String toString(){
		return c+" = "+getValeur();
	}

}