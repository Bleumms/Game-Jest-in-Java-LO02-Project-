package Jest;

import Jest.Carte.Carte;
import Jest.Carte.Symbole;

public class CarteClassique extends Carte {
		
		@Override
		public String toString() {
			return numero + " de " + symbole ;
		}

		public int numero;
		public Symbole symbole;
		
		public CarteClassique(int n, Symbole s) {
			super();
			this.numero=n;
			this.symbole=s;
			String nom = ""+n+" "+s;
			this.setNom(nom);		
		}
		
		public int getNumero() {
			return this.numero;
		}
		
		public Symbole getSymbole() {
			return this.symbole;
		}

	}
