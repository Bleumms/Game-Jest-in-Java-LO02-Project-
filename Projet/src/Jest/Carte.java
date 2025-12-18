package Jest;

import java.io.Serializable;
import java.util.List;

public class Carte implements Serializable{

    private static final long serialVersionUID = 1L;

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
		return "Carte "+this.nom;
	}
	
	public int JoueurGagnantCarte(List<Joueur> joueurs) {
		return this.condition.VerificationVictoire(joueurs);
	}

	public String getNom(){
		return this.nom;
	}

	public ConditionVictoire getConditionVictoire(){
		return this.condition;
	}
	
}
