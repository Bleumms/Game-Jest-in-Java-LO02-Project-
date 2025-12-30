/*
 * Interface pour les classes acceptant un visiteur.
 * 
 * @author Nina et Emeline
 * @see Visitor
*/

package Jest;

public interface Visitable {
	
	/*
	 * Accepte un visiteur pour le traitement
	 * @param visiteur Le visiteur qui va traiter l'objet
	*/
	void accept (Visitor visiteur);
}
