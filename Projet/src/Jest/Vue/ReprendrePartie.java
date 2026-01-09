package Jest.Vue;

import Jest.Controler.MenuCreerControler;
import Jest.Controler.MenuDebutControler;
import Jest.Controler.MenuReprendreControler;
import  Jest.Model.Menu;
import Jest.Model.Partie;
import Jest.Model.EtatMenu;
import  Jest.Model.Jeu;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import javax.swing.*;



public class ReprendrePartie implements Observer {
	
	private JFrame frame;

    private Menu menu;

	private List<JButton> boutonsJeu;
    private List<String> fichiers;

	

	public void update(Observable instanceObservable, Object arg1){
		
	}

	public ReprendrePartie(Menu m) {

        this.menu = m;
        this.boutonsJeu=new ArrayList<JButton>();
		this.menu.addObserver(this);

		interfaceLancementMenuReprendre();
        new MenuReprendreControler(this.frame, boutonsJeu, fichiers);
        
	}

	public JFrame getFrame(){
		return this.frame;
	}

    private void retour() {
        frame.dispose();
        MenuPrincipal m = new MenuPrincipal();
    }

	private void interfaceLancementMenuReprendre() {

        fichiers=null;
		try{
			fichiers = Partie.listerSauvegardes();
		} catch (IOException e){
			e.printStackTrace();
		}

		//Creating the Frame
    	frame = new JFrame();
		frame.setBounds(100, 100, 200, 100+(fichiers.size()*100));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

        int position=0;

        
		if (fichiers!=null && fichiers.size()>0){
			for (int i=0; i<fichiers.size();i++){
                JButton btn = new JButton(fichiers.get(i));
                btn.setActionCommand(String.valueOf(position));
                btn.setBounds(50, 50+(position*75),100,50);
                boutonsJeu.add(btn);
                frame.getContentPane().add(btn);
			}
			
		} else {
			JLabel text = new JLabel("Aucune sauvegardes");
            text.setBounds(100, 75,100,40);
            frame.getContentPane().add(text);
            JButton btn = new JButton("Retour");
            btn.setBounds(100, 150,100,40);
            btn.addActionListener(event -> this.retour());

		}
        
		frame.setVisible(true);
	}

}
