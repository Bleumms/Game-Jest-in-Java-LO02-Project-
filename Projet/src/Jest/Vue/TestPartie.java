/*
* TesPartie : Classe mettant à jour le visuel de la partie en fonction de l'état de la partie et des joueurs
* (Ne pas se fier au nom, on a pas pu le changer)
* @author Nina et Emeline
*/

package Jest.Vue;

import Jest.Controler.PartieControler;
import Jest.Controler.ChoisiCarteControler;
import Jest.Model.Partie;
import Jest.Model.Carte;
import Jest.Model.EtatJoueur;
import Jest.Model.EtatPartie;
import Jest.Model.Joueur;
import Jest.Model.JoueurPhysique;
import Jest.Model.JoueurVirtuel;

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

@SuppressWarnings("deprecation")
public class TestPartie implements Observer {

    private JFrame frame;

    private Partie partie;

    private JButton jouer;
    private JPanel panelTrophe;
    private List<ButtonGroup> toutesLesOffres;
    private List<JPanel> toutesLesCollections;
    private ButtonGroup boutonsFaireMonOffre;
    private List<Integer> possedeUnBouton;
    private JPanel flecheProchainJoueurQuiJoue;
    private JPanel panelJoueurs;
    private int numTour;
    private int numJoueurPresente;

    /*
    * Met à jour l'affichage en fonction de l'état de la partie et des joueurs
    * On gère ici toutes les différentes étapes : attente offre, offre faite, attente choix, choix fait, fin de partie
    * @param instanceObservable : l'objet observable (la partie ou un joueur)
    * @param arg1 : argument supplémentaire (non utilisé)
    */
    public void update(Observable instanceObservable, Object arg1){
        //on l'enlève dès la première interaction
        this.supprimerBoutonJouer();

        // lorsqu'on attend une offre -> on propose a l'utilisateur de faire son offre
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.AttenteOffre){
            this.ajouterBoutonOffre((Joueur)instanceObservable);
        }


