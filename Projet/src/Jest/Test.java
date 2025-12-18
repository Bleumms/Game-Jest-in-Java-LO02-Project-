package Jest;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		/* 
		// LES CONDITIONS DE VICTOIRES : CA MARCHE DE OUF
		List<Carte> lc = creerToutesCartes();
		Joueur j1 = new JoueurPhysique("Nina");
		j1.ajouteASaCollection(lc.get(6));
		j1.ajouteASaCollection(lc.get(14));
		j1.ajouteASaCollection(lc.get(1));
		j1.ajouteASaCollection(lc.get(4));
		Joueur j2 = new JoueurPhysique("Emeline");
		j2.ajouteASaCollection(lc.get(10));
		j2.ajouteASaCollection(lc.get(2));
		j2.ajouteASaCollection(lc.get(3));
		j2.ajouteASaCollection(lc.get(5));
		Joueur j3 = new JoueurPhysique("Robo");
		j3.ajouteASaCollection(lc.get(16));
		j3.ajouteASaCollection(lc.get(8));
		j3.ajouteASaCollection(lc.get(13));
		j3.ajouteASaCollection(lc.get(0));

		Reference r = new Reference();
		Regle r1 = new RegleCoeur();
		Regle r2 = new RegleCarreau();
		Regle r3 = new RegleAs();
		Regle r4 = new RegleDoubleNoir();
		Regle r5 = new RegleJocker();
		r.ajouterRegle(r1);
		r.ajouterRegle(r2);
		r.ajouterRegle(r3);
		r.ajouterRegle(r4);
		r.ajouterRegle(r5);

		CalculateurScore cs = new CalculateurScore();
		cs.setReference(r);
		j1.accept(cs);
		j2.accept(cs);
		j3.accept(cs);

		ConditionVictoire cv1 = new ConditionJocker();
		ConditionVictoire cv2 = new ConditionMaxMinSymbole(1,Symbole.TREFLE);
		ConditionVictoire cv3 = new ConditionMaxMinSymbole(-1,Symbole.TREFLE);
		ConditionVictoire cv4 = new ConditionMaxMinSymbole(1,Symbole.CARREAU);
		ConditionVictoire cv5 = new ConditionMaxMinSymbole(-1,Symbole.CARREAU);
		ConditionVictoire cv6 = new ConditionMaxScore(true);
		ConditionVictoire cv8 = new ConditonPlusCarteValeur(3);
		ConditionVictoire cv9 = new ConditonPlusCarteValeur(1);
		ConditionVictoire cv10 = new ConditionMaxMinSymbole(1,Symbole.COEUR);
		ConditionVictoire cv11 = new ConditionMaxScore(false);

		List<Joueur> js = new ArrayList<Joueur>();
		js.add(j1);
		js.add(j2);
		js.add(j3);

		Carte c = c = new CarteClassique(1, Symbole.COEUR);
		c.ajouterConditionVictoire(cv1);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv2);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv3);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv4);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv5);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv6);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		j2.ajouteASaCollection(lc.get(11));
		j2.accept(cs);
		System.out.println(" Joueur gagant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv8);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv9);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv10);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		c.ajouterConditionVictoire(cv11);
		j1.ajouteASaCollection(lc.get(16));
		j1.accept(cs);
		System.out.println(" Joueur gagnant : "+js.get(c.JoueurGagnantCarte(js)).getNom());
		*/

		 
		// Créer un menu

		Menu m = new Menu();

		// Recuperer la partie pour pouvoir jouer
		Partie p = m.getPartieEnCours();
		

		// Commencer la partie
		p.initialiserLaPartie();
		System.out.println("\n\nID : "+p.getID()+"\n");

		boolean fin = p.faireUnTourDeJeu();
		Partie p2 = m.creerUnePartie();
		p2.initialiserLaPartie();
		fin = p2.faireUnTourDeJeu();
		Partie p3 = m.creerUnePartie();
		p3.initialiserLaPartie();
		fin = p3.faireUnTourDeJeu();
		System.out.println("\n\nID : "+p2.getID()+"\n");
		System.out.println("\n\nID : "+p3.getID()+"\n");

		Partie ptest = Partie.charger(0);
		System.out.println(ptest);
		ptest = Partie.charger(11);
		System.out.println(ptest);
		ptest = Partie.charger(2);
		System.out.println(ptest);
		/*// Faire des tours jusquà la fin 
		boolean fin =false;
		while (fin == false){
			fin = p.faireUnTourDeJeu();
		}
		p.finDePartie();
		System.out.println(p);*/
		
	}

}
