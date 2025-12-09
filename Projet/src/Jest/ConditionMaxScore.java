package Jest;

import java.util.List;

public class ConditionMaxScore implements ConditionVictoire {

	public ConditionMaxScore() {
		// TODO Auto-generated constructor stub
	}

	// renvoie le numero du joueur avec le score max
	// vérifié
	@Override
	public int VerificationVictoire(List<Joueur> joueurs) {
		// TODO Auto-generated method stub
		int max = joueurs.get(0).getScore();
		int index=0;
		for (int i=1; i<joueurs.size(); i++) {
			if (joueurs.get(i).getScore() == max ) {
				// Egalité : tirage au hasard
				System.out.print("Deux scores sont égaux, tirages au hasard au profit ...");
				double randomValue = Math.random();
				if (randomValue<0.5) {
					System.out.println("... Du joueur numéro "+index+" !");
				} else {
					System.out.println("... Du joueur numéro "+i+" !");
					max = joueurs.get(i).getScore();
					index=i;
				}
			}
			else if (joueurs.get(i).getScore() > max ) {
				max = joueurs.get(i).getScore();
				index=i;
			}
		}
		return index;
	}

}
