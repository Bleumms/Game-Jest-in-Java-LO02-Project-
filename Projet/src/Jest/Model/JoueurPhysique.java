/*
 * Représente un joueur humain interagissant via la console.
 * Hérite de Joueur et implémente les méthodes de choix via Scanner.
 * 
 * @author Nina et Emeline
 * @see Joueur
 */

package Jest.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JoueurPhysique extends Joueur{

	/* 
	 * Constructeur avec paramètre.
	 * Initialise le nom du joueur physique.
	 * @param n Le nom du joueur
	*/
	public JoueurPhysique(String n, int i) {
		super(n,i);
	}

	public void attendreUneOffre(){
		this.etat=EtatJoueur.AttenteOffre;
		this.setChanged();
		this.notifyObservers("en attente d'une offre");
		System.out.println("DEBUG : joueur : "+getNom()+" offre en attente");
	}

	public void attendreUnChoix(List<Joueur> joueurs){
		this.etat=EtatJoueur.AttenteChoix;
		this.setChanged();
		this.notifyObservers("en attente d'un choix");
		System.out.println("DEBUG : joueur : "+getNom()+" choix en attente");
	}

	/* 
	 * Permet au joueur physique de faire une offre en choisissant une carte à rendre visible.
	 * Utilise la console pour récupérer les informations.
	 * Place en position 0 la carte visible et en 1 celle qui ne l'es pas.	 
	 * Composé de tests pour la validité du choix.
	*/
	public void faireUneOffre() {
		System.out.println("\n   >>>   " + this.getNom() + "   :          (faites Enter pour jouer)");
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		@SuppressWarnings("unused")
		String rep = clavier.nextLine(); //juste pour attendre que je joueur soit prêt !
		System.out.print("Voici vos deux cartes reçues :   ");
		System.out.println("carte 1= " + this.getCartesDistribuees().get(0) + "  ;  carte 2= "
				+ this.getCartesDistribuees().get(1));
		List<String> valsAcceptees = new ArrayList<String>();
		valsAcceptees.add("2");
		valsAcceptees.add("1");
		String reponse = ReponseUtilisateur("Quelle carte souhaitez vous rendre visible aux autres joueurs ? (1/2) : ",
				valsAcceptees);
		this.choisirCarteVisible(Integer.parseInt(reponse) - 1);
	}

	/* 
	 * Méthode pour obtenir une réponse valide de l'utilisateur via la console.
	 * Continue de poser la question jusqu'à obtenir une réponse valide.
	 * Composé de test sur la validité de la réponse.
	 * @param question La question à poser à l'utilisateur
	 * @param valsAcceptees La liste des réponses acceptées (vide si toutes les réponses sont acceptées)
	 * @return La réponse valide de l'utilisateur
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

	/* 
	 * Méthode pour choisir une carte parmi les cartes des autres joueurs
	 * Que les cartes soient visibles ou cachées.
	 * Obtient le choix via la console.
	 * Demande d'abord le joueur ciblé, puis la carte (visible ou cachée).
	 * Composé de tests pour la validité les choix.
	 * Si le joueur est le seul avec une carte, le choix de l'utilisateur est automatique. 
	 * @param joueurs La liste des joueurs en compétition
	 * @return La liste contenant l'indice du joueur ciblé et l'indice de la carte choisie.
	*/
	public List<Integer> choisirUneCarte(List<Joueur> joueurs) {
		List<Integer> res = new ArrayList<Integer>();
		if (joueurs.size() == 1 && joueurs.contains(this)) {
			res.add(0);
			res.add(this.ChoisiUneDeSesCartes());
		} else {
			List<String> valsAcceptees = new ArrayList<String>();

			System.out.println("\n   >>>   " + this.getNom() + "   :          (faites Enter pour jouer)");
			@SuppressWarnings("resource")
			Scanner clavier = new Scanner(System.in);
			@SuppressWarnings("unused")
			String rep = clavier.nextLine(); //juste pour attendre que je joueur soit prêt !
		
			System.out.println("Vous pouvez choisir une carte parmis celle disponibles :   ");
			int compte = 1;
			int indexLuiMeme = -1;
			for (int i = 0; i < joueurs.size(); i++) {
				Joueur j = joueurs.get(i);
				if (j.equals(this) == false) {
					System.out.println(compte + ". Cartes de " + j.getNom() + " :\n carte visible= "
							+ j.getCarteVisible() + "  ;  carte cachée= ???");
					valsAcceptees.add(Integer.toString(compte));
					compte++;
				} else {
					indexLuiMeme = i;
				}
			}

			String numeroJoueur = ReponseUtilisateur("Chez quel joueur voulez vous prendre une carte ? (1/2/...) : ",
					valsAcceptees);
			if (indexLuiMeme == -1 || Integer.parseInt(numeroJoueur) <= indexLuiMeme) {
				res.add(Integer.parseInt(numeroJoueur) - 1);
			} else {
				res.add(Integer.parseInt(numeroJoueur));
			}

			valsAcceptees.clear();
			valsAcceptees.add("V");
			valsAcceptees.add("C");
			String visOuCache = ReponseUtilisateur(
					"Quelle carte vous intéresse, celle visible ou celle cachée ? (V/C) : ", valsAcceptees);
			if (visOuCache == "V") {
				res.add(0);
			} else {
				res.add(1);
			}
		}
		return res;
	}

	/* 
	 * Méthode pour choisir une de ses propres cartes à offrir
	 * Obtient le choix via la console.
	 * Composé de tests pour la validité du choix.
	 * @return L'indice de la carte choisie
	*/
	public int ChoisiUneDeSesCartes() {
		List<String> valsAcceptees = new ArrayList<String>();
		valsAcceptees.add("V");
		valsAcceptees.add("C");
		System.out.println(
				"Vous êtes le seul a qui il reste 2 cartes, la quelle voulez vous ajouter à votre collection ? ");
		System.out.println(
				" V. Carte visible= " + this.getCarteVisible() + "  ;   C. Carte cachée= " + this.getCarteCachee());
		String numeroCarte = ReponseUtilisateur("(V/C) : ", valsAcceptees);
		if (numeroCarte == "V") {
			return 0;
		} else {
			return 1;
		}
	}
}
