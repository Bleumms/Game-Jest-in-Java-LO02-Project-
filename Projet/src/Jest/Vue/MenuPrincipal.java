package Jest.Vue;

import Jest.Controler.MenuCreerControler;
import Jest.Controler.MenuDebutControler;
import  Jest.Model.Menu;
import Jest.Model.EtatMenu;
import  Jest.Model.Jeu;

import java.awt.*;
import java.util.*;
import javax.swing.*;

//import com.sun.java.swing.plaf.windows.resources.windows;


public class MenuPrincipal implements Observer {
	
	private JFrame frame;

    private Menu menu;

	// Les element de la page interfaceLancementPremierMenu
	private JButton creer;
    private JButton reprendre;
	

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
			// A FAIRE
		}
	}

	public MenuPrincipal() {

        this.menu = new Menu();
		this.menu.addObserver(this);

		interfaceLancementPremierMenu();
        new MenuDebutControler(this.menu, creer,reprendre);
        
	}

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

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MenuPrincipal window = new MenuPrincipal();
					Menu m = new Menu();
					m.setJeuSelectionne(0);
					//m.setNbJoueursSelectionne(4);
					m.setNbJoueursSelectionne(3);
					m.validerPageCreerPartie();
					m.validerUnJoueur("Reel", "Nina",0);
					m.validerUnJoueur("Reel", "Em",0);
					m.validerUnJoueur("Virtu", "Robo",1);
					//m.validerUnJoueur("Virtu", "Robo2",0);
					System.out.println("\n SETTINGS :\n"+m.getPartieEnCours()+"\n\n");
					TestPartie window = new TestPartie(m.getPartieEnCours());
					window.getFrame().setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
