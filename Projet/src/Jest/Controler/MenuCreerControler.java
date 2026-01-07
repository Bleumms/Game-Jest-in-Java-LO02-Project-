package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;


public class MenuCreerControler {
    private Menu menu;
	private ButtonGroup radiosBoutonsJeux;
    private ButtonGroup boutonsNbJoueurs;
    private JButton valider;
	
	public MenuCreerControler (Menu m, ButtonGroup radiosBoutonsJeux, ButtonGroup boutonsNbJoueurs, JButton valider){
		this.menu=m;
		this.radiosBoutonsJeux=radiosBoutonsJeux;
        this.boutonsNbJoueurs=boutonsNbJoueurs;
        this.valider=valider;
		

		initRadioBoutonsJeux();
		initBoutonsNbJoueurs();
        initValider();
    }

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

	private void initValider(){
		this.valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menu.validerPageCreerPartie();
			}
		});
	}
}
