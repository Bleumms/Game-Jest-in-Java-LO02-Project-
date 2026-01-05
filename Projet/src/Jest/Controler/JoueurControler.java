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
    private int compteurOffreFaite;
    private JButton Carte1;
    private JButton Carte2;

    public JoueurControler(Partie p, Joueur j, JButton Carte1, JButton Carte2){
        this.joueur=j;
        this.partie=p;
        this.compteurOffreFaite=compteurOffreFaite;
        this.Carte1=Carte1;
        this.Carte2=Carte2;

		this.Carte1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("DEBUG : Carte 1 choisie");
                joueur.choisirCarteVisible(0);
                partie.ajouterCompteurOffreFaite();
			}
		});

        this.Carte2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("DEBUG : Carte 2 choisie");
                joueur.choisirCarteVisible(1);
                partie.ajouterCompteurOffreFaite();
			}
		});
	}
}
