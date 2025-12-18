package Jest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Partie {
	private Date dateHeureDeCreation;
	private List<Joueur> participants; 
	private Jeu jeu;
	private List<Carte> pioche;
	private List<Carte> trophe;

	public Partie() {
		this.dateHeureDeCreation = new Date();
		this.participants = new ArrayList<Joueur>();
		this.pioche = new ArrayList<Carte>();
		this.trophe = new ArrayList<Carte>();
	}

	public void ajouterUnJoueur(Joueur j) {
		this.participants.add(j);
	}

	public void choisirUnJeu(Jeu j) {
		this.jeu = j;
	}

	public void choisirLesTrophes(int nbTrophes) {
		this.pioche = jeu.getCartes();
		this.melangerLaPioche();
		for (int i = 0; i < nbTrophes; i++) {
			this.trophe.add(this.pioche.remove(0));
		}
	}

	public void melangerLaPioche() {
		Collections.shuffle(pioche);
	}

	public void distribuer() {
		if (this.pioche.size() >= 2*this.participants.size()) {
			for (int i = 0; i < participants.size(); i++) {
				Joueur j = participants.get(i);
				j.assignerCarteDistribuees(pioche.remove(0));
			}
			for (int i = 0; i < participants.size(); i++) {
				Joueur j = participants.get(i);
				j.assignerCarteDistribuees(pioche.remove(0));
			}
		}
	}

	@Override
	public String toString() {
		return "Partie :\n  dateHeureDeCreation=" + dateHeureDeCreation + ", \n  participants=" + participants
				+ ", \n  jeu=" + jeu + ", \n  pioche=" + pioche + ", \n  trophe=" + trophe;
	}

	public void initialiserLaPartie(int NombreTrophe) {
		if (this.trophe.size() == 0) {
			this.choisirLesTrophes(NombreTrophe);
			this.melangerLaPioche();
			System.out.println("\nLes trophées sont :   ");
			for (int i=0; i<this.trophe.size();i++){
				System.out.print(this.trophe.get(i)+"   ;   ");
			}
			System.out.println();
		}
	}

	public void initialiserLaPartie() {
		if (this.trophe.size() == 0) {
			this.choisirLesTrophes(2);
			this.melangerLaPioche();
		}
	}

	public boolean faireUnTourDeJeu() {
		// INITIALISATION DES VARS
		// créer la liste des joueur pour les quels on peut prendre une carte
		List<Joueur> joueursDispo = new ArrayList<Joueur>(this.participants);
		List<Joueur> joueursPasEncoreJoue = new ArrayList<Joueur>(this.participants);
		Joueur jFaisSonChoix = null;
		
		//DISTRIBUER LES CARTES
		this.distribuer();
		
		// CHACUNS LEURS OFFRES
		for (int i=0;i<this.participants.size();i++) {
			this.participants.get(i).faireUneOffre();
		}
		
		// Affichage pour connaitre l'état de la table de jeu
		this.affichageTable();
				
		while (joueursPasEncoreJoue.size()>0) {
			// choisir le 1er joueur
			if (jFaisSonChoix==null) {
				jFaisSonChoix = definirJoueurSuivant(joueursPasEncoreJoue);
			} 
			System.out.println("\nC'est à "+ jFaisSonChoix.getNom()+" de jouer");

			List<Integer> resultat = jFaisSonChoix.choisirUneCarte(joueursDispo);
			int numJoueur = resultat.get(0);
			int numCarte = resultat.get(1);
			
			// Le joueur a qui on enleve une carte ne pourra pas avoir encore une autre carte de prise
			Joueur joueurChoisi = joueursDispo.remove(numJoueur);
			// on lui enlève la carte prise
			Carte c = joueurChoisi.recupererCarte(numCarte);

			if (jFaisSonChoix instanceof JoueurPhysique){
				System.out.println("Vous avez choisi la carte "+c.getNom()+" de "+joueurChoisi.getNom());
			}
			if (numCarte==0){
				System.out.println("\n\n"+jFaisSonChoix.getNom()+" a choisi la carte visible : "+c.getNom()+" de "+joueurChoisi.getNom());

			} else {
				System.out.println("\n\n"+jFaisSonChoix.getNom()+" a choisi la carte cachée de "+joueurChoisi.getNom());
			}

			// et la donne a celui qui a fait son choix
			jFaisSonChoix.ajouteASaCollection(c);
			
			// on retire de ceux qui n'ont pas encore joué
			joueursPasEncoreJoue.remove(jFaisSonChoix);
			
			//c'est maintenant son tour de prendre une carte
			//seulement si il n'en a pas déjà choisie une carte
			if (joueursPasEncoreJoue.contains(joueurChoisi)) {
				jFaisSonChoix=joueurChoisi;
			} else {
				jFaisSonChoix=null;
			}
		}
		
		// on remet a la pioche seulement si on peut encore faire un tour
		boolean finDePartie = false;
		if (this.pioche.size()>=this.participants.size()){
			// on récupère les cartes non choisies
			List<Carte> recup = new ArrayList<Carte>();
			for (int i=0;i<this.participants.size();i++) {
				recup.add(this.participants.get(i).remiseALaPioche());
			}
			this.remiseALaPioche(recup);
		} else {
			for (int i=0;i<this.participants.size();i++) {
				this.participants.get(i).recupFinDePartie();
			}
			finDePartie=true;
		}
		calculScore();
		return finDePartie;
	}
	
	public void affichageTable(){
		System.out.print( "\n\nEtat de la table de jeu : \n Trophes :   " );
		for (int i=0; i<this.trophe.size();i++){
			System.out.print(this.trophe.get(i)+"   ;   ");
		}
		System.out.println("\n Joueurs : ");
		for (int j=0; j<this.participants.size();j++){
			System.out.println(this.participants.get(j).getNom()+" :   carte visible = "+this.participants.get(j).getCarteVisible()+"   ;    nombre de carte dans sa collection = "+this.participants.get(j).getCollection().size());
		}
	}
	
	public void remiseALaPioche(List<Carte> cartes) {
		Collections.shuffle(cartes);
		this.pioche.addAll(0, cartes);
	}
	
	public Joueur definirJoueurSuivant(List<Joueur> joueursPasEncoreJoue) {
		Carte CarteMax=joueursPasEncoreJoue.get(0).getCarteVisible();
		int indexJoueur = 0;
		for (int i=1;i<joueursPasEncoreJoue.size();i++) {
			Carte c = joueursPasEncoreJoue.get(i).getCarteVisible();
			boolean besoinChanger = this.jeu.estSupperieur(c,CarteMax);
			if (besoinChanger) {
				CarteMax = c;
				indexJoueur = i;
			}
		}
		return joueursPasEncoreJoue.get(indexJoueur);
	}

	public void calculScore() {
		// pour chaque joueur on donne sa main complete au jeu qui va faire appel a sa carte de référence
		Visitor calcScore = new CalculateurScore();
		calcScore.setReference(jeu.getReference());
		for (int j = 0; j < this.participants.size(); j++) {
			this.participants.get(j).accept(calcScore);
		}
	}

	public void attribuerLesTrophes(){
		for (int i=0; i<this.trophe.size(); i++){
			Carte c = this.trophe.get(i);
			int indexJ = c.JoueurGagnantCarte(this.participants);
			this.participants.get(indexJ).ajouteASaCollection(c);
		}
	}

	public void finDePartie(){
		System.out.println("\n\nFin de partie : ");
		
		// Attribuer les trophés
		this.attribuerLesTrophes();

		int maxscore = this.participants.get(0).getScore();
		List<Joueur> jMaxScore= new ArrayList<Joueur>();
		jMaxScore.add(this.participants.get(0));
		System.out.println("\nJoueur 1 : "+this.participants.get(0).getNom()+"\n   Score : "+this.participants.get(0).getScore()+"\n    Cartes : "+this.participants.get(0).getCollection());
		for (int i=1; i < this.participants.size(); i++){
			Joueur j = this.participants.get(i);
			System.out.println("\nJoueur "+(i+1)+" : "+j.getNom()+"\n   Score : "+j.getScore()+"\n    Cartes : "+j.getCollection());
			if (j.getScore() == maxscore){
				jMaxScore.add(j);
			}
			if (j.getScore() > maxscore){
				maxscore=j.getScore();
				jMaxScore.clear();
				jMaxScore.add(j);
			}
		}
		if (jMaxScore.size()==1){
			System.out.println("\n\nLe gagnant de la partie est "+jMaxScore.get(0).getNom()+" avec un score de "+maxscore);
		} else {
			System.out.println("\n\nEgalité !");
			System.out.print("Les joueurs ");
			for(int i=0; i<jMaxScore.size();i++){
				System.out.print(jMaxScore.get(i));
				if (i<jMaxScore.size()-2){
					System.out.print(", ");
				} else {
					if (i<jMaxScore.size()-1){
						System.out.print(" et ");
					}
				}
			}
			System.out.println(" ont un score de "+maxscore);
		}
	}
}