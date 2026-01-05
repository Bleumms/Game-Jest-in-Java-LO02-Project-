package Jest.Controler;
import Jest.Model.Joueur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;

public class ChoisiCarteControler {
    private Joueur j;
    private List<Joueur> jDispos;
    private List<ButtonGroup> toutesLesOffres;
    private boolean actif;


    public ChoisiCarteControler(Joueur j, List<Joueur> jDispos, List<ButtonGroup> toutesLesOffres){
        this.j=j;
        this.jDispos=jDispos;
        this.toutesLesOffres=toutesLesOffres;
        this.actif=true;

        initBoutonsCarte();
    }

    private void initBoutonsCarte(){
        if (actif){
            for (Joueur jD : this.jDispos){
                Enumeration<AbstractButton> buttons = this.toutesLesOffres.get(jD.getID()).getElements();

                while (buttons.hasMoreElements()) {
                    AbstractButton btn = buttons.nextElement();

                    btn.addActionListener(e -> {
                        if (!actif) return;
                        System.out.println("DEBUG : Bouton carte choisie");
                        //menu.setNbJoueursSelectionne(index);
                        actif=false;
                    });
                }
            }
        }
	}
}
