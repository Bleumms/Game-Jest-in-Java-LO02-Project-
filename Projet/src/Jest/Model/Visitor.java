/*
 * Interface du pattern Visitor pour le calcul des scores.
 * 
 * @author Nina et Emeline
 * @see Visitable
*/

package Jest.Model;

public interface Visitor {

	/*
	 * Visite un joueur pour calculer son score
	 * @param player Le joueur à visiter
	*/
	void visit (Joueur player);

	/*
	 * Définit la référence des règles à utiliser pour le calcul des scores
	 * @param r La référence des règles
	*/
	void setReference(Reference r);
}
