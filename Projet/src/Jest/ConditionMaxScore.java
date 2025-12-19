/*
 * Condition : le joueur avec le score maximum remporte la carte.
 * Peut exclure les joueurs possédant un Jocker.
 * 
 * @author Nina et Emeline
*/

package Jest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConditionMaxScore implements ConditionVictoire, Serializable {

	private static final long serialVersionUID = 1L;
	

	/*
	 * Si vrai, les joueurs avec un Jocker sont inclus dans la vérification du score maximum.
	*/
	private boolean jockerAccepte; 

	/*
	 * Constructeur de ConditionMaxScore
	 * @param jockerAccepte Indique si les joueurs avec un Jocker sont inclus
	*/
	public ConditionMaxScore(boolean jockerAccepte) {
		this.jockerAccepte = jockerAccepte;
	}

	/*
	 * Redéfinit la méthode toString pour afficher la condition de victoire
	 * @return Une chaîne de caractères représentant la condition de victoire
	*/
	@Override
	public String toString(){
        String message="Le joueur qui a le score maximum";
        if (jockerAccepte==false){
            message=message+" sans Jocker";
        }
        return message;
    }

	/*
	 * Vérifie la condition de victoire en fonction du score maximum
	 * Permet de savoir qui a le score maximum parmi les joueurs.
	 * En cas d'égalité, un gagnant est choisi aléatoirement parmi les joueurs à score maximum.
	 * @param joueurs La liste des joueurs en jeu
	 * @return L'indice du joueur gagnant si la condition est remplie, sinon -1 
	*/
	@Override
	public int VerificationVictoire(List<Joueur> joueurs) {
		List<Boolean> joueursAvecJocker = new ArrayList<Boolean>();
		for (int i=0; i<joueurs.size(); i++) {
			boolean aUnJocker = false;
			for (int j=0; j<joueurs.get(i).getCollection().size(); j++){
				if (joueurs.get(i).getCollection().get(j) instanceof Jocker){
					aUnJocker = true;
				}
			}
			joueursAvecJocker.add(aUnJocker);
		}
		int indexJoueur=0;
		int max = -20;
		List<Integer> jMaxScore= new ArrayList<Integer>();
		for (int i=0; i<joueurs.size(); i++) {
			Joueur j = joueurs.get(i);
			if (jockerAccepte==true || joueursAvecJocker.get(i) == false ){
				if (j.getScore() == max ) {
					jMaxScore.add(i);
				}
				if (j.getScore() > max){
					max=j.getScore();
					jMaxScore.clear();
					jMaxScore.add(i);
				}
			} 
		}
		//egalité
		if (jMaxScore.size()>1){
			double alea = Math.random()*jMaxScore.size();
			int aleaInt = Double.valueOf(alea).intValue();
			indexJoueur=jMaxScore.get(aleaInt);
		} else {
			indexJoueur=jMaxScore.get(0);
		}
		return indexJoueur;
	}

}
