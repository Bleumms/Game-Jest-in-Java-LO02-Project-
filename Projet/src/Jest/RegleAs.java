package Jest;

import java.util.List;

public class RegleAs extends Regle {

	@Override
	public String toString() {
		return "RegleAs";
	}

	public RegleAs() {
		// TODO Auto-generated constructor stub
	}

	// !! Ca marche qu'il passe avant ou après chaque règle SAUF la règle double
	// dans le cas où on a double as noir.
	// Pour cette raison, cette méthode doit nécessairement passer avant
	// NORMALEMENT BUG PATCHER, A VERIFIER
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
	 * public List<Integer> modifierValeurCarte(Carte c, List<Carte>
	 * toutesLesCartes) { if (c instanceof CarteClassique && c.getNumero()==1) {
	 * List<Integer> res = new ArrayList<Integer>(); boolean unAutre =
	 * possedeUnAutre(c.getSymbole(),toutesLesCartes); if (unAutre==true) {
	 * res.add(1); } else { res.add(5); } res.add(1); return res; } else { return
	 * super.modifierValeurCarte(c, toutesLesCartes); } }
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
