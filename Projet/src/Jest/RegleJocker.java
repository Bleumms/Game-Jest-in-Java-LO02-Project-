package Jest;

import java.util.List;

public class RegleJocker extends Regle {

	public RegleJocker() {
		// TODO Auto-generated constructor stub
	}

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
	 * // renvoie la bonne valeur ou à ignorer, ignorer si le deuxieme int =0 public
	 * List<Integer> modifierValeurCarte(Carte c, List<Carte> toutesLesCartes) { if
	 * (c instanceof Jocker) { List<Integer> res = new ArrayList<Integer>(); boolean
	 * unCoeur = possedeUnCoeur(toutesLesCartes); if (unCoeur==false) { res.add(4);
	 * } else { res.add(0); } res.add(1); return res; } else { return
	 * super.modifierValeurCarte(c, toutesLesCartes); } }
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

	@Override
	public String toString() {
		return "RegleJocker";
	}

}