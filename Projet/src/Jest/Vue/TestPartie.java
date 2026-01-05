package Jest.Vue;

import Jest.Controler.PartieControler;
import  Jest.Model.Partie;
import  Jest.Model.Carte;
import Jest.Model.EtatJoueur;
import Jest.Model.EtatPartie;
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
import javax.swing.border.LineBorder;

public class TestPartie implements Observer {

    private JFrame frame;

    private Partie partie;

    private JButton jouer;
    private List<ButtonGroup> toutesLesOffres;
    private ButtonGroup boutonsFaireMonOffre;
    private List<Integer> possedeUnBouton;
    private JPanel flecheProchainJoueurQuiJoue;

    public void update(Observable instanceObservable, Object arg1){
        //on l'enlève dès la première intéraction
        this.supprimerBoutonJouer();

        // lorsqu'on attend une offre -> donc on propose a l'utilisateur de faire son offre
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.AttenteOffre){
            //System.out.println("DEBUG : UPDATE : joueur : "+((Joueur)instanceObservable).getNom()+" offre en attente");
            this.ajouterBoutonOffre((Joueur)instanceObservable);
        }


        // lorsqu'on a obtenu l'offre de l'utilisateur -> la rendre visible
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.OffreFaite){
            //System.out.println("DEBUG : UPDATE : joueur : "+((Joueur)instanceObservable).getNom()+" offre faite ");
            // si il y avait un bouton on l'enlève
            if (this.possedeUnBouton.contains(((Joueur)instanceObservable).getID())){
                this.supprimerBoutonJoueur(((Joueur)instanceObservable).getID());
            }
            // on présente l'offre
            try{
                this.presenterOffre((Joueur)instanceObservable);
            } catch (IOException e ){
                e.printStackTrace();
            }
        }


        // si toutes les offres ont été faites
        if (instanceObservable instanceof Partie && ((Partie)instanceObservable).getEtat()==EtatPartie.OffreFinis){
            System.out.println("DEBUG : UPDATE : on passe au choix de carte");
            // affiche qui joue
            System.out.println("DEBUG : étape 5 ");
            try{
                this.initialiserAfficheProchainJoueur();
            } catch (IOException e ){
                e.printStackTrace();
            }
        }

        // si le choix a été fait 
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.ChoixFait){
            System.out.println("DEBUG : UPDATE : le joueur : "+((Joueur)instanceObservable).getNom()+" a fait son choix : "+((Joueur)instanceObservable).getChoix());
            this.pauseAvantAjoutCollection((Joueur)instanceObservable);
        }

        // si le choix est en attente - > donc on laisse l'utilisateur choisir la carte qu'il veux
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.AttenteChoix){
            System.out.println("DEBUG : UPDATE : le joueur : "+((Joueur)instanceObservable).getNom()+" attend son choix ");
            
        }
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public TestPartie(Partie p){
        this.boutonsFaireMonOffre=new ButtonGroup();
        this.toutesLesOffres = new ArrayList<ButtonGroup>();
        this.possedeUnBouton = new ArrayList<Integer>();
        this.flecheProchainJoueurQuiJoue = new JPanel();
        this.partie = p;
		this.partie.addObserver(this);
        for (int i =0 ; i<this.partie.getParticipants().size(); i++){ 
            this.toutesLesOffres.add(null);
        }
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

    private void pauseAvantAjoutCollection( Joueur j ){
        // Pause VISUELLE de 1 seconde, pour qu'on voit ce qu'il se passe avec les joueurs virtuels
        System.out.println("DEBUG : je fais ma pause");
        Timer timer = new Timer(1000, e -> {
            try {
                ajouterASaCollection(j);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        timer.setRepeats(false);
        timer.start(); 
    }

    private void ajouterASaCollection(Joueur j) throws IOException{
        List<Integer> choix = j.getChoix();
        List<Joueur> joueurs = this.partie.getPasEncoreDeCartePrise();
        Joueur jDontLaCarteEstPrise = joueurs.get(choix.get(0));
        System.out.println("DEBUG : ajout a la collection");

        // partie visuel
        ButtonGroup offreDuJoueur = this.toutesLesOffres.get(jDontLaCarteEstPrise.getID());
        Enumeration<AbstractButton> buttons = offreDuJoueur.getElements();
        AbstractButton btn;
        btn = buttons.nextElement();
        if (choix.get(1)==0){
            btn.setVisible(false);
        } else {
            btn = buttons.nextElement();
            btn.setVisible(false);
        }

        // partie model
        this.partie.aPrisUneCarte(j, jDontLaCarteEstPrise, choix.get(1));
        System.out.println("DEBUG : j : "+j.getCollection());

        // ensuite affiche le prochain joueur
        this.partie.prochainJoueur();
        // attention cas où il a pas de prochain
        if ( this.partie.getEtat()!=EtatPartie.ChoixFinis){
            this.afficheProchainJoueur();
        }
    }

    private void initialiserAfficheProchainJoueur() throws IOException{
        //qui joue ?
        Joueur j = this.partie.getfaisSonChoix();

        // position
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);

        // Fleche pour savoir qui joue
        flecheProchainJoueurQuiJoue.setBounds(positionCentre-25, 250, 55, 55);
        BufferedImage img = ImageIO.read(new File("Test_fleche.png"));
        JLabel pic = new JLabel(new ImageIcon(img));
        flecheProchainJoueurQuiJoue.add(pic);
        frame.getContentPane().add(flecheProchainJoueurQuiJoue);
        frame.getContentPane().setComponentZOrder(flecheProchainJoueurQuiJoue, 0);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();    
        
		j.attendreUnChoix(this.partie.getPasEncoreDeCartePrise());	
    }

    private void afficheProchainJoueur() throws IOException{
        System.out.println("DEBUG : étape 6");
        //qui joue ?
        Joueur j = this.partie.getfaisSonChoix();

        // position
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);
        //System.out.println("DEBUG : id : "+numeroJ+" ; position : "+positionCentre);

        // Fleche pour savoir qui joue
        this.flecheProchainJoueurQuiJoue.setBounds(positionCentre-25, 250, 55, 55);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();    
        
		System.out.println("DEBUG : C'est à "+ j.getNom()+" de jouer");
		j.attendreUnChoix(this.partie.getPasEncoreDeCartePrise());	
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

        // pour le moment où les utilisateurs feront leur choix ce sont des boutons mais rien ne passe quand 
        // on click dessus tant qu'on est pas au choix
        ButtonGroup offre = new ButtonGroup();
        //carte visible
        BufferedImage imgV = ImageIO.read(new File("Test_carte.png"));
        JButton btnCarteVisible = new JButton(new ImageIcon(imgV));
        btnCarteVisible.setBounds(positionCentre-105, 320, 105, 155);
        btnCarteVisible.setLayout(new BorderLayout());

        btnCarteVisible.setBorderPainted(false);
        btnCarteVisible.setContentAreaFilled(false);
        btnCarteVisible.setFocusPainted(false);

        JLabel nomV = new JLabel(j.getCarteVisible().getNom(), SwingConstants.CENTER);
        btnCarteVisible.add(nomV, BorderLayout.CENTER);

        offre.add(btnCarteVisible);

        Container content = frame.getContentPane();
        content.add(btnCarteVisible);
        content.setComponentZOrder(btnCarteVisible, 0); // devant

        //carte cachée
        BufferedImage imgC = ImageIO.read(new File("Test_carte_dos.png"));
        JButton btnCarteCachee = new JButton(new ImageIcon(imgC));
        btnCarteCachee.setBounds(positionCentre, 320, 105, 155);
        btnCarteCachee.setLayout(new BorderLayout());

        btnCarteCachee.setBorderPainted(false);
        btnCarteCachee.setContentAreaFilled(false);
        btnCarteCachee.setFocusPainted(false);

        offre.add(btnCarteCachee);

        content.add(btnCarteCachee);
        content.setComponentZOrder(btnCarteCachee, 0); // devant
        content.revalidate();
        content.repaint();    
        this.toutesLesOffres.set(j.getID(),offre);
    }

    private void ajouterPageOffre(Joueur j){
        // Ouvrir une page où le joueur choisi sa carte visible
        FaireUneOffre window2 = new FaireUneOffre(this.partie, j);
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

            //System.out.println("DEBUG : icones\n joueur :"+j.getNom());

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