        // lorsqu'on a obtenu l'offre de l'utilisateur -> la rendre visible
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.OffreFaite){
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
            // affiche qui joue
            try{
                this.initialiserAfficheProchainJoueur();
            } catch (IOException e ){
                e.printStackTrace();
            }
        }


        // si le choix a été fait (par un joueur physique)
        if (instanceObservable instanceof JoueurPhysique && ((Joueur)instanceObservable).getEtat()==EtatJoueur.ChoixFait){
            try{
                this.ajouterASaCollection((Joueur)instanceObservable);
            } catch (IOException e ){
                e.printStackTrace();
            }
        }

        // si le choix a été fait (par un joueur virtuel) -> on rajoute un délais sinon l'action est pas clair pours les joueurs
        if (instanceObservable instanceof JoueurVirtuel && ((Joueur)instanceObservable).getEtat()==EtatJoueur.ChoixFait){
            this.pauseAvantAjouterCollection((Joueur)instanceObservable);
        }


        // si le choix est en attente - > on laisse l'utilisateur choisir la carte qu'il veux
        if (instanceObservable instanceof Joueur && ((Joueur)instanceObservable).getEtat()==EtatJoueur.AttenteChoix){
            this.choisirUneCarte(((Joueur)instanceObservable));
        }


        // si tous les choix ont été fais
        if (instanceObservable instanceof Partie && ((Partie)instanceObservable).getEtat()==EtatPartie.ChoixFinis){
            this.enleverLaFleche();
            this.remiseALaPioche();
            this.partie.calculScore();
            Partie.sauvegarder(this.partie);
            if(this.partie.isFinDePartie()==false){
                this.partie.remettreDansPioche();
                this.numTour++;
                partie.distribuer();
                AffichageTour window2 = new AffichageTour(numTour);
                window2.getFrame().setVisible(true);
                this.reinitialiser();
                partie.attendreUneOffre();
            } else {
                this.partie.garderDerniereCarte();
                this.partie.finDePartie();
                this.donnerLesTrophes();
            }
        }
    }



    /*
    * Getter pour le frame de la partie
    * @return JFrame : le frame de la partie
    */
    public JFrame getFrame(){
        return this.frame;
    }



    /*    
    * Constructeur de la classe TestPartie
    * Initialise les composants graphiques et les observers
    * @param p : la partie à afficher
    */
    public TestPartie(Partie p){
        this.boutonsFaireMonOffre=new ButtonGroup();
        this.toutesLesOffres = new ArrayList<ButtonGroup>();
        this.toutesLesCollections = new ArrayList<JPanel>();
        this.possedeUnBouton = new ArrayList<Integer>();
        this.flecheProchainJoueurQuiJoue = new JPanel();
        this.numTour=1;
        this.partie = p;
		this.partie.addObserver(this);
        for (Joueur j : this.partie.getParticipants()){
            j.addObserver(this);
        }
        for (int i =0 ; i<this.partie.getParticipants().size(); i++){ 
            this.toutesLesOffres.add(null);
        }
        try {
            interfaceTableDeJeu();
        } catch (IOException e ){
            e.printStackTrace();
        }
        new PartieControler(this.partie,this.jouer);
    }



    /*
    * Réinitialise les offres et les boutons pour un nouveau tour
    */
    private void reinitialiser(){
        this.boutonsFaireMonOffre=new ButtonGroup();
        this.toutesLesOffres.clear();
        this.possedeUnBouton.clear();
        for (int i =0 ; i<this.partie.getParticipants().size(); i++){ 
            this.toutesLesOffres.add(null);
        }
    }

    /*
    * Remet toutes les cartes offertes à la pioche visuellement
    */
    private void remiseALaPioche(){
        for (ButtonGroup b: this.toutesLesOffres){
            Enumeration<AbstractButton> buttons = b.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton btn = buttons.nextElement();
                btn.setVisible(false);
            }
        }
    }

    /* 
    * Pause avant de passer au prochain joueur, pour une meilleure lisibilité visuelle
    */
    private void pauseAvantProhainJoueur( ){
        // Pause VISUELLE de 1 seconde, pour qu'on voit ce qu'il se passe avec les joueurs virtuels
        Timer timer = new Timer(1000, e -> {
            try {
                this.partie.prochainJoueur();
                // S'il a pas de prochain
                if ( this.partie.getEtat()!=EtatPartie.ChoixFinis){
                    this.afficheProchainJoueur();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        timer.setRepeats(false);
        timer.start(); 
    }

    /* 
    * Pause avant d'ajouter une carte à la collection, pour une meilleure lisibilité visuelle
    */
    private void pauseAvantAjouterCollection(Joueur j){
        // Pause VISUELLE de 1 seconde, pour qu'on voit ce qu'il se passe avec les joueurs virtuels
        Timer timer = new Timer(1000, e -> {
            try {
                this.ajouterASaCollection(j);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        timer.setRepeats(false);
        timer.start(); 
    }

    /*
    * Permet au joueur de choisir une carte parmi les offres disponibles
    * @param j : le joueur qui doit choisir une carte
    */
    private void choisirUneCarte(Joueur j){
        List<Joueur> joueursDispo = this.partie.getPasEncoreDeCartePrise();
        boolean etaitDansLaListe=false;
        if(joueursDispo.size()>1 && joueursDispo.contains(j)){
            joueursDispo.remove(j);
            etaitDansLaListe=true;
        }

        // On rend plus visible les cartes qu'on peut prendre
        for (Joueur jDispo : joueursDispo){
            ButtonGroup offreDuJoueur = this.toutesLesOffres.get(jDispo.getID());
            Enumeration<AbstractButton> buttons = offreDuJoueur.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton btn = buttons.nextElement();
                btn.setBorderPainted(true);
                btn.setBorder(new LineBorder(Color.GREEN, 5));
            }
        }
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();   
        // On surveille l'action du joueur
        new ChoisiCarteControler(this.partie,j,joueursDispo,this.toutesLesOffres);

        if (etaitDansLaListe==true){
            joueursDispo.add(j.getID(),j);
        }
    }

    /*
    * Ajoute la carte choisie par le joueur à sa collection visuellement et dans le modèle
    * @param j : le joueur qui a choisi une carte
    */
    private void ajouterASaCollection(Joueur j) throws IOException{
        List<Integer> choix = j.getChoix();

        // partie visuel 1 : on enlève la carte des offres
        ButtonGroup offreDuJoueur = this.toutesLesOffres.get(choix.get(0));
        Enumeration<AbstractButton> buttons = offreDuJoueur.getElements();
        AbstractButton btn;
        btn = buttons.nextElement();
        if (choix.get(1)==0){
            btn.setVisible(false);
        } else {
            btn = buttons.nextElement();
            btn.setVisible(false);
        }

        // partie visuel 2 : on l'ajoute a la collection
        JPanel panel =this.toutesLesCollections.get(j.getID());
        ImageIcon icon = new ImageIcon("Carte_dos.png");
        Image img = icon.getImage().getScaledInstance(30, 45, Image.SCALE_SMOOTH);
        JLabel pic = new JLabel(new ImageIcon(img));
        pic.setBounds((numTour-1)*10, 0, 30, 45);
        panel.add(pic);
        panel.setComponentZOrder(pic, 0);
        panel.revalidate();
        panel.repaint();

        this.partie.aPrisUneCarte(j, this.partie.getParticipants().get(choix.get(0)), choix.get(1));
        pauseAvantProhainJoueur();
    }

    /*
    * Initialise l'affichage du prochain joueur qui doit jouer
    */
    private void initialiserAfficheProchainJoueur() throws IOException{
        
        // mettre a jour la position de la fleche
        Joueur j = this.partie.getfaisSonChoix();
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);

        // Fleche pour savoir qui joue
        flecheProchainJoueurQuiJoue.setBounds(positionCentre-25, 250, 55, 55);
        flecheProchainJoueurQuiJoue.setVisible(true);
        BufferedImage img = ImageIO.read(new File("Fleche.png"));
        JLabel pic = new JLabel(new ImageIcon(img));
        flecheProchainJoueurQuiJoue.add(pic);
        frame.getContentPane().add(flecheProchainJoueurQuiJoue);
        frame.getContentPane().setComponentZOrder(flecheProchainJoueurQuiJoue, 0);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();    
        
		j.attendreUnChoix(this.partie.getPasEncoreDeCartePrise());	
    }

    /*
    * Affiche le prochain joueur qui doit jouer
    */
    private void afficheProchainJoueur() throws IOException{
        // remettre les cartes sans bordures 
        for (ButtonGroup b : toutesLesOffres){
            Enumeration<AbstractButton> buttons = b.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton btn = buttons.nextElement();
                if (btn!=null){
                    btn.setBorderPainted(false);
                }
            }
        }

        // mettre a jour la position de la fleche
        Joueur j = this.partie.getfaisSonChoix();

        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);

        // Fleche pour savoir qui joue
        this.flecheProchainJoueurQuiJoue.setBounds(positionCentre-25, 250, 55, 55);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();    
        
		j.attendreUnChoix(this.partie.getPasEncoreDeCartePrise());	
    }

    /*
    * Enlève la flèche indiquant le prochain joueur qui doit jouer
    */
    private void enleverLaFleche(){
        this.flecheProchainJoueurQuiJoue.setVisible(false);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();   
    }

    /* 
    * Annonce les scores des joueurs à la fin de la partie
    */
    private void annonceScores(){
        try {
            presenterJoueur(numJoueurPresente);
        } catch ( IOException e){
            e.printStackTrace();
        }
        
    }

    /* 
    * Présente un joueur avec son score et sa collection
    * @param i : l'index du joueur à présenter
    */
    private void presenterJoueur(int i) throws IOException{
        Component[] components = this.panelJoueurs.getComponents();
        components[i].setVisible(false);
        JPanel panel =this.toutesLesCollections.get(i);
        panel.setVisible(false);
        Joueur j = this.partie.getParticipants().get(i);

        JPanel panelUnJoueur = new JPanel();
        panelUnJoueur.setBounds(100, 325, 55, 80);
        BufferedImage imgJ = ImageIO.read(new File("Joueur.png"));
        JLabel picJ = new JLabel(new ImageIcon(imgJ));

        JLabel nomJ = new JLabel(j.getNom());
        panelUnJoueur.add(picJ);
        panelUnJoueur.add(nomJ);
        frame.getContentPane().add(panelUnJoueur);

        JLabel score = new JLabel("Score : "+j.getScore());
        score.setBounds(75, 280, 100, 20);
        frame.add(score);
        int compte=0;
        List<JLabel> toutesMesCartes= new ArrayList<JLabel>();
        for (Carte carte : j.getCollection()){
            BufferedImage img = ImageIO.read(new File("Carte.png"));
            JLabel pic = new JLabel(new ImageIcon(img));
            pic.setLayout(new BorderLayout());
            JLabel nom = new JLabel(carte.getNom(), SwingConstants.CENTER);
            pic.add(nom, BorderLayout.CENTER);
            pic.setBounds(250+(compte*150), 280, 100, 150);
            frame.getContentPane().add(pic);
            toutesMesCartes.add(pic);
            compte++;
        }

        JButton boutonSuivant = new JButton("Suivant");
        boutonSuivant.setBounds(75, 425, 100,25);
        boutonSuivant.addActionListener(event -> this.enleverPresenterJoueur(toutesMesCartes, panelUnJoueur, score, boutonSuivant));
        frame.getContentPane().add(boutonSuivant);
        
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    /* 
    * Enlève l'affichage du joueur présenté et passe au suivant
    * @param toutesMesCartes : la liste des cartes affichées du joueur
    * @param c : le panel du joueur affiché
    * @param score : le label du score affiché
    * @param boutonSuivant : le bouton pour passer au suivant
    */
    private void enleverPresenterJoueur(List<JLabel> toutesMesCartes, JPanel c, JLabel score, JButton boutonSuivant){
        frame.getContentPane().remove(score);
        frame.getContentPane().remove(c);
        for (JLabel lb : toutesMesCartes) {
            frame.getContentPane().remove(lb);
        }
        frame.getContentPane().remove(boutonSuivant);
        this.numJoueurPresente++;
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint(); 
        if (numJoueurPresente<this.partie.getParticipants().size()) {
            annonceScores();
        } else {
            annonceGagnants();
        }
    }

    /* 
    * Annonce les gagnants de la partie et ouvre la fenêtre de fin de partie
    */
    private void annonceGagnants(){
        Partie.supprimerPartie(this.partie.getID()); 
        frame.dispose();
        FinDePartie window2 = new FinDePartie(this.partie);
		window2.getFrame().setVisible(true);
        
    }

    /* 
    * Donne les trophés aux joueurs qui les ont gagnés
    * Ajout du trophé à la collection visuellement
    */
    private void donnerLesTrophes (){
        Component[] components = this.panelTrophe.getComponents();
        int compte=0;
        List<JLabel> labels = new ArrayList<JLabel>();
        for (Component c : components){
            Carte trophe = this.partie.getTrophes().get(compte);
            int indexJ = trophe.JoueurGagnantCarte(this.partie.getParticipants());
            if (indexJ>=0){
                Joueur gagnantTrophe = this.partie.getParticipants().get(indexJ);

                JLabel labelTrophe = new JLabel("Le trophe "+trophe.getNom()+" est donné à "+gagnantTrophe.getNom()+ " car "+trophe.getConditionVictoire());
                labelTrophe.setBounds(100, 275+(compte*30), 800, 20);
                frame.getContentPane().add(labelTrophe);
                labels.add(labelTrophe);

                // partie visuel  : on l'ajoute a la collection
                JPanel panel =this.toutesLesCollections.get(gagnantTrophe.getID());
                ImageIcon icon = new ImageIcon("Carte_dos.png");
                Image img = icon.getImage().getScaledInstance(30, 45, Image.SCALE_SMOOTH);
                JLabel pic = new JLabel(new ImageIcon(img));
                pic.setBounds((numTour)*10, 0, 30, 45);
                panel.add(pic);
                panel.setComponentZOrder(pic, 0);
                panel.revalidate();
                panel.repaint();
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();   
            }
            c.setVisible(false);
            compte++;
        }
        JButton boutonOK = new JButton("ok");
        boutonOK.setBounds(450, 275+(compte*50), 100,25);
        boutonOK.addActionListener(event -> this.suppressionMessageTrophe(labels, boutonOK));
        frame.getContentPane().add(boutonOK);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();   
        
    }

    /* 
    * Supprime le message des trophés et passe à l'annonce des scores
    * @param labels : la liste des labels des messages
    * @param boutonOK : le bouton ok pour passer à la suite
    */
    private void suppressionMessageTrophe(List<JLabel> labels, JButton boutonOK){
        for (JLabel l : labels){
            frame.getContentPane().remove(l);
        }
        frame.getContentPane().remove(boutonOK);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint(); 
        this.annonceScores();  
    }

    /* 
    * Supprime le bouton jouer
    */
    private void supprimerBoutonJouer(){
        this.jouer.setVisible(false);
    }

    /* 
    * Supprime le bouton faire mon offre d'un joueur
    * @param idJoueur : l'ID du joueur dont on supprime le bouton
    */
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

    /* 
    * Ajoute le bouton pour faire une offre pour un joueur
    * @param j : le joueur qui doit faire une offre
    */
    private void ajouterBoutonOffre(Joueur j){
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);

        JButton faireMonOffre = new JButton("Faire mon offre");
		faireMonOffre.setBounds(positionCentre-75, 425, 150,25);
        faireMonOffre.addActionListener(event -> ajouterPageOffre(j));
		frame.getContentPane().add(faireMonOffre);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();    

        this.boutonsFaireMonOffre.add(faireMonOffre);
        this.possedeUnBouton.add(numeroJ);
    }

    /* 
    * Présente l'offre d'un joueur visuellement
    * @param j : le joueur qui présente son offre
    */
    private void presenterOffre(Joueur j) throws IOException{
        int numeroJ = j.getID();
        int positionCentre = 125+(225*numeroJ);

        ButtonGroup offre = new ButtonGroup();
        //carte visible
        BufferedImage imgV = ImageIO.read(new File("Carte.png"));
        JButton btnCarteVisible = new JButton(new ImageIcon(imgV));
        btnCarteVisible.setActionCommand(String.valueOf(numeroJ)+";"+String.valueOf(0));
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
        content.setComponentZOrder(btnCarteVisible, 0);

        //carte cachée
        BufferedImage imgC = ImageIO.read(new File("Carte_dos.png"));
        JButton btnCarteCachee = new JButton(new ImageIcon(imgC));
        btnCarteCachee.setActionCommand(String.valueOf(numeroJ)+";"+String.valueOf(1));
        btnCarteCachee.setBounds(positionCentre, 320, 105, 155);
        btnCarteCachee.setLayout(new BorderLayout());

        btnCarteCachee.setBorderPainted(false);
        btnCarteCachee.setContentAreaFilled(false);
        btnCarteCachee.setFocusPainted(false);

        offre.add(btnCarteCachee);

        content.add(btnCarteCachee);
        content.setComponentZOrder(btnCarteCachee, 0);
        content.revalidate();
        content.repaint();    
        this.toutesLesOffres.set(j.getID(),offre);
    }

    /* 
    * Ouvre la page pour faire une offre
    * @param j : le joueur qui doit faire une offre
    */
    private void ajouterPageOffre(Joueur j){
        // Ouvrir une page où le joueur choisi sa carte visible
        FaireUneOffre window2 = new FaireUneOffre(this.partie, j);
		window2.getFrame().setVisible(true);
    }

    /* 
    * Initialise et affiche la fenêtre de la table de jeu
    */
    private void interfaceTableDeJeu() throws IOException {
        frame = new JFrame();
		frame.setBounds(100, 60, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("Partie en cours");
		label.setBounds(30, 30, 100, 20);
		frame.getContentPane().add(label);

        //Trophés

        panelTrophe = new JPanel();
        int taille = 105*this.partie.getTrophes().size();
        panelTrophe.setBounds(250, 30, taille, 155);
        // pour chaques trophés :
        for (Carte c : this.partie.getTrophes()){
            BufferedImage img = ImageIO.read(new File("Carte.png"));
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
        BufferedImage img = ImageIO.read(new File("Carte_dos.png"));
        JLabel pic = new JLabel(new ImageIcon(img));
        panelPioche.add(pic);
        frame.getContentPane().add(panelPioche);


        //Un icone par joueur
        
        panelJoueurs = new JPanel();
        panelJoueurs.setBounds(0, 475, 1000, 85);
        panelJoueurs.setLayout(null);
        int position=100;
        // pour chaques trophés :
        for (int i=0; i<this.partie.getParticipants().size(); i++){
            Joueur j  = this.partie.getParticipants().get(i);

            JPanel panelUnJoueur = new JPanel();
            panelUnJoueur.setBounds(position+(225*i), 0, 55, 80);

            BufferedImage imgJ = ImageIO.read(new File("Joueur.png"));
            JLabel picJ = new JLabel(new ImageIcon(imgJ));

            JLabel nom = new JLabel(j.getNom());
            panelUnJoueur.add(picJ);
            panelUnJoueur.add(nom);
            panelJoueurs.add(panelUnJoueur);

            JPanel panelCollectionJoueurs = new JPanel();
            panelCollectionJoueurs.setBounds(position+(225*i)+50, 520, 90, 45);
            panelCollectionJoueurs.setLayout(null);
            this.toutesLesCollections.add(panelCollectionJoueurs);
            frame.getContentPane().add(panelCollectionJoueurs);
        }
        frame.getContentPane().add(panelJoueurs);

        jouer = new JButton("Jouer !");
		jouer.setBounds(30, 60, 150,25);
		frame.getContentPane().add(jouer);

        frame.setVisible(true);
	}

}
