package Jest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jeu {

	private List<Carte> cartes;
	private Reference ref;

	public Jeu() {
		this.cartes = new ArrayList<Carte>();
	}

	public void ajouterCarte(Carte c) {
		this.cartes.add(c);
	}

	public void ajouterDesCartes(List<Carte> c) {
		for (int i = 0; i < c.size(); i++) {
			this.cartes.add(c.get(i));
		}
	}
	
	public Reference getReference() {
		return this.ref;
	}

	@Override
	public String toString() {
		return "Jeu :  \n  Possède : " + cartes + "\n  Référence : "+ref;
	}

	public void ajouterReference(Reference r) {
		this.ref = r;
	}

	public List<ArrayList<Carte>> choisirTrophe(int nbTrophes) {
		Collections.shuffle(cartes);
		ArrayList<Carte> t = new ArrayList<Carte>();
		ArrayList<Carte> p = new ArrayList<Carte>();
		for (int i = 0; i < nbTrophes; i++) {
			Carte c = this.cartes.get(i);
			t.add(c);
		}
		for (int j = nbTrophes; j < cartes.size(); j++) {
			Carte c = this.cartes.get(j);
			p.add(c);
		}
		List<ArrayList<Carte>> res = new ArrayList<ArrayList<Carte>>();
		res.add(t);
		res.add(p);
		return res;
	}

	public void ajouterRegle(Regle r) {
		this.ref.ajouterRegle(r);
	}

	public List<Regle> getRegles() {
		return ref.getRegles();
	}
	
	public List<Carte> getCartes() {
		return cartes;
	}

	public boolean estSupperieur(Carte c1, Carte c2) {
		if (c1 instanceof Jocker) {  //plus petit score, il ne sera jamais strictement supp
			return false;
		}	
		if (c2 instanceof Jocker) { //plus petit score, il sera toujours inf ou egal
			return true;
		}	
		// pour que ce soit plus lisible je n'utilise pas de else ici, 
		// on sort de toute facon de la méthode à chaque return rencontré.
		// aucun Jocker donc
		CarteClassique cc1 = (CarteClassique) c1;
		CarteClassique cc2 = (CarteClassique) c2;
		if (cc1.getNumero()>cc2.getNumero()){
			return true;
		}
		if (cc1.getNumero()==cc2.getNumero() && cc2.getSymbole().compareTo(cc1.getSymbole())>0 ){
			return true;
		}
		return false;
	}
}
