/**
 * Classe abstraite représentant une règle du jeu Jest.
 * Une règle modifie la valeur des cartes lors du calcul du score.
 * 
 * @author Nina et Emeline
 * @see Reference
 * @see ValeurParCarte
*/

package Jest.Model;

import java.io.Serializable;
import java.util.List;

public class Regle implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Constructeur de Regle
	*/
	public Regle() {
	}

	/*
	 * Redéfinit la méthode toString pour afficher les règles
	 * @return Une chaîne de caractères représentant la règle
	*/
	@Override
	public String toString() {
		return "Regle";
	}

	
	/*
	 * Modifie la valeur des cartes selon la règle
	 * @param cartes La liste des cartes à modifier
	 * @param valeurs La liste des valeurs par carte à modifier
	*/
	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		
	}
}
