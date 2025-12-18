package Jest;

import java.util.ArrayList;
import java.util.List;

public class ConditionMaxScore implements ConditionVictoire {

	public ConditionMaxScore() {
		// TODO Auto-generated constructor stub
	}

	// renvoie le numero du joueur avec le score max
	// vérifié
	@Override
	public int VerificationVictoire(List<Joueur> joueurs) {
		int indexJoueur=0;
		int max = joueurs.get(0).getScore();
		List<Integer> jMaxScore= new ArrayList<Integer>();
		jMaxScore.add(0);
		for (int i=1; i<joueurs.size(); i++) {
			Joueur j = joueurs.get(i);
			if (j.getScore() == max ) {
				jMaxScore.add(i);
			}
			if (j.getScore() > max){
				max=j.getScore();
				jMaxScore.clear();
				jMaxScore.add(i);
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
