/*
 * Classe abstraite représentant un joueur dans le jeu Jest.
 * Un joueur possède une collection de cartes, un score, et des cartes distribuées à chaque tour.
 * Implémente le pattern Visitor pour le calcul du score.
 * 
 * @author Nina et Emeline
 * @see JoueurPhysique
 * @see JoueurVirtuel
 * @see Visitable
*/

package Jest.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Joueur extends Observable implements Visitable, Serializable{

    private static final long serialVersionUID = 1L;

	
	private String nom;
	/*
	* Collection de cartes gagnées par le joueur
	*/
	private List<Carte> collection;
	/*
	 * Cartes distribuées au début d'un tour 
	 * (une carte visible et une carte cachée)
	*/
	private List<Carte> cartesDistribuees;
	/*
	 * Carte visible choisie par le joueur
	*/
	private Carte carteVisible;
	/*
	 * Carte cachée choisie par le joueur
	*/
	private Carte carteCachee;
	/*
	 * Score du joueur
	*/
	private int score;

	protected EtatJoueur etat;
	private int ID;
	protected List<Integer> choix;
	
	/* 
	 * Constructeur avec paramètre.
	 * Initialise le nom du joueur et crée des listes vides pour la collection et les cartes distribuées.
	 * @param n Le nom du joueur
	*/
	public Joueur(String n, int i) {
		this.setNom(n);
		collection = new ArrayList<Carte>();
		cartesDistribuees = new ArrayList<Carte>();
		score=0;
		this.ID=i;
		this.etat=EtatJoueur.Initial;
	}

	/* 
	 * Ajoute une carte aux cartes distribuées du joueur.
	 * @param c La carte à ajouter
	*/
	public void assignerCarteDistribuees(Carte c) {
		this.cartesDistribuees.add(c);
	}

	/* 
	 * Récupère les cartes visibles et cachées à la fin de la partie.
	 * Ajoute ces cartes à la collection du joueur.
	*/
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

	/* 
	 * Retourne une représentation textuelle du joueur.
	 * @return Une chaîne de caractères représentant le joueur
	*/
	@Override
	public String toString() {
		return this.nom + " : \n  collection=" + collection + ", \n  carte reçues =" + this.cartesDistribuees + ", \n  carte visible ="+this.carteVisible+", \n     score=" + score+"\n";
	}

	/* 
	 * Accepte un visiteur pour le pattern Visitor.
	 * @param visiteur Le visiteur à accepter
	*/
	public void accept ( Visitor visiteur) {
		visiteur.visit(this);		
	}

	/* 
	 * Récupère et retire une carte (visible ou cachée) du joueur.
	 * Si i est différent de 1 ou 0, défini la carte rentourné en NULL.
	 * @param i Indice de la carte à récupérer (0 pour visible, 1 pour cachée)
	 * @return La carte récupérée
	*/
	public Carte recupererCarte (int i) {
		Carte c = new Carte();
		if (i==0) {
			c = this.carteVisible;
			this.carteVisible=null;
		} else if (i==1) {
			c = this.carteCachee;
			this.carteCachee=null;
		} else {
			c = null;
			System.out.println("Indice de carte invalide.");
		}
		return c;
	}

	/* 
	 * Ajoute une carte à la collection du joueur.
	 * @param c La carte à ajouter
	*/
	public void ajouteASaCollection(Carte c) {
		this.collection.add(c);
	}

	/* 
	 * Choisit une carte visible parmi les cartes distribuées.
	 * La carte choisie devient la carte visible, et l'autre devient la carte cachée.
	 * @param i Indice de la carte choisie comme visible
	*/
	public void choisirCarteVisible(int i) {
		this.carteVisible = this.cartesDistribuees.remove(i);
		this.carteCachee = this.cartesDistribuees.remove(0);
		this.etat=EtatJoueur.OffreFaite;
		System.out.println("DEBUG : étape 1 ");
		this.setChanged();
		this.notifyObservers("offre faite");
		System.out.println("DEBUG : joueur : "+getNom()+" offre faite");
	}

	/* 
	 * Remet une carte (visible ou cachée) à la pioche.
	 * La carte remise est retirée du joueur.
	 * @return La carte remise à la pioche
	*/
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
	/* 
	 * Méthode pour faire l'offre de cartes aux autres joueurs
	*/
	public void faireUneOffre () {
	}

	public void attendreUneOffre(){

	}

	public void attendreUnChoix(List<Joueur> joueurs){

	}

	/* 
	 * Méthode pour choisir une carte parmi les cartes des autres joueurs
	 * @param j La liste des joueurs en compétition
	 * @return La liste des indices des cartes choisies
	*/ /*
	public List<Integer> choisirUneCarte (List<Joueur> j){
		return null;
	}*/

	/* 
	 * Méthode pour choisir une de ses propres cartes à offrir
	 * @return L'indice de la carte choisie
	*/
	public int ChoisiUneDeSesCartes() {
		return 0;
	}
	
	
	
	// GETTER
	/* 
	 * Renvoi le score du joueur.
	 * @return le score du joueur
	*/
	public int getScore() {
		return this.score;
	}

	public List<Integer> getChoix(){
		return this.choix;
	}

	/* 
	 * Renvoi la collection de cartes du joueur.
	 * @return la collection de cartes du joueur
	*/
	public List<Carte> getCollection() {
		return this.collection;
	}

	/* 
	 * Renvoi le nom du joueur.
	 * @return le nom du joueur
	*/
	public String getNom() {
		return nom;
	}

	/* 
	 * Renvoi les cartes distribuées au joueur.
	 * @return les cartes distribuées au joueur
	*/
	public List<Carte> getCartesDistribuees(){
		return this.cartesDistribuees;
	}

	/* 
	 * Renvoi la carte visible du joueur.
	 * @return la carte visible du joueur
	*/
	public Carte getCarteVisible() {
		return this.carteVisible;
	}

	/* 
	 * Renvoi la carte cachée du joueur.
	 * @return la carte cachée du joueur
	*/
	public Carte getCarteCachee() {
		return this.carteCachee;
	}

	public EtatJoueur getEtat(){
		return this.etat;
	}

	public int getID(){
		return this.ID;
	}
	
	
	
	// SETTER
	/* 
	 * Permet de modifier le nom du joueur.
	 * @param nom Le nom du joueur
	*/
	public void setNom(String nom) {
		this.nom = nom;
	}

	/* 
	 * Ajoute une carte à la collection du joueur.
	 * @param c La carte à ajouter
	*/
	public void addCollection(Carte c){
		this.collection.add(c);
	}

	/* 
	 * Permet de modifier le score du joueur.
	 * @param score Le nouveau score du joueur
	*/
	public void setScore(int score) {
		this.score = score;
	}

}


