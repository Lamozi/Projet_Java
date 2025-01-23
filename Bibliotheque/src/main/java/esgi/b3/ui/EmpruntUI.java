package esgi.b3.ui;

import esgi.b3.dao.LivreDAO;
import esgi.b3.dao.UserDAO;
import esgi.b3.services.EmpruntService;

import java.sql.SQLException;
import java.util.Scanner;

public class EmpruntUI {
    /** EmpruntService */
    private final EmpruntService empruntService = new EmpruntService();
    /** Scanner */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Afficher le menu des emprunts
     */
    public void afficherMenuEmprunts() {
        while (true) {
            System.out.println("\n === Gestion des emprunts ===");
            System.out.println("1. Lister les emprunts");
            System.out.println("2. Emprunter un livre");
            System.out.println("3. Rendre un livre");
            System.out.println("0. Retour au menu principal \n");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1 -> listerEmprunts();
                case 2 -> emprunterLivre();
                case 3 -> rendreLivre();
                case 0 -> System.out.println("Retour au menu principal");
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    /**
     * Lister les emprunts
     */
    private void listerEmprunts() {
        try {
            final UserDAO userDAO = new UserDAO();
            final LivreDAO livreDAO = new LivreDAO();
            empruntService.getEmprunts().forEach(emprunt ->
            {
                try {
                    System.out.println(" Le livre : " + livreDAO.getTitreById(emprunt.getLivreId()) + " emprunté par " + userDAO.getUserNameById(emprunt.getUserId()) + " du " + emprunt.getDateEmprunt() + " au " + emprunt.getDateRetour() );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des emprunts : " + e.getMessage());
        }
    }

    /**
     * Emprunter un livre
     */
    private void emprunterLivre() {
        System.out.println("Nom de l'utilisateur : ");
        String nom = scanner.next();
        System.out.println("Titre du livre : ");
        // Consommer la saisie de l'utilisateur avec les espaces
        scanner.useDelimiter("\n");
        String titre = scanner.next();

        try {
            empruntService.emprunterLivre(nom, titre);
            // changer le status du livre à emprunte
            empruntService.changeStatus(titre, "emprunte");
            System.out.println("Livre emprunté avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Rendre un livre
     */
    private void rendreLivre() {
        System.out.println("Titre du livre : ");
        String titre = scanner.next();

        try {
            empruntService.rendreLivre(titre);
            // changer le status du livre à disponible
            empruntService.changeStatus(titre, "disponible");
            System.out.println("Livre rendu avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
