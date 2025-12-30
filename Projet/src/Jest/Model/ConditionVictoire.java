/*
 * Interface définissant une condition de victoire pour une carte trophée.
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.util.List;

public interface ConditionVictoire {

	/*
	 * Vérifie si la condition de victoire est remplie pour un joueur donné.
	 * @param joueurs La liste des joueurs en jeu
	 * @return L'indice du joueur gagnant si la condition est remplie, sinon -1
	*/
	public int VerificationVictoire(List<Joueur> joueurs);
}

