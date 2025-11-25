package Jest;

public class Test {

	public static void main(String[] args) {
		Menu m = new Menu();
		Partie p = m.creerUnePartie();
		Jeu jeu = new Jeu();
		for (char j = 'A'; j < 'E'; j++) {
			for (int i = 1; i < 5; i++) {
				String n ="";
				n=n+j;
				n=n+i;
				Carte c = new Carte(n);
				jeu.ajouterCarte(c);
			}
		}
		p.choisirUnJeu(jeu);
		Joueur j1 = new Joueur("Nina");
		Joueur j2 = new Joueur("Emeline");
		p.ajouterUnJoueur(j1);
		p.ajouterUnJoueur(j2);
		System.out.println(p);
		System.out.println();
		p.choisirLesTrophes(2);
		p.distribuer();
		System.out.println(p);
		System.out.println();
		
	}

}
