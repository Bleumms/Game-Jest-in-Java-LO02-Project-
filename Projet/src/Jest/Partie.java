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
				j.assignerCarteDistribuees(pioche.remove(0), pioche.remove(0));
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
		}
	}

	public void initialiserLaPartie() {
		if (this.trophe.size() == 0) {
			this.choisirLesTrophes(2);
			this.melangerLaPioche();
		}
	}

	public void faireUnTourDeJeu() {
		// On gerera le cas de fin de partie apres mais on peut imaginer une methode IsFinDePartie():boolean
		
		
		// INITIALISATION DES VARS
		// créer la liste des joueur pour les quels on peut prendre une carte
		List<Joueur> joueursDispo = new ArrayList<Joueur>(this.participants);
		List<Joueur> joueursPasEncoreJoue = new ArrayList<Joueur>(this.participants);
		Joueur jFaisSonChoix = null;
		
		//DISTRIBUER LES CARTES
		this.distribuer();
		
		System.out.println("//////////////");
		System.out.println("distribue");
		for (int i=0;i<this.participants.size();i++) {
			System.out.println(this.participants.get(i));
		}
		System.out.println("//////////////");
		
		// CHACUNS LEURS OFFRES
		for (int i=0;i<this.participants.size();i++) {
			this.participants.get(i).faireUneOffre();
		}
		
		System.out.println("//////////////");
		System.out.println("les offres ont été faites");
		for (int i=0;i<this.participants.size();i++) {
			System.out.println(this.participants.get(i));
		}
		System.out.println("//////////////");
				
		while (joueursPasEncoreJoue.size()>0) {
			// choisir le 1er joueur
			if (jFaisSonChoix==null) {
				jFaisSonChoix = definirJoueurSuivant(joueursPasEncoreJoue);
			} 
			System.out.println("//////////////");
			System.out.println("C'est "+ jFaisSonChoix.getNom()+" qui joue");
			System.out.println("    a savoir : "+joueursDispo+"\n"+joueursPasEncoreJoue);
			System.out.println("//////////////");
			
			List<Integer> resultat = jFaisSonChoix.choisirUneCarte(joueursDispo);
			int numJoueur = resultat.get(0);
			int numCarte = resultat.get(1);
			
			System.out.println("//////////////");
			System.out.println("il a choisi la "+numCarte+" de "+joueursDispo.get(numJoueur).getNom());
			System.out.println("//////////////");
			// Le joueur a qui on enleve une carte ne pourra pas avoir encore une autre carte de prise
			Joueur joueurChoisi = joueursDispo.remove(numJoueur);
			// on lui enlève la carte prise
			Carte c = joueurChoisi.recupererCarte(numCarte);
			// et la donne a celui qui a fait son choix
			jFaisSonChoix.ajouteASaCollection(c);
			
			System.out.println("//////////////");
			System.out.println("Les changements ont été faits : ");
			System.out.println(jFaisSonChoix);
			System.out.println(joueurChoisi);
			System.out.println("//////////////");
			
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
		
		// on récupère les cartes non choisies
		List<Carte> recup = new ArrayList<Carte>();
		for (int i=0;i<this.participants.size();i++) {
			recup.add(this.participants.get(i).remiseALaPioche());
		}
		this.remiseALaPioche(recup);
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

	// pour tester : normalement la fonction appelle le pattern visitor
	public List<Integer> calculScore() {
		List<Integer> res = new ArrayList<Integer>();
		// pour chaque joueur on donne sa main complete au jeu qui va faire appel a sa
		// carte de référence
		for (int j = 0; j < this.participants.size(); j++) {
			int r = this.jeu.getReference().calculScore(this.participants.get(j).getCollection());
			res.add(r);
		}
		return res;
	}

	// pour tester
	public void partieFactice(List<Joueur> participantsF, Jeu jeuF, List<Carte> piocheF, List<Carte> tropheF) {
		participants = participantsF;
		jeu = jeuF;
		pioche = piocheF;
		trophe = tropheF;
	}
}