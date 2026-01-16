/*
 * Condition : le joueur avec la carte la plus haute/basse d'un symbole.
 * 
 * @author Nina et Emeline
*/
package Jest.Model;

import java.io.Serializable;
import java.util.List;

public class ConditionMaxMinSymbole implements ConditionVictoire, Serializable {

    private static final long serialVersionUID = 1L;
    
    /*
     * maxMin : si positif, on cherche le maximum, si négatif, on cherche le minimum
    */
    private int maxMin; 
    private Symbole s;

    /*
     * Constructeur de ConditionMaxMinSymbole
     * @param maxMin Indique si on cherche le maximum (positif) ou le minimum (négatif)
     * @param s Le symbole des cartes à considérer
    */
    public ConditionMaxMinSymbole(int maxMin, Symbole s){
        this.maxMin = maxMin;
        this.s = s;
    }

    /*
     * Redéfinit la méthode toString pour afficher la condition de victoire
     * Affiche si on cherche la carte la plus haute ou la plus basse d'un symbole
     * @return Une chaîne de caractères représentant la condition de victoire
    */
    @Override
    public String toString(){
        String message="Le joueur qui a une carte de symbole "+this.s;
        if (maxMin <0){
            message=message+" la plus petite";
        } else {
            message=message+" la plus grande";
        }
        return message;
    }

    /*
     * Vérifie la condition de victoire en fonction de la carte la plus haute/basse d'un symbole
     * Permet de savoir qui a la carte la plus haute/basse d'un symbole parmi les joueurs.
     * @param joueurs La liste des joueurs en jeu
     * @return L'indice du joueur gagnant si la condition est remplie
    */
    public int VerificationVictoire(List<Joueur> joueurs){

        int extremum;
        int indexJoueur=0;

        if (this.maxMin<0){
            // On cherche le min
            extremum =10;
        } else {
            // On cherche le max
            extremum=-10;
        }

		for (int i=0; i<joueurs.size(); i++) {
            for (int j=0; j<joueurs.get(i).getCollection().size(); j++){
                Carte c =joueurs.get(i).getCollection().get(j);
                if (c instanceof CarteClassique){
                    CarteClassique cc = (CarteClassique) c;
                    if (this.maxMin<0){
                        // On cherche le min
                        if (cc.getNumero() < extremum && cc.getSymbole()==s){
                            extremum = cc.getNumero();
                            indexJoueur = i;
                        }
                    } else {
                        // On cherche le max
                        if (cc.getNumero() > extremum && cc.getSymbole()==s){
                            extremum = cc.getNumero();
                            indexJoueur = i;
                        }
                    }
                }
            }
		}

        return indexJoueur;
    }
}
