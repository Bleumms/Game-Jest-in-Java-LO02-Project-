package Jest;

public class Menu {
	private Partie partieEnCours;
	
	public Menu() {
		
	}
	
	public Partie creerUnePartie() {
		Partie p = new Partie();
		this.partieEnCours= p;
		return p;
	}
}
