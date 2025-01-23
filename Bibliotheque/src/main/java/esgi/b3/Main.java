package esgi.b3;
import esgi.b3.ui.EmpruntUI;
import esgi.b3.ui.LivreUI;
import esgi.b3.ui.UserUI;
import java.util.Scanner;

public class Main {
    /**
     * Main
     * @param args arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LivreUI livreUI = new LivreUI();
        UserUI userUI = new UserUI();
        EmpruntUI empruntUI = new EmpruntUI();

        while (true) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Gestion des livres");
            System.out.println("2. Gestion des utilisateurs");
            System.out.println("3. Suivi des emprunts");
            System.out.println("0. Quitter");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1 -> livreUI.afficherMenuLivres();
                case 2 -> userUI.afficheMenuUsers();
                case 3 -> empruntUI.afficherMenuEmprunts();
                case 0 -> {
                    System.out.println("Au revoir !");
                    System.exit(0);
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}