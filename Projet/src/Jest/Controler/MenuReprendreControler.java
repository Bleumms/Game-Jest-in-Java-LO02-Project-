/*
* MenuReprendreControler : Controleur pour le menu de reprise de partie
* @author Emeline et Nina
*/

package Jest.Controler;

import  Jest.Model.Menu;
import Jest.Model.Partie;
import Jest.Vue.TestPartie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;


public class MenuReprendreControler {
    private JFrame frame;
	private List<JButton> boutonsJeu;

    /*
    * Les fichiers de sauvegarde disponibles
    */
    private List<String> fichiers;
	
    /*    
    * Constructeur du controleur pour le menu de reprise de partie
    * @param f La frame du menu
    * @param boutons Les boutons de reprise de partie
    * @param fichiers Les fichiers de sauvegarde disponibles
    */
	public MenuReprendreControler (JFrame f, List<JButton> boutons, List<String> fichiers){
		this.frame = f;
		this.boutonsJeu=boutons;
        this.fichiers=fichiers;
		
        for (JButton btn : boutonsJeu){
            // L'appuie sur le bouton creer
            btn.addActionListener(e -> {
                AbstractButton source = (AbstractButton) e.getSource();
                int index = Integer.parseInt(source.getActionCommand());
                Partie p = Partie.charger(fichiers.get(index));
                this.frame.dispose();
                TestPartie window = new TestPartie(p);
                window.getFrame().setVisible(true);
            });

        }
	}
}
