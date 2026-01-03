package Jest.Vue;

import Jest.Controler.PartieControler;
import  Jest.Model.Partie;
import  Jest.Model.Carte;
import Jest.Model.EtatJoueur;
import Jest.Model.Joueur;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;

public class TestPartie implements Observer {

    private JFrame frame;

    private Partie partie;

    private JButton jouer;
    private ButtonGroup boutonsFaireMonOffre;
    private List<Integer> possedeUnBouton;
    private int compteurOffreFaite;

    public void update(Observable instanceObservable, Object arg1){
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.AttenteOffre){
            System.out.println("DEBUG : UPDATE : joueur : "+((Joueur)instanceObservable).getNom()+" offre en attente");
            this.ajouterBoutonOffre((Joueur)instanceObservable);
        }
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.OffreFaite){
            compteurOffreFaite++;
            System.out.println("DEBUG : UPDATE : joueur : "+((Joueur)instanceObservable).getNom()+" offre faite");
            if (this.possedeUnBouton.contains(((Joueur)instanceObservable).getID())){
                this.supprimerBoutonJoueur(((Joueur)instanceObservable).getID());
            }
            try{
                this.presenterOffre((Joueur)instanceObservable);
            } catch (IOException e ){
                e.printStackTrace();
            }
            if (compteurOffreFaite==this.partie.getParticipants().size()){
                
            }
        }
        this.supprimerBoutonJouer();
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public TestPartie(Partie p){
        this.boutonsFaireMonOffre=new ButtonGroup();
        this.possedeUnBouton = new ArrayList<Integer>();
        this.partie = p;
		this.partie.addObserver(this);
        this.compteurOffreFaite=0;
        for (Joueur j : this.partie.getParticipants()){
            j.addObserver(this);
        }
        try {
		interfaceTableDeJeu();
        } catch (IOException e ){
            e.printStackTrace();
        }
        new PartieControler(this.partie,this.jouer);
        
    }

    private void supprimerBoutonJouer(){
        this.jouer.setVisible(false);
    }

    private void supprimerBoutonJoueur(int idJoueur){
        int n_ieme = this.possedeUnBouton.indexOf(idJoueur);
        Enumeration<AbstractButton> buttons = boutonsFaireMonOffre.getElements();
        int compte=0;
        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();
            if (compte==n_ieme){
                btn.setVisible(false);
            }
            compte++;
        }
    }

    private void ajouterBoutonOffre(Joueur j){
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);

        // bouton faire mon offre
        JButton faireMonOffre = new JButton("Faire mon offre");
		faireMonOffre.setBounds(positionCentre-75, 425, 150,25);
        faireMonOffre.addActionListener(event -> ajouterPageOffre(j));
		frame.getContentPane().add(faireMonOffre);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();    

        this.boutonsFaireMonOffre.add(faireMonOffre);
        this.possedeUnBouton.add(numeroJ);
    }

    private void presenterOffre(Joueur j) throws IOException{
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);
        System.out.println("DEBUG : presenter une offre\n id :"+numeroJ+" pos : "+positionCentre);

        //carte visible
        JPanel panelOffreVisible = new JPanel();
        panelOffreVisible.setBounds(positionCentre-105, 320, 105, 155);
        BufferedImage imgV = ImageIO.read(new File("Test_carte.png"));
        JLabel picV = new JLabel(new ImageIcon(imgV));
        picV.setLayout(new BorderLayout());
        JLabel nomV = new JLabel(j.getCarteVisible().getNom(), SwingConstants.CENTER);
        picV.add(nomV, BorderLayout.CENTER);
        panelOffreVisible.add(picV);

        Container content = frame.getContentPane();
        content.add(panelOffreVisible);
        content.setComponentZOrder(panelOffreVisible, 0); // devant

        //carte cachée
        JPanel panelOffreCachee = new JPanel();
        panelOffreCachee.setBounds(positionCentre, 320, 105, 155);
        BufferedImage imgC = ImageIO.read(new File("Test_carte_dos.png"));
        JLabel picC = new JLabel(new ImageIcon(imgC));
        panelOffreCachee.add(picC);

        content.add(panelOffreCachee);
        content.setComponentZOrder(panelOffreCachee, 0); // devant
        content.revalidate();
        content.repaint();    
    }

    private void ajouterPageOffre(Joueur j){
        // Ouvrir une page où le joueur choisi sa carte visible
        FaireUneOffre window2 = new FaireUneOffre(j);
		window2.getFrame().setVisible(true);
    }

    private void interfaceTableDeJeu() throws IOException {
        //Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 60, 1000, 600);
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
        panelJoueurs.setBounds(0, 475, 1000, 85);
        panelJoueurs.setLayout(null);
        int position=100;
        // pour chaques trophés :
        for (int i=0; i<this.partie.getParticipants().size(); i++){
            Joueur j  = this.partie.getParticipants().get(i);

            System.out.println("DEBUG : icones\n joueur :"+j.getNom());

            JPanel panelUnJoueur = new JPanel();
            panelUnJoueur.setBounds(position+(225*i), 0, 55, 80);

            BufferedImage imgJ = ImageIO.read(new File("Test_joueur.png"));
            JLabel picJ = new JLabel(new ImageIcon(imgJ));

            JLabel nom = new JLabel(j.getNom());
            panelUnJoueur.add(picJ);
            panelUnJoueur.add(nom);
            panelJoueurs.add(panelUnJoueur);
        }
        frame.getContentPane().add(panelJoueurs);

        // bouton Jouer
        jouer = new JButton("Jouer !");
		jouer.setBounds(30, 60, 150,25);
		frame.getContentPane().add(jouer);

        frame.setVisible(true);
	}

}
