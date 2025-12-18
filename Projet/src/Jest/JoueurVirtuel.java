package Jest;

import java.util.List;

public class JoueurVirtuel extends Joueur {

	private Strategie strat;

	public JoueurVirtuel(String n, Strategie strategie) {
		super(n);
		this.strat = strategie;
	}

	public void setStrategie(Strategie stra) {
		this.strat = stra;
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
