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
		System.out.println("On cherche son score");
		int totalScore = calculScore(player.getCollection());
		System.out.println("Son score est de "+totalScore);
		player.setScore(totalScore);
		System.out.println("Le joueur a donc bien attribué son score à "+player.getScore()+"\n\n\n");
	}

	public int calculScore(List<Carte> collection) {
		int somme=0;
		List<ValeurParCarte> valeurs = new ArrayList<ValeurParCarte>();
		for (int c=0; c<collection.size(); c++) {
			// chaque carte aura une valeur attitrée
			ValeurParCarte val = new ValeurParCarte(collection.get(c));
			valeurs.add(val);
		}
		System.out.println("\n!! D'abord : \n   La liste de ses cartes : "+collection+"\n La liste des valeurs : "+valeurs);
		List<Regle> regles  = ref.getRegles();
		for (int r=0; r<regles.size();r++) {
			System.out.println("\nRègle : "+regles.get(r));
			regles.get(r).modifierValeurCarte(collection, valeurs);
			System.out.println("Nouvelle liste de carte : "+valeurs);
		}
		for (int i=0; i<valeurs.size(); i++) {
			somme = somme + valeurs.get(i).getValeur();
		}
		System.out.println("Le score total donne donc : "+somme);
		return somme;
	}

}
