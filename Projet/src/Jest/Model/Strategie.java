/*
 * Interface définissant une stratégie de jeu pour un joueur virtuel.
 * 
 * @author Nina et Emeline
 * @see JoueurVirtuel
*/

package Jest.Model;

import java.util.List;

public interface Strategie {
	
	/*
	 * Exécute la stratégie de faire une offre
	 * @param cartesDistribuées La liste des cartes distribuées au joueur
	 * @param cartesEnCollection La liste des cartes déjà en collection du joueur
	 * @return L'index de la carte qui sera visible
	*/
	int executeFaireUneOffre (List <Carte> cartesDistribuées, List <Carte> cartesEnCollection);

	/*
	 * Exécute la stratégie de choisir une carte parmi les cartes proposées par les autres joueurs
	 * @param joueurs La liste des joueurs en jeu
	 * @param moiMeme Le joueur virtuel qui exécute la stratégie
	 * @return Une liste contenant l'index du joueur choisi et l'index de la carte choisie
	*/
	List<Integer> executeChoisirUneCarte (List <Joueur> joueurs, Joueur moiMeme);
	
	/*
	 * Redéfinit la méthode toString pour afficher le nom de la stratégie
	 * @return Une chaîne de caractères représentant la stratégie
	*/
	public String toString();
}
