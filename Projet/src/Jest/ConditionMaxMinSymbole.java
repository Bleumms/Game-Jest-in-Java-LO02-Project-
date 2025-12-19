package Jest;

import java.io.Serializable;
import java.util.List;

public class ConditionMaxMinSymbole implements ConditionVictoire, Serializable {

    private static final long serialVersionUID = 1L;
    
    private int maxMin; 
    private Symbole s;

    public ConditionMaxMinSymbole(int maxMin, Symbole s){
        this.maxMin = maxMin;
        this.s = s;
    }

    public String toString(){
        String message="Le joueur qui a une carte de symbole "+this.s;
        if (maxMin <0){
            message=message+" la plus petite";
        } else {
            message=message+" la plus grande";
        }
        return message;
    }

    public int VerificationVictoire(List<Joueur> joueurs){

        int extremum;
        int indexJoueur=0;

        if (this.maxMin<0){
            //on cherche le min
            extremum =10;
        } else {
            // on cherche le max
            extremum=-10;
        }

		for (int i=0; i<joueurs.size(); i++) {
            for (int j=0; j<joueurs.get(i).getCollection().size(); j++){
                Carte c =joueurs.get(i).getCollection().get(j);
                if (c instanceof CarteClassique){
                    CarteClassique cc = (CarteClassique) c;
                    if (this.maxMin<0){
                        //on cherche le min
                        if (cc.getNumero() < extremum && cc.getSymbole()==s){
                            extremum = cc.getNumero();
                            indexJoueur = i;
                        }
                    } else {
                        // on cherche le max
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