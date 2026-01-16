/*
* MenuDebutControler : Controleur pour le menu de début de partie
* @author Emeline et Nina
*/

package Jest.Controler;

import  Jest.Model.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MenuDebutControler {
    private Menu menu;
	private JButton boutonCreer;
    private JButton boutonReprendre;
	
	/*	
	* Constructeur du controleur pour le menu de début de partie (création ou reprise)
	* @param m Le menu
	* @param boutonC Le bouton de création de partie
	* @param boutonR Le bouton de reprise de partie
	*/
	public MenuDebutControler (Menu m, JButton boutonC, JButton boutonR){
		this.menu=m;
		this.boutonCreer=boutonC;
        this.boutonReprendre=boutonR;
		
		// L'appuie sur le bouton creer
		this.boutonCreer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menu.creerPartie();
			}
		});

        // L'appuie sur le bouton reprendre
		this.boutonReprendre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menu.reprendrePartie();
			}
		});
	}
}
