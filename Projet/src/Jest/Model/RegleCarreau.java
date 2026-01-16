/*
 * Règle concernant les cartes de Carreau.
 * Tous les Carreaux valent leur valeur négative.
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.util.List;

public class RegleCarreau extends Regle {

	/*
	 * Constructeur de RegleCarreau
	*/
	public RegleCarreau() {
	}

	/*
	 * Affiche le nom de la règle
	 * @return Une chaîne de caractères
	*/
	@Override
	public String toString() {
		return "RegleCarreau";
	}


	/*
	 * Modifie la valeur des cartes selon la règle des Carreaux
	 * Tous les Carreaux valent leur valeur négative
	 * @param cartes La liste des cartes à modifier
	 * @param valeurs La liste des valeurs par carte à modifier
	*/
	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) c;
				if (cc.getSymbole() == Symbole.CARREAU) {
					valeurs.get(i).multiplieVal(-1);
				}
			}
		}
	}

}
