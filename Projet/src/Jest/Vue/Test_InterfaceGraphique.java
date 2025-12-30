package Jest.Vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;

//import com.sun.java.swing.plaf.windows.resources.windows;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;

public class Test_InterfaceGraphique implements Observer {
	
	// Les propri�t�s de la classe
	private JButton boutonInterrupteur;
	private JLabel labelCommutateur;
	private JCheckBox checkBoxLampe1;
	private JCheckBox checkBoxLampe2;
	private JCheckBox checkBoxLampe3;
	private JCheckBox checkBoxLampe4;
	private JCheckBox checkBoxLampe5;
    
	// Le Update est d�clench� quand une lampe ou le commutateur change
	public void update(Observable instanceObservable, Object arg1){
		
		if(instanceObservable instanceof Lampe){
			switch (((Lampe)instanceObservable).getNumero()) {
			case 0 : checkBoxLampe1.setSelected(((Lampe)instanceObservable).isAllumee());
			case 1 : checkBoxLampe2.setSelected(((Lampe)instanceObservable).isAllumee());
			case 2 : checkBoxLampe3.setSelected(((Lampe)instanceObservable).isAllumee());
			case 3 : checkBoxLampe4.setSelected(((Lampe)instanceObservable).isAllumee());
			case 4 : checkBoxLampe5.setSelected(((Lampe)instanceObservable).isAllumee());
			}
		}
		
		if (instanceObservable instanceof Commutateur){
	 		System.out.println("Controleur du commutateur: activation du commutateur\n");
	 		labelCommutateur.setText("commuteur �tat ="+((Commutateur)instanceObservable).getEtat() );
		}
	}

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// Construction des objets du Mod�le
		// Cr�ation de l'interrupteur qui cr�e le commutateur qui cr�e les lampes
		Interrupteur monInterrupteur = new Interrupteur();
		
		// Cr�ation du thread qui cr�e l'interface graphique	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonInterface window = new MonInterface(monInterrupteur);
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Cr�ation de la console - Texte
		VueTexte maConsoleText = new VueTexte(monInterrupteur); 
	}

	/**
	 * Create the application.
	 */
	public MonInterface(Interrupteur inter) {

		initialize();
		
		//notifie que l'Interface graphique Observe les lampes et le commutateur
		this.unInterrupteur = inter;
		Lampe[] lampes = unInterrupteur.getCommutateur().getLampes();
		for (int i = 0; i < lampes.length; i++) {
		    lampes[i].addObserver(this);
		}
		unInterrupteur.getCommutateur().addObserver(this);

		// * Cr�ation du Controleur de l'interrupteur: lien entre le Mod�le et la Vue
		new ControleurInterrupteur(unInterrupteur, boutonInterrupteur);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		boutonInterrupteur = new JButton("Interrupteur");
		boutonInterrupteur.setBounds(317, 103, 107, 23);
		frame.getContentPane().add(boutonInterrupteur);
		
		checkBoxLampe1 = new JCheckBox("Lampe 1");
		checkBoxLampe1.setBounds(45, 23, 97, 23);
		frame.getContentPane().add(checkBoxLampe1);
		
		checkBoxLampe2 = new JCheckBox("Lampe 2");
		checkBoxLampe2.setBounds(45, 61, 97, 23);
		frame.getContentPane().add(checkBoxLampe2);
		
		checkBoxLampe3 = new JCheckBox("Lampe 3");
		checkBoxLampe3.setBounds(45, 103, 97, 23);
		frame.getContentPane().add(checkBoxLampe3);
		
		checkBoxLampe4 = new JCheckBox("Lampe 4");
		checkBoxLampe4.setBounds(45, 145, 97, 23);
		frame.getContentPane().add(checkBoxLampe4);
		
		checkBoxLampe5 = new JCheckBox("Lampe 5");
		checkBoxLampe5.setBounds(45, 191, 97, 23);
		frame.getContentPane().add(checkBoxLampe5);
		
		labelCommutateur = new JLabel("Commutateur");
		labelCommutateur.setBounds(147, 105, 172, 18);
		frame.getContentPane().add(labelCommutateur);
	}
}
