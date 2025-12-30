/*
 * Représente un jeu de cartes avec ses règles.
 * Contient les cartes et la référence aux règles du jeu.
 * 
 * @author Nina et Emeline
 * @see Reference
 * @see Carte
 */

package Jest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jeu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Liste des cartes du jeu 
	*/
	private List<Carte> cartes;

	/*
	 * Référence contenant les règles du jeu
	*/
	private Reference ref;

	/*
	 * Constructeur de Jeu initialisant la liste des cartes.
	 * Construit un jeu vide.
	*/
	public Jeu() {
		this.cartes = new ArrayList<Carte>();
	}

	/* 
	 * Ajoute une carte au jeu.
	 * @param c La carte à ajouter
	*/
	public void ajouterCarte(Carte c) {
		this.cartes.add(c);
	}

	/* 
	 * Ajoute une liste de cartes au jeu.
	 * @param c La liste de cartes à ajouter
	*/
	public void ajouterDesCartes(List<Carte> c) {
		for (int i = 0; i < c.size(); i++) {
			this.cartes.add(c.get(i));
		}
	}
	
	/* 
	 * Retourne la référence du jeu.
	 * @return La référence contenant les règles
	*/
	public Reference getReference() {
		return this.ref;
	}

	/* 
	 * Retourne une représentation textuelle du jeu.
	 * On y inclut les cartes et les règles de la carte de référence.
	 * @return Une chaîne de caractères représentant le jeu
	*/
	@Override
	public String toString() {
		return "Jeu :  \n      Cartes : " + cartes + "\n      Règles sur la carte de référence : "+ref;
	}

	/* 
	 * Ajoute une référence au jeu.
	 * @param r La référence à ajouter
	*/
	public void ajouterReference(Reference r) {
		this.ref = r;
	}

	/* 
	 * Choisit des trophées parmi les cartes du jeu.
	 * Mélange les cartes et sépare les trophées des autres cartes.
	 * @param nbTrophes Le nombre de trophées à choisir
	 * @return Une liste contenant deux listes : la première avec les trophées, la deuxième avec les autres cartes
	*/
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

	/* 
	 * Ajoute une règle au jeu via la référence.
	 * @param r La règle à ajouter
	*/
	public void ajouterRegle(Regle r) {
		this.ref.ajouterRegle(r);
	}

	/* 
	 * Retourne la liste des règles du jeu.
	 * @return La liste des règles
	*/
	public List<Regle> getRegles() {
		return ref.getRegles();
	}
	
	/* 
	 * Retourne la liste des cartes du jeu.
	 * @return La liste des cartes
	*/
	public List<Carte> getCartes() {
		List<Carte> liste = new ArrayList<Carte>();
		for (int i=0; i<this.cartes.size();i++){
			liste.add(this.cartes.get(i));
		}
		return liste;
	}

	/* 
	 * Compare deux cartes pour déterminer si la première est supérieure à la seconde.
	 * @param c1 La première carte
	 * @param c2 La seconde carte
	 * @return true si la première carte est supérieure, false sinon
	*/
	public boolean estSupperieur(Carte c1, Carte c2) {
		if (c1 instanceof Jocker) {  //plus petit score, il ne sera jamais strictement supp
			return false;
		}	
		if (c2 instanceof Jocker) { //plus grand score, il sera toujours inf ou egal
			return true;
		}
		// on sort de la méthode à chaque return rencontré.
		// aucun Jocker, uniquement des cartes classiques
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
