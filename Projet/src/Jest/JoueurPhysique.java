package Jest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JoueurPhysique extends Joueur{

	public JoueurPhysique(String n) {
		super(n);
	}

	// place en position 0 celle visible et en 1 celle qui ne l'es pas
	public void faireUneOffre() {
		System.out.println("   " + this.getNom() + " : ");
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

	public List<Integer> choisirUneCarte(List<Joueur> joueurs) {
		List<Integer> res = new ArrayList<Integer>();
		if (joueurs.size() == 1 && joueurs.contains(this)) {
			res.add(0); // de toute façon il y a qu'un joueur dans la liste donc c'est dans celui la qu'on prend la carte
			res.add(this.ChoisiUneDeSesCartes());
		} else {
			List<String> valsAcceptees = new ArrayList<String>();

			System.out.println("   " + this.getNom() + " : ");
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
