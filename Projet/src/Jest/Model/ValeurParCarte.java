/*
 * Associe une valeur modifiable à une carte pour le calcul du score.
 * Permet d'appliquer les règles et bonus sur la valeur de base.
 * 
 * @author Nina et Emeline
 * @see Carte
 * @see Regle
*/

package Jest.Model;

public class ValeurParCarte {

	/*
	 * La carte associée 
	*/
	private Carte c;

	/*
	 * La valeur actuelle (après exécution des règles) 
	*/
	private int valeur;

	/*
	 * Le bonus applicable à la carte
	*/
	private int bonus=0;
	
	/*
	 * Constructeur de ValeurParCarte
	 * @param c La carte à associer
	*/
	public ValeurParCarte(Carte c) {
		this.c = c;
		this.valeur = valeurOriginale();
	}
	
	/*
	 * Retourne la valeur originale de la carte (avant application des règles)
	 * @return La valeur originale
	*/
	public int valeurOriginale() {
		if (this.c instanceof CarteClassique) {
			CarteClassique cc =(CarteClassique) c;
			return cc.getNumero();
		} else {
			return 0;
		}
	}

	/*
	 * Modifie la valeur actuelle de la carte
	 * @param i La nouvelle valeur
	*/
	public void setValeur(int i) {
		this.valeur = i;
		
	}

	/*
	 * Multiplie la valeur actuelle de la carte par un facteur
	 * @param i Le facteur de multiplication
	*/
	public void multiplieVal(int i) {
		this.valeur = this.valeur*i;
		
	}

	/*
	 * Modifie le bonus applicable à la carte
	 * @param i Le nouveau bonus
	*/
	public void setBonus(int i) {
		this.bonus=i;
	}
	
	/*
	 * Retourne la valeur finale de la carte (valeur + bonus)
	 * @return La valeur finale
	*/
	public int getValeur() {
		if (this.valeur<0) {
			return this.valeur-this.bonus;
		} else {
			return this.valeur+this.bonus;
		}
	}

	/*
	 * Affiche la carte et sa valeur actuelle
	 * @return Une chaîne de caractères
	*/	
	@Override
	public String toString(){
		return c+" = "+getValeur();
	}

}
