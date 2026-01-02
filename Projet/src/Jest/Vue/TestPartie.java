package Jest.Vue;

import Jest.Controler.MenuJoueursControler;
import  Jest.Model.Partie;
import  Jest.Model.Carte;
import Jest.Model.Joueur;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Observable;

import javax.swing.*;

public class TestPartie implements Observer {

    private JFrame frame;

    private Partie partie;

    public void update(Observable instanceObservable, Object arg1){
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public TestPartie(Partie p){
        this.partie = p;
		this.partie.addObserver(this);
        try {
		interfaceTableDeJeu();
        } catch (IOException e ){
            e.printStackTrace();
        }
        //new UnControler(this.partie);
        
    }

    private void interfaceTableDeJeu() throws IOException {
        //Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 60, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("Partie en cours");
		label.setBounds(30, 30, 100, 20);
		frame.getContentPane().add(label);

        //Trophés

        JPanel panelTrophe = new JPanel();
        int taille = 105*this.partie.getTrophes().size();
        panelTrophe.setBounds(250, 30, taille, 155);
        // pour chaques trophés :
        for (Carte c : this.partie.getTrophes()){
            BufferedImage img = ImageIO.read(new File("Test_carte.png"));
            JLabel pic = new JLabel(new ImageIcon(img));
            pic.setLayout(new BorderLayout());
            JLabel nom = new JLabel(c.getNom(), SwingConstants.CENTER);
            pic.add(nom, BorderLayout.CENTER);
            panelTrophe.add(pic);
        }
        frame.getContentPane().add(panelTrophe);

        // Pioche
        JPanel panelPioche = new JPanel();
        panelPioche.setBounds(525, 30, 105, 155);
        BufferedImage img = ImageIO.read(new File("Test_carte_dos.png"));
        JLabel pic = new JLabel(new ImageIcon(img));
        panelPioche.add(pic);
        frame.getContentPane().add(panelPioche);


        //Un icone par joueur
        
        JPanel panelJoueurs = new JPanel();
        panelJoueurs.setBounds(0, 475, 700, 85);
        panelJoueurs.setLayout(null);
        int position=50;
        // pour chaques trophés :
        for (int i=0; i<this.partie.getParticipants().size(); i++){
            Joueur j  = this.partie.getParticipants().get(i);

            JPanel panelUnJoueur = new JPanel();
            panelUnJoueur.setBounds(position+(180*i), 0, 55, 80);

            BufferedImage imgJ = ImageIO.read(new File("Test_joueur.png"));
            JLabel picJ = new JLabel(new ImageIcon(imgJ));
            picJ.setLayout(new BorderLayout());

            JLabel nom = new JLabel(j.getNom());
            panelUnJoueur.add(picJ);
            panelUnJoueur.add(nom);
            panelJoueurs.add(panelUnJoueur);
        }
        frame.getContentPane().add(panelJoueurs);
		frame.setVisible(true);
	}

}
