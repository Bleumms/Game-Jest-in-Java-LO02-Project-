/*
* MenuCreerControler : Controleur pour la création d'une partie depuis le menu
* @author Emeline et Nina
*/

package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;


@SuppressWarnings("unused")
public class MenuCreerControler {
    private Menu menu;
	private ButtonGroup radiosBoutonsJeux;
    private ButtonGroup boutonsNbJoueurs;
    private JButton valider;
	

    /*
    * Constructeur du controleur pour la création d'une partie depuis le menu
    * @param m Le menu
    * @param radiosBoutonsJeux Le groupe de boutons radio pour le choix du jeu
    * @param boutonsNbJoueurs Le groupe de boutons radio pour le choix du nombre de joueurs
    * @param valider Le bouton de validation de la création de la partie
    */
	public MenuCreerControler (Menu m, ButtonGroup radiosBoutonsJeux, ButtonGroup boutonsNbJoueurs, JButton valider){
		this.menu=m;
		this.radiosBoutonsJeux=radiosBoutonsJeux;
        this.boutonsNbJoueurs=boutonsNbJoueurs;
        this.valider=valider;
		

		initRadioBoutonsJeux();
		initBoutonsNbJoueurs();
        initValider();
    }

    /*
    * Initialise les boutons radio pour le choix du jeu
    */
    private void initRadioBoutonsJeux() {
        Enumeration<AbstractButton> buttons = this.radiosBoutonsJeux.getElements();

        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();

            btn.addActionListener(e -> {
                AbstractButton source = (AbstractButton) e.getSource();
                int index = Integer.parseInt(source.getActionCommand());
                menu.setJeuSelectionne(index);
            });
        }
    }

    /*
    * Initialise les boutons radio pour le choix du nombre de joueurs
    */
    private void initBoutonsNbJoueurs() {
        Enumeration<AbstractButton> buttons = this.boutonsNbJoueurs.getElements();

        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();

            btn.addActionListener(e -> {
                AbstractButton source = (AbstractButton) e.getSource();
                int index = Integer.parseInt(source.getActionCommand());
                menu.setNbJoueursSelectionne(index);
            });
        }
    }

    /*
    * Initialise le bouton de validation de la création de la partie
    */
	private void initValider(){
		this.valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menu.validerPageCreerPartie();
			}
		});
	}
}
