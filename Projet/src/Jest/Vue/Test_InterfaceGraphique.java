package Jest.Vue;

import  Jest.Model.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;

//import com.sun.java.swing.plaf.windows.resources.windows;


public class Test_InterfaceGraphique implements Observer {
	
	// Les propri�t�s de la classe
	private JButton boutonInterrupteur;
	private JLabel labelCommutateur;
	private JCheckBox checkBoxLampe1;
	private JCheckBox checkBoxLampe2;
	private JCheckBox checkBoxLampe3;
	private JCheckBox checkBoxLampe4;
	private JCheckBox checkBoxLampe5;

	private JFrame frame;


	public void update(Observable instanceObservable, Object arg1){

	}

	public static void main(String[] args) {
		
		// Construction des objets du Mod�le
		// Cr�ation de l'interrupteur qui cr�e le commutateur qui cr�e les lampes
		
		
		// Cr�ation du thread qui cr�e l'interface graphique	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test_InterfaceGraphique window = new Test_InterfaceGraphique();
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
	public Test_InterfaceGraphique() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		/*
		//Creating the Frame
    	frame = new JFrame("test Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 400);

		String message =  "     JEST     -     by Nina et Emeline     ";
		JLabel label = new JLabel(message);
		label.setBounds(10, 10, 100, 10);
		frame.getContentPane().add(label);

		
		JLabel label2 = new JLabel("Que souhaitez vous faire ? ");
		label2.setBounds(100, 300, 1, 1);
		JLabel label3 = new JLabel("          1 -   Créer une nouvelle partie !");
		label3.setBounds(100, 300, 1,1);
		JLabel label4  = new JLabel("          2 -   Reprendre une ancienne partie ! ? ");
		label4.setBounds(100, 300, 1,1);

		JButton creer = new JButton("Créer une partie");
		creer.setBounds(100, 300, 1,1);
        JButton reprendre = new JButton("Reprendre une partie");
		reprendre.setBounds(100, 300, 1,1);

		

        frame.getContentPane().add(label2);
		frame.getContentPane().add(label3);
		frame.getContentPane().add(label4);

		frame.getContentPane().add(creer);
        frame.getContentPane().add(reprendre); 
		
        frame.setVisible(true);
		*/




		/* 
        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
		*/
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		boutonInterrupteur = new JButton("Interrupteur");
		boutonInterrupteur.setBounds(317, 10, 107, 23);
		frame.getContentPane().add(boutonInterrupteur);

		labelCommutateur = new JLabel("Commutateur");
		labelCommutateur.setBounds(147, 105, 172, 18);
		frame.getContentPane().add(labelCommutateur);
		
		checkBoxLampe1 = new JCheckBox("Lampe 1");
		checkBoxLampe1.setBounds(45, 23, 97, 23);
		frame.getContentPane().add(checkBoxLampe1);
		
		checkBoxLampe2 = new JCheckBox("Lampe 2");
		checkBoxLampe2.setBounds(45, 61, 97, 23);
		frame.getContentPane().add(checkBoxLampe2);
		
		checkBoxLampe3 = new JCheckBox("Lampe 3");
		checkBoxLampe3.setBounds(45, 103, 97, 23);
		frame.getContentPane().add(checkBoxLampe3);
		
		checkBoxLampe4 = new JCheckBox("Lampe 4");
		checkBoxLampe4.setBounds(45, 145, 97, 23);
		frame.getContentPane().add(checkBoxLampe4);
		
		checkBoxLampe5 = new JCheckBox("Lampe 5");
		checkBoxLampe5.setBounds(45, 191, 97, 23);
		frame.getContentPane().add(checkBoxLampe5);
		
	}
}
