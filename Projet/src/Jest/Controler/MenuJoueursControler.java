package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;


public class MenuJoueursControler {
    private Menu menu;
	private ButtonGroup radiosBoutonsStrat;
    private ButtonGroup radiosBoutonsType;
    private JButton valider;
    private JTextField  zoneTexte;
	
    private int strategieSelectionne;
    private String typeJoueur;
    private String nom;

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

    private void intiZoneDeTexte(){
        zoneTexte.addActionListener(e -> {
            String contenu = zoneTexte.getText();
            System.out.println("DEBUG : CONTROLER : nom "+contenu);
            this.nom = contenu;
        });
    }

	private void initValider(){
		this.valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG : CONTROLER : valider ");
				menu.validerUnJoueur(typeJoueur, nom,  strategieSelectionne);
			}
		});
	}
}