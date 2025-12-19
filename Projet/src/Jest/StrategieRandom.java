package Jest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StrategieRandom implements Strategie, Serializable{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return "Stratégie random";
	}
	
	@Override
	public int executeFaireUneOffre(List<Carte> cartesDistribuées, List<Carte> cartesEnCollection) {
		double aleaCarte = Math.random()*2;
		int numCarte = Double.valueOf(aleaCarte).intValue();
		return numCarte ;
	}

	@Override
	public List<Integer> executeChoisirUneCarte(List <Joueur> joueurs, Joueur moiMeme) {
		List<Integer> res = new ArrayList<Integer>();
		if (joueurs.size() == 1 && joueurs.contains(moiMeme)) {
			res.add(0); // de toute façon il y a qu'un joueur dans la liste donc c'est dans celui la qu'on prend la carte
			res.add(this.executeChoisiUneDeSesCartes());
		} else {
			double aleaJoueur = Math.random();
			int numJoueur =0;
			if (joueurs.contains(moiMeme)){
				aleaJoueur = aleaJoueur*(joueurs.size()-1);
				numJoueur = Double.valueOf(aleaJoueur).intValue();
				int indexMoiMeme = joueurs.indexOf(moiMeme);
				if (numJoueur>=indexMoiMeme){
					numJoueur++;
				}
			} else {
				aleaJoueur = aleaJoueur*joueurs.size();
				numJoueur = Double.valueOf(aleaJoueur).intValue();
			}
			res.add(numJoueur);
			double aleaCarte = Math.random()*2;
			int numCarte = Double.valueOf(aleaCarte).intValue();
			res.add(numCarte);
		}
		return res;
	}

	private int executeChoisiUneDeSesCartes(){
		double alea = Math.random();
		int res = 0;
		if (alea>0.5){
			res=1;
		}
		return res;
	}
}