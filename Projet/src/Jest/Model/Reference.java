/*
 * Contient les règles du jeu qui modifient les valeurs des cartes.
 * 
 * @author Nina et Emeline
 * @see Regle
*/

package Jest.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reference implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Liste des règles du jeu
	*/
	public List<Regle> regles;

	/*
	 * Constructeur de Reference
	*/
	public Reference() {
		this.regles = new ArrayList<Regle>();
	}
	
	// ajouter vérification qu'une regle n'est pas 2 fois dans la liste
	/*
	 * Ajoute une règle à la liste des règles
	 * @param r La règle à ajouter
	*/
	public void ajouterRegle(Regle r) {
		this.regles.add(r);
	}
	
	/*
	 * Retourne la liste des règles
	 * @return La liste des règles
	*/
	public List<Regle> getRegles(){
		return regles;
	}
	
	/*
	 * Redéfinit la méthode toString pour afficher les règles
	 * @return Une chaîne de caractères représentant les règles
	*/
	@Override
	public String toString() {
		return regles + "";
	}
	
}


