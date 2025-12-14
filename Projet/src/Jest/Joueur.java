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
	
	public void assignerCarteDistribuees(Carte c1, Carte c2) {
		this.cartesDistribuees.add(c1);
		this.cartesDistribuees.add(c2);
	}

	@Override
	public String toString() {
		return this.nom + " : \n  collection=" + collection + ", \n  carte reçues =" + this.cartesDistribuees + ", \n  carte visible ="+this.carteVisible+", \n  carte cachée ="+this.carteCachee+", \n     score=" + score;
	}
	
	// place en position 0 celle visible et en 1 celle qui ne l'es pas
	public void faireUneOffre () {
	}
	
	//Trouver comment faire retourner 2 objets, on a aussi besoin du numéro de la Carte
	// juste retourner une liste d'entiers : l'index du joueur et l'index de la carte choisi.
	// !!!!!!!! j'ai juste fait ça en attendant pour pas que ça bug
	public List<Integer> choisirUneCarte (List<Joueur> j){
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);
		l.add(0);
		return l;
	}
	
	public int ChoisiUneDeSesCartes() {
		return 0;
	}
	
	public void accept ( Visitor visiteur) {
		
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
	
	
	
	// GETTER
	public int getScore() {
		// TODO Auto-generated method stub
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
}
