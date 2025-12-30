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
	 * Modifie la valeur d'une carte spécifique selon la règle du Jocker
	 * @param c La carte à modifier
	 * @param toutesLesCartes La liste de toutes les cartes du jeu
	 * @return Une liste contenant la nouvelle valeur et le nouveau multiplicateur
	*//*
	 * // renvoie la bonne valeur ou à ignorer, ignorer si le deuxieme int =0 public
	 * List<Integer> modifierValeurCarte(Carte c, List<Carte> toutesLesCartes) { if
	 * (c instanceof Jocker) { List<Integer> res = new ArrayList<Integer>(); boolean
	 * unCoeur = possedeUnCoeur(toutesLesCartes); if (unCoeur==false) { res.add(4);
	 * } else { res.add(0); } res.add(1); return res; } else { return
	 * super.modifierValeurCarte(c, toutesLesCartes); } }
	*/

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
