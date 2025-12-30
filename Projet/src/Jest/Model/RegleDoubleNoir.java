/*
 * Règle du double noir.
 * Si un joueur possède un Pique ET un Trèfle du même numéro,
 * chaque carte reçoit un bonus de +1 point.
 * 
 * @author Nina et Emeline
*/

package Jest;

import java.util.List;

public class RegleDoubleNoir extends Regle {
	
	
	/*
	 * Constructeur de RegleDoubleNoir
	*/
	public RegleDoubleNoir() {
	}
	
	/*
	 * Affiche le nom de la règle
	 * @return Une chaîne de caractères
	*/
	@Override
	public String toString() {
		return "RegleDoubleNoir";
	}

	/*
	 * Modifie la valeur des cartes selon la règle du double noir
	 * Si un joueur possède un Pique ET un Trèfle du même numéro, chaque carte reçoit +1 point.
	 * @param cartes La liste des cartes à modifier
	 * @param valeurs La liste des valeurs par carte à modifier
	*/
	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		for (int i = 0; i < cartes.size(); i++) {
			Carte c = cartes.get(i);
			if (c instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) c;
				if (cc.getSymbole() == Symbole.PIQUE || cc.getSymbole() == Symbole.TREFLE) {
					boolean doubleNoir = possedeUnDouble(cc, cartes);
					if (doubleNoir == true) {
						valeurs.get(i).setBonus(1);
					}
				}
			}
		}
	}

	/*
	 * Vérifie si une carte a son double (même numéro, symbole noir opposé) dans la liste
	 * @param c La carte à vérifier
	 * @param cartes La liste des cartes à vérifier
	 * @return true si le double est présent, false sinon
	*/
	private boolean possedeUnDouble(CarteClassique c, List<Carte> cartes) {
		Symbole sym;
		if (c.getSymbole() == Symbole.PIQUE) {
			sym = Symbole.TREFLE;
		} else {
			sym = Symbole.PIQUE;
		}
		for (int i = 0; i < cartes.size(); i++) {
			Carte ca = cartes.get(i);
			if (ca instanceof CarteClassique) {
				CarteClassique cc = (CarteClassique) ca;
				if (cc.getNumero() == c.getNumero() && cc.getSymbole() == sym) {
					return true;
				}
			}
		}
		return false;
	}
}
