package Jest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Jest.Carte.Carte;
import Jest.Carte.CarteClassique;
import Jest.Carte.Jocker;
import Jest.Carte.Symbole;

public class Jeu {

	private List<Carte> cartes;
	private Reference ref;

	public Jeu() {
		this.cartes = new ArrayList<Carte>();
	}

	public void ajouterCarte(Carte c) {
		this.cartes.add(c);
	}

	public void ajouterDesCartes(List<Carte> c) {
		for (int i = 0; i < c.size(); i++) {
			this.cartes.add(c.get(i));
		}
	}
	
	public Reference getReference() {
		return this.ref;
	}

	@Override
	public String toString() {
		return "Jeu :  \n  Possède : " + cartes + "\n  Référence : "+ref;
	}

	public void ajouterReference(Reference r) {
		this.ref = r;
	}

	public List<ArrayList<Carte>> choisirTrophe(int nbTrophes) {
		Collections.shuffle(cartes);
		ArrayList<Carte> t = new ArrayList<Carte>();
		ArrayList<Carte> p = new ArrayList<Carte>();
		for (int i = 0; i < nbTrophes; i++) {
			Carte c = this.cartes.get(i);
			t.add(c);
		}
		for (int j = nbTrophes; j < cartes.size(); j++) {
			Carte c = this.cartes.get(j);
			p.add(c);
		}
		List<ArrayList<Carte>> res = new ArrayList<ArrayList<Carte>>();
		res.add(t);
		res.add(p);
		return res;
	}

	public void ajouterRegle(Regle r) {
		this.ref.ajouterRegle(r);
	}

	public List<Regle> getRegles() {
		return ref.getRegles();
	}
	
	public List<Carte> getCartes() {
		return cartes;
	}

	public boolean estSupperieur(Carte c1, Carte c2) {
		if (c2 instanceof Jocker) {// on choisi de définir que le jocker commence toujours
			return false;
		}
		// pour que ce soit plus lisible je n'utilise pas de else ici, 
		// on sort de toute facon de la méthode à chaque return rencontré.
		if (c1 instanceof Jocker) { 
			return true;
		}		
		// aucun Jocker donc
		CarteClassique cc1 = (CarteClassique) c1;
		CarteClassique cc2 = (CarteClassique) c2;
		// si la carte est noire
		if (cc1.getSymbole() == Symbole.PIQUE || cc1.getSymbole() == Symbole.TREFLE) {
			// la carte est donc forcément suppérieur aux cartes rouges
			if (cc2.getSymbole() == Symbole.CARREAU || cc1.getSymbole() == Symbole.COEUR) {
				return true;
			}
			// si l'autre carte n'est pas rouge on compare le numéro de la carte
			if (cc1.getNumero()>cc2.getNumero()) {
				return true;
			}
			// si elles ont le même numéro on compare leur symboles
			if (cc1.getNumero()==cc2.getNumero()) {
				if (cc2.getSymbole().compareTo(cc1.getSymbole())>0) {
					return true;
				}
				return false;
			}
		}
		// les coeurs sont a part : même si c'est une carte rouge elle ne compte rien (même pas négative)
		// dans ce cas soit ce sont deux cartes rouges et priorité à celle qui a le plus petit chiffre 
		// (on la traite quand même comme une carte rouge), soit l'autre carte n'est pas un coeur alors elle est forcément inférieur
		if (cc1.getSymbole() == Symbole.COEUR) {
			if (cc2.getSymbole() == Symbole.COEUR && cc2.getNumero()<cc1.getNumero()) {
				return true;
			}
			return false;
		}
		// si c'est un carreau
		// elle est inférieur aux cartes noires
		if (cc2.getSymbole() == Symbole.PIQUE || cc2.getSymbole() == Symbole.TREFLE) {
			return false;
		}
		// elle est suppérieure aux coeurs
		if (cc2.getSymbole() == Symbole.COEUR) {
			return true;
		}
		// sinon ça dépend du numéro
		if (cc1.getNumero()>cc2.getNumero()) {
			return true;
		}
		return false;
	}
}
