/*
 * Représente une carte classique avec un numéro (1-4) et un symbole.
 * Les cartes classiques sont les cartes standard du jeu, par opposition au Jocker.
 * 
 * @author Nina et Emeline
 * @see Carte
 * @see Symbole
 */

package Jest;

public class CarteClassique extends Carte {
		
		/* Retourne une représentation textuelle de la carte classique.
		 * @return Une chaîne de caractères représentant la carte classique
		*/
		@Override
		public String toString() {
			return numero + " de " + symbole ;
		}

		/* Numéro de la carte (1 à 4, où 1 représente l'As) */
		public int numero;

		/* Symbole de la carte (COEUR, CARREAU, TREFLE, PIQUE) */
		public Symbole symbole;
		
		/* Constructeur avec paramètres.
		 * Initialise le numéro et le symbole de la carte classique.
		 * @param n Le numéro de la carte (1-4)
		 * @param s Le symbole de la carte
		*/
		public CarteClassique(int n, Symbole s) {
			super();
			this.numero=n;
			this.symbole=s;
			String nom = ""+n+" "+s;
			this.setNom(nom);		
		}
		
		/* Retourne le numéro de la carte.
		 * @return le numéro de la carte
		*/
		public int getNumero() {
			return this.numero;
		}
		
		/* Retourne le symbole de la carte.
		 * @return le symbole de la carte
		*/
		public Symbole getSymbole() {
			return this.symbole;
		}

	}
