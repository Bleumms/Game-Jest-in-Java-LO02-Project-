package Jest.Controler;

import Jest.Model.Partie;
import Jest.Vue.AffichageTour;

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
                partie.distribuer();
                AffichageTour window2 = new AffichageTour(1);
			    window2.getFrame().setVisible(true);
                partie.attendreUneOffre();
			}
		});
	}
}
