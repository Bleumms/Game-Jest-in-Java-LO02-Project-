/*
 * Visiteur calculant le score d'un joueur selon les règles du jeu.
 * Implémente le pattern Visitor pour parcourir les joueurs.
 * 
 * @author Nina et Emeline
 * @see Visitor
 * @see Reference
 */

package Jest;

public class CalculateurScore implements Visitor{


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

}
