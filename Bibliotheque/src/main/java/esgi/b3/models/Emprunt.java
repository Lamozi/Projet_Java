package esgi.b3.models;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private int userId;
    private int livreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    /**
     * Constructeur
     * @param id
     * @param userId
     * @param livreId
     * @param dateEmprunt
     * @param dateRetour
     */
    public Emprunt(int id, int userId, int livreId, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.userId = userId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    /** Getters, setters */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getLivreId() {
        return livreId;
    }
    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    public LocalDate getDateRetour() {
        return dateRetour;
    }
    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

}

