/*
 * Classe représentant une carte du jeu Jest.
 * Une carte possède un nom et une condition de victoire qui détermine
 * quel joueur remporte cette carte en fin de partie.
 * 
 * @author Nina et Emeline
 * @see ConditionVictoire
 * @see CarteClassique
 * @see Jocker
 */

package Jest;

import java.util.List;

public class Carte {
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
	 * @return Une chaîne de caractères représentant la carte
	*/
	@Override
	public String toString() {
		return "Carte [nom=" + nom + "]";
	}
	
	/* Détermine quel joueur remporte cette carte en fonction de la condition de victoire.
	 * @param joueurs La liste des joueurs en compétition
	 * @return L'indice du joueur gagnant dans la liste
	 */
	public int JoueurGagnantCarte(List<Joueur> joueurs) {
		return this.condition.VerificationVictoire(joueurs);
	}
	
}
