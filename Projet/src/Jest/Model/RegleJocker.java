/*
 * Règle concernant le Jocker.
 * Sans Cœur : le Jocker vaut 4 points
 * Avec Cœur : le Jocker vaut 0 point
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.util.List;

public class RegleJocker extends Regle {

	/*
	 * Constructeur de RegleJocker
	*/
	public RegleJocker() {
	}

	/*
	 * Modifie la valeur des cartes selon la règle du Jocker
	 * Sans Cœur : le Jocker vaut 4 points, avec Cœur : le Jocker vaut 0 point
	 * @param cartes La liste des cartes à modifier
	 * @param valeurs La liste des valeurs par carte à modifier
	*/
	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof Jocker) {
				boolean unCoeur = possedeUnCoeur(cartes);
				if (unCoeur == false) {
					valeurs.get(i).setValeur(4);
				} else {
					valeurs.get(i).setValeur(0);
				}
			}
		}
	}


	/*
	 * Vérifie si la liste de cartes contient au moins un Cœur
	 * @param cartes La liste des cartes à vérifier
	 * @return true s'il y a au moins un Cœur, false sinon
	*/
	private boolean possedeUnCoeur(List<Carte> cartes) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) c;
				if (cc.getSymbole() == Symbole.COEUR) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Affiche le nom de la règle
	 * @return Une chaîne de caractères
	*/
	@Override
	public String toString() {
		return "RegleJocker";
	}

}
