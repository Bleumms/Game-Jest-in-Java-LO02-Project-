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
		if (num==0){
			c=new Jocker();
		} else {
			Symbole s= Symbole.PIQUE;
			if (symb==1){
				s=Symbole.TREFLE;
			} else {
				if (symb==2){
					s=Symbole.CARREAU;
				} else {
					if (symb==3){
						s=Symbole.COEUR;
					}
				}
			}
			c = new CarteClassique(num, s);
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
		p.calculScore();
		System.out.println(p);
	}

}
