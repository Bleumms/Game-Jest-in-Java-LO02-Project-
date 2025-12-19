package Jest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConditionMaxScore implements ConditionVictoire, Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean jockerAccepte; 

	public ConditionMaxScore(boolean jockerAccepte) {
		this.jockerAccepte = jockerAccepte;
	}

	public String toString(){
        String message="Le joueur qui a le score maximum";
        if (jockerAccepte==false){
            message=message+" sans Jocker";
        }
        return message;
    }

	// renvoie le numero du joueur avec le score max
	// vérifié
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