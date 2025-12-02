package Jest;

import java.util.ArrayList;
//test
import java.util.Collections;
import java.util.List;

public class Jeu {

	private List<Carte> cartes;
	private Reference ref;
	
	public Jeu() {
		this.cartes = new ArrayList<Carte>();
	}

	public void ajouterCarte(Carte c ) {
		this.cartes.add(c);
	}
	
	@Override
	public String toString() {
		return "Jeu [cartes=" + cartes + "]";
	}

	public void ajouterReference( Reference r) {
		this.ref=r;
	}
	
	public List<ArrayList<Carte>> choisirTrophe(int nbTrophes){
		Collections.shuffle(cartes);
		ArrayList<Carte> t = new ArrayList<Carte>();
		ArrayList<Carte> p = new ArrayList<Carte>();
		for (int i=0; i<nbTrophes; i++) {
			Carte c = this.cartes.get(i);
			t.add(c);
		}
		for (int j=nbTrophes; j<cartes.size(); j++) {
			Carte c = this.cartes.get(j);
			p.add(c);
		}
		List<ArrayList<Carte>> res = new ArrayList<ArrayList<Carte>>();
		res.add(t);
		res.add(p);
		return res;
	}
}
