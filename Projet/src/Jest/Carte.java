package Jest;

import java.util.List;

public class Carte {
	private String nom;
	private ConditionVictoire condition;
	
	public Carte(String n) {
		nom=n;
	}
	
	public Carte() {
		nom="";
	}
	
	public void setNom(String n) {
		this.nom=n;
	}
	
	public void ajouterConditionVictoire(ConditionVictoire cv) {
		this.condition = cv;
	}

	@Override
	public String toString() {
		return "Carte [nom=" + nom + "]";
	}
	
	public int JoueurGagnantCarte(List<Joueur> joueurs) {
		return this.condition.VerificationVictoire(joueurs);
	}

	public String getNom(){
		return this.nom;
	}
	
}
