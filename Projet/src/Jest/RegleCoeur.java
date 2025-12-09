package Jest;

import java.util.ArrayList;
import java.util.List;

public class RegleCoeur extends Regle {

	@Override
	public String toString() {
		return "RegleCoeur";
	}

	public RegleCoeur() {
		// TODO Auto-generated constructor stub
	}

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
	 * public List<Integer> modifierValeurCarte(Carte c, List<Carte>
	 * toutesLesCartes) { if (c instanceof CarteClassique && c.getSymbole() ==
	 * Symbole.COEUR) { List<Integer> res = new ArrayList<Integer>(); int num =
	 * c.getNumero(); boolean possedeJocker = possedeUnJocker(toutesLesCartes);
	 * boolean tousLesCoeurs = possedeTousLesCoeurs(toutesLesCartes); if
	 * (possedeJocker == false) { res.add(0); } else { if (tousLesCoeurs==false) {
	 * res.add(-num); } else { res.add(num); } } res.add(1); return res; } else {
	 * return super.modifierValeurCarte(c, toutesLesCartes); } }
	 */

	private boolean possedeUnJocker(List<Carte> cartes) {
		for (int i = 0; i < cartes.size(); i++) {
			if (cartes.get(i) instanceof Jocker) {
				return true;
			}
		}
		return false;
	}

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
