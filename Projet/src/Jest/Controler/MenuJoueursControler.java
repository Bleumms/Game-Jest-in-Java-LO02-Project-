/*
* MenuJoueursControler : Controleur pour la configuration des joueurs dans le menu
* @author Emeline et Nina
*/

package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;


public class MenuJoueursControler {
    private Menu menu;

    /*
    * Les boutons radio pour le choix de la stratégie
    */
	private ButtonGroup radiosBoutonsStrat;

    /*
    * Les boutons radio pour le choix du type de joueur
    */
    private ButtonGroup radiosBoutonsType;
    private JButton valider;
    private JTextField  zoneTexte;
	
    private int strategieSelectionne;
    private String typeJoueur;
    private String nom;

    /*
    * Constructeur du controleur pour la configuration des joueurs dans le menu
    * @param m Le menu
    * @param zoneTexte La zone de texte pour le nom du joueur
    * @param valider Le bouton de validation de la configuration du joueur
    * @param radiosBoutonsStrat Le groupe de boutons radio pour le choix de la stratégie
    * @param radiosBoutonsType Le groupe de boutons radio pour le choix du type de joueur
    */
	public MenuJoueursControler (Menu m, JTextField  zoneTexte, JButton valider, ButtonGroup radiosBoutonsStrat, ButtonGroup radiosBoutonsType){
		this.menu=m;
		this.radiosBoutonsStrat=radiosBoutonsStrat;
        this.radiosBoutonsType=radiosBoutonsType;
        this.valider=valider;
        this.zoneTexte=zoneTexte;

        this.nom="";
        this.strategieSelectionne=-1;
        typeJoueur="Reel";

		initRadioBoutonsStrat();
		initRadioBoutonsType();
        intiZoneDeTexte();
        initValider();
    }


    /*
    * Initialise les boutons radio pour le choix de la stratégie
    */
    private void initRadioBoutonsStrat() {
        Enumeration<AbstractButton> buttons = this.radiosBoutonsStrat.getElements();

        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();

            btn.addActionListener(e -> {
                AbstractButton source = (AbstractButton) e.getSource();
                int index = Integer.parseInt(source.getActionCommand());
                System.out.println("DEBUG : CONTROLER : strat "+index);
                this.strategieSelectionne = index;
            });
        }
    }

    /*
    * Initialise les boutons radio pour le choix du type de joueur
    */
    private void initRadioBoutonsType() {
        Enumeration<AbstractButton> buttons = this.radiosBoutonsType.getElements();

        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();

            btn.addActionListener(e -> {
                AbstractButton source = (AbstractButton) e.getSource();
                String type = source.getActionCommand();
                System.out.println("DEBUG : CONTROLER : type "+type);
                this.typeJoueur = type;
            });
        }
    }

    /*
    * Initialise la zone de texte pour le nom du joueur
    */
    private void intiZoneDeTexte(){
        zoneTexte.addActionListener(e -> {
            String contenu = zoneTexte.getText();
            System.out.println("DEBUG : CONTROLER : nom "+contenu);
            this.nom = contenu;
        });
    }

    /*
    * Initialise le bouton de validation de la configuration du joueur
    */
	private void initValider(){
		this.valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG : CONTROLER : valider ");
				menu.validerUnJoueur(typeJoueur, nom,  strategieSelectionne);
			}
		});
	}
}