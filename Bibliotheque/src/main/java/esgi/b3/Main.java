package esgi.b3;
import esgi.b3.exports.ExportEnJson;
import esgi.b3.exports.ImportJson;
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
        ExportEnJson exportEnJson = new ExportEnJson();

        while (true) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Gestion des livres");
            System.out.println("2. Gestion des utilisateurs");
            System.out.println("3. Suivi des emprunts");
            System.out.println("4. Exporter la base de données (JSON)");
            System.out.println("5. Importer la base de données (JSON)");
            System.out.println("0. Quitter");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante après nextInt()

            switch (choix) {
                case 1 -> livreUI.afficherMenuLivres();
                case 2 -> userUI.afficheMenuUsers();
                case 3 -> empruntUI.afficherMenuEmprunts();
                case 4 -> exportEnJson.exporterBaseDeDonneesEnJson();
                case 5 -> {
                    System.out.println("Chemin du fichier JSON à importer :");
                    String filePath = scanner.nextLine();
                    new ImportJson().importerBaseDeDonneesDepuisJson(filePath);
                }
                case 0 -> {
                    System.out.println("Au revoir !");
                    System.exit(0);
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}