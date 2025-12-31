package Jest.Vue;

import Jest.Controler.MenuCreerControler;
import Jest.Controler.MenuDebutControler;
import  Jest.Model.Menu;
import  Jest.Model.Jeu;

import java.awt.*;
import java.util.*;
import javax.swing.*;

//import com.sun.java.swing.plaf.windows.resources.windows;


public class Test2 implements Observer {
	
	private JFrame frame;

    private Menu menu;

	// Les element de la page interfaceLancementPremierMenu
	private JButton creer;
    private JButton reprendre;

	// Les element de la page interfaceCreerUnePartie
	private ButtonGroup radiosBoutonsJeux;
    private ButtonGroup boutonsNbJoueurs;
	private JButton valider;

	

	public void update(Observable instanceObservable, Object arg1){
		// cas 1 : après le premier menu l'utilisateur a choisi de creer une partie
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==1){
			frame.dispose();
			interfaceCreerUnePartie();
			new MenuCreerControler(this.menu, this.radiosBoutonsJeux, this.boutonsNbJoueurs, this.valider);
		}
	}

	public static void main(String[] args) {
		
		// Construction des objets du Mod�le
		// Cr�ation de l'interrupteur qui cr�e le commutateur qui cr�e les lampes
		
		
		// Cr�ation du thread qui cr�e l'interface graphique	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test2 window = new Test2();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test2() {

        this.menu = new Menu();
		this.menu.addObserver(this);

		interfaceLancementPremierMenu();
        new MenuDebutControler(this.menu, creer,reprendre);
        
	}

	private void interfaceCreerUnePartie(){
		//Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
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

		int compte = 0;
		this.radiosBoutonsJeux = new ButtonGroup();
		for (Jeu j : this.menu.getJeux()){
			JRadioButton jRadioButtonJeu = new JRadioButton();
			jRadioButtonJeu.setText((compte+1)+"   "+j.getNom());
			jRadioButtonJeu.setBounds(75, (120+20*compte), 250,20);
			frame.getContentPane().add(jRadioButtonJeu);
			this.radiosBoutonsJeux.add(jRadioButtonJeu);
			compte++;
		}

		// Joueurs

		//Combien de joueurs
		JLabel label3 = new JLabel("A combien de joueurs voulez vous jouer");
		compte = 120 + 20*compte +30;
		label3.setBounds(75, compte, 250,20);
		frame.getContentPane().add(label3);

		this.boutonsNbJoueurs = new ButtonGroup();

		JButton j3 = new JButton("3");
		compte = compte +20;
		j3.setBounds(125, compte, 50,15);
		frame.getContentPane().add(j3);
		this.boutonsNbJoueurs.add(j3);

        JButton j4 = new JButton("4");
		j4.setBounds(225, compte, 50,15);
		frame.getContentPane().add(j4);
		this.boutonsNbJoueurs.add(j4);

		// valider
		valider = new JButton("Valider");
		compte = compte +50;
		valider.setBounds(125, compte, 150,25);
		frame.getContentPane().add(valider);
		
        
		frame.setVisible(true);
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
}
