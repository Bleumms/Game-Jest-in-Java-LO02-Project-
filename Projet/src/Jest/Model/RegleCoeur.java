/*
 * Règle concernant les cartes de Cœur.
 * Sans Jocker : les Cœurs valent 0 point
 * Avec Jocker mais pas tous les Cœurs : valeur négative
 * Avec Jocker et tous les Cœurs (1,2,3,4) : valeur positive
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.util.List;

public class RegleCoeur extends Regle {

	/*
	 * Affiche le nom de la règle
	 * @return Une chaîne de caractères
	*/
	@Override
	public String toString() {
		return "RegleCoeur";
	}

	/*
	 * Constructeur de RegleCoeur
	*/
	public RegleCoeur() {
	}

	/*
	 * Modifie la valeur des cartes selon la règle des Cœurs
	 * Zero sans Jocker, négatif avec Jocker, positif si tous les Cœurs sont présents
	 * @param cartes La liste des cartes à modifier
	 * @param valeurs La liste des valeurs par carte à modifier
	*/
	@Override
	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) c;
				if (cc.getSymbole() == Symbole.COEUR) {
					boolean possedeJocker = possedeUnJocker(cartes);
					boolean tousLesCoeurs = possedeTousLesCoeurs(cartes);
					if (possedeJocker == false) {
						valeurs.get(i).setValeur(0);
					} else {
						if (tousLesCoeurs == false) {
							valeurs.get(i).multiplieVal(-1);
						} else {
							int val = valeurs.get(i).valeurOriginale();
							valeurs.get(i).setValeur(val);
						}
					}
				}
			}
		}
	}

	/*
	 * Vérifie si la liste de cartes contient un Jocker
	 * @param cartes La liste des cartes à vérifier
	 * @return true si un Jocker est présent, false sinon
	*/
	private boolean possedeUnJocker(List<Carte> cartes) {
		for (int i = 0; i < cartes.size(); i++) {
			if (cartes.get(i) instanceof Jocker) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Vérifie si la liste de cartes contient tous les Cœurs (1,2,3,4)
	 * @param cartes La liste des cartes à vérifier
	 * @return true si tous les Cœurs sont présents, false sinon
	*/
	private boolean possedeTousLesCoeurs(List<Carte> cartes) {
		int compteur = 0;
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) c;
				if (cc.getSymbole() == Symbole.COEUR) {
					compteur++;
				}
			}
		}
		if (compteur == 4) {
			return true;
		} else {
			return false;
		}
	}
}
