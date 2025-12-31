package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MenuControler {
    private Menu menu;
	private JButton boutonCreer;
    private JButton boutonReprendre;
	
	public MenuControler (Menu m, JButton boutonC, JButton boutonR){
		this.menu=m;
		this.boutonCreer=boutonC;
        this.boutonReprendre=boutonR;
		
		// L'appuie sur le bouton creer
		this.boutonCreer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.print("\nControleur de l'interrupteur: activation de "+menu);
				menu.creerPartie();
			}
		});

        // L'appuie sur le bouton reprendre
		this.boutonReprendre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.print("\nControleur de l'interrupteur: activation de "+menu);
				menu.reprendrePartie();
			}
		});
	}
}
