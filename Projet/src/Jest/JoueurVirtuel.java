package Jest;

public class JoueurVirtuel extends Joueur{

	private Strategie strat;
	
	public JoueurVirtuel (String n, Strategie strategie) {
		super (n);
		this.strat = strategie;
	}
	
	public void setStrategie (Strategie stra) {
		this.strat = stra;
	}
	
	public void executeStrategieOffre() {
		
	}
	
	public void executeStrategieChoix() {
		
	}
}
