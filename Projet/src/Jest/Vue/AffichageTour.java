/*
 * Classe AffichageTour : affiche une fenêtre indiquant le numéro du tour en cours
 * 
 * @author Nina et Emeline
*/

package Jest.Vue;

import javax.swing.*;


public class AffichageTour {
    private JFrame frame;

    private int numTour;

    /*
    * Constructeur AffichageTour : initialise l'affichage du numéro de tour
    * @param num : numéro du tour en cours
    */
    public AffichageTour(int num){
        this.numTour=num;
        affichage();
        attenteAvantFermeture();
    }

    /*
    * Retourne la frame de l'affichage du tour
    * @return JFrame : la frame de l'affichage du tour
    */
    public JFrame getFrame(){
        return this.frame;
    }

    /*
    *Attend 1.5 secondes avant de fermer la fenêtre
    */
    private void attenteAvantFermeture(){
        // Pause VISUELLE de 1 seconde et demie, pour qu'on voit le message puis ça se ferme
        System.out.println("DEBUG : petit message");
        Timer timer = new Timer(1500, e -> {
            fermer();
        });

        timer.setRepeats(false);
        timer.start(); 
    }

    /*
    * Ferme la fenêtre de l'affichage du tour
    */
    private void fermer(){
        frame.dispose();
    }

    /*
    * Initialise et affiche la fenêtre indiquant le numéro du tour
    */
    private void affichage(){
        //Creating the Frame
    	frame = new JFrame();
		frame.setBounds(200, 200, 180, 90);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String message =  "Tour numéro "+numTour;
		JLabel label = new JLabel(message);
		label.setBounds(10, 10, 140, 20);
		frame.getContentPane().add(label);	
        
		frame.setVisible(true);
    }
}
