package Jest;

import java.util.List;

public class ConditionJocker implements ConditionVictoire {
    
    public ConditionJocker() {

    }

    public String toString(){
        return "Le joueur qui a un Jocker gagne cette carte";
    }

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
