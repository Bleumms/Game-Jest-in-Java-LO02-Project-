package Jest;

import java.util.ArrayList;
import java.util.List;

public class Joueur implements Visitable{

	private String nom;
	private List<Carte> collection;
	private List<Carte> cartesDistribuees;
	private Carte carteVisible;
	private Carte carteCachee;
	private int score;
	

	public Joueur(String n) {
		this.setNom(n);
		collection = new ArrayList<Carte>();
		cartesDistribuees = new ArrayList<Carte>();
		score=0;
	}
	
	public void assignerCarteDistribuees(Carte c) {
		this.cartesDistribuees.add(c);
	}

	public void recupFinDePartie(){
		if (this.carteVisible!=null) {
			this.collection.add(this.carteVisible);
			this.carteVisible=null;
		}
		if (this.carteCachee!=null) {
			this.collection.add(this.carteCachee);
			this.carteCachee=null;
		}
	}

	@Override
	public String toString() {
		return this.nom + " : \n  collection=" + collection + ", \n  carte reçues =" + this.cartesDistribuees + ", \n  carte visible ="+this.carteVisible+", \n     score=" + score+"\n";
	}
	
	public void accept ( Visitor visiteur) {
		visiteur.visit(this);		
	}
	
	public Carte recupererCarte (int i) {
		//gérer si i!=0 et 1
		Carte c = new Carte();
		if (i==0) {
			c = this.carteVisible;
			this.carteVisible=null;
		} else {
			c = this.carteCachee;
			this.carteCachee=null;
		}
		return c;
	}
	
	public void ajouteASaCollection(Carte c) {
		this.collection.add(c);
	}
	
	public void ChoisirCarteVisible(int i) {
		this.carteVisible = this.cartesDistribuees.remove(i);
		this.carteCachee = this.cartesDistribuees.remove(0);
	}
	
	public Carte remiseALaPioche() {
		Carte c= new Carte();
		if (this.carteCachee!=null) {
			c = this.carteCachee;
			this.carteCachee = null;
		} else {
			c=this.carteVisible;
			this.carteVisible = null;
		}
		return c;
	}



	// METHODES REDEFINIES ENSUITE
	public void faireUneOffre () {

	}
	
	public List<Integer> choisirUneCarte (List<Joueur> j){
		return null;
	}
	
	public int ChoisiUneDeSesCartes() {
		return 0;
	}
	
	
	
	// GETTER
	public int getScore() {
		return this.score;
	}
	
	public List<Carte> getCollection() {
		return this.collection;
	}
	
	public String getNom() {
		return nom;
	}
	
	public List<Carte> getCartesDistribuees(){
		return this.cartesDistribuees;
	}
	
	public Carte getCarteVisible() {
		return this.carteVisible;
	}
	
	public Carte getCarteCachee() {
		return this.carteCachee;
	}
	
	
	
	// SETTER
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void addCollection(Carte c){
		this.collection.add(c);
	}
	
	public void setScore(int score) {
		this.score = score;
	}

}

