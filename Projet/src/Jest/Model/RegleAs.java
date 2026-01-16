/*
 * Règle concernant les As / 1.
 * As seul de sa couleur : vaut 5× sa valeur (5 points)
 * As avec un autre As de même couleur : vaut sa valeur normale (1 point)
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.util.List;

public class RegleAs extends Regle {

	/*
	 * Constructeur de RegleAs
	*/
	public RegleAs() {
	}

	/*
	 * Affiche le nom de la règle
	 * @return Une chaîne de caractères
	*/
	@Override
	public String toString() {
		return "RegleAs";
	}

	/*
	 * Modifie la valeur des cartes selon la règle des As
	 * As vaut 5 s'il n'y a pas un autre As de même couleur
	 * @param cartes La liste des cartes à modifier
	 * @param valeurs La liste des valeurs par carte à modifier
	*/
	@Override
	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) c;
				if (cc.getNumero() == 1) {
					boolean autreMemeCouleur = possedeUnAutreMemeCouleur(cc, cartes);
					if (autreMemeCouleur == false) {
						valeurs.get(i).multiplieVal(5);
					}
				}

			}
		}
	}


	/*
	 * Vérifie si la liste de cartes contient un autre As de même couleur
	 * @param cc La carte As à vérifier
	 * @param cartes La liste des cartes à vérifier
	 * @return true si un autre As de même couleur est présent, false sinon
	*/
	private boolean possedeUnAutreMemeCouleur(CarteClassique cc, List<Carte> cartes) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique ca_cl = (CarteClassique) c;
				if (cc != ca_cl && cc.getSymbole() == ca_cl.getSymbole()) {
					return true;
				}
			}

		}
		return false;
	}

}
