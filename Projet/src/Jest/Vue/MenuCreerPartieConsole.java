package Jest.Vue;

import java.util.Observable;
import java.util.Observer;

import Jest.Controler.MenuDebutControler;
import Jest.Model.EtatMenu;
import Jest.Model.Menu;
import java.util.Scanner;

@SuppressWarnings({ "unused", "deprecation" })
public class MenuCreerPartieConsole implements Observer {

    private Menu menu;
    private Scanner scanner;


    public MenuCreerPartieConsole(Menu m) {

        this.menu = m;
		this.menu.addObserver(this);
        scanner = new Scanner(System.in);

		affichage();
	}

    private String ecouterReponse(){
        System.out.print("Votre réponse : ");
        return scanner.nextLine();
    }

    private void affichage(){
        System.out.println("Créer une partie :\n");
        System.out.println("Quel jeu vous intéresse ? :");
        for (int i=0; i<this.menu.getJeux().size(); i++){
            System.out.println("      "+(i+1)+" -   "+this.menu.getJeux().get(i));
        }
		
        boolean valide = false;
        while (!valide){
            String rep = ecouterReponse();
            if (Character.getNumericValue(rep.charAt(0))>=1 && Character.getNumericValue(rep.charAt(0))<=this.menu.getJeux().size()){
                System.out.println("\nVous avez choisi le jeu "+this.menu.getJeux().get((Character.getNumericValue(rep.charAt(0))-1)));
                valide=true;
            } 
        }
        System.out.println("\n Par manque de temps nous n'irons pas plus loin sur la partie sur console.");
        System.out.println("Pour ce qui est de jouer à la fois avec l'interface graphique et avec la console, d'abord bien évidement ne créer qu'un seul main où les deux façon de jouer serait lancé et ajouter les ajouter tous les deux en temps qu'observer, il faudrait ensuite gérer les deux façon de jouer comme des Threads différents, et pour cela ajouter \' synchronized\' devant les fonction en aillant besoin, ainsi que utiliser wait()");
    }

    public void update(Observable instanceObservable, Object arg1){
	}
}
