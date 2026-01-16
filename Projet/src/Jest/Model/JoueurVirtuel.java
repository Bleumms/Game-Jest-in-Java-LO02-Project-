/*
 * Représente un joueur contrôlé par l'ordinateur.
 * Utilise une stratégie pour prendre ses décisions.
 * 
 * @author Nina et Emeline
 * @version 1.0
 * @see Joueur
 * @see Strategie
*/

package Jest.Model;

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
	public JoueurVirtuel(String n, int i, Strategie strategie) {
		super(n,i);
		this.strat = strategie;
	}

	/* 
	 * Permet au joueur virtuel d'attendre une offre des autres joueurs
	*/
	public void attendreUneOffre(){
		this.faireUneOffre();
	}
	
	/* 
	 * Permet au joueur virtuel de choisir un joueur
	 * @param joueurs La liste des joueurs en compétition
	*/
	public void attendreUnChoix(List<Joueur> joueurs){
		this.choix = choisirUneCarte(joueurs);
		this.etat=EtatJoueur.ChoixFait;
		this.setChanged();
		this.notifyObservers("choix fait");
	}

	/* 
	 * Définit la stratégie du joueur virtuel.
	 * @param stra La stratégie à définir
	*/
	public void setStrategie(Strategie stra) {
		this.strat = stra;
	}

	/* 
	 * Fait une offre en utilisant la stratégie définie.
	*/
	public void faireUneOffre() {
		int numCarte = this.strat.executeFaireUneOffre(this.getCartesDistribuees(), this.getCollection());
		this.choisirCarteVisible(numCarte);
	}

	/* 
	 * Choisit une carte parmi les cartes des autres joueurs en utilisant la stratégie définie.
	 * @param j La liste des autres joueurs
	 * @return La liste des indices des cartes choisies
	*/
	public List<Integer> choisirUneCarte (List<Joueur> j){
		List<Integer> res = this.strat.executeChoisirUneCarte(j, this);
		return res;
	}
}
