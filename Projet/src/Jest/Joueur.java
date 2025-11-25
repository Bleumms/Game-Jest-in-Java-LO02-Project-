package Jest;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

	private String nom;
	private List<Carte> collection;
	private List<Carte> offre;
	private int score;

	public Joueur(String n) {
		this.nom = n;
		collection = new ArrayList<Carte>();
		offre = new ArrayList<Carte>();
	}

	public void assignerCarteDistribuees(Carte c1, Carte c2) {
		offre.add(c1);
		offre.add(c2);
	}

	@Override
	public String toString() {
		return "Joueur :\n     nom=" + nom + ", \n     collection=" + collection + ", \n     offre=" + offre + ", \n     score=" + score;
	}

	
}
