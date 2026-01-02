package Jest.Controler;

import Jest.Model.Partie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class PartieControler {
    private Partie partie;
    private JButton jouer;

    public PartieControler(Partie partie, JButton jouer){
        this.partie=partie;
        this.jouer=jouer;

        initJouer();
    }

    private void initJouer(){
		this.jouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("DEBUG : Bouton jouer activé");
                partie.distribuer();
                partie.attendreUneOffre();
			}
		});
	}
}
