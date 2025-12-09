package Jest;

import java.util.ArrayList;
import java.util.List;

public class RegleCarreau extends Regle {

	@Override
	public String toString() {
		return "RegleCarreau";
	}

	public RegleCarreau() {
	}

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

	/*
	 * // renvoie la bonne valeur ou à ignorer, ignorer si le deuxieme int =0 public
	 * List<Integer> modifierValeurCarte(Carte c, List<Carte> toutesLesCartes) {
	 * List<Integer> res = new ArrayList<Integer>(); if (c instanceof CarteClassique
	 * && c.getSymbole() == Symbole.CARREAU) { int num = c.getNumero();
	 * res.add(-num); res.add(1); return res; } else { return
	 * super.modifierValeurCarte(c, toutesLesCartes); } }
	 */
}
