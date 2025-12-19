/*
 * Représente la carte Jocker, carte spéciale du jeu.
 * Le Jocker a des règles de valeur particulières selon les cartes possédées.
 * 
 * @author Nina et Emeline
 * @see Carte
 */

package Jest;

public class Jocker extends Carte {

	/* 
	 * Constructeur par défaut.
	 * Initialise le nom de la carte à "Jocker".
	*/
	public Jocker() {
		super("Jocker");
	}
	
	/* 
	 * Retourne une représentation textuelle de la carte Jocker.
	 * @return Une chaîne de caractères représentant la carte Jocker
	*/
	@Override
	public String toString() {
		return "Jocker";
	}
}
