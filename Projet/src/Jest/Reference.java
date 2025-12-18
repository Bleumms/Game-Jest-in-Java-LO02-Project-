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
	
	@Override
	public String toString() {
		return regles+"";
	}
	
}


