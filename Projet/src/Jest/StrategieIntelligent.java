package Jest;

import java.util.ArrayList;
import java.util.List;

public class StrategieIntelligent implements Strategie{

	@Override
	public int executeFaireUneOffre(List<Carte> cartesDistribuées, List<Carte> cartesEnCollection) {
		int numCarte = 0;
		if (cartesDistribuées.get(0) instanceof Jocker){
			// dans cette stratégie le Jocker reste toujours en carte cachée
			numCarte = 1;
		}
		if (cartesDistribuées.get(1) instanceof Jocker){
			numCarte = 0;
		}
		CarteClassique cc0 = (CarteClassique) cartesDistribuées.get(0);
		CarteClassique cc1 = (CarteClassique) cartesDistribuées.get(1);
		// si les deux sont des cartes noires
		if (Symbole.TREFLE.compareTo(cc0.getSymbole())>=0 && Symbole.TREFLE.compareTo(cc1.getSymbole())>=0){
			if (cc0.getNumero()<cc1.getNumero()){
				numCarte = 0;
			}
			numCarte = 1;
		}
		// sinon on rend visible celle avec le symbole le plus bas (forcément rouge)
		if (cc1.getSymbole().compareTo(cc0.getSymbole())<0){
			numCarte=0;
		}
		if (cc1.getSymbole().compareTo(cc0.getSymbole())>0){
			numCarte=1;
		}
		// si même symbole, forcément rouge, on montre le plus gros chiffre ( puisque points négatifs )
		if (cc0.getNumero()<cc1.getNumero()){
			numCarte = 1;
		}
		return numCarte;
	}

	@Override
	public List<Integer> executeChoisirUneCarte(List <Joueur> joueurs, Joueur moiMeme) {
		System.out.println("On va choisir zé partiiiii");
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

		System.out.println("Donc NORMALEMENT dans ma collection, j'ai un jocker ? "+ aUnJocker+", j'ai "+nbCoeurs+" coeurs et un coeur m'interresserais spécialement ?"+interesseParCoeur);

		List<Integer> res = new ArrayList<Integer>();
		if (joueurs.size()==1 && joueurs.contains(moiMeme)){
			System.out.println("Je joue toute seule wsh, ça c'est parce que dans la liste des joueurs y a "+joueurs);
			res.add(0);
			res.add(executeChoisiUneDeSesCartes(moiMeme, aUnJocker, nbCoeurs, interesseParCoeur));
		} else {
			List <Joueur> joueursSansMoi = new ArrayList<Joueur>(joueurs);
			// pour pas prendre dans sa propre offre
			joueursSansMoi.remove(moiMeme);
			System.out.println("Je joue contre : "+joueursSansMoi+" \n a la base y avait : "+joueurs);
			
			int numJoueur = 0;
			int numCarte = 0;
			Carte max = null;
			int prioTrouvee = 10;
			// on va chercher parmis les cartes visibles la carte qui correspond le plus aux priorités, sinon au hasard parmis les cachées
			for (int i=0; i<joueursSansMoi.size(); i++){
				Carte c = joueursSansMoi.get(i).getCarteVisible();
				System.out.println("\nTOUR "+i+"  :  on compare la carte "+c+" de  "+joueursSansMoi.get(i)+" à la carte max : "+max);
				// PRIORITE 1 : si on a le jocker et qu'on peut finir la collection des coeurs
				if (interesseParCoeur && c instanceof CarteClassique){
					CarteClassique cc = (CarteClassique) c;
					if (cc.getSymbole()==Symbole.COEUR){
						max = c;
						prioTrouvee = 1;
						numJoueur = i;
						System.out.println("Prio 1 ! Maintenant carte max : "+max);
					}
				}
				if (prioTrouvee>1){
					// PRIORITE 2: si le jocker est intéressant à prendre on le prend.
					if (c instanceof Jocker && nbCoeurs==0){ // seule possibilité pour qu'il veuille bien d'un jocker
						max = c;
						prioTrouvee = 2;
						numJoueur = i; // ça sera ré-ajusté plus tard celon la position de "moi"
						System.out.println("Prio 2 ! Maintenant carte max : "+max);
					}
				}
				if (prioTrouvee>2){
					// PRIORITE 3: c'est une carte noire plus élevée que celle que j'ai potentionnellement
					if (c instanceof CarteClassique){
						CarteClassique cc = (CarteClassique) c;
						if( Symbole.TREFLE.compareTo(cc.getSymbole())>=0){  //donc carte noire
							System.out.println("C'est une carte noire ");
							if (max==null || prioTrouvee>3){ // pas défini ou défini mais un coeur
								System.out.println("On prend dans tous les cas -ca ahahah ");
								max = c;
								prioTrouvee = 3;
								numJoueur = i;
								System.out.println("Prio 3 ! Maintenant carte max : "+max);
							} else if (prioTrouvee==3){
								CarteClassique ccmax = (CarteClassique) max;
								System.out.println("Y en avais déjà ");
								if (cc.getNumero()>ccmax.getNumero()){
									System.out.println("mais ca va c'est mieux ");
									max = c;
									numJoueur = i;
									System.out.println("Prio 3 ! Maintenant carte max : "+max);
								}
							}
						}
					}
				}
				if (prioTrouvee>3){
					// PRIORITE 4: c'est une un coeur plus élevée que celle que j'ai potentionnellement SAUF si j'ai un jocker
					if (c instanceof CarteClassique){
						CarteClassique cc = (CarteClassique) c;
						if (cc.getSymbole()==Symbole.COEUR){
							System.out.println("C'est un coeur <3 ");
							if (max==null){ // pas défini ou défini mais un coeur
								System.out.println("On prend dans tous les cas -ca ahahah ");
								max = c;
								prioTrouvee = 4;
								numJoueur = i;
								System.out.println("Prio 4 ! Maintenant carte max : "+max);
							} else if (prioTrouvee==4){
								CarteClassique ccmax = (CarteClassique) max;
								if (cc.getNumero()<ccmax.getNumero()){   // on prend quand même le plus petit num en coeur
									System.out.println("mais ca va c'est mieux parce que inf ");
									max = c;
									numJoueur = i;
									System.out.println("Prio 4 ! Maintenant carte max : "+max);
								}
							}
						}
					}
				}
			}
			// si on en trouvé aucune qui correspond on fait au piff
			if (prioTrouvee==10){
				System.out.println("On en a jamais trouvé d'intéressante :(");
				numCarte=1;
				double alea = Math.random()*joueursSansMoi.size();
				numJoueur = Double.valueOf(alea).intValue();
				System.out.println("Du coup carte cachée d "+numJoueur+"eme de "+joueursSansMoi);
			}
		
			// juste l'index du joueur est pas bon car il s'est enlevé de la liste 
			if (joueurs.contains(moiMeme)){
				System.out.print("Le  "+numJoueur+"eme de "+joueursSansMoi);
				int indexMoi = joueurs.indexOf(moiMeme);
				if (numJoueur>=indexMoi){
					numJoueur++;
				}
				System.out.println("c'est le "+numJoueur+"eme de "+joueurs);
			}
		
			res.add(numJoueur);
			res.add(numCarte);
		}
		return res;
	}

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
