/*
* JoueurControler : Controleur pour les actions d'un joueur 
* @author Nina et Emeline
*/

package Jest.Controler;

import Jest.Model.Joueur;
import Jest.Model.Partie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

public class JoueurControler {

    private Joueur joueur;
    private Partie partie;
    private JButton Carte1;
    private JButton Carte2;

    /*    
    * Constructeur du controleur pour les actions du joueur lors d'une offre
    * @param p La partie en cours
    * @param j Le joueur contrôlé
    * @param Carte1 Le bouton de la première carte
    * @param Carte2 Le bouton de la deuxième carte
    */
    public JoueurControler(Partie p, Joueur j, JButton Carte1, JButton Carte2){
        this.joueur=j;
        this.partie=p;
        this.Carte1=Carte1;
        this.Carte2=Carte2;

		this.Carte1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                joueur.choisirCarteVisible(0);
                partie.ajouterCompteurOffreFaite();
			}
		});

        this.Carte2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                joueur.choisirCarteVisible(1);
                partie.ajouterCompteurOffreFaite();
			}
		});
	}
}
