package esgi.b3.services;

import esgi.b3.dao.LivreDAO;
import esgi.b3.models.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class LivreService {

    // Logger pour la classe
    private static final Logger logger = LoggerFactory.getLogger(LivreService.class);

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
        try {
            logger.info("Récupération de la liste des livres...");
            List<Livre> livres = livreDAO.getAllLivres();
            logger.info("Liste des livres récupérée avec succès.");
            return livres;
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des livres.", e);
            throw e;
        }
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
        try {
            if (titre == null || titre.isEmpty() || auteur == null || auteur.isEmpty()) {
                logger.error("Titre ou auteur manquant. Titre: '{}', Auteur: '{}'", titre, auteur);
                throw new IllegalArgumentException("Titre et auteur sont obligatoires.");
            }
            if (livreDAO.existsByTitre(titre)) {
                logger.error("Le livre '{}' existe déjà.", titre);
                throw new IllegalArgumentException("Le livre existe déjà.");
            }

            livreDAO.addLivre(new Livre(0, titre, auteur, genre, status));
            logger.info("Le livre '{}' a été ajouté avec succès.", titre);
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout du livre '{}'.", titre, e);
            throw e;
        }
    }

    /**
     * Modifier un livre
     * @param livre livre
     */
    public void modifierLivre(Livre livre) throws SQLException {
        try {
            livreDAO.updateLivre(livre);
            logger.info("Le livre '{}' a été modifié avec succès.", livre.getTitre());
        } catch (SQLException e) {
            logger.error("Erreur lors de la modification du livre '{}'.", livre.getTitre(), e);
            throw e;
        }
    }

    /**
     * Récupérer la liste des livres disponibles
     * @return liste des livres disponibles
     */
    public List<Livre> getLivresDisponible() throws SQLException {
        logger.info("Récupération des livres disponibles...");
        List<Livre> livresDisponibles = livreDAO.getAllLivresDisponible();
        logger.info("Livres disponibles récupérés avec succès.");
        return livresDisponibles;
    }

    /**
     * Récupérer la liste des livres empruntés
     * @return liste des livres empruntés
     */
    public List<Livre> getLivresEmprunte() throws SQLException {
        logger.info("Récupération des livres empruntés...");
        List<Livre> livresEmpruntes = livreDAO.getAllLivresEmprunte();
        logger.info("Livres empruntés récupérés avec succès.");
        return livresEmpruntes;
    }

    /**
     * Rechercher un livre par son titre
     * @param titre titre du livre
     * @return livre
     */
    public Livre rechercherLivreByTitre(String titre) {
        logger.info("Recherche du livre par titre: '{}'.", titre);
        Livre livre = livreDAO.getLivreByTitre(titre);
        if (livre != null) {
            logger.info("Le livre '{}' a été trouvé.", titre);
        } else {
            logger.warn("Aucun livre trouvé avec le titre '{}'.", titre);
        }
        return livre;
    }

    /**
     * Supprimer un livre
     * @param id id du livre
     */
    public void supprimerLivre(int id) {
        logger.info("Suppression du livre avec l'ID: '{}'.", id);
        livreDAO.deleteLivre(id);
        logger.info("Le livre avec l'ID '{}' a été supprimé avec succès.", id);
    }

    /**
     * Rechercher un livre par son auteur
     * @param auteur auteur du livre
     * @return liste des livres
     */
    public List<Livre> rechercherLivreByAuteur(String auteur) {
        logger.info("Recherche des livres par auteur: '{}'.", auteur);
        List<Livre> livres = livreDAO.getLivreByAuteur(auteur);
        logger.info("Livres par l'auteur '{}' récupérés avec succès.", auteur);
        return livres;
    }

    /**
     * Rechercher un livre par son genre
     * @param genre genre du livre
     * @return liste des livres
     */
    public List<Livre> rechercherLivreByGenre(String genre) {
        logger.info("Recherche des livres par genre: '{}'.", genre);
        List<Livre> livres = livreDAO.getLivreByGenre(genre);
        logger.info("Livres du genre '{}' récupérés avec succès.", genre);
        return livres;
    }
}
