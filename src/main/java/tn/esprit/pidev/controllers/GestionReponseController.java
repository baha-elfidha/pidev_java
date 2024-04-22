package tn.esprit.pidev.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import tn.esprit.pidev.HelloApplication;
import tn.esprit.pidev.models.Reclamation;
import tn.esprit.pidev.models.Reponse;
import tn.esprit.pidev.services.ReponseService;

import java.io.IOException;
import java.util.List;

public class GestionReponseController {

    @FXML
    private ListView<Reponse> reponsesListView;

    private int reclamationId;

    private final ReponseService reponseService = new ReponseService();

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
        loadReponsesForReclamation();
    }

    private void loadReponsesForReclamation() {
        List<Reponse> reponses = reponseService.getReponsesForReclamation(reclamationId);
        reponsesListView.setItems(FXCollections.observableArrayList(reponses));
    }

    @FXML
    private void addResponseAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateOrEditReponse.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            CreateOrEditReponseController controller = fxmlLoader.getController();
            controller.initializeData(null,reclamationId);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void editResponseAction(ActionEvent actionEvent) {
        Reponse selectedResponse = reponsesListView.getSelectionModel().getSelectedItem();
        if (selectedResponse != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateOrEditReponse.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                CreateOrEditReponseController controller = fxmlLoader.getController();
                controller.initializeData(selectedResponse,selectedResponse.getReclamationId());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a response to edit.");
        }
    }

    @FXML
    private void deleteResponseAction(ActionEvent actionEvent) {
        Reponse selectedResponse = reponsesListView.getSelectionModel().getSelectedItem();
        if (selectedResponse != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Response");
            alert.setContentText("Are you sure you want to delete this response?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    int deleteResult = reponseService.removeReponse(selectedResponse.getId());
                    if (deleteResult > 0) {
                        loadReponsesForReclamation();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Delete Error");
                        errorAlert.setContentText("Failed to delete response.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a response to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void refreshListAction(ActionEvent actionEvent) {
        loadReponsesForReclamation();
    }
}
