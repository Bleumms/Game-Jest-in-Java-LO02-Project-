/* 
 * MenuCreerPartie: interface pour créer une nouvelle partie
 * @author Nina et Emeline
 */

package Jest.Vue;

import Jest.Controler.MenuCreerControler;
import Jest.Controler.MenuDebutControler;
import Jest.Model.Menu;
import Jest.Model.EtatMenu;
import Jest.Model.Jeu;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MenuCreerPartie implements Observer {
	
	private JFrame frame;

    private Menu menu;

	/*
	* Les element de la page interfaceCreerUnePartie
	*/ 
	private ButtonGroup radiosBoutonsJeux;
    private ButtonGroup boutonsNbJoueurs;
	private JButton valider;
    private int compte;

	
	/*
	* Retourne la frame de l'interface de création de partie
	* @return JFrame : la frame de l'interface de création de partie
	*/
    public JFrame getFrame(){
        return this.frame;
    }

	/*
	* Met à jour l'affichage en fonction de l'état du menu
	* @param instanceObservable : l'objet observable (le menu)
	* @param arg1 : argument supplémentaire (non utilisé)
	*/
	public void update(Observable instanceObservable, Object arg1){
		// La validation n'a pas fonctionné
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.CreerPartieAvecErreur){
            System.out.println("DEBUG : Mal saisi!");
            this.ajoutMessageErreur();
		}

		// Validation réussie
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.SelectionnerPremierJoueur){
			frame.dispose();
			MenuAjoutJoueurs window2 = new MenuAjoutJoueurs(this.menu);
			window2.getFrame().setVisible(true);
		}
	}

	/*
	* Constructeur MenuCreerPartie : initialise l'interface de création de partie
	* @param m : le menu principal
	*/
	public MenuCreerPartie(Menu m) {

        this.menu = m;
		this.menu.addObserver(this);

		interfaceCreerUnePartie();
		new MenuCreerControler(this.menu, this.radiosBoutonsJeux, this.boutonsNbJoueurs, this.valider);
        
	}

	/*
	* Ajoute du message d'erreur à l'interface
	*/
    private void ajoutMessageErreur(){
        System.out.println("DEBUG : Mal saisi "+compte);
		JLabel label5 = new JLabel("Attention, les informations sont mal remplis !");
		label5.setBounds(70, compte-20, 300,20);
		Container content = frame.getContentPane();
        content.add(label5);
        content.setComponentZOrder(label5, 0); // devant
        content.repaint();
    }

	/*
	* Initialise et affiche la fenêtre de création de partie
	* On demande de choisir un jeu de cartes et le nombre de joueurs
	*/
	private void interfaceCreerUnePartie(){
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String message =  "     Créer une partie     ";
		JLabel label = new JLabel(message);
		label.setBounds(100, 30, 200, 20);
		frame.getContentPane().add(label);

		// le jeu
		JLabel label2 = new JLabel("Quel jeu de carte vous intéresse ?");
		label2.setBounds(75, 100, 250,20);
		frame.getContentPane().add(label2);

		compte = 0;
		this.radiosBoutonsJeux = new ButtonGroup();
		for (int i=0; i< this.menu.getJeux().size(); i++){
            Jeu j = this.menu.getJeux().get(i);

			JRadioButton jRadioButtonJeu = new JRadioButton((compte+1)+"   "+j.getNom());
            jRadioButtonJeu.setActionCommand(String.valueOf(i));
			jRadioButtonJeu.setBounds(75, (120+20*compte), 250,20);
			frame.getContentPane().add(jRadioButtonJeu);
			this.radiosBoutonsJeux.add(jRadioButtonJeu);
			compte++;
		}

		//Combien de joueurs
		JLabel label3 = new JLabel("A combien de joueurs voulez vous jouer ?");
		compte = 120 + 20*compte +30;
		label3.setBounds(75, compte, 250,20);
		frame.getContentPane().add(label3);

		this.boutonsNbJoueurs = new ButtonGroup();

		JButton j3 = new JButton("3");
		compte = compte +30;
		j3.setBounds(125, compte, 50,15);
        j3.setActionCommand(String.valueOf(3));
		frame.getContentPane().add(j3);
		this.boutonsNbJoueurs.add(j3);

        JButton j4 = new JButton("4");
		j4.setBounds(225, compte, 50,15);
        j4.setActionCommand(String.valueOf(4));
		frame.getContentPane().add(j4);
		this.boutonsNbJoueurs.add(j4);

		//Valider le choix
		valider = new JButton("Valider");
		compte = compte +70;
		valider.setBounds(125, compte, 150,25);
		frame.getContentPane().add(valider);
		
        
		frame.setVisible(true);
	}

}
