package Jest.Controler;
import Jest.Model.Joueur;
import Jest.Model.Partie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;

public class ChoisiCarteControler {
    private Partie partie;
    private Joueur j;
    private List<Joueur> jDispos;
    private List<ButtonGroup> toutesLesOffres;
    private boolean actif;


    public ChoisiCarteControler(Partie partie, Joueur j, List<Joueur> jDispos, List<ButtonGroup> toutesLesOffres){
        this.partie=partie;
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
                        AbstractButton source = (AbstractButton) e.getSource();
                        String regex = "[;]";
                        String[] arrayContenuBouton = source.getActionCommand().split(regex);
                        int idJoueur = Integer.parseInt(arrayContenuBouton[0]);
                        int indexCarte = Integer.parseInt(arrayContenuBouton[1]);
                        this.j.setChoix(idJoueur,indexCarte);
                        j.choixFait();
                        actif=false;
                    });
                }
            }
        }
	}
}
