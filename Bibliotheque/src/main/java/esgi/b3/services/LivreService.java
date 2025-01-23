package esgi.b3.services;
import esgi.b3.dao.LivreDAO;
import esgi.b3.models.Livre;
import java.sql.SQLException;
import java.util.List;

public class LivreService {
    /** LivreDAO pour accéder aux données */
    private final LivreDAO livreDAO = new LivreDAO();

    /**
     * Récupérer la liste des livres
     * @return liste des livres
     */
    public List<Livre> getLivres() throws SQLException {
        return livreDAO.getAllLivres();
    }

    /**
     * Ajouter un livre
     * @param titre titre du livre
     * @param auteur auteur du livre
     * @param genre genre du livre
     * @param status status du livre
     */
    public void ajouterLivre(String titre, String auteur, String genre, String status) throws SQLException {
        if (titre == null || titre.isEmpty() || auteur == null || auteur.isEmpty()) {
            throw new IllegalArgumentException("Titre et auteur sont obligatoires.");
        }
        livreDAO.addLivre(new Livre(0, titre, auteur, genre, status));
    }
    /**
     * Récupérer la liste des livre disponible
     * @return liste des livres disponible
     */
    public List<Livre> getLivresDisponible() throws SQLException {
        return livreDAO.getAllLivresDisponible();
    }

}
