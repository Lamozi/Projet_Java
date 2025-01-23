package esgi.b3.dao;
import esgi.b3.DatabaseManager;
import esgi.b3.models.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LivreDAO {
    /**
     * Récupérer la liste des livres
     * @return liste des livres
     */
    public List<Livre> getAllLivres() throws SQLException {
        String query = "SELECT * FROM livres";
        List<Livre> livres = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                livres.add(new Livre(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getString("genre"),
                        resultSet.getString("status")
                ));
            }
        }
        return livres;
    }

    /**
     * Ajouter un livre
     * @param livre livre
     */
    public void addLivre(Livre livre) throws SQLException {
        String query = "INSERT INTO livres (titre, auteur, genre, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getGenre());
            preparedStatement.setString(4, livre.getStatus().toString());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Récupérer l'id du livre par son titre
     * @param titre titre du livre
     * @return id du livre
     */
    public int getIdLivreId(String titre) throws SQLException {
        String query = "SELECT id FROM livres WHERE titre = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, titre);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return -1;
    }

    /**
     * Récupérer le livre par son id
     * @param id id du livre
     * @return livre
     */
    public Livre getLivreById(int id) throws SQLException {
        String query = "SELECT * FROM livres WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Livre(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("auteur"),
                            resultSet.getString("genre"),
                            resultSet.getString("status")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Supprimer un livre
     * @param id id du livre
     */
    public String getTitreById(int id) throws SQLException {
        String query = "SELECT titre FROM livres WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("titre");
                }
            }
        }
        return null;
    }

    /**
     * Supprimer un livre
     * @param idLivre id du livre
     * @param emprunte status du livre
     */
    public void changeStatus(int idLivre, String emprunte) {
        String query = "UPDATE livres SET status = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emprunte);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifier si un livre existe par son titre
     * @param titre titre du livre
     * @return true si le livre existe, sinon false
     */
    public boolean existsByTitle(String titre) {
        try {
            String query = "SELECT * FROM livres WHERE titre = ?";
            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, titre);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupérer la liste des livres disponibles
     * @return liste des livres disponibles
     */
    public List<Livre> getAllLivresDisponible() {
        try {
            String query = "SELECT * FROM livres WHERE status = 'disponible'";
            List<Livre> livres = new ArrayList<>();
            try (Connection connection = DatabaseManager.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    livres.add(new Livre(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("auteur"),
                            resultSet.getString("genre"),
                            resultSet.getString("status")
                    ));
                }
            }
            return livres;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Vérifier si un livre est disponible
     * @param livre livre
     * @return true si le livre est disponible, sinon false
     */
    public boolean isAvailable(Livre livre) {
        try {
            String query = "SELECT * FROM livres WHERE id = ? AND status = 'disponible'";
            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, livre.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
