package tn.esprit.pidev.models;

public class Reponse {
    private int id;
    private String description;
    private int reclamationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
    }

    @Override
    public String toString() {
        return description;
    }
}
