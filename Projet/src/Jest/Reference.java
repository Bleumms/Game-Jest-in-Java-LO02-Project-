package Jest;

import java.util.ArrayList;
import java.util.List;

public class Reference {


	public List<Regle> regles;

	public Reference() {
		this.regles = new ArrayList<Regle>();
	}
	
	// ajouter vérification qu'une regle n'est pas 2 fois dans la liste
	public void ajouterRegle(Regle r) {
		this.regles.add(r);
	}
	
	public List<Regle> getRegles(){
		return regles;
	}
	
	public int calculScore(List<Carte> collection) {
		int somme=0;
		List<ValeurParCarte> valeurs = new ArrayList<ValeurParCarte>();
		for (int c=0; c<collection.size(); c++) {
			// chaque carte aura une valeur attitrée
			ValeurParCarte val = new ValeurParCarte(collection.get(c));
			valeurs.add(val);
		}
		for (int r=0; r<this.regles.size();r++) {
			this.regles.get(r).modifierValeurCarte(collection, valeurs);
		}
		for (int i=0; i<valeurs.size(); i++) {
			somme= somme + valeurs.get(i).getValeur();
		}
		return somme;
	}
	
	@Override
	public String toString() {
		return regles+"";
	}
	
}
