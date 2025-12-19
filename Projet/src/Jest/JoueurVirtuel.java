/*
 * Représente un joueur contrôlé par l'ordinateur.
 * Utilise une stratégie pour prendre ses décisions.
 * 
 * @author Nina et Emeline
 * @version 1.0
 * @see Joueur
 * @see Strategie
*/

package Jest;

import java.util.List;

public class JoueurVirtuel extends Joueur {

	/*
	 * Stratégie utilisée par le joueur virtuel pour prendre des décisions
	*/
	private Strategie strat;

	/* 
	 * Constructeur avec paramètre.
	 * Initialise le nom du joueur virtuel et sa stratégie.
	 * @param n Le nom du joueur
	 * @param strategie La stratégie à utiliser
	*/
	public JoueurVirtuel(String n, Strategie strategie) {
		super(n);
		this.strat = strategie;
	}

	/* 
	 * Définit la stratégie du joueur virtuel.
	 * @param stra La stratégie à définir
	*/
	public void setStrategie(Strategie stra) {
		this.strat = stra;
	}

	/* 
	 * Exécute la stratégie pour faire une offre.
	*/
	public void executeStrategieOffre() {

	}

	/* 
	 * Exécute la stratégie pour choisir une carte parmi les cartes des autres joueurs.
	*/
	public void executeStrategieChoix() {

	}

	// a changer avec strategie
	public void faireUneOffre() {
		int numCarte = this.strat.executeFaireUneOffre(this.getCartesDistribuees(), this.getCollection());
		this.ChoisirCarteVisible(numCarte);

	}

	public List<Integer> choisirUneCarte (List<Joueur> j){
		List<Integer> res = this.strat.executeChoisirUneCarte(j, this);
		return res;
	}
}
