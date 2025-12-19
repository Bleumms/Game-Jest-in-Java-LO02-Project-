package Jest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	private Partie partieEnCours;
	private List<Jeu> jeuxExistants;
	private List<Strategie> strategiesDisponibles;

	public Menu() {
		this.jeuxExistants = new ArrayList<Jeu>();
		this.strategiesDisponibles = new ArrayList<Strategie>();
		this.creerLesElementsDeBase();
		int actionDemande = this.affichageMenu();
		if (actionDemande==1){
			Partie p = this.creerUnePartie();
			this.partieEnCours = p;
		} else {
			Partie p =this.restorerUnePartie();
			this.partieEnCours = p;
		}
	}

	public int affichageMenu(){
		System.out.println(" -------------------------------------------------");
		System.out.println("|        JEST     -     by Nina et Emeline        |");
		System.out.println(" -------------------------------------------------");
		System.out.println("\nQue souhaitez vous faire ?");
		System.out.println("      1 -   Créer une nouvelle partie ! ");
		System.out.println("      2 -   Reprendre une ancienne partie ! ");
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

	public Partie restorerUnePartie(){
		System.out.println("\n Quelle partie voulez vous reprendre : ");
		List<String> fichiers=null;
		try{
			fichiers = Partie.listerSauvegardes();
		} catch (IOException e){
			e.printStackTrace();
		}
		List<Integer> valsAcceptable= new ArrayList<Integer>();
		for (int i=0; i<fichiers.size();i++){
			System.out.println("      "+(i+1)+"   -   "+fichiers.get(i));
			int x = Integer.parseInt(fichiers.get(i).replace("Partie_", "").replace(".obj", ""));
			valsAcceptable.add(i+1);
		}
		int rep = demanderPartieARestorer(valsAcceptable);
		Partie p = Partie.charger(fichiers.get(rep));
		return p;
	}

	private int demanderPartieARestorer(List<Integer> valsAcceptable){
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
	
	public Partie getPartieEnCours() {
		return this.partieEnCours;
	}

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

	private Jeu choixDuJeu(List<Jeu> jeux) {
		// Verifier que jeux non vide !!
		Scanner clavier = new Scanner(System.in);
		boolean repValide = false;
		int numero = 0;
		while (repValide == false) {
			System.out.println("Voici la liste des jeux de cartes disponibles : ");
			for (int i = 0; i < jeux.size(); i++) {
				System.out.print(" " + i + ". ");
				System.out.println(jeux.get(i));
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

	private List<Joueur> choixDesJoueurs(List<Strategie> strats) {
		Scanner clavier = new Scanner(System.in);
		List<String> valsAcceptees = new ArrayList<String>();
		boolean ajouter = false;
		int compte = 1;
		List<Joueur> joueurs = new ArrayList<Joueur>();
		while (compte < 4 || (compte < 5 && ajouter == true)) {
			System.out.println("Joueur " + compte + " : ");
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
				joueurs.add(new JoueurPhysique(nom));
				// Si virtu
			} else {
				// choix de la strat
				boolean repValide = false;
				int numero = 0;
				while (repValide == false) {
					System.out.println("Voici la liste des startégies disponibles : ");
					for (int i = 0; i < strats.size(); i++) {
						System.out.print(" " + i + ". ");
						System.out.println(strats.get(i));
					}
					System.out.print("Choisissez le numéro de la stratégie qui vous intéresse : ");
					numero = clavier.nextInt();
					if (numero >= 0 && numero < strats.size()) {
						repValide = true;
					}
				}
				Strategie strat = strats.get(numero);
				joueurs.add(new JoueurVirtuel(nom, strat));
			}
			compte++;
			if (compte == 4) {
				valsAcceptees.clear();
				valsAcceptees.add("Y");
				valsAcceptees.add("N");
				String Joueur4 = ReponseUtilisateur("Voulez vous ajouter un 4eme joueur ? (Y/N) : ", valsAcceptees);
				if (Joueur4.equals("Y")) {
					ajouter = true;
				}
			}
		}
		return joueurs;
	}

	private String ReponseUtilisateur(String question, List<String> valsAcceptees) {
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


	public static Jeu creerUnJeu(String type){
		Jeu jeu = new Jeu();

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
