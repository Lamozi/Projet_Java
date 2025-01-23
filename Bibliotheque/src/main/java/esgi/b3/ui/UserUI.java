package esgi.b3.ui;
import esgi.b3.models.User;
import esgi.b3.services.UserService;
import java.util.Scanner;

public class UserUI {
    /** UserService */
    private final UserService userService = new UserService();
    /** Scanner */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Afficher le menu des utilisateurs
     */
    public void afficheMenuUsers() {
        System.out.println("=== Gestion des Utilisateurs ===");
        System.out.println("1. Lister les utilisateurs");
        System.out.println("2. Ajouter un utilisateur");
        System.out.println("3. Supprimer un utilisateur");
        System.out.println("0. Retour");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour de ligne

        switch (choix) {
            case 1 -> listerUsers();
            case 2 -> ajouterUser();
            case 3 -> supprimerUser();
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
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
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
            System.out.println("Utilisateur ajouté avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
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
            System.out.println("Utilisateur non trouvé.");
            return;
        }
        try {
            userService.deleteUser(user);
            System.out.println("Utilisateur supprimé avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
