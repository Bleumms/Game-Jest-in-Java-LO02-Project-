package Jest;

import java.util.List;

public interface Strategie {
	
	List <Carte> executeOffre (List <Carte> cartesDistribuées, List <Carte> cartesEnCollection);
	Joueur executeChoix (List <Joueur> cartesAuChoix, List <Carte> cartesEnCollection, Joueur j, Carte c);
	//Doit aussi renvoyer le num de la carte choisie...
}
