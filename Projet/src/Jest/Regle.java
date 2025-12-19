package Jest;

import java.io.Serializable;
import java.util.List;

public class Regle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Regle";
	}

	public Regle() {
		// TODO Auto-generated constructor stub
	}

	public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
		
	}

	/*
	public List<Integer> modifierValeurCarte(Carte c, List<Carte> toutesLesCartes) {
		List<Integer> res = new ArrayList<Integer>();
		if (c instanceof CarteClassique) {
			int num = c.getNumero();
			res.add(num);
			res.add(0);
			return res;
		} else {
			res.add(4);
			res.add(0);
			return res;
		}
	}
	*/
}