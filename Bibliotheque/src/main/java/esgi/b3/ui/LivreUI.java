package esgi.b3.ui;
import esgi.b3.dao.LivreDAO;
import esgi.b3.models.Livre;
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
        System.out.println("3. Modifier un livre");
        System.out.println("4. Supprimer un livre");
        System.out.println("5. Lister les livres disponibles");
        System.out.println("6. Lister les livres empruntés");
        System.out.println("7. Rechercher un livre par son titre");
        System.out.println("8. Rechercher un livre par son auteur");
        System.out.println("9. Rechercher un livre par son genre");
        System.out.println("0. Retour");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour de ligne

        switch (choix) {
            case 1 -> listerLivres();
            case 2 -> ajouterLivre();
            case 3 -> modifierLivre();
            case 4 -> supprimerLivre();
            case 5 -> listerLivresDisponibles();
            case 6 -> listerLivresEmpruntes();
            case 7 -> rechercherLivreByTitre();
            case 8 -> rechercherLivreByAuteur();
            case 9 -> rechercherLivreByGenre();
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
     * Lister les livres empruntés
     */
    private void listerLivresEmpruntes() {
        try {
            livreService.getLivresEmprunte().forEach(livre ->
                    System.out.println(livre.getId() + " - " + livre.getTitre() + " par " + livre.getAuteur() + " - " + livre.getStatus()));
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
     * Modifier un livre avec son titre
     */
    private void modifierLivre() {
        try {
            System.out.println("Titre du livre à modifier : ");
            String titre = scanner.nextLine();
            LivreDAO livreDAO = new LivreDAO();
            int id_livre = livreDAO.getLivreId(titre);

            System.out.println("Nouveau titre : ");
            String newTitre = scanner.nextLine();
            System.out.println("Nouveau auteur : ");
            String newAuteur = scanner.nextLine();
            System.out.println("Nouveau genre : ");
            String newGenre = scanner.nextLine();
            System.out.println("Nouveau status : ");
            String newStatus = scanner.nextLine();

            livreService.modifierLivre(new Livre(id_livre, newTitre, newAuteur, newGenre, newStatus));
            System.out.println("Livre modifié avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Supprimer un livre
     */
    private void supprimerLivre() {
        System.out.println("Titre du livre à supprimer : ");
        String titre = scanner.nextLine();
        try {
            LivreDAO livreDAO = new LivreDAO();
            int id_livre = livreDAO.getLivreId(titre);

            livreService.supprimerLivre(id_livre);
            System.out.println("Livre supprimé avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Rechercher un livre par son titre
     */
    private void rechercherLivreByTitre() {
        System.out.println("Titre du livre à rechercher : ");
        String titre = scanner.nextLine();
        try{
            Livre livre = livreService.rechercherLivreByTitre(titre);
            System.out.println(" ID : " + livre.getId() + " - Titre " + livre.getTitre() + " Auteur : " + livre.getAuteur() + " - Genre : " + livre.getGenre() + " - Status : " + livre.getStatus());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Rechercher un livre par son auteur
     */
    private void rechercherLivreByAuteur() {
        System.out.println("Auteur du livre à rechercher : ");
        String auteur = scanner.nextLine();
        try {
            livreService.rechercherLivreByAuteur(auteur).forEach(livre ->
                    System.out.println(" ID : " + livre.getId() + " - Titre " + livre.getTitre() + " Auteur : " + livre.getAuteur() + " - Genre : " + livre.getGenre() + " - Status : " + livre.getStatus()));
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Rechercher un livre par son genre
     */
    private void rechercherLivreByGenre() {
        System.out.println("Genre du livre à rechercher : ");
        String genre = scanner.nextLine();
        try {
            livreService.rechercherLivreByGenre(genre).forEach(livre ->
                    System.out.println(" ID : " + livre.getId() + " - Titre " + livre.getTitre() + " Auteur : " + livre.getAuteur() + " - Genre : " + livre.getGenre() + " - Status : " + livre.getStatus()));
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}

