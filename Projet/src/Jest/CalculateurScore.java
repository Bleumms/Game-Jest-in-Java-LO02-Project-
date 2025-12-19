/*
 * Visiteur calculant le score d'un joueur selon les règles du jeu.
 * Implémente le pattern Visitor pour parcourir les joueurs.
 * 
 * @author Nina et Emeline
 * @see Visitor
 * @see Reference
 */

package Jest;

import java.util.ArrayList;
import java.util.List;

public class CalculateurScore implements Visitor{

	/* 
	 * Référence des règles utilisées pour le calcul du score
	*/
	private Reference ref;


	/*
	 * Constructeur par défaut.
	 */
	public CalculateurScore() {
	}
	
	/* Calcule et met à jour le score d'un joueur en fonction de ses cartes.
	 * @param player Le joueur dont le score doit être calculé
	*/
	@Override
	public void visit(Joueur player) {
		// Calcul du score en fonction des cartes dans la collection du joueur
		int totalScore = 0;
		for (Carte c : player.getCollection()) {
			totalScore += 0; 
		}
		player.setScore(totalScore);

	}

	/*
	 * Modifie la référence des règles utilisées pour le calcul du score.
	 * @param r La nouvelle référence 
	*/
	public void setReference(Reference r){
		this.ref = r;
	}

	/* 
	 * Calcule le score total d'une collection de cartes selon les règles du jeu.
	 * Importe la classe ValeurParCarte pour stocker les valeurs modifiées de chaque carte en fonction des règles.
	 * Boucle sur chaque règle pour modifier les valeurs des cartes.
	 * Effectue la somme des valeurs modifiées pour obtenir le score total.
	 * @param collection La liste des cartes dans la collection du joueur
	 * @return Le score total calculé
	*/
	public int calculScore(List<Carte> collection) {
		int somme=0;
		List<ValeurParCarte> valeurs = new ArrayList<ValeurParCarte>();
		for (int c=0; c<collection.size(); c++) {
			// chaque carte aura une valeur attitrée
			ValeurParCarte val = new ValeurParCarte(collection.get(c));
			valeurs.add(val);
		}
		List<Regle> regles  = ref.getRegles();
		for (int r=0; r<regles.size();r++) {
			regles.get(r).modifierValeurCarte(collection, valeurs);
		}
		for (int i=0; i<valeurs.size(); i++) {
			somme = somme + valeurs.get(i).getValeur();
		}
		return somme;
	}

}
