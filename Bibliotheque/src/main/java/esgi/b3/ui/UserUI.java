package esgi.b3.ui;
import esgi.b3.dao.UserDAO;
import esgi.b3.models.LivreEmprunt;
import esgi.b3.models.User;
import esgi.b3.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class UserUI {
    /** UserService */
    private final UserService userService = new UserService();
    /** Scanner */
    private final Scanner scanner = new Scanner(System.in);
    // Initialisation du logger
    private static final Logger logger = LoggerFactory.getLogger(UserUI.class);

    /**
     * Afficher le menu des utilisateurs
     */
    public void afficheMenuUsers() {
        System.out.println("=== Gestion des Utilisateurs ===");
        System.out.println("1. Lister les utilisateurs");
        System.out.println("2. Ajouter un utilisateur");
        System.out.println("3. Supprimer un utilisateur");
        System.out.println("4. Modifier un utilisateur");
        System.out.println("5. Historique des emprunts");
        System.out.println("0. Retour");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour de ligne

        switch (choix) {
            case 1 -> listerUsers();
            case 2 -> ajouterUser();
            case 3 -> supprimerUser();
            case 4 -> modifierUser();
            case 5 -> historiqueEmprunts();
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("Choix invalide.");
        }
    }

    /**
     * Lister les utilisateurs
     */
    private void listerUsers() {
        try {
            userService.getUsers().forEach(user ->
                    System.out.println(user.getId() + " - " + user.getNom() + " (" + user.getEmail() + ")"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des utilisateurs : ", e);
        }
    }

    /**
     * Ajouter un utilisateur
     */
    private void ajouterUser() {
        System.out.println("Nom : ");
        String nom = scanner.nextLine();
        System.out.println("Email : ");
        String email = scanner.nextLine();

        try {
            userService.addUser(nom, email);
            logger.info("L'utilisateur a été ajouté avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout de l'utilisateur : ", e);
        }
    }

    /**
     * Supprimer un utilisateur
     */
    private void supprimerUser() {
        System.out.println("Nom de l'utilisateur à supprimer : ");
        String nom = scanner.nextLine();
        final User user = userService.getUserByName(nom);
        if (user == null) {
            logger.error("Utilisateur non trouvé.");
            return;
        }
        try {
            userService.deleteUser(user);
            logger.info("L'utilisateur a été supprimé avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'utilisateur : ", e);
        }
    }

    /**
     * Modifier un utilisateur
     */
    private void modifierUser() {
        System.out.println("Nom de l'utilisateur à modifier : ");
        String nom = scanner.nextLine();
        final User user = userService.getUserByName(nom);
        if (user == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }
        System.out.println("Nouveau nom : ");
        String newNom = scanner.nextLine();
        System.out.println("Nouvel email : ");
        String newEmail = scanner.nextLine();
        try {
            userService.updateUser(user, newNom, newEmail);
            logger.info("L'utilisateur a été modifié avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la modification de l'utilisateur : ", e);
        }
    }

    /**
     * Historique des emprunts de l'utilisateur par son nom
     */
    private void historiqueEmprunts() {
        System.out.println("Nom de l'utilisateur : ");
        String nom = scanner.nextLine();

        try {
            int id_user = userService.getUserByName(nom).getId();
            List<LivreEmprunt> emprunts = userService.historiqueEmprunts(id_user);

            if (emprunts.isEmpty()) {
                logger.warn("Aucun emprunt trouvé pour l'utilisateur.");
            } else {
                emprunts.forEach(emprunt ->
                        System.out.println("Livre : " + emprunt.getTitre() +
                                " emprunté du " + emprunt.getDateEmprunt() +
                                " au " + (emprunt.getDateRetour() != null
                                ? emprunt.getDateRetour() : "Pas encore retourné")));
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'historique des emprunts : ", e);
        }
    }
}
