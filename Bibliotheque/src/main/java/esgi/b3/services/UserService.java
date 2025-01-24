package esgi.b3.services;

import esgi.b3.dao.UserDAO;
import esgi.b3.models.LivreEmprunt;
import esgi.b3.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    // Logger pour la classe
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO = new UserDAO();

    /**
     * Récupérer la liste des utilisateurs
     * @return liste des utilisateurs
     */
    public List<User> getUsers() throws SQLException {
        try {
            logger.info("Récupération de la liste des utilisateurs...");
            List<User> users = userDAO.getAllUsers();
            logger.info("Liste des utilisateurs récupérée avec succès.");
            return users;
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des utilisateurs.", e);
            throw e;
        }
    }

    /**
     * Ajouter un utilisateur
     * @param name nom de l'utilisateur
     * @param email email de l'utilisateur
     */
    public void addUser(String name, String email) throws SQLException {
        try {
            if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
                logger.error("Nom ou email manquant. Nom: '{}', Email: '{}'", name, email);
                throw new IllegalArgumentException("Nom et email sont obligatoires.");
            }

            // vérifier si l'utilisateur existe déjà
            if (userDAO.existsByName(name) || userDAO.existsByEmail(email)) {
                logger.error("L'utilisateur avec le nom '{}' ou l'email '{}' existe déjà.", name, email);
                throw new IllegalArgumentException("L'utilisateur existe déjà.");
            }

            userDAO.addUser(new User(0, name, email));
            logger.info("L'utilisateur '{}' a été ajouté avec succès.", name);
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'utilisateur '{}'.", name, e);
            throw e;
        }
    }

    /**
     * Supprimer un utilisateur
     * @param user l'utilisateur à supprimer
     */
    public void deleteUser(User user) throws SQLException {
        logger.info("Suppression de l'utilisateur avec ID: '{}'.", user.getId());
        userDAO.deleteUser(user);
        logger.info("Utilisateur avec ID '{}' supprimé avec succès.", user.getId());
    }

    /**
     * Récupérer l'utilisateur par son nom
     * @param nom nom de l'utilisateur
     * @return utilisateur
     */
    public User getUserByName(String nom) {
        logger.info("Recherche de l'utilisateur par nom: '{}'.", nom);
        User user = userDAO.getUserByName(nom);
        if (user != null) {
            logger.info("Utilisateur '{}' trouvé avec succès.", nom);
        } else {
            logger.warn("Aucun utilisateur trouvé avec le nom '{}'.", nom);
        }
        return user;
    }

    /**
     * Modifier un utilisateur
     * @param user utilisateur
     * @param newNom nouveau nom
     * @param newEmail nouveau email
     */
    public void updateUser(User user, String newNom, String newEmail) {
        logger.info("Modification de l'utilisateur avec ID: '{}'. Nouveau nom: '{}', Nouveau email: '{}'", user.getId(), newNom, newEmail);
        userDAO.updateUser(user, newNom, newEmail);
        logger.info("Utilisateur avec ID '{}' modifié avec succès.", user.getId());
    }

    /**
     * Récupérer l'historique des emprunts d'un utilisateur
     * @param id_user ID de l'utilisateur
     * @return liste des emprunts de l'utilisateur
     */
    public List<LivreEmprunt> historiqueEmprunts(int id_user) throws SQLException {
        logger.info("Récupération de l'historique des emprunts pour l'utilisateur avec ID: '{}'.", id_user);
        List<LivreEmprunt> emprunts = userDAO.historiqueEmprunts(id_user);
        logger.info("Historique des emprunts pour l'utilisateur avec ID '{}' récupéré avec succès.", id_user);
        return emprunts;
    }
}
