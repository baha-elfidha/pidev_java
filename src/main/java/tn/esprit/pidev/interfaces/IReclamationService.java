package tn.esprit.pidev.interfaces;

import java.util.List;
import tn.esprit.pidev.models.Reclamation;

public interface IReclamationService {
    int createReclamation(Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    int updateReclamation(Reclamation reclamation, int id);
    Reclamation getReclamationById(int id);
    int removeReclamation(int id);
}
