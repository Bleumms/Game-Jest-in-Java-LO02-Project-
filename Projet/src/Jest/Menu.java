package Jest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	private Partie partieEnCours;

	public Menu() {

	}
	
	public Partie getPartieEnCours() {
		return this.partieEnCours;
	}

	public Partie creerUnePartie() {
		Partie p = new Partie();
		// Choix du jeu
		this.partieEnCours = p;
		return p;
	}

	public Partie creerUnePartie(List<Jeu> jeux, List<Strategie> strats) {
		Partie p = new Partie();
		// ajouter un jeu
		Jeu jeu = choixDuJeu(jeux);
		p.choisirUnJeu(jeu);
		// ajouter des joueurs
		List<Joueur> joueurs = choixDesJoueurs(strats);
		for (int i=0; i<joueurs.size(); i++) {
		p.ajouterUnJoueur(joueurs.get(i));
		}
		this.partieEnCours = p;
		return p;
	}

	private Jeu choixDuJeu(List<Jeu> jeux) {
		// Verifier que jeux non vide !!
		boolean repValide = false;
		int numero = 0;
		while (repValide == false) {
			System.out.println("Voici la liste des jeux de cartes disponibles : ");
			for (int i = 0; i < jeux.size(); i++) {
				System.out.print(" " + i + ". ");
				System.out.println(jeux.get(i));
			}
			System.out.print("Choisissez le numéro du jeu qui vous intéresse : ");
			Scanner clavier = new Scanner(System.in);
			numero = clavier.nextInt();
			if (numero >= 0 && numero < jeux.size()) {
				repValide = true;
			}
			if (repValide==false) {
				System.out.println("Réponse invalide : " + numero);
			}
			clavier.close();

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
		clavier.close();
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
		clavier.close();
		}
		return reponse;
	}

}
