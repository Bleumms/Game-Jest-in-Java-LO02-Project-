package Jest.Vue;

import Jest.Model.EtatJoueur;
import Jest.Model.Joueur;
import Jest.Controler.JoueurControler;
import Jest.Controler.PartieControler;
import Jest.Model.Carte;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.Observable;
import java.util.List;

import javax.swing.*;


public class FaireUneOffre implements Observer {
    
    private JFrame frame;
    private Joueur joueur; 
    private JButton btnCarte1;
    private JButton btnCarte2;

    public void update(Observable instanceObservable, Object arg1){
         if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.OffreFaite){
            System.out.println("DEBUG : UPDATE JOUEUR : joueur : "+((Joueur)instanceObservable).getNom()+" offre faite");
            frame.dispose();
        }
    }

    public FaireUneOffre(Joueur j){
        this.joueur=j;
        this.joueur.addObserver(this);
        try{
            this.interfaceFaireUneOffre();
        } catch (IOException e){
            e.printStackTrace();
        }
        new JoueurControler(this.joueur,this.btnCarte1, this.btnCarte2);
    }

    public JFrame getFrame(){
        return this.frame;
    }

    private void interfaceFaireUneOffre() throws IOException{
        //Creating the Frame
    	frame = new JFrame();
		frame.setBounds(150, 110, 400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("Quelle carte voulez vous rendre visible ? ");
		label.setBounds(30, 30, 350, 20);
		frame.getContentPane().add(label);

        List<Carte> cartes = this.joueur.getCartesDistribuees();
        //carte 1
        BufferedImage img1 = ImageIO.read(new File("Test_carte.png"));
        btnCarte1 = new JButton(new ImageIcon(img1));
        btnCarte1.setBounds(90, 75, 100, 150);
        btnCarte1.setLayout(new BorderLayout());

        btnCarte1.setBorderPainted(false);
        btnCarte1.setContentAreaFilled(false);
        btnCarte1.setFocusPainted(false);

        JLabel nom1 = new JLabel(cartes.get(0).getNom(), SwingConstants.CENTER);
        btnCarte1.add(nom1, BorderLayout.CENTER);

        frame.getContentPane().add(btnCarte1);

        //carte 2
        BufferedImage img2 = ImageIO.read(new File("Test_carte.png"));
        btnCarte2 = new JButton(new ImageIcon(img2));
        btnCarte2.setBounds(210, 75, 100, 150);
        btnCarte2.setLayout(new BorderLayout());

        btnCarte2.setBorderPainted(false);
        btnCarte2.setContentAreaFilled(false);
        btnCarte2.setFocusPainted(false);

        JLabel nom2 = new JLabel(cartes.get(1).getNom(), SwingConstants.CENTER);
        btnCarte2.add(nom2, BorderLayout.CENTER);

        frame.getContentPane().add(btnCarte2);
    }
}
