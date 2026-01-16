/*
 * Gère le menu principal et la création des parties.
 * Permet de créer ou reprendre une partie, configurer les joueurs.
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class Menu extends Observable {
	private EtatMenu etat;
	private int jeuSelectionne;
	private int nbJoueursSelectionne;
	private Partie partieEnCours;
	private List<Jeu> jeuxExistants;
	private List<Strategie> strategiesDisponibles;

	/*
	 * Constructeur de Menu
	 * Initialise les éléments de base, démarre une partie, et la récupération d'une partie sauvegardée
	 * Gère le menu principal
	*/
	public Menu() {
		this.jeuxExistants = new ArrayList<Jeu>();
		this.strategiesDisponibles = new ArrayList<Strategie>();
		this.creerLesElementsDeBase();
		this.etat=EtatMenu.Initialisation;
		this.jeuSelectionne=-1;
		this.nbJoueursSelectionne=0;
	}

	/*
	 * Permet de définir le jeu sélectionné.
	 * @param i L'indice du jeu sélectionné
	*/
	public void setJeuSelectionne(int i){
		this.jeuSelectionne=i;
	}

	/*
	 * Permet de définir le nombre de joueurs sélectionné.
	 * @param i Le nombre de joueurs sélectionné
	*/
	public void setNbJoueursSelectionne(int i){
		this.nbJoueursSelectionne=i;
	}

	/*
	* S'assure de la validité de la page de création de partie en fonction des choix faits
	* Met à jour l'état du menu et la partie en cours
	*
	*/
	public void validerPageCreerPartie(){
		System.out.println("DEBUG : jeu selectionné :  "+jeuSelectionne+" ; nbJoueur selectionné : "+nbJoueursSelectionne);
		String message="";
		if (this.jeuSelectionne>=0 && this.jeuSelectionne<this.jeuxExistants.size() && (this.nbJoueursSelectionne==3  || this.nbJoueursSelectionne==4)){
			this.etat=EtatMenu.SelectionnerPremierJoueur;
			message="selectionner les joueurs";
			Partie p = new Partie();
			p.choisirUnJeu(this.jeuxExistants.get(this.jeuSelectionne));
			this.partieEnCours=p;
			
		} else {
			this.etat=EtatMenu.CreerPartieAvecErreur;
			message="erreur settings en creant une partie";
		}
		System.out.println("DEBUG : partie :  "+this.partieEnCours);
		this.setChanged();
		this.notifyObservers(message);
	}

	/* 
	 * S'assure de la validité de la création d'un joueur et l'ajoute à la partie en cours.
	 * Met à jour l'état du menu en fonction du nombre de joueurs ajoutés.
	 * @param typeJoueur Le type de joueur ("Virtu" ou "Reel")
	 * @param nom Le nom du joueur
	 * @param strategieSelectionne L'indice de la stratégie sélectionnée (pour les joueurs virtuels)
	*/
	public void validerUnJoueur(String typeJoueur, String nom, int strategieSelectionne){
		String message="";
		if (nom=="" || (typeJoueur=="Virtu" && strategieSelectionne==-1)){
			message="erreur settings en creant le joueur";
			this.etat = EtatMenu.SelectionnerJoueursAvecErreur;
		} else {
			Joueur j;
			if (typeJoueur=="Virtu"){
				message="joueur virtuel créé";
				j = new JoueurVirtuel(nom, partieEnCours.getParticipants().size(), this.strategiesDisponibles.get(strategieSelectionne));
			} else {
				message="joueur réel créé";
				j = new JoueurPhysique(nom, partieEnCours.getParticipants().size());
			}
			this.partieEnCours.ajouterUnJoueur(j);
			System.out.println("DEBUG : partie :  "+this.partieEnCours);
			if (this.partieEnCours.getParticipants().size()==nbJoueursSelectionne){
				this.etat=EtatMenu.LancerPartie;
				this.partieEnCours.initialiserLaPartie();
			} else {
				if (this.etat==EtatMenu.SelectionnerJoueur){
					this.etat=EtatMenu.SelectionnerJoueurEncore;
				} else {
					this.etat=EtatMenu.SelectionnerJoueur;
				}
				
			}
		}
		this.setChanged();
		this.notifyObservers(message);
	}
	
	/* 
	 * Permet de lancer la création d'une nouvelle partie.
	*/
	public void creerPartie(){
		this.etat=EtatMenu.CreerPartie;
		this.setChanged();
		this.notifyObservers("creer une partie");
	}

	/* 
	 * Permet de lancer la reprise d'une partie sauvegardée.
	 * Met à jour l'état du menu.
	*/
	public void reprendrePartie(){
		this.etat=EtatMenu.ReprendrePartie;
		this.setChanged();
		this.notifyObservers("reprendre une partie");
	}

	/* 
	 * Retourne l'état actuel du menu.
	 * @return l'état actuel du menu
	*/
	public EtatMenu getEtat(){
		return this.etat;
	}

	/* 
	 * Retourne la liste des jeux disponibles.
	 * @return la liste des jeux disponibles
	*/
	public List<Jeu> getJeux(){
		return this.jeuxExistants;
	}

	/* 
	 * Retourne la liste des stratégies disponibles.
	 * @return la liste des stratégies disponibles
	*/
	public List<Strategie> getStrats(){
		return this.strategiesDisponibles;
	}


	

	/*
	 * Affiche le menu principal et demande une action à l'utilisateur
	 * @return Le numéro de l'action choisie
	*/
	public int affichageMenu(){
		System.out.println(" -------------------------------------------------");
		System.out.println("|        JEST     -     by Nina et Emeline        |");
		System.out.println(" -------------------------------------------------");
		System.out.println("\nQue souhaitez vous faire ?");
		System.out.println("      1 -   Créer une nouvelle partie ! ");
		System.out.println("      2 -   Reprendre une ancienne partie ! ");
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		boolean repValide = false;
		int numero = 0;
		while (repValide == false) {
			System.out.print(">>> (1/2) :   ");
			numero = clavier.nextInt();
			if (numero == 1 || numero==2) {
				repValide = true;
			}
			if (repValide==false) {
				System.out.println("Réponse invalide : " + numero);
			}
		}
		return numero;
	}

	/*
	 * Restaure une partie sauvegardée
	 * @return La partie restaurée
	*/
	public Partie restorerUnePartie(){
		
		List<String> fichiers=null;
		try{
			fichiers = Partie.listerSauvegardes();
		} catch (IOException e){
			e.printStackTrace();
		}
		Partie p=null;
		if (fichiers!=null && fichiers.size()>0){
			System.out.println("\n Quelle partie voulez vous reprendre : ");
			List<Integer> valsAcceptable= new ArrayList<Integer>();
			for (int i=0; i<fichiers.size();i++){
				System.out.println("      "+(i+1)+"   -   "+fichiers.get(i));
				@SuppressWarnings("unused")
				int x = Integer.parseInt(fichiers.get(i).replace("Partie_", "").replace(".obj", ""));
				valsAcceptable.add(i+1);
			}
			int rep = demanderPartieARestorer(valsAcceptable);
			p = Partie.charger(fichiers.get(rep-1));
		} else {
			System.out.println("Aucune partie sauvegardée, créez une partie : \n");
			p =this.creerUnePartie();
			p.initialiserLaPartie();
		}
		return p;
	}

	/*
	 * Demande à l'utilisateur quel numéro de partie restaurer
	 * @param valsAcceptable La liste des numéros de parties disponibles
	 * @return Le numéro de la partie choisie
	*/
	private int demanderPartieARestorer(List<Integer> valsAcceptable){
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		boolean repValide = false;
		int numero = 0;
		while (repValide == false) {
			System.out.print(">>> (1/2/...) :   ");
			numero = clavier.nextInt();
			if (numero>0 && numero<=valsAcceptable.size()) {
				repValide = true;
			} else if (repValide==false) {
				System.out.println("Réponse invalide, in n'y a pas de " + numero);
			}
		}
		return numero;
	}
	
	/*
	 * Retourne la partie en cours
	 * @return La partie en cours
	*/
	public Partie getPartieEnCours() {
		return this.partieEnCours;
	}

	/*
	 * Crée une nouvelle partie
	 * @return La partie créée
	*/
	public Partie creerUnePartie() {
		Partie p = new Partie();
		// ajouter un jeu
		Jeu jeu = choixDuJeu(this.jeuxExistants);
		p.choisirUnJeu(jeu);
		// ajouter des joueurs
		List<Joueur> joueurs = choixDesJoueurs(this.strategiesDisponibles);
		for (int i=0; i<joueurs.size(); i++) {
		p.ajouterUnJoueur(joueurs.get(i));
		}
		return p;
	}

	/*
	 * Demande à l'utilisateur quel jeu choisir parmi une liste
	 * @param jeux La liste des jeux disponibles
	 * @return Le jeu choisi
	*/
	private Jeu choixDuJeu(List<Jeu> jeux) {
		// Verifier que jeux non vide !!
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		boolean repValide = false;
		int numero = 0;
		while (repValide == false) {
			System.out.println("\n\nVoici la liste des jeux de cartes disponibles : ");
			for (int i = 0; i < jeux.size(); i++) {
				System.out.print(" " + i + ". ");
				System.out.println(jeux.get(i));
				System.out.println();
			}
			System.out.print("Choisissez le numéro du jeu qui vous intéresse : ");
			numero = clavier.nextInt();
			if (numero >= 0 && numero < jeux.size()) {
				repValide = true;
			}
			if (repValide==false) {
				System.out.println("Réponse invalide : " + numero);
			}
		}
		return jeux.get(numero);
	}

	/*
	 * Demande à l'utilisateur de choisir les joueurs parmi des joueurs réels et virtuels
	 * Récupérer les informations nécessaires pour chaque joueur
	 * @param strats La liste des stratégies disponibles pour les joueurs virtuels
	 * @return La liste des joueurs choisis
	*/
	private List<Joueur> choixDesJoueurs(List<Strategie> strats) {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		List<String> valsAcceptees = new ArrayList<String>();
		valsAcceptees.add("3");
		valsAcceptees.add("4");
		int nb=4;
		String nbJoueurs = ReponseUtilisateur("\nA combien de joueurs voulez vous jouer ? (3/4) : ", valsAcceptees);
		if (nbJoueurs=="3"){
			nb=3;
		} 
		List<Joueur> joueurs = new ArrayList<Joueur>();
		for (int i=1; i<=nb; i++){
			System.out.println("Joueur " + i + " : ");
			// Joueur reel ou virtu
			valsAcceptees.clear();
			valsAcceptees.add("R");
			valsAcceptees.add("V");
			String reelOuVirtu = ReponseUtilisateur("Joueur réel ou virtuel ? (R/V) : ", valsAcceptees);

			// Son nom
			valsAcceptees.clear();
			String nom = ReponseUtilisateur("Entrez un nom : ", valsAcceptees);

			// Si reel
			if (reelOuVirtu.equals("R")) {
				joueurs.add(new JoueurPhysique(nom, partieEnCours.getParticipants().size()));
				// Si virtu
			} else {
				// choix de la strat
				boolean repValide = false;
				int numero = 0;
				while (repValide == false) {
					System.out.println("Voici la liste des startégies disponibles : ");
					for (int j = 0; j < strats.size(); j++) {
						System.out.print(" " + j + ". ");
						System.out.println(strats.get(j));
					}
					System.out.print("Choisissez le numéro de la stratégie qui vous intéresse : ");
					numero = clavier.nextInt();
					if (numero >= 0 && numero < strats.size()) {
						repValide = true;
					}
				}
				Strategie strat = strats.get(numero);
				joueurs.add(new JoueurVirtuel(nom, partieEnCours.getParticipants().size(),  strat));
			}
		}
		return joueurs;
	}

	/*
	 * Demande à l'utilisateur une réponse à la question donnée en argument, avec des valeurs acceptées
	 * @param question La question à poser
	 * @param valsAcceptees La liste des réponses acceptées (vide si toutes les réponses sont acceptées)
	 * @return La réponse choisie
	*/
	private String ReponseUtilisateur(String question, List<String> valsAcceptees) {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		String reponse = null;
		boolean repValide = false;
		while (repValide == false) {
			System.out.print(question);
			String rep = clavier.nextLine();
			if (valsAcceptees.size() > 0) {
				for (int i = 0; i < valsAcceptees.size(); i++) {
					if (rep.equals(valsAcceptees.get(i))) {
						repValide = true;
						reponse = valsAcceptees.get(i);
					}
				}
			} else {
				repValide = true;
				reponse = rep;
			}
			if (repValide == false) {
				System.out.println("Réponse invalide : " + rep);
			}
		}
		return reponse;
	}













	// CREER LES ELEMENTS DE BASE

	/*
	 * Crée les éléments de base du menu : jeux et stratégies disponibles
	*/
	public void creerLesElementsDeBase(){
		// Le jeu n°1:
		Jeu jeu = creerUnJeu("TOUT");
		this.jeuxExistants.add(jeu);
		// Le jeu n°2 :
		Jeu jeu2 = creerUnJeu("MINI");
		this.jeuxExistants.add(jeu2);

		this.strategiesDisponibles.add(new StrategieRandom());
		this.strategiesDisponibles.add(new StrategieIntelligent());
	}

	/*
	 * Crée la référence des règles du jeu
	 * @return La référence créée
	*/
	public static Reference creerReference(){
		Reference r = new Reference();
		Regle r1 = new RegleCoeur();
		Regle r2 = new RegleCarreau();
		Regle r3 = new RegleAs();
		Regle r4 = new RegleDoubleNoir();
		Regle r5 = new RegleJocker();
		r.ajouterRegle(r1);
		r.ajouterRegle(r2);
		r.ajouterRegle(r3);
		r.ajouterRegle(r4);
		r.ajouterRegle(r5);
		return r;
	}

	/*
	 * Crée une carte en fonction de son symbole et de sa valeur
	 * @param symb Le symbole de la carte (0=PIQUE, 1=TREFLE, 2=CARREAU, 3=COEUR)
	 * @param num La valeur de la carte (1 à 4 pour les cartes classiques, 0 pour le jocker)
	 * @return La carte créée
	*/
	public static Carte creerCarte(int symb, int num){
		Carte c;
		ConditionVictoire cv=null;
		if (num==0){
			c=new Jocker();
			cv = new ConditionMaxScore(true);
			c.ajouterConditionVictoire(cv);
		} else {
			Symbole s= null;
			switch (symb) {
				case 0:
					s=Symbole.PIQUE;
					switch (num) {
						case 1:
							cv= new ConditionMaxMinSymbole(1,Symbole.TREFLE);
							break;

						case 2:
							cv= new ConditonPlusCarteValeur(3);
							break;

						case 3:
							cv= new ConditonPlusCarteValeur(2);
							break;

						case 4:
							cv= new ConditionMaxMinSymbole(-1,Symbole.TREFLE);
							break;

						default:
							break;
					}
					break;

				case 1:
					s=Symbole.TREFLE;
					switch (num) {
						case 1:
							cv= new ConditionMaxMinSymbole(1,Symbole.PIQUE);
							break;

						case 2:
							cv= new ConditionMaxMinSymbole(-1,Symbole.COEUR);
							break;

						case 3:
							cv= new ConditionMaxMinSymbole(1,Symbole.COEUR);
							break;

						case 4:
							cv= new ConditionMaxMinSymbole(-1,Symbole.PIQUE);
							break;

						default:
							break;
					}
					break;

				case 2:
					s=Symbole.CARREAU;
					switch (num) {
						case 1:
							cv= new ConditonPlusCarteValeur(4);
							break;

						case 2:
							cv= new ConditionMaxMinSymbole(1,Symbole.CARREAU);
							break;

						case 3:
							cv= new ConditionMaxMinSymbole(-1,Symbole.CARREAU);
							break;

						case 4:
							cv= new ConditionMaxScore(false);
							break;

						default:
							break;
					}
					break;

				case 3:
					s=Symbole.COEUR;
					cv= new ConditionJocker();
					break;
			
				default:
					break;
			}
			
			c = new CarteClassique(num, s);
			c.ajouterConditionVictoire(cv);
		}
		return c;
	}

	/*
	 * Crée toutes les cartes classiques du jeu
	 * @return La liste de toutes les cartes classiques
	*/
	public static List<Carte> creerToutesCartes(){
		List<Carte> toutesCartes = new ArrayList<Carte>();
		for (int symb=0; symb<4; symb++){
			for (int num=1; num<5 ; num++){
				toutesCartes.add(creerCarte(symb,num));
			}
		}
		toutesCartes.add(creerCarte(0,0));
		return toutesCartes;
	}

	/*
	 * Crée un jeu de cartes mini (8 cartes + 1 jocker)
	 * @return Le jeu de cartes mini créé
	*/
	public static List<Carte> creerMiniCartes(){
		List<Carte> toutesCartes = new ArrayList<Carte>();
		for (int symb=0; symb<4; symb++){
			for (int num=1; num<3 ; num++){
				toutesCartes.add(creerCarte(symb,num));
			}
		}
		toutesCartes.add(creerCarte(0,0));
		return toutesCartes;
	}

	/*
	 * Crée un jeu de cartes en fonction du type demandé
	 * @param type Le type de jeu ("MINI" ou autre pour le jeu complet)
	 * @return Le jeu de cartes créé
	*/
	public static Jeu creerUnJeu(String type){
		Jeu jeu = new Jeu("Jeu de carte "+type);

		List<Carte> Cartes=null;
		if (type=="MINI"){
			Cartes = creerMiniCartes();
		} else {
			Cartes = creerToutesCartes();
		}
		jeu.ajouterDesCartes(Cartes);

		Reference r = creerReference();
		jeu.ajouterReference(r);

		return jeu;
	}
}

