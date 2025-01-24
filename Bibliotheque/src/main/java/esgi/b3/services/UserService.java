package esgi.b3.services;

import esgi.b3.dao.UserDAO;
import esgi.b3.models.Livre;
import esgi.b3.models.LivreEmprunt;
import esgi.b3.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    /**
     * Récupérer la liste des utilisateurs
     * @return liste des utilisateurs
     */
    public List<User> getUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    /**
     * Ajouter un utilisateur
     * @param name nom de l'utilisateur
     * @param email email de l'utilisateur
     */
    public void addUser(String name, String email) throws SQLException {
        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Nom et email sont obligatoires.");
        }
        // vérifier si l'utilisateur existe déjà
        if (userDAO.existsByName(name) || userDAO.existsByEmail(email)) {
            throw new IllegalArgumentException("L'utilisateur existe déjà.");
        }
        userDAO.addUser(new User(0, name, email));
    }

    /**
     * Supprimer un utilisateur
     * @param user l'utilisateur à supprimer
     */
    public void deleteUser(User user) throws SQLException {
        userDAO.deleteUser(user);
    }

    /**
     * Récupérer l'utilisateur par son nom
     * @param nom nom de l'utilisateur
     * @return utilisateur
     */
    public User getUserByName(String nom) {
        return userDAO.getUserByName(nom);
    }

    /**
     * Modifier un utilisateur
     * @param user utilisateur
     * @param newNom nouveau nom
     * @param newEmail nouveau email
     */
    public void updateUser(User user, String newNom, String newEmail) {
        userDAO.updateUser(user, newNom, newEmail);
    }

    public List<LivreEmprunt> historiqueEmprunts(int id_user) throws SQLException {
        return userDAO.historiqueEmprunts(id_user);
    }

}
