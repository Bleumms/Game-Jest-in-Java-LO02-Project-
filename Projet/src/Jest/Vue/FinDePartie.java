package Jest.Vue;

import Jest.Controler.JoueurControler;
import Jest.Model.EtatJoueur;
import Jest.Model.Joueur;
import Jest.Model.Partie;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.Observable;
import java.util.List;

import javax.swing.*;

public class FinDePartie {
    
    private JFrame frame;
    private Partie partie;

    public FinDePartie(Partie p){
        this.partie=p;
        interfaceResultat();
    }

    public JFrame getFrame(){
        return this.frame;
    }

    private void relancerDebut(){
        frame.dispose();
        MenuPrincipal window = new MenuPrincipal();
        window.getFrame().setVisible(true);
    }

    public void interfaceResultat(){
        frame = new JFrame();
		frame.setBounds(150, 110, 400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

        List<Joueur> gagnants = this.partie.getGagnant();
        if (gagnants.size()==1){
            JLabel Texte = new JLabel("Le gagnant est "+gagnants.get(0).getNom()+" avec un score de "+gagnants.get(0).getScore()+" ! Bravo !");
            Texte.setBounds(50,20,300,20);
            frame.getContentPane().add(Texte);
        } else {
            JLabel Texte1 = new JLabel("Egalité !");
            Texte1.setBounds(150,30,100,20);
            frame.getContentPane().add(Texte1);
            int compte=0;
            String listeNoms = "";
            for (Joueur j : gagnants){
                listeNoms = listeNoms+j.getNom();
                if(compte<gagnants.size()-2){
                    listeNoms=listeNoms+", ";
                } else if(compte<gagnants.size()-1){
                    listeNoms=listeNoms+" et ";
                }
                compte++;
            }
            JLabel Texte2 = new JLabel("Les gagnant sont : "+listeNoms);
            Texte2.setBounds(100,60,200,20);
            frame.getContentPane().add(Texte2);
            JLabel Texte3 = new JLabel("avec un score de "+gagnants.get(0).getScore());
            Texte3.setBounds(100,90,200,20);
            frame.getContentPane().add(Texte3);
        }
        JButton rejouer = new JButton("Rejouer");
        rejouer.setBounds(150,130,100,30);
        frame.getContentPane().add(rejouer);
        rejouer.addActionListener(event -> this.relancerDebut());

        frame.setVisible(true);
    }
}
