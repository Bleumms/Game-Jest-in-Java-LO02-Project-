package Jest;

public class CalculateurScore implements Visitor{

	public CalculateurScore() {
		
	}
	
	@Override
	public void visit(Joueur player) {
		// Calcul du score en fonction des cartes dans la collection du joueur
		int totalScore = 0;
		for (Carte c : player.getCollection()) {
			totalScore += 0; //Dois être remplacé par la méthode de calcul de score de chaque carte
		}
		player.setScore(totalScore);

	}

}
