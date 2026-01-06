/**
 * Représente une partie complète du jeu Jest.
 * Gère le déroulement de la partie, la distribution des cartes,
 * le calcul des scores et la sauvegarde/chargement.
 * 
 * @author Nina et Emeline
 * @see Jeu
 * @see Joueur
 */

package Jest.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Observable;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.*;

public class Partie extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Date et heure de création de la partie
	*/
	private Date dateHeureDeCreation;

	/*
	 * Liste des joueurs participants à la partie
	*/
	private List<Joueur> participants; 

	/*
	 * Le jeu qui sera utilisé pour la partie
	*/
	private Jeu jeu;

	/*
	 * La pioche de cartes pour la partie, qui dépendra du jeu choisi
	*/
	private List<Carte> pioche;

	/*
	 * Les trophées à attribuer aux joueurs à la fin de la partie
	*/
	private List<Carte> trophe;

	/*
	 * Identifiant unique de la partie pour la sauvegarde
	*/
	private int ID;

	private EtatPartie etat;
	private int compteurOffreFaite;
	private Joueur faisSonChoix;
	private List<Joueur> pasEncoreJoue;
	private List<Joueur> pasEncoreDeCartePrise;

	/*
	 * Constructeur de la partie
	*/
	public Partie() {
		this.dateHeureDeCreation = new Date();
		this.participants = new ArrayList<Joueur>();
		this.pioche = new ArrayList<Carte>();
		this.trophe = new ArrayList<Carte>();
		this.ID = -1;
		this.etat=EtatPartie.Initial;
		this.faisSonChoix=null;
		this.compteurOffreFaite=0;
	}


	/*
	 * Ajoute un joueur à la partie
	 * @param j Le joueur à ajouter
	*/
	public void ajouterUnJoueur(Joueur j) {
		this.participants.add(j);
	}

	public List<Joueur> getParticipants() {
		return this.participants;
	}

	public List<Joueur> getPasEncoreDeCartePrise(){
		return this.pasEncoreDeCartePrise;
	}

	public List<Carte> getTrophes(){
		return this.trophe;
	}

	public EtatPartie getEtat(){
		return this.etat;
	}

	public Joueur getfaisSonChoix(){
		return this.faisSonChoix;
	}

	public void aPrisUneCarte(Joueur jAJouer, Joueur jPersSaCarte, int numCarte){
		System.out.println("DEBUG : "+jAJouer.getNom()+" a pris la carte "+numCarte+" de "+jPersSaCarte.getNom());
		pasEncoreJoue.remove(jAJouer);
		pasEncoreDeCartePrise.remove(jPersSaCarte);
		Carte c = jPersSaCarte.recupererCarte(numCarte);
		jAJouer.ajouteASaCollection(c);
		if (pasEncoreJoue.contains(jPersSaCarte)) {
			this.faisSonChoix=jPersSaCarte;
		} else {
			this.faisSonChoix=null;
		}

	}

	public void prochainJoueur(){
		System.out.println("DEBUG : étape 7 ");
		if (faisSonChoix==null && this.pasEncoreJoue.size()!=0) {
			faisSonChoix = definirJoueurSuivant();
		} 
		if (this.pasEncoreJoue.size()==0){
			this.etat=EtatPartie.ChoixFinis;
		}
	}

	public void ajouterCompteurOffreFaite(){
		this.compteurOffreFaite++;
		finDesOffres();
	}

	public void initialiserLeChoix(){
		this.pasEncoreJoue= new ArrayList<Joueur>(this.participants);
		this.pasEncoreDeCartePrise= new ArrayList<Joueur>(this.participants);
		this.faisSonChoix=definirJoueurSuivant();
	}

	public void finDesOffres(){
		//System.out.println("DEBUG : compteur : "+this.compteurOffreFaite+" ; nb participants : "+this.participants.size());
		if (this.compteurOffreFaite==this.participants.size()){
			System.out.println("DEBUG : étape 3 ");
			this.etat=EtatPartie.OffreFinis;
			this.initialiserLeChoix();
			//System.out.println("DEBUG : on passe au choix de carte");
			this.setChanged();
			this.notifyObservers();
			
		}
	}

	public void attendreUneOffre(){
		for (int i=0;i<this.participants.size();i++) {
			this.participants.get(i).attendreUneOffre();
		}
		for (Joueur j : this.participants){
			if (j.getEtat()==EtatJoueur.OffreFaite){
				compteurOffreFaite++;
			}
		}
		finDesOffres();
	}
	
	/*
	 * Définit le jeu à utiliser pour la partie
	 * @param j Le jeu à définir
	*/
	public void choisirUnJeu(Jeu j) {
		this.jeu = j;
	}

	/*
	 * Choisit les trophées de la partie
	 * @param nbTrophes Le nombre de trophées à définir
	*/
	public void choisirLesTrophes(int nbTrophes) {
		this.pioche = jeu.getCartes();
		this.melangerLaPioche();
		for (int i = 0; i < nbTrophes; i++) {
			this.trophe.add(this.pioche.remove(0));
		}
	}

	/*
	 * Mélange la pioche de cartes
	*/
	public void melangerLaPioche() {
		Collections.shuffle(pioche);
	}

	/*
	 * Distribue les cartes aux joueurs
	 * On s'assure que la pioche a assez de cartes
	 * Puis on va assigner 2 cartes par joueur
	 * On enlève à chaque fois les cartes distribuées de la pioche, pour ne pas les redonner
	*/
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
		this.etat=EtatPartie.Distribué;
		this.setChanged();
		this.notifyObservers();
	}

	/*
	 * Affiche les informations de la partie
	 * Inclut l'ID, la date de création, les participants et les trophées
	*/
	@Override
	public String toString() {
		String message="";
		if (this.ID!=-1){
			message=message+"Partie "+this.ID;
		} else {
			message=message+"Partie ";
		}
		message=message+"\n      date et heure de création : "+dateHeureDeCreation;
		message=message+"\n      paricipants : ";
		for (int i=0; i<this.participants.size();i++){
			Joueur j = this.participants.get(i);
			message=message+j.getNom();
			if (i<this.participants.size()-2){
				message=message+", ";
			} else if (i<this.participants.size()-1){
				message=message+" et ";
			}
		}
		message=message+"\n      les trophés : ";
		for (int i=0; i<this.trophe.size();i++){
			Carte c = this.trophe.get(i);
			message=message+c.getNom()+" : "+c.getConditionVictoire();
			 if (i<this.participants.size()-1){
				message=message+"   ;   ";
			} 
		}
		return message;
	}

	/*
	 * Initialise la partie en choisissant les trophées et en mélangeant la pioche
	 * @param NombreTrophe Le nombre de trophées à choisir
	*/
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

	/*
	 * Initialise la partie en choisissant 2 trophées et en mélangeant la pioche
	*/
	public void initialiserLaPartie() {
		if (this.trophe.size() == 0) {
			this.choisirLesTrophes(2);
			this.melangerLaPioche();
			System.out.println("\nLes trophées sont :   ");
			for (int i=0; i<this.trophe.size();i++){
				System.out.print(this.trophe.get(i)+"   ;   ");
			}
			System.out.println();
		}
	}

	/*
	 * Déroulement d'un tour de jeu complet
	 * Retourne true si la partie est terminée, false sinon
	*/	/*
	public boolean faireUnTourDeJeu() {
		if (this.pioche.size()<this.participants.size()){
			return true;
		}
		// INITIALISATION DES VARS
		// créer la liste des joueur pour lesquels on peut prendre une carte
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
				jFaisSonChoix = definirJoueurSuivant();
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
				System.out.println("\n"+jFaisSonChoix.getNom()+" a choisi la carte visible : "+c.getNom()+" de "+joueurChoisi.getNom());

			} else {
				System.out.println("\n"+jFaisSonChoix.getNom()+" a choisi la carte cachée de "+joueurChoisi.getNom());
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
		Partie.sauvegarder(this);
		return finDePartie;
	} */
	

	/*
	 * Affiche les informations de la table de jeu
	 * Inclut les trophées et les joueurs avec leurs cartes visibles et le nombre de cartes en collection
	*/
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
	
	/*
	 * Mélange les cartes restantes et les remet dans la pioche
	 * @param cartes Les cartes à remettre dans la pioche
	*/
	public void remiseALaPioche(List<Carte> cartes) {
		Collections.shuffle(cartes);
		this.pioche.addAll(0, cartes);
	}
	
	/*
	 * Détermine l'ordre initial des joueurs en fonction de leur carte visible			
	 * Le joueur avec la carte visible la plus faible joue en premier
	 * @param joueursPasEncoreJoue La liste des joueurs n'ayant pas encore joué ce tour
	 * @return Le joueur qui doit jouer
	*/
	public Joueur definirJoueurSuivant() {
		System.out.println("DEBUG : étape 4 ");
		Carte CarteMax=this.pasEncoreJoue.get(0).getCarteVisible();
		int indexJoueur = 0;
		for (int i=1;i<this.pasEncoreJoue.size();i++) {
			Carte c = this.pasEncoreJoue.get(i).getCarteVisible();
			boolean besoinChanger = this.jeu.estSupperieur(c,CarteMax);
			if (besoinChanger) {
				CarteMax = c;
				indexJoueur = i;
			}
		}
		return this.pasEncoreJoue.get(indexJoueur);
	}

	/*
	 * Calcule le score de chaque joueur en fonction des cartes dans leur collection
	*/
	public void calculScore() {
		// pour chaque joueur on donne sa main complete au jeu qui va faire appel a la carte de référence
		Visitor calcScore = new CalculateurScore();
		calcScore.setReference(jeu.getReference());
		for (int j = 0; j < this.participants.size(); j++) {
			this.participants.get(j).accept(calcScore);
		}
	}

	/*
	 * Attribue les trophées aux joueurs en fonction des conditions de victoire des cartes trophées
	*/
	public void attribuerLesTrophes(){
		for (int i=0; i<this.trophe.size(); i++){
			Carte c = this.trophe.get(i);
			int indexJ = c.JoueurGagnantCarte(this.participants);
			if (indexJ>=0){
				this.participants.get(indexJ).ajouteASaCollection(c);
				System.out.println("\nLe trophé "+c+" est attribué à "+this.participants.get(indexJ).getNom() + "\n("+c.getConditionVictoire()+")");
			}
		}
	}

	/*
	 * Termine la partie en affichant les scores finaux et le gagnant
	 * Supprime la sauvegarde de la partie
	*/
	public void finDePartie(){
		System.out.println("\n\nFin de partie : ");
		
		// Attribuer les trophés
		this.attribuerLesTrophes();

		this.calculScore();

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

		// Supprimer des sauvegardes parce qu'on peux pas reprendre cette partie
		Partie.supprimerPartie(this.ID);

	}

	/*
	 * Récupère l'ID de la partie
	 * @return L'ID de la partie
	*/
	public int getID(){
		return this.ID;
	}

	/*
	 * Permet de modifier l'ID de la partie
	 * @param id L'ID à définir
	*/
	public void setID(int id){
		this.ID=id;
	}

	/*
	 * Sauvegarde la partie dans un fichier depuis son ID
	 * Le nom du fichier est "Partie_ID.obj" où ID est l'identifiant de la partie
	 * Si l'ID n'est pas défini, il est attribué automatiquement
	 * @param p La partie à sauvegarder
	*/
	//SAUVEGARDE
	public static void sauvegarder(Partie p) {
		if (p.getID()==-1){
			List<String> fichiers=null;
			try{
				fichiers = Partie.listerSauvegardes();
			} catch (IOException e){
				e.printStackTrace();
			}
			//dernier nom de sauvegarde
			int max = -1;
	    	for (String s : fichiers) {
    	    	int x = Integer.parseInt(s.replace("Partie_", "").replace(".obj", ""));
		        if (x > max) {
    		        max = x;
        		}
	    	}
			p.setID(max+1);
		}
		String titre = "Partie_"+(p.getID())+".obj";
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(titre))) {

            oos.writeObject(p);

        } catch (IOException e) {
            System.out.println("Problème lors de la sauvegarde de la partie");
        }
    }

	/*
	 * Charge une partie depuis un fichier en utilisant son ID
	 * Le nom du fichier est "Partie_ID.obj" où ID est l'identifiant de la partie
	 * @param ID L'ID de la partie à charger
	 * @return La partie chargée, ou null en cas d'erreur
	*/
	public static Partie charger(int ID)  {
		String titre = "Partie_"+ID+".obj";
		Partie p=null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(titre))) {
	        p = (Partie) ois.readObject();
    	    System.out.println("Partie chargée avec succès");
        	
		} catch (IOException | ClassNotFoundException e){
			System.out.println("Problème de chargement de la partie");
		}
		return p;
    }

	/*
	 * Charge une partie depuis un fichier en utilisant son nom
	 * @param nomFichier Le nom du fichier à charger
	 * @return La partie chargée ou null en cas d'erreur
	*/
	public static Partie charger(String nomFichier) {
        Partie p=null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
	        p = (Partie) ois.readObject();
    	    System.out.println("Partie chargée avec succès");
        	
		} catch (IOException | ClassNotFoundException e){
			System.out.println("Problème de chargement de la partie");
		}
		return p;
    }

	/*
	 * Liste les sauvegardes de parties existantes dans le répertoire courant
	 * @return La liste des noms de fichiers de sauvegarde 
	*/
	public static List<String> listerSauvegardes() throws IOException{
		Path dossierCourant = Paths.get(".");
		return Files.list(dossierCourant).filter(Files::isRegularFile).map(path->path.getFileName().toString()).filter(nom ->nom.matches("Partie_\\d+\\.obj")).collect(Collectors.toList());
	}

	/*
	 * Supprime la sauvegarde d'une partie en utilisant son ID
	 * @param ID L'ID de la partie à supprimer
	 * @return true si la suppression a réussi, false sinon
	*/
	public static boolean supprimerPartie(int ID) {
    	String nomFichier = "Partie_" + ID + ".obj";
    	File f = new File(nomFichier);
	    if (f.exists()) {
    	    return f.delete(); // true si suppression OK
	    } else {
    	    return false; // fichier inexistant
    	}
	}
}