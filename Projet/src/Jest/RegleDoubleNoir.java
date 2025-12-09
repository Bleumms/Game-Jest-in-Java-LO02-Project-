package Jest;

import java.util.ArrayList;
import java.util.List;

public class RegleDoubleNoir extends Regle {

	@Override
	public String toString() {
		return "RegleDoubleNoir";
	}

	public RegleDoubleNoir() {
		// TODO Auto-generated constructor stub
	}

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
	 * // Un double noir donne un +2, or la paire va être remarquée 2 fois (a chaque
	 * fois que c est l'un des membres de la paire // Donc, on va mettre un bonus de
	 * 1 qui sera appliqué 2 fois public List<Integer> modifierValeurCarte(Carte c,
	 * List<Carte> toutesLesCartes) { if (c instanceof CarteClassique &&
	 * (c.getSymbole()==Symbole.PIQUE || c.getSymbole()==Symbole.CARREAU)) {
	 * List<Integer> res = new ArrayList<Integer>(); int num = c.getNumero();
	 * Symbole s = c.getSymbole(); boolean doubleN =
	 * posseSonDouble(num,s,toutesLesCartes); if (doubleN==true) { res.add(num+1);
	 * // PAS +1 MAIS METHODE BONUS POUR GERER LES NEGATIFS } else { res.add(num); }
	 * res.add(1); return res; } else { return super.modifierValeurCarte(c,
	 * toutesLesCartes); } }
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
