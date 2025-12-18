package Jest;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		/*
		 * Menu m = new Menu(); Partie p = m.creerUnePartie(); Jeu jeu = new Jeu(); for
		 * (char j = 'A'; j < 'E'; j++) { for (int i = 1; i < 5; i++) { String n ="";
		 * n=n+j; n=n+i; Carte c = new Carte(n); jeu.ajouterCarte(c); } }
		 * p.choisirUnJeu(jeu); Joueur j1 = new Joueur("Nina"); Joueur j2 = new
		 * Joueur("Emeline"); p.ajouterUnJoueur(j1); p.ajouterUnJoueur(j2);
		 * System.out.println(p); System.out.println(); p.choisirLesTrophes(2);
		 * p.distribuer(); System.out.println(p); System.out.println();
		 */

		// Ce qui devrai etre stocké déjà de base : plusieurs jeux de cartes dispo
		Carte c0 = new Jocker();
		Carte c1 = new CarteClassique(1, Symbole.COEUR);
		Carte c2 = new CarteClassique(2, Symbole.COEUR);
		Carte c3 = new CarteClassique(3, Symbole.COEUR);
		Carte c4 = new CarteClassique(4, Symbole.COEUR);
		Carte c5 = new CarteClassique(1, Symbole.TREFLE);
		Carte c6 = new CarteClassique(2, Symbole.TREFLE);
		Carte c7 = new CarteClassique(3, Symbole.TREFLE);
		Carte c8 = new CarteClassique(4, Symbole.TREFLE);
		Carte c9 = new CarteClassique(1, Symbole.PIQUE);
		Carte c10 = new CarteClassique(2, Symbole.PIQUE);
		Carte c11 = new CarteClassique(3, Symbole.PIQUE);
		Carte c12 = new CarteClassique(4, Symbole.PIQUE);
		Carte c13 = new CarteClassique(1, Symbole.CARREAU);
		Carte c14 = new CarteClassique(2, Symbole.CARREAU);
		Carte c15 = new CarteClassique(3, Symbole.CARREAU);
		Carte c16 = new CarteClassique(4, Symbole.CARREAU);

		ConditionVictoire v = new ConditionMaxScore();
		c6.ajouterConditionVictoire(v);
		c3.ajouterConditionVictoire(v);
		c7.ajouterConditionVictoire(v);
		c15.ajouterConditionVictoire(v);

		List<Carte> ToutesCartes = new ArrayList<Carte>();
		ToutesCartes.add(c0);
		ToutesCartes.add(c1);
		ToutesCartes.add(c2);
		ToutesCartes.add(c3);
		ToutesCartes.add(c4);
		ToutesCartes.add(c5);
		ToutesCartes.add(c6);
		ToutesCartes.add(c7);
		ToutesCartes.add(c8);
		ToutesCartes.add(c9);
		ToutesCartes.add(c10);
		ToutesCartes.add(c11);
		ToutesCartes.add(c12);
		ToutesCartes.add(c13);
		ToutesCartes.add(c14);
		ToutesCartes.add(c15);
		ToutesCartes.add(c16);

		// Le jeu n°1:
		Jeu jeu = new Jeu();
		jeu.ajouterDesCartes(ToutesCartes);
		Reference r = new Reference();
		jeu.ajouterReference(r);
		Regle r1 = new RegleCoeur();
		Regle r2 = new RegleCarreau();
		Regle r3 = new RegleAs();
		Regle r4 = new RegleDoubleNoir();
		Regle r5 = new RegleJocker();
		jeu.ajouterRegle(r1);
		jeu.ajouterRegle(r2);
		jeu.ajouterRegle(r3);
		jeu.ajouterRegle(r4);
		jeu.ajouterRegle(r5);

		List<Carte> MiniJeu = new ArrayList<Carte>();
		ToutesCartes.add(c0);
		ToutesCartes.add(c1);
		ToutesCartes.add(c2);
		ToutesCartes.add(c3);
		ToutesCartes.add(c4);
		ToutesCartes.add(c9);
		ToutesCartes.add(c10);
		ToutesCartes.add(c11);
		ToutesCartes.add(c12);

		// Le jeu n°2 :
		Jeu jeu2 = new Jeu();
		jeu.ajouterDesCartes(MiniJeu);

		List<Jeu> jeux = new ArrayList<Jeu>();
		jeux.add(jeu);
		jeux.add(jeu2);

		List<Strategie> str = new ArrayList<Strategie>();
		str.add(new StrategieRandom());
		str.add(new StrategieIntelligent());
		
		/*
		Strategie s = new StrategieIntelligent();
		Joueur jv1 = new JoueurVirtuel("robo1", s);
		Joueur jv2 = new JoueurVirtuel("robo2", s);
		Joueur jv3 = new JoueurVirtuel("robo3", s);
		Joueur jv4 = new JoueurVirtuel("robo4", s);
		Joueur jv5 = new JoueurVirtuel("robo5", s);
		Joueur jv6 = new JoueurVirtuel("robo6", s);
		Joueur jv7 = new JoueurVirtuel("robo7", s);
		Joueur jv8 = new JoueurVirtuel("robo8", s);

		jv1.assignerCarteDistribuees(c0);
		jv1.assignerCarteDistribuees(c13);
		jv1.faireUneOffre();
		System.out.println("\n"+jv1.getCarteVisible());
		jv2.assignerCarteDistribuees(c10);
		jv2.assignerCarteDistribuees(c0);
		jv2.faireUneOffre();
		System.out.println("\n"+jv2.getCarteVisible());
		jv3.assignerCarteDistribuees(c10);
		jv3.assignerCarteDistribuees(c5);
		jv3.faireUneOffre();
		System.out.println("\n"+jv3.getCarteVisible());
		jv4.assignerCarteDistribuees(c9);
		jv4.assignerCarteDistribuees(c14);
		jv4.faireUneOffre();
		System.out.println("\n"+jv4.getCarteVisible());
		jv5.assignerCarteDistribuees(c2);
		jv5.assignerCarteDistribuees(c6);
		jv5.faireUneOffre();
		System.out.println("\n"+jv5.getCarteVisible());
		jv6.assignerCarteDistribuees(c2);
		jv6.assignerCarteDistribuees(c14);
		jv6.faireUneOffre();
		System.out.println("\n"+jv6.getCarteVisible());
		jv7.assignerCarteDistribuees(c15);
		jv7.assignerCarteDistribuees(c3);
		jv7.faireUneOffre();
		System.out.println("\n"+jv7.getCarteVisible());
		jv8.assignerCarteDistribuees(c15);
		jv8.assignerCarteDistribuees(c14);
		jv8.faireUneOffre();
		System.out.println("\n"+jv8.getCarteVisible());
		*/

		/*
		// TEST JOUEUR VIRTUEL
		// je crée mon joueur
		Strategie s = new StrategieIntelligent();
		Joueur jv = new JoueurVirtuel("robo", s);

		// je crée des joueurs concurents
		Joueur adv1 = new JoueurPhysique("Nina");
		Joueur adv2 = new JoueurPhysique("Emeline");
		Joueur adv3 = new JoueurVirtuel("robo2",s);
		Joueur adv4 = new JoueurPhysique("Yassine");

		// on lui distribue des cartes pour qu'il fasse son offre
		jv.assignerCarteDistribuees(c14);
		jv.assignerCarteDistribuees(c13);
		jv.addCollection(c1);
		jv.addCollection(c3);
		jv.addCollection(c4);
		jv.addCollection(c0);
		System.out.print(jv);
		jv.faireUneOffre();
		System.out.print(jv); // ça marche

		//On attribut des cartes aux autres 
		adv1.assignerCarteDistribuees(c2);
		adv1.assignerCarteDistribuees(c9);
		adv1.ChoisirCarteVisible(0);
		adv2.assignerCarteDistribuees(c16);
		adv2.assignerCarteDistribuees(c8);
		adv2.ChoisirCarteVisible(0);
		adv3.assignerCarteDistribuees(c14);
		adv3.assignerCarteDistribuees(c6);
		adv3.ChoisirCarteVisible(0);
		adv4.assignerCarteDistribuees(c10);
		adv4.assignerCarteDistribuees(c11);
		adv4.ChoisirCarteVisible(0);
		List<Joueur> l = new ArrayList<Joueur>();
		l.add(adv1);
		l.add(adv2);
		l.add(jv);
		l.add(adv3);
		l.add(adv4);
		System.out.print(jv.choisirUneCarte(l));
		
		

		List<Joueur> l2 = new ArrayList<Joueur>();
		l2.add(jv);
		System.out.print(jv.choisirUneCarte(l2)); //ça marche
		*/

		
		/*
		// TEST VISITOR
		Joueur joueurTest = new Joueur("Test");
		Visitor vis = new CalculateurScore();
		joueurTest.accept(vis);
		System.out.print(joueurTest.getScore());
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
		p.calculScore();
		System.out.println(p);
	}

}
