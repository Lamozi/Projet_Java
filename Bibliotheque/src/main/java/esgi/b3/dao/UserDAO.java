package esgi.b3.dao;
import esgi.b3.DatabaseManager;
import esgi.b3.models.Livre;
import esgi.b3.models.LivreEmprunt;
import esgi.b3.models.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    /**
     * Récupérer la liste des utilisateurs
     * @return liste des utilisateurs
     */
    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
            }
        }
        return users;
    }

    /**
     * Ajouter un utilisateur
     * @param user utilisateur
     */
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Récupérer l'id de l'utilisateur par son nom
     * @param name nom de l'utilisateur
     * @return id de l'utilisateur
     */
    public int getUserIdByName(String name) throws SQLException {
        String query = "SELECT id FROM users WHERE name = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return -1;
    }

    /**
     * Récupérer le nom de l'utilisateur par son id
     * @param id id de l'utilisateur
     * @return nom de l'utilisateur
     */
    public String getUserNameById(int id) throws SQLException {
        String query = "SELECT name FROM users WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        }
        return null;
    }

    /**
     * Vérifier si l'utilisateur existe par son nom
     * @param nom nom de l'utilisateur
     * @return true si l'utilisateur existe, sinon false
     */
    public boolean existsByName(String nom) {
        try {
            String query = "SELECT * FROM users WHERE name = ?";
            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nom);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Vérifier si l'utilisateur existe par son email
     * @param email email de l'utilisateur
     * @return true si l'utilisateur existe, sinon false
     */
    public boolean existsByEmail(String email) {
        try {
            String query = "SELECT * FROM users WHERE email = ?";
            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Supprimer un utilisateur
     * @param id id de l'utilisateur
     */
    public void deleteUser(User user) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupérer l'utilisateur par son nom
     * @param nom nom de l'utilisateur
     * @return utilisateur
     */
    public User getUserByName(String nom) {
        String query = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Modifier un utilisateur
     * @param user utilisateur
     * @param newNom nouveau nom
     * @param newEmail nouveau email
     */
    public void updateUser(User user, String newNom, String newEmail) {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newNom);
            preparedStatement.setString(2, newEmail);
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupérer l'utilisateur par son id
     * @param id_user id de l'utilisateur
     * @return utilisateur
     */
    public List<LivreEmprunt> historiqueEmprunts(int id_user) {
        String query = "SELECT l.id, l.titre, e.date_emprunt, e.date_retour " +
                "FROM emprunts e " +
                "JOIN livres l ON e.id_livre = l.id WHERE e.id_user = ?";
        List<LivreEmprunt> emprunts = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_user);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idLivre = resultSet.getInt("id");
                    String titre = resultSet.getString("titre");
                    LocalDate dateEmprunt = resultSet.getDate("date_emprunt").toLocalDate();
                    LocalDate dateRetour = resultSet.getDate("date_retour") != null
                            ? resultSet.getDate("date_retour").toLocalDate()
                            : null;

                    emprunts.add(new LivreEmprunt(idLivre, titre, dateEmprunt, dateRetour));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

}
