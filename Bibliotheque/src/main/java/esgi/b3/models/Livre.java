package esgi.b3.models;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String genre;
    private Status status;

    /**
     * Constructeur
     * @param id        id du livre
     * @param titre     titre du livre
     * @param auteur    auteur du livre
     * @param genre     genre du livre
     * @param status    status du livre
     */
    public Livre(int id, String titre, String auteur, String genre, String status) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.status = Status.valueOf(status);
    }

    /** getters and setters */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


}

