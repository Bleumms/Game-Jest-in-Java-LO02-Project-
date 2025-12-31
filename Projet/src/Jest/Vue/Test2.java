package Jest.Vue;

import Jest.Controler.MenuControler;
import  Jest.Model.Menu;

import java.awt.*;
import java.util.*;
import javax.swing.*;

//import com.sun.java.swing.plaf.windows.resources.windows;


public class Test2 implements Observer {
	
	// Les propri�t�s de la classe
	private JButton creer;
    private JButton reprendre;
	private JLabel label;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

	private JFrame frame;

    private Menu menu;

	public void update(Observable instanceObservable, Object arg1){

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
		initialize();
        new MenuControler(this.menu, creer,reprendre);
        
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String message =  "     JEST     -     by Nina et Emeline     ";
		label = new JLabel(message);
		label.setBounds(100, 30, 200, 20);
		frame.getContentPane().add(label);

		
		label2 = new JLabel("Que souhaitez vous faire ? ");
		label2.setBounds(75, 100, 250,20);
		label3 = new JLabel("          1 -   Créer une nouvelle partie !");
		label3.setBounds(75, 120, 250,20);
		label4  = new JLabel("          2 -   Reprendre une ancienne partie ! ? ");
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
