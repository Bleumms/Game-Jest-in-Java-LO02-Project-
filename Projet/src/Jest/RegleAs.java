/*
 * Règle concernant les cartes de Carreau.
 * Tous les Carreaux valent leur valeur négative.
 * 
 * @author Nina et Emeline
*/

package Jest;

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

	

	// !! Ca marche qu'il passe avant ou après chaque règle SAUF la règle double
	// dans le cas où on a double as noir.
	// Pour cette raison, cette méthode doit nécessairement passer avant
	// NORMALEMENT BUG PATCHER, A VERIFIER
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
	 * Exemple de méthode à redéfinir dans les sous-classes
	 * Modifie la valeur d'une carte spécifique selon la règle des As
	 * @param c La carte à modifier
	 * @param toutesLesCartes La liste de toutes les cartes du jeu
	 * @return Une liste contenant la nouvelle valeur et le nouveau multiplicateur
	*//*
	 * public List<Integer> modifierValeurCarte(Carte c, List<Carte>
	 * toutesLesCartes) { if (c instanceof CarteClassique && c.getNumero()==1) {
	 * List<Integer> res = new ArrayList<Integer>(); boolean unAutre =
	 * possedeUnAutre(c.getSymbole(),toutesLesCartes); if (unAutre==true) {
	 * res.add(1); } else { res.add(5); } res.add(1); return res; } else { return
	 * super.modifierValeurCarte(c, toutesLesCartes); } }
	*/

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
