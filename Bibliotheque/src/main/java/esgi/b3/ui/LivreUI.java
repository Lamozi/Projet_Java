package esgi.b3.ui;
import esgi.b3.services.LivreService;
import java.sql.SQLException;
import java.util.Scanner;

public class LivreUI {
    /** LivreService */
    private final LivreService livreService = new LivreService();
    /** Scanner */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Afficher le menu des livres
     */
    public void afficherMenuLivres() {
        System.out.println("=== Gestion des Livres ===");
        System.out.println("1. Lister les livres");
        System.out.println("2. Ajouter un livre");
        System.out.println("3. Supprimer un livre");
        System.out.println("4. Lister les livres disponibles");
        System.out.println("0. Retour");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour de ligne

        switch (choix) {
            case 1 -> listerLivres();
            case 2 -> ajouterLivre();
            case 3 -> supprimerLivre();
            case 4 -> listerLivresDisponibles();
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("Choix invalide.");
        }
    }

    /**
     * Lister les livres disponibles
     */
    private void listerLivresDisponibles() {
        try {
            livreService.getLivresDisponible().forEach(livre ->
                    System.out.println(livre.getId() + " - " + livre.getTitre() + " par " + livre.getAuteur() + " - " + livre.getStatus()) );
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    /**
     * Lister les livres
     */
    private void listerLivres() {
        try {
            livreService.getLivres().forEach(livre ->
                    System.out.println(livre.getId() + " - " + livre.getTitre() + " par " + livre.getAuteur() + " - " + livre.getStatus()) );
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    /**
     * Ajouter un livre
     */
    private void ajouterLivre() {
        System.out.println("Titre : ");
        String titre = scanner.nextLine();
        System.out.println("Auteur : ");
        String auteur = scanner.nextLine();
        System.out.println("Genre : ");
        String genre = scanner.nextLine();

        try {
            livreService.ajouterLivre(titre, auteur, genre, "disponible"); // le livre est disponible par défaut à l'ajout
            System.out.println("Livre ajouté avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Supprimer un livre
     */
    private void supprimerLivre() {
        System.out.println("ID du livre à supprimer : ");
        int id = scanner.nextInt();

        // Implémentez la suppression via le service
    }
}

