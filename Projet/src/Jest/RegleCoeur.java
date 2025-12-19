/*
 * Règle concernant les cartes de Cœur.
 * Sans Jocker : les Cœurs valent 0 point</li>
 * Avec Jocker mais pas tous les Cœurs : valeur négative</li>
 * Avec Jocker et tous les Cœurs (1,2,3,4) : valeur positive</li>
 * 
 * @author Nina et Emeline
*/

package Jest;

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

	/* Exemple de méthode à redéfinir dans les sous-classes
	 * Modifie la valeur d'une carte spécifique selon la règle des Cœurs
	 * @param c La carte à modifier
	 * @param toutesLesCartes La liste de toutes les cartes du jeu
	 * @return Une liste contenant la nouvelle valeur et le nouveau multiplicateur
	*//*
	 * @Override
	 * public List<Integer> modifierValeurCarte(Carte c, List<Carte>
	 * toutesLesCartes) { if (c instanceof CarteClassique && c.getSymbole() ==
	 * Symbole.COEUR) { List<Integer> res = new ArrayList<Integer>(); int num =
	 * c.getNumero(); boolean possedeJocker = possedeUnJocker(toutesLesCartes);
	 * boolean tousLesCoeurs = possedeTousLesCoeurs(toutesLesCartes); if
	 * (possedeJocker == false) { res.add(0); } else { if (tousLesCoeurs==false) {
	 * res.add(-num); } else { res.add(num); } } res.add(1); return res; } else {
	 * return super.modifierValeurCarte(c, toutesLesCartes); } }
	*/

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
