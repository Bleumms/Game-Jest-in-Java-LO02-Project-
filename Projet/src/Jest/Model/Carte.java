/*
 * Classe représentant une carte du jeu Jest.
 * Une carte possède un nom et une condition de victoire qui détermine
 * quel joueur remporte cette carte en fin de partie.
 * La classe offre des méthodes pour définir le nom de la carte,
 * ajouter une condition de victoire, et déterminer le joueur gagnant
 * en fonction de cette condition.
 * 
 * 
 * @author Nina et Emeline
 * @see ConditionVictoire
 * @see CarteClassique
 * @see Jocker
 */

package Jest;

import java.io.Serializable;
import java.util.List;

public class Carte implements Serializable{

	/*
	 * Référence de sérialisation pour la classe Carte.
	*/
    private static final long serialVersionUID = 1L;

	private String nom;
	private ConditionVictoire condition;


	/* Constructeur avec paramètre.
	 * Initialise le nom de la carte avec la valeur donnée.
	 * @param n Le nom de la carte
	*/
	public Carte(String n) {
		nom=n;
	}

	/* Constructeur par défaut.
	 * Initialise le nom de la carte à une chaîne vide.
	*/
	public Carte() {
		nom="";
	}

	/* Retourne le nom de la carte.
	 * @return Le nom de la carte
	*/
	public void setNom(String n) {
		this.nom=n;
	}

	/* Ajoute une condition de victoire à la carte.
	 * @param cv La condition de victoire à ajouter
	*/
	public void ajouterConditionVictoire(ConditionVictoire cv) {
		this.condition = cv;
	}

	/* 
	 * Retourne une représentation textuelle de la carte.
	 * @return Une chaîne décrivant la carte
	*/
	@Override
	public String toString() {
		return "Carte "+this.nom;
	}

	/* Détermine quel joueur remporte cette carte en fonction de la condition de victoire.
	 * @param joueurs La liste des joueurs en compétition
	 * @return L'indice du joueur gagnant dans la liste
	 */
	public int JoueurGagnantCarte(List<Joueur> joueurs) {
		return this.condition.VerificationVictoire(joueurs);
	}

	/* 
	 * Renvoi le nom de la carte.
	 * @return Le nom de la carte
	*/
	public String getNom(){
		return this.nom;
	}

	/* 
	 * Renvoi la condition de victoire de la carte.
	 * @return La condition de victoire
	*/
	public ConditionVictoire getConditionVictoire(){
		return this.condition;
	}
	
}

