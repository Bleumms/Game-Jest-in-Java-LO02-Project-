package Jest.Vue;

import javax.swing.*;

public class AffichageTour {
    private JFrame frame;

    private int numTour;

    public AffichageTour(int num){
        this.numTour=num;
        affichage();
        attenteAvantFermeture();
    }

    public JFrame getFrame(){
        return this.frame;
    }

    private void attenteAvantFermeture(){
        // Pause VISUELLE de 1 seconde et demie, pour qu'on voit le message puis ça se ferme
        System.out.println("DEBUG : petit message");
        Timer timer = new Timer(1500, e -> {
            fermer();
        });

        timer.setRepeats(false);
        timer.start(); 
    }

    private void fermer(){
        frame.dispose();
    }

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
