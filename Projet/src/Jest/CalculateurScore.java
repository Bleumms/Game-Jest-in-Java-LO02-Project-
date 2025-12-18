package Jest;

import java.util.ArrayList;
import java.util.List;

public class CalculateurScore implements Visitor{

	private Reference ref;

	public CalculateurScore() {
		
	}

	public void setReference(Reference r){
		this.ref = r;
	}
	
	@Override
	public void visit(Joueur player) {
		// Calcul du score en fonction des cartes dans la collection du joueur
		int totalScore = calculScore(player.getCollection());
		player.setScore(totalScore);
	}

	public int calculScore(List<Carte> collection) {
		int somme=0;
		List<ValeurParCarte> valeurs = new ArrayList<ValeurParCarte>();
		for (int c=0; c<collection.size(); c++) {
			// chaque carte aura une valeur attitrée
			ValeurParCarte val = new ValeurParCarte(collection.get(c));
			valeurs.add(val);
		}
		List<Regle> regles  = ref.getRegles();
		for (int r=0; r<regles.size();r++) {
			regles.get(r).modifierValeurCarte(collection, valeurs);
		}
		for (int i=0; i<valeurs.size(); i++) {
			somme = somme + valeurs.get(i).getValeur();
		}
		return somme;
	}

}
