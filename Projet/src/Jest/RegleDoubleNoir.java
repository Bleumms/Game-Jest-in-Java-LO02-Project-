package Jest;

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