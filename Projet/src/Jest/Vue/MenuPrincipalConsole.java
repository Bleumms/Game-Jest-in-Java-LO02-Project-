/*
*MenuPrincipalConsole : Classe représentant le menu principal en ligne de commande
*@author Nina et Emeline
*/

package Jest.Vue;

import java.util.Observable;
import java.util.Observer;

import Jest.Controler.MenuDebutControler;
import Jest.Model.Menu;
import java.util.Scanner;


public class MenuPrincipalConsole implements Observer {

    private Menu menu;
    private Scanner scanner;

    /*
    * Constructeur MenuPrincipalConsole : initialise le menu
    */
    public MenuPrincipalConsole() {

        this.menu = new Menu();
		this.menu.addObserver(this);
        scanner = new Scanner(System.in);

		affichage();
	}

    /*
    * Récupère la réponse de l'utilisateur (1 ou 2)
    * @return String : la réponse de l'utilisateur
    */
    private String ecouterReponse(){
        System.out.print("Votre réponse : ");
        return scanner.nextLine();
    }

    /*
    * Affiche le menu principal dans le terminal de commande
    */
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
                valide=true;
            } 
            if (rep.charAt(0)=='2' ){
                System.out.println("\nVous avez choisi de reprendre une partie !");
                valide=true;
            }
        }
    }

    /*
    * Met à jour l'affichage en fonction de l'état du menu
    * @param instanceObservable : l'objet observable (le menu)
    * @param arg1 : argument supplémentaire (non utilisé)
    */
    public void update(Observable instanceObservable, Object arg1){
    }

    /*
    * Lancement de l'application
    */
    public static void main(String[] args) {
		MenuPrincipalConsole m = new MenuPrincipalConsole();
	}
}
