package Jest;

import java.util.ArrayList;
import java.util.List;

public class Test {


	public static Reference creerReference(){
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
		return r;
	}

	public static Carte creerCarte(int symb, int num){
		Carte c;
		ConditionVictoire cv=null;
		if (num==0){
			c=new Jocker();
			cv = new ConditionMaxScore(true);
			c.ajouterConditionVictoire(cv);
		} else {
			Symbole s= null;
			switch (symb) {
				case 0:
					s=Symbole.PIQUE;
					switch (num) {
						case 1:
							cv= new ConditionMaxMinSymbole(1,Symbole.TREFLE);
							break;

						case 2:
							cv= new ConditonPlusCarteValeur(3);
							break;

						case 3:
							cv= new ConditonPlusCarteValeur(2);
							break;

						case 4:
							cv= new ConditionMaxMinSymbole(-1,Symbole.TREFLE);
							break;

						default:
							break;
					}
					break;

				case 1:
					s=Symbole.TREFLE;
					switch (num) {
						case 1:
							cv= new ConditionMaxMinSymbole(1,Symbole.PIQUE);
							break;

						case 2:
							cv= new ConditionMaxMinSymbole(-1,Symbole.COEUR);
							break;

						case 3:
							cv= new ConditionMaxMinSymbole(1,Symbole.COEUR);
							break;

						case 4:
							cv= new ConditionMaxMinSymbole(-1,Symbole.PIQUE);
							break;

						default:
							break;
					}
					break;

				case 2:
					s=Symbole.CARREAU;
					switch (num) {
						case 1:
							cv= new ConditonPlusCarteValeur(4);
							break;

						case 2:
							cv= new ConditionMaxMinSymbole(1,Symbole.CARREAU);
							break;

						case 3:
							cv= new ConditionMaxMinSymbole(-1,Symbole.CARREAU);
							break;

						case 4:
							cv= new ConditionMaxScore(false);
							break;

						default:
							break;
					}
					break;

				case 3:
					s=Symbole.COEUR;
					cv= new ConditionJocker();
					break;
			
				default:
					break;
			}
			
			c = new CarteClassique(num, s);
			c.ajouterConditionVictoire(cv);
		}
		return c;
	}

	public static List<Carte> creerToutesCartes(){
		List<Carte> toutesCartes = new ArrayList<Carte>();
		for (int symb=0; symb<4; symb++){
			for (int num=1; num<5 ; num++){
				toutesCartes.add(creerCarte(symb,num));
			}
		}
		toutesCartes.add(creerCarte(0,0));
		return toutesCartes;
	}

	public static List<Carte> creerMiniCartes(){
		List<Carte> toutesCartes = new ArrayList<Carte>();
		for (int symb=0; symb<4; symb++){
			for (int num=1; num<3 ; num++){
				toutesCartes.add(creerCarte(symb,num));
			}
		}
		toutesCartes.add(creerCarte(0,0));
		return toutesCartes;
	}


	public static Jeu creerUnJeu(String type){
		Jeu jeu = new Jeu();

		List<Carte> Cartes=null;
		if (type=="MINI"){
			Cartes = creerMiniCartes();
		} else {
			Cartes = creerToutesCartes();
		}
		jeu.ajouterDesCartes(Cartes);

		Reference r = creerReference();
		jeu.ajouterReference(r);

		return jeu;
	}
	public static void main(String[] args) {
		
		// Le jeu n°1:
		Jeu jeu = creerUnJeu("TOUT");

		// Le jeu n°2 :
		Jeu jeu2 = creerUnJeu("MINI");

		List<Jeu> jeux = new ArrayList<Jeu>();
		jeux.add(jeu);
		jeux.add(jeu2);

		List<Strategie> str = new ArrayList<Strategie>();
		str.add(new StrategieRandom());
		str.add(new StrategieIntelligent());
		

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

		// Dans le menu utiliser la fonction Créer un partie
		m.creerUnePartie(jeux, str);

		// Recuperer la partie pour pouvoir jouer
		Partie p = m.getPartieEnCours();

		// Commencer la partie
		p.initialiserLaPartie();

		// Faire un tour de jeu
		boolean fin =false;
		while (fin == false){
			fin = p.faireUnTourDeJeu();
		}
		p.finDePartie();
		System.out.println(p);
		
	}

}
