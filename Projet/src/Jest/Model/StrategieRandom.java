/*
 * Stratégie de jeu aléatoire.
 * Tous les choix sont effectués au hasard.
 * 
 * @author Nina et Emeline
*/
package Jest.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StrategieRandom implements Strategie, Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructeur de la stratégie random
	 * @return Une instance de StrategieRandom
	*/
	@Override
	public String toString(){
		return "Stratégie random";
	}

	public String getNom(){
		return "Stratégie random";
	}
	
	/*
	 * Exécute la stratégie de faire une offre
	 * Le choix de la carte visible est fait aléatoirement.
	 * @param cartesDistribuées La liste des cartes distribuées au joueur
	 * @param cartesEnCollection La liste des cartes déjà en collection du joueur
	 * @return L'index de la carte qui sera visible
	*/
	@Override
	public int executeFaireUneOffre(List<Carte> cartesDistribuées, List<Carte> cartesEnCollection) {
		double aleaCarte = Math.random()*2;
		int numCarte = Double.valueOf(aleaCarte).intValue();
		return numCarte ;
	}

	/*
	 * Exécute la stratégie de choisir une carte parmi les cartes proposées par les autres joueurs
	 * Détermine aléatoirement un joueur et une carte parmi ceux proposés.
	 * @param joueurs La liste des joueurs en jeu
	 * @param moiMeme Le joueur virtuel qui exécute la stratégie
	 * @return Une liste contenant l'index du joueur choisi et l'index de la carte choisie
	*/
	@Override
	public List<Integer> executeChoisirUneCarte(List <Joueur> joueurs, Joueur moiMeme) {
		List<Integer> res = new ArrayList<Integer>();
		if (joueurs.size() == 1 && joueurs.contains(moiMeme)) {
			res.add(moiMeme.getID()); // de toute façon il y a qu'un joueur dans la liste donc c'est dans celui la qu'on prend la carte
			res.add(this.executeChoisiUneDeSesCartes());
		} else {
			double aleaJoueur = Math.random();
			int idJoueur =0;
			if (joueurs.contains(moiMeme)){
				int numJoueur =0;
				aleaJoueur = aleaJoueur*(joueurs.size()-1);
				numJoueur = Double.valueOf(aleaJoueur).intValue();
				int indexMoiMeme = joueurs.indexOf(moiMeme);
				if (numJoueur>=indexMoiMeme){
					numJoueur++;
				}
				idJoueur=joueurs.get(numJoueur).getID();
			} else {
				int numJoueur =0;
				aleaJoueur = aleaJoueur*joueurs.size();
				numJoueur = Double.valueOf(aleaJoueur).intValue();
				idJoueur=joueurs.get(numJoueur).getID();
			}
			res.add(idJoueur);
			double aleaCarte = Math.random()*2;
			int numCarte = Double.valueOf(aleaCarte).intValue();
			res.add(numCarte);
		}
		return res;
	}

	/*
	 * Exécute la stratégie de choisir une carte parmi ses propres cartes
	 * @return L'index de la carte choisie
	*/
	private int executeChoisiUneDeSesCartes(){
		double alea = Math.random();
		int res = 0;
		if (alea>0.5){
			res=1;
		}
		return res;
	}
}
