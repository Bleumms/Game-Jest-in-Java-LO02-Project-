package Jest.Vue;

import Jest.Controler.MenuJoueursControler;
import  Jest.Model.Menu;
import Jest.Model.Strategie;
import Jest.Model.EtatMenu;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MenuAjoutJoueurs implements Observer {
	
	private JFrame frame;

    private Menu menu;

    private JButton valider;
    private JTextField  zoneTexte;
    private ButtonGroup radiosBoutonsStrat;
    private ButtonGroup radiosBoutonsType;
    private static int compteNumeroJoueur;



	public void update(Observable instanceObservable, Object arg1){
		// La validation n'a pas fonctionné 
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.SelectionnerJoueursAvecErreur){
            System.out.println("DEBUG : Mal saisi!");
            this.ajoutMessageErreur();
		}
        // Un nouveau joueur a été ajouté et il faut en recréer un
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.SelectionnerJoueur){
            System.out.println("DEBUG : Encore un joueur");
            enleverMessageErreur();
            frame.dispose();
            compteNumeroJoueur++;
            this.interfaceAjouteUnJoueur();
		}
        // Tous les joueurs ont étés ajoutés
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.LancerPartie){
            System.out.println("DEBUG : J'ai tous les joueurs");
            frame.dispose();
            TestPartie window2 = new TestPartie(this.menu.getPartieEnCours());
			window2.getFrame().setVisible(true);
		}
	}

    private void ajoutMessageErreur(){
		JLabel label3 = new JLabel("Attention, les informations sont mal remplis !");
        JLabel label4 = new JLabel("(Pensez a faire Entrer pour contabiliser la saisie)");
		label3.setBounds(50, 170, 350,20);
        label4.setBounds(50, 180, 350,20);
		Container content = frame.getContentPane();
        content.add(label3);
        content.setComponentZOrder(label3, 0); // devant
        content.add(label4);
        content.setComponentZOrder(label4, 0); // devant
        content.repaint();
    }

    private void enleverMessageErreur(){
        Component[] components = frame.getContentPane().getComponents();
        int compteur =0;
        for (Component c : components) {
            if (c instanceof JPanel) {
                compteur ++;
                if (compteur<=2){    // ça compte a partir du bas parce qu'on parcours les panels
                    frame.getContentPane().remove(c); // supprime les boutons radios sauf ceux pour le type
                }
            }
        }
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    public JFrame getFrame(){
        return this.frame;
    }

	public MenuAjoutJoueurs(Menu m) {

        this.menu = m;
		this.menu.addObserver(this);

        compteNumeroJoueur=1;
		interfaceAjouteUnJoueur();
        new MenuJoueursControler(this.menu, this.zoneTexte,  this.valider, this.radiosBoutonsStrat, this.radiosBoutonsType);
        
	}

    private void interfaceAjouteUnJoueurVirtuel(){
        Enumeration<AbstractButton> buttons = radiosBoutonsStrat.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();
            btn.setVisible(true);
        }
    }

    private void interfaceAjouteUnJoueurNonVirtuel(){
        Enumeration<AbstractButton> buttons = radiosBoutonsStrat.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();
            btn.setVisible(false);
        }
    }

	private void interfaceAjouteUnJoueur() {

		//Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

        String message =  "Joueur "+compteNumeroJoueur;
		JLabel label = new JLabel(message);
		label.setBounds(30, 30, 70, 20);
		frame.getContentPane().add(label);

        // type de joueur
        this.radiosBoutonsType = new ButtonGroup();
        
        JRadioButton jRadioButtonType = new JRadioButton();
		jRadioButtonType.setText("Joueur réel");
        jRadioButtonType.setActionCommand("Reel");
		jRadioButtonType.setBounds(115, 30 , 75,20);
        jRadioButtonType.setSelected(true);
		frame.getContentPane().add(jRadioButtonType);
		this.radiosBoutonsType.add(jRadioButtonType);

        jRadioButtonType.addActionListener(event -> interfaceAjouteUnJoueurNonVirtuel());

        JRadioButton jRadioButtonType2 = new JRadioButton();
		jRadioButtonType2.setText("Joueur virtuel");
        jRadioButtonType2.setActionCommand("Virtu");
		jRadioButtonType2.setBounds(205, 30 , 75,20);
		frame.getContentPane().add(jRadioButtonType2);
		this.radiosBoutonsType.add(jRadioButtonType2);

        jRadioButtonType2.addActionListener(event -> interfaceAjouteUnJoueurVirtuel());

        // nom
        JLabel label2 = new JLabel("Nom :");
		label2.setBounds(30, 60, 50, 20);
		frame.getContentPane().add(label2);

        zoneTexte = new JTextField (20);
        zoneTexte.setFont(new Font("Arial", Font.PLAIN, 14));
        zoneTexte.setBounds(100, 60, 100, 20);
		frame.getContentPane().add(zoneTexte);
        
        this.radiosBoutonsStrat = new ButtonGroup();
		for (int i=0; i< this.menu.getStrats().size(); i++){
            Strategie s = this.menu.getStrats().get(i);

			JRadioButton jRadioButtonStrat = new JRadioButton();
			jRadioButtonStrat.setText((i+1)+"   "+s.getNom());
            jRadioButtonStrat.setActionCommand(String.valueOf(i));
			jRadioButtonStrat.setBounds(100, 90+(20*i), 200,20);
			frame.getContentPane().add(jRadioButtonStrat);
            jRadioButtonStrat.setVisible(false);
			this.radiosBoutonsStrat.add(jRadioButtonStrat);
		}	

        // valider
		valider = new JButton("Valider");
		valider.setBounds(125, 140, 150,25);
		frame.getContentPane().add(valider);

		frame.setVisible(true);
	}

	
}
