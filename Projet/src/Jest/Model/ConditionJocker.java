/*
 * Condition : le joueur possédant le Jocker remporte la carte.
 * 
 * @author Nina et Emeline
*/

package Jest;

import java.io.Serializable;
import java.util.List;

public class ConditionJocker implements ConditionVictoire, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Constructeur de ConditionJocker
    */
    public ConditionJocker() {
    }

    /*
     * Redéfinit la méthode toString pour afficher la condition de victoire
     * @return Une chaîne de caractères représentant la condition de victoire
    */
    public String toString(){
        return "Le joueur qui a un Jocker gagne cette carte";
    }

    /*
     * Vérifie la condition de victoire en fonction de la possession du Jocker
     * Permet de savoir qui possède le Jocker parmi les joueurs.
     * @param joueurs La liste des joueurs en jeu
     * @return L'indice du joueur gagnant si la condition est remplie
    */
    public int VerificationVictoire(List<Joueur> joueurs){
        int indexJoueur=-1;
        for (int i=0; i<joueurs.size(); i++) {
            for (int j=0; j<joueurs.get(i).getCollection().size(); j++){
                Carte c =joueurs.get(i).getCollection().get(j);
                if (c instanceof Jocker){
                    indexJoueur = i;
                }
            }
        }
        return indexJoueur;
    }
}
