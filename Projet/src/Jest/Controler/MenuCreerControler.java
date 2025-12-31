package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		// L'appuie sur le bouton creer
		this.valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Bouton validé activé");
				// vérifié que coché
			}
		});
	}
}