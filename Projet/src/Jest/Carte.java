package Jest;

public class Carte {
	private String nom;
	
	public Carte(String n) {
		nom=n;
	}

	@Override
	public String toString() {
		return "Carte [nom=" + nom + "]";
	}
}
