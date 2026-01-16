/*
* ChoisireCarteControler : Controleur pour le choix de carte lors d'une offre
* @author Nina et Emeline
*/

package Jest.Controler;
import Jest.Model.Joueur;
import Jest.Model.Partie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("unused")
public class ChoisiCarteControler {

    /*
    *La partie en cours
    */
    private Partie partie;

    /*
    * Le joueur qui doit faire un choix
    */
    private Joueur j;

    /*
    * Les joueurs disposant d'offres
    */
    private List<Joueur> jDispos;

    /*
    * Les boutons correspondant aux offres des joueurs
    */
    private List<ButtonGroup> toutesLesOffres;

    /*
    * Indique si le controleur est actif ou non
    */
    private boolean actif;


    /*    
    * Constructeur du controleur pour le choix de carte lors d'une offre
    * @param partie La partie en cours
    * @param j Le joueur qui doit faire un choix
    * @param jDispos Les joueurs disposant d'offres
    * @param toutesLesOffres Les boutons correspondant aux offres des joueurs
    */
    public ChoisiCarteControler(Partie partie, Joueur j, List<Joueur> jDispos, List<ButtonGroup> toutesLesOffres){
        this.partie=partie;
        this.j=j;
        this.jDispos=jDispos;
        this.toutesLesOffres=toutesLesOffres;
        this.actif=true;

        initBoutonsCarte();
    }

    /*
    * Initialise les boutons pour le choix de carte
    */
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
