/*
 * Stratégie de jeu intelligente.
 * Analyse les cartes et applique des priorités :
 * Compléter la collection de Cœurs si Jocker possédé
 * Prendre le Jocker si aucun Cœur possédé
 * Privilégier les cartes noires de haute valeur
 * 
 * @author Nina et Emeline
*/

package Jest.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StrategieIntelligent implements Strategie, Serializable{


	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructeur de la stratégie intelligente
	 * @return Une instance de StrategieIntelligent
	*/
	@Override
	public String toString(){
		return "Stratégie intelligente";
	}

	public String getNom(){
		return "Stratégie intelligente";
	}

	/*
	 * Exécute la stratégie de faire une offre
	 * Il choisit la carte à rendre visible en fonction de sa valeur et de son symbole.
	 * Il privilégie les cartes noires de faible valeur et évite de montrer les Cœurs ou le Jocker.	
	 * @param cartesDistribuées La liste des cartes distribuées au joueur
	 * @param cartesEnCollection La liste des cartes déjà en collection du joueur
	 * @return L'index de la carte qui sera visible
	*/
	@Override
	public int executeFaireUneOffre(List<Carte> cartesDistribuées, List<Carte> cartesEnCollection) {
		int numCarte = 0;
		if (cartesDistribuées.get(0) instanceof Jocker){
			// dans cette stratégie le Jocker reste toujours en carte cachée
			numCarte = 1;
		}
		else if (cartesDistribuées.get(1) instanceof Jocker){
			numCarte = 0;
		} else {
			CarteClassique cc0 = (CarteClassique) cartesDistribuées.get(0);
			CarteClassique cc1 = (CarteClassique) cartesDistribuées.get(1);
			// si les deux sont des cartes noires
			if (Symbole.TREFLE.compareTo(cc0.getSymbole())>=0 && Symbole.TREFLE.compareTo(cc1.getSymbole())>=0){
				if (cc0.getNumero()<cc1.getNumero()){
					numCarte = 0;
				}
				numCarte = 1;
			} else {
				// sinon on rend visible celle avec le symbole le plus bas (forcément rouge)
				if (cc1.getSymbole().compareTo(cc0.getSymbole())<0){
					numCarte=0;
				} else if (cc1.getSymbole().compareTo(cc0.getSymbole())>0){
					numCarte=1;
				} else {
					// si même symbole, forcément rouge, on montre le plus gros chiffre ( puisque points négatifs )
					if (cc0.getNumero()<cc1.getNumero()){
						numCarte = 1;
					} else {
						numCarte=0;
					}
				}
			}
		}
		return numCarte;
	}

	/*
	 * Exécute la stratégie de choisir une carte parmi les cartes proposées par les autres joueurs
	 * Détermine la carte qui correspond le plus aux priorités définies.
	 * @param joueurs La liste des joueurs en jeu
	 * @param moiMeme Le joueur virtuel qui exécute la stratégie
	 * @return Une liste contenant l'ID du joueur choisi et l'index de la carte choisie
	*/
	@Override
	public List<Integer> executeChoisirUneCarte(List <Joueur> joueurs, Joueur moiMeme) {
		boolean aUnJocker = false;
		int nbCoeurs = 0;
		List<Carte> cartesEnCollection = moiMeme.getCollection();
		for (int j=0; j<cartesEnCollection.size(); j++){
			if (cartesEnCollection.get(j) instanceof Jocker){
				aUnJocker=true;
			} else {
				CarteClassique cc = (CarteClassique) cartesEnCollection.get(j);
				if (cc.getSymbole()==Symbole.COEUR){
					nbCoeurs++;
				}
			}
		}
		// si j'ai un Jocker on évite les coeurs SAUF si j'ai déjà 3 coeurs
		boolean interesseParCoeur = (aUnJocker && nbCoeurs==3);		

		List<Integer> res = new ArrayList<Integer>();
		if (joueurs.size()==1 && joueurs.contains(moiMeme)){
			res.add(moiMeme.getID());
			res.add(executeChoisiUneDeSesCartes(moiMeme, aUnJocker, nbCoeurs, interesseParCoeur));
		} else {
			List <Joueur> joueursSansMoi = new ArrayList<Joueur>(joueurs);
			// pour pas prendre dans sa propre offre
			joueursSansMoi.remove(moiMeme);
			
			int numJoueur = 0;
			int numCarte = 0;
			Carte max = null;
			int prioTrouvee = 10;
			// on va chercher parmis les cartes visibles la carte qui correspond le plus aux priorités, sinon au hasard parmis les cachées
			for (int i=0; i<joueursSansMoi.size(); i++){
				Carte c = joueursSansMoi.get(i).getCarteVisible();
				// PRIORITE 1 : si on a le jocker et qu'on peut finir la collection des coeurs
				if (interesseParCoeur && c instanceof CarteClassique){
					CarteClassique cc = (CarteClassique) c;
					if (cc.getSymbole()==Symbole.COEUR){
						max = c;
						prioTrouvee = 1;
						numJoueur = i;
					}
				}
				if (prioTrouvee>1){
					// PRIORITE 2: si le jocker est intéressant à prendre on le prend.
					if (c instanceof Jocker && nbCoeurs==0){ // seule possibilité pour qu'il veuille bien d'un jocker
						max = c;
						prioTrouvee = 2;
						numJoueur = i; // ça sera ré-ajusté plus tard celon la position de "moi"
					}
				}
				if (prioTrouvee>2){
					// PRIORITE 3: c'est une carte noire plus élevée que celle que j'ai potentionnellement
					if (c instanceof CarteClassique){
						CarteClassique cc = (CarteClassique) c;
						if( Symbole.TREFLE.compareTo(cc.getSymbole())>=0){  //donc carte noire
							if (max==null || prioTrouvee>3){ // pas défini ou défini mais un coeur
								max = c;
								prioTrouvee = 3;
								numJoueur = i;
							} else if (prioTrouvee==3){
								CarteClassique ccmax = (CarteClassique) max;
								if (cc.getNumero()>ccmax.getNumero()){
									max = c;
									numJoueur = i;
								}
							}
						}
					}
				}
			}
			// si on en trouvé aucune qui correspond on fait au piff
			if (prioTrouvee==10){
				numCarte=1;
				double alea = Math.random()*joueursSansMoi.size();
				numJoueur = Double.valueOf(alea).intValue();
			}
		
			// récupère son ID 
			int idJoueur = joueursSansMoi.get(numJoueur).getID();
		
			res.add(idJoueur);
			res.add(numCarte);
		}
		return res;
	}

	/*
	 * Exécute la stratégie de choisir une carte parmi ses propres cartes
	 * Il choisit en fonction de ses besoins et priorités :
	 * Prendre un Cœur si intéressé par les Cœurs
	 * Prendre le Jocker si aucun Cœur possédé
	 * Privilégier les cartes noires de haute valeur
	 * @param moiMeme Le joueur virtuel qui exécute la stratégie
	 * @param aUnJocker Indique si le joueur possède un Jocker dans sa collection
	 * @param nbCoeurs Le nombre de Cœurs dans la collection du joueur
	 * @param interesseParCoeur Indique si le joueur est intéressé par les Cœurs
	 * @return L'index de la carte choisie
	*/
	private int executeChoisiUneDeSesCartes(Joueur moiMeme, boolean aUnJocker, int nbCoeurs,  boolean interesseParCoeur){
		Carte cV = moiMeme.getCarteVisible();
		Carte cC = moiMeme.getCarteCachee();
		// PRIORITE 1
		if (interesseParCoeur) {
			if (cV instanceof CarteClassique){
				CarteClassique ccV = (CarteClassique) cV;
				if (ccV.getSymbole()==Symbole.COEUR){
					return 0;
				}
			}
			if (cC instanceof CarteClassique){
				CarteClassique ccC = (CarteClassique) cC;
				if (ccC.getSymbole()==Symbole.COEUR){
					return 1;
				}
			}
		}

		// PRIORITE 2
		if (nbCoeurs==0) {
			if (cV instanceof Jocker){
				 return 0;
			}
			if (cC instanceof Jocker){
				return 1;
			}
		}
		
		if (cV instanceof CarteClassique ){
			CarteClassique ccV = (CarteClassique) cV;
			if (Symbole.TREFLE.compareTo(ccV.getSymbole())>=0){
				if (cC instanceof CarteClassique){
					CarteClassique ccC = (CarteClassique) cC;
					if (Symbole.TREFLE.compareTo(ccC.getSymbole())>=0 && ccC.getNumero()>ccV.getNumero()){
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			} else {
				if (cC instanceof CarteClassique){
					CarteClassique ccC = (CarteClassique) cC;
					if (Symbole.TREFLE.compareTo(ccC.getSymbole())>=0){
						return 1;
					}
					if(ccV.getSymbole()==Symbole.COEUR && (ccC.getSymbole()!=Symbole.COEUR || (ccC.getSymbole()==Symbole.COEUR && ccC.getNumero()>ccV.getNumero()))){
						return 0;
					} 
					if (ccV.getSymbole()==Symbole.CARREAU ){
						if (ccC.getSymbole()!=Symbole.CARREAU){
							return 1;
						} else {
							if (ccV.getNumero()<ccC.getNumero()){
								return 0;
							}
							else {
								return 1;
							}
						}
					}
					return 1;
				} else  {
					return 0;
				}
			}
		} else {
			return 1;
		}
	}
}
