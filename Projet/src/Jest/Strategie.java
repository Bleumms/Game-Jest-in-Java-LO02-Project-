package Jest;

import java.util.List;

public interface Strategie {
	
	int executeFaireUneOffre (List <Carte> cartesDistribuées, List <Carte> cartesEnCollection);
	//renvoie l'index de la carte qui sera visible

	List<Integer> executeChoisirUneCarte (List <Joueur> joueurs, Joueur moiMeme);

	public String toString();
}