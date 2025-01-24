package esgi.b3.services;
import esgi.b3.dao.LivreDAO;
import esgi.b3.models.Livre;
import java.sql.SQLException;
import java.util.List;

public class LivreService {
    /**
     * LivreDAO pour accéder aux données
     */
    private final LivreDAO livreDAO = new LivreDAO();

    /**
     * Récupérer la liste des livres
     *
     * @return liste des livres
     */
    public List<Livre> getLivres() throws SQLException {
        return livreDAO.getAllLivres();
    }

    /**
     * Ajouter un livre
     *
     * @param titre  titre du livre
     * @param auteur auteur du livre
     * @param genre  genre du livre
     * @param status status du livre
     */
    public void ajouterLivre(String titre, String auteur, String genre, String status) throws SQLException {
        if (titre == null || titre.isEmpty() || auteur == null || auteur.isEmpty()) {
            throw new IllegalArgumentException("Titre et auteur sont obligatoires.");
        }
        if (livreDAO.existsByTitre(titre)) {
            throw new IllegalArgumentException("Le livre existe déjà.");
        }
        livreDAO.addLivre(new Livre(0, titre, auteur, genre, status));
    }

    /**
     * Modifier un livre
     *
     * @param livre livre
     */
    public void modifierLivre(Livre livre) throws SQLException {
        livreDAO.updateLivre(livre);
    }

    /**
     * Récupérer la liste des livre disponible
     *
     * @return liste des livres disponible
     */
    public List<Livre> getLivresDisponible() throws SQLException {
        return livreDAO.getAllLivresDisponible();
    }

    /**
     * Récupérer la liste des livre emprunté
     *
     * @return liste des livres emprunté
     */
    public List<Livre> getLivresEmprunte() throws SQLException {
        return livreDAO.getAllLivresEmprunte();
    }

    /**
     * Rechercher un livre par son titre
     *
     * @param titre titre du livre
     * @return livre
     */
    public Livre rechercherLivreByTitre(String titre) {
        return livreDAO.getLivreByTitre(titre);
    }

    /**
     * Supprimer un livre
     *
     * @param id id du livre
     */
    public void supprimerLivre(int id) {
        livreDAO.deleteLivre(id);
    }

    /**
     * Rechercher un livre par son auteur
     *
     * @param auteur auteur du livre
     * @return liste des livres
     */
    public List<Livre> rechercherLivreByAuteur(String auteur) {
        return livreDAO.getLivreByAuteur(auteur);
    }

    /**
     * Rechercher un livre par son genre
     * @param genre genre du livre
     * @return liste des livres
     */
    public List<Livre> rechercherLivreByGenre(String genre) {
        return livreDAO.getLivreByGenre(genre);
    }
}
