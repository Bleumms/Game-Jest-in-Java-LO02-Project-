/*
* MenuPrincipal : gère l'affichage du menu graphique
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


public class MenuPrincipal implements Observer {
	
	private JFrame frame;

    private Menu menu;

	/*
	* Les élement de la page de lancement du tout premier menu
	*/ 
	private JButton creer;
    private JButton reprendre;
	
	/*
	* Met à jour l'affichage en fonction de l'état du menu (soit on crée une partie, soit on en reprend une)
	* @param instanceObservable : l'objet observable (le menu)
	* @param arg1 : argument supplémentaire (non utilisé)
	*/
	public void update(Observable instanceObservable, Object arg1){
		// après le premier menu l'utilisateur a choisi de creer une partie
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.CreerPartie){
			frame.dispose();
			MenuCreerPartie window2 = new MenuCreerPartie(this.menu);
			window2.getFrame().setVisible(true);
		}

		//  après le premier menu l'utilisateur a choisi de reprendre une par
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.ReprendrePartie){
			frame.dispose();
			ReprendrePartie window = new ReprendrePartie(menu);
			window.getFrame().setVisible(true);
		}
	}

	/*
	* Constructeur MenuPrincipal : initialise l'interface du menu principal
	*/
	public MenuPrincipal() {

        this.menu = new Menu();
		this.menu.addObserver(this);

		interfaceLancementPremierMenu();
        new MenuDebutControler(this.menu, creer,reprendre);
        
	}

	/*
	* Retourne la frame du menu principal
	* @return JFrame : la frame du menu principal
	*/
	public JFrame getFrame(){
		return this.frame;
	}

	/*
	* Initialise et affiche la fenêtre de lancement du tout premier menu
	*/
	private void interfaceLancementPremierMenu() {

		//Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String message =  "     JEST     -     by Nina et Emeline     ";
		JLabel label = new JLabel(message);
		label.setBounds(100, 30, 200, 20);
		frame.getContentPane().add(label);

		
		JLabel label2 = new JLabel("Que souhaitez vous faire ? ");
		label2.setBounds(75, 100, 250,20);
		JLabel label3 = new JLabel("          1 -   Créer une nouvelle partie !");
		label3.setBounds(75, 120, 250,20);
		JLabel label4  = new JLabel("          2 -   Reprendre une ancienne partie ! ? ");
		label4.setBounds(75, 140, 250,20);

		creer = new JButton("1");
		creer.setBounds(75, 220, 100,25);
        reprendre = new JButton("2");
		reprendre.setBounds(225, 220, 100,25);

		

        frame.getContentPane().add(label2);
		frame.getContentPane().add(label3);
		frame.getContentPane().add(label4);

		frame.getContentPane().add(creer);
        frame.getContentPane().add(reprendre); 
		
        
		frame.setVisible(true);
	}

	/*
	* Lancement de l'application
	*/
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
