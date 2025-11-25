package Jest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Partie {
	private Date dateHeureDeCreation;
	private List<Joueur> participants;   // surement plutôt ArrayList
	private Jeu jeu;
	private List<Carte> pioche;
	private List<Carte> trophe;

	public Partie() {
		this.dateHeureDeCreation = new Date();
		this.participants = new ArrayList<Joueur>() ;
		this.pioche = new ArrayList<Carte>() ;
		this.trophe = new ArrayList<Carte>() ;
	}

	public void ajouterUnJoueur(Joueur j) {
		this.participants.add(j);
	}
	
	public void choisirUnJeu(Jeu j) {
		this.jeu= j;
	}
	
	public void choisirLesTrophes(int nbTrophes) {
		List<ArrayList<Carte>> listes = jeu.choisirTrophe(nbTrophes);
		this.trophe= listes.get(0);
		this.pioche= listes.get(1);
		/*for (int i=0; i<listes.get(0).size(); i++) {
			trophe.add(listes.get(0).get(i));
		}
		for (int j=0; j<listes.get(1).size(); j++) {
			pioche.add(listes.get(1).get(j));
		}*/
	}
	
	public void melangerLaPioche() {
		Collections.shuffle(pioche);
	}
	
	public void distribuer() {
		for (int i=0; i<participants.size(); i++) {
			Joueur j = participants.get(i);
			j.assignerCarteDistribuees(pioche.remove(0), pioche.remove(0));
		}
	}

	@Override
	public String toString() {
		return "Partie :\ndateHeureDeCreation=" + dateHeureDeCreation + ", \nparticipants=" + participants + ", \njeu=" + jeu
				+ ", \npioche=" + pioche + ", \ntrophe=" + trophe;
	}
}
