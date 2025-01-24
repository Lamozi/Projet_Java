package esgi.b3.services;

import esgi.b3.dao.EmpruntDAO;
import esgi.b3.dao.LivreDAO;
import esgi.b3.dao.UserDAO;
import esgi.b3.models.Emprunt;
import esgi.b3.models.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmpruntService {

    // Logger pour la classe
    private static final Logger logger = LoggerFactory.getLogger(EmpruntService.class);

    /** EmpruntDAO */
    private final EmpruntDAO empruntDAO = new EmpruntDAO();

    /**
     * Récupérer la liste des emprunts
     * @return liste des emprunts
     */
    public List<Emprunt> getEmprunts() throws SQLException {
        return empruntDAO.getAllEmprunts();
    }

    /**
     * Emprunter un livre
     * @param nom   nom de l'utilisateur
     * @param titre titre du livre
     */
    public void emprunterLivre(String nom, String titre) throws SQLException {

        if (nom == null || nom.isEmpty() || titre == null || titre.isEmpty()) {
            logger.error("Nom ou titre manquant. Nom: '{}', Titre: '{}'", nom, titre);
            throw new IllegalArgumentException("Nom et titre sont obligatoires.");
        }
        final UserDAO userDAO = new UserDAO();
        final LivreDAO livreDAO = new LivreDAO();

        if (!userDAO.existsByName(nom) || !livreDAO.existsByTitre(titre)) {
            logger.error("L'utilisateur '{}' n'existe pas.", nom);
            throw new IllegalArgumentException("L'utilisateur n'existe pas.");
        }

        int id_user = userDAO.getUserIdByName(nom);
        int id_livre = livreDAO.getLivreId(titre);
        Livre livre = livreDAO.getLivreById(id_livre);

        // Vérifier si le livre est disponible
        if (!livreDAO.isAvailable(livre)) {
            logger.error("Le livre '{}' n'est pas disponible.", titre);
            throw new IllegalArgumentException("Le livre n'est pas disponible.");
        }

        Emprunt newEmprunt = new Emprunt(0, id_livre, id_user, LocalDate.now(), LocalDate.now().plusDays(15));
        empruntDAO.addEmprunt(newEmprunt);
        logger.info("Le livre '{}' a été emprunté avec succès par l'utilisateur '{}'.", titre, nom);
    }

    /**
     * Changer le status d'un livre
     * @param titre     titre du livre
     * @param emprunte  status du livre
     */
    public void changeStatus(String titre, String emprunte) {
        try {
            final LivreDAO livreDAO = new LivreDAO();
            int id_livre = livreDAO.getLivreId(titre);
            livreDAO.changeStatus(id_livre, emprunte);
            logger.info("Le statut du livre '{}' a été mis à jour avec succès.", titre);
        } catch (SQLException e) {
            logger.error("Erreur lors du changement de statut du livre '{}'.", titre, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Rendre un livre
     * @param titre titre du livre
     */
    public void rendreLivre(String titre) {
        try {
            final LivreDAO livreDAO = new LivreDAO();
            final EmpruntDAO empruntDAO = new EmpruntDAO();
            int id_livre = livreDAO.getLivreId(titre);

            Emprunt emprunt = empruntDAO.getEmpruntByLivreId(id_livre);
            empruntDAO.rendreLivre(emprunt);

            logger.info("Le livre '{}' a été retourné avec succès.", titre);
        } catch (SQLException e) {
            logger.error("Erreur lors du retour du livre '{}'.", titre, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Ajouter un nouvel emprunt
     * @param emprunt
     */
    public void addEmprunt(Emprunt emprunt) {
        try {
            empruntDAO.addEmprunt(emprunt);
            logger.info("L'emprunt a été ajouté avec succès.");
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'emprunt.", e);
            throw new RuntimeException(e);
        }
    }
}
