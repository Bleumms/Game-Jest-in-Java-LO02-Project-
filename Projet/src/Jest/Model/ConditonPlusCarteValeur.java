/*
 * Condition : le joueur ayant le plus de cartes d'une valeur donnée.
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class ConditonPlusCarteValeur implements ConditionVictoire, Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * num : le numéro de la carte dont on compte les occurrences
    */
    private int num;
    

    /*
     * Constructeur de ConditonPlusCarteValeur
     * @param num Le numéro de la carte dont on compte les occurrences
    */
    public ConditonPlusCarteValeur(int num) {
        this.num=num;
    }

    /*
     * Redéfinit la méthode toString pour afficher la condition de victoire
     * @return Une chaîne de caractères représentant la condition de victoire
    */
    public String toString(){
        return "Le joueur qui a le plus de fois une carte de numéro "+num;
    }

    /*
     * Vérifie la condition de victoire en fonction du nombre de cartes d'une valeur donnée
     * Permet de savoir qui a le plus de cartes d'une valeur donnée parmi les joueurs.
     * En cas d'égalité, un gagnant est choisi aléatoirement parmi les joueurs à égalité.
     * @param joueurs La liste des joueurs en jeu
     * @return L'indice du joueur gagnant si la condition est remplie
    */
    public int VerificationVictoire(List<Joueur> joueurs){
        List<Integer> nbCartesPossedes = new ArrayList<Integer>();
        for (int i=0; i<joueurs.size(); i++) {
            nbCartesPossedes.add(0);
        }
        for (int i=0; i<joueurs.size(); i++) {
            for (int j=0; j<joueurs.get(i).getCollection().size(); j++){
                Carte c =joueurs.get(i).getCollection().get(j);
                if (c instanceof CarteClassique){
                   CarteClassique cc = (CarteClassique) c;
                    if (cc.getNumero() == num){
                        nbCartesPossedes.set(i,nbCartesPossedes.get(i)+1);
                    }
                }
            }
        }
        int res=0;
        int max =nbCartesPossedes.get(0);
        List<Integer> indexMax = new ArrayList<Integer>();
        indexMax.add(0);
        for (int i=1; i<nbCartesPossedes.size(); i++) {
            int nb = nbCartesPossedes.get(i);
            if (nb==max){
                indexMax.add(i);
            }
            if (nb> max){
                max=nb;
                indexMax.clear();
                indexMax.add(i);
            }
        }
        if (indexMax.size()==1){
            res=indexMax.get(0);
        } else {
            double alea = Math.random()*indexMax.size();
			int aleaInt = Double.valueOf(alea).intValue();
			res=indexMax.get(aleaInt);
        }
        return res;
    }
}