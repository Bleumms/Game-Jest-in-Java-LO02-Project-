package Jest.Vue;

import java.util.Observable;
import java.util.Observer;

import Jest.Controler.MenuDebutControler;
import Jest.Model.EtatMenu;
import Jest.Model.Menu;
import java.util.Scanner;

public class MenuPrincipalConsole implements Observer {

    private Menu menu;
    private Scanner scanner;


    public MenuPrincipalConsole() {

        this.menu = new Menu();
		this.menu.addObserver(this);
        scanner = new Scanner(System.in);

		affichage();
	}

    private String ecouterReponse(){
        System.out.print("Votre réponse : ");
        return scanner.nextLine();
    }

    private void affichage(){
        System.out.println("     JEST     -     by Nina et Emeline     \n\n");
        System.out.println("\nQue souhaitez vous faire ?");
		System.out.println("      1 -   Créer une nouvelle partie ! ");
		System.out.println("      2 -   Reprendre une ancienne partie ! ");
        
        boolean valide = false;
        while (!valide){
            String rep = ecouterReponse();
            if (rep.charAt(0)=='1'){
                System.out.println("\nVous avez choisi de créer une nouvelle partie !");
                menu.creerPartie();
                valide=true;
            } 
            if (rep.charAt(0)=='2' ){
                System.out.println("\nVous avez choisi de reprendre une partie !");
                menu.reprendrePartie();
                valide=true;
            }
        }
    }

    public void update(Observable instanceObservable, Object arg1){

        // après le premier menu l'utilisateur a choisi de creer une partie
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.CreerPartie){
			new MenuCreerPartieConsole(this.menu);
		}

		//  après le premier menu l'utilisateur a choisi de reprendre une par
		if (instanceObservable instanceof Menu && ((Menu)instanceObservable).getEtat()==EtatMenu.ReprendrePartie){
			new ReprendrePartieConsole(menu);
		}

    }

    public static void main(String[] args) {
		MenuPrincipalConsole m = new MenuPrincipalConsole();
	}
}
