package tn.esprit.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.pidev.HelloApplication;
import tn.esprit.pidev.models.Reclamation;
import tn.esprit.pidev.services.ReclamationService;

import java.io.IOException;
import java.util.List;

public class GestionReclamationController {

    @FXML
    private ListView<Reclamation> reclamationsListView;

    private final ReclamationService reclamationService;

    public GestionReclamationController() {
        this.reclamationService = new ReclamationService();
    }

    @FXML
    public void initialize() {
        // Load reclamations into the ListView
        loadReclamations();

        // Set custom cell factory
        reclamationsListView.setCellFactory(new Callback<ListView<Reclamation>, ListCell<Reclamation>>() {
            @Override
            public ListCell<Reclamation> call(ListView<Reclamation> listView) {
                return new ReclamationCell();
            }
        });

        // Set double-click action on list items
        reclamationsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Reclamation selectedReclamation = reclamationsListView.getSelectionModel().getSelectedItem();
                if (selectedReclamation != null) {
                    // Show reclamation content
                    showReclamationContent(selectedReclamation);
                }
            }
        });
    }

    private void loadReclamations() {
        reclamationsListView.getItems().clear();
        reclamationsListView.getItems().addAll(reclamationService.getAllReclamations());
    }

    public void addReclamationAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateOrEditReclamation.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReclamationAction(ActionEvent actionEvent) {
        Reclamation selectedReclamation = reclamationsListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Reclamation");
            alert.setContentText("Are you sure you want to delete this reclamation?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    int deleteResult = reclamationService.removeReclamation(selectedReclamation.getId());
                    if (deleteResult > 0) {
                        loadReclamations();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Delete Error");
                        errorAlert.setContentText("Failed to delete reclamation.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a reclamation to delete.");
            alert.showAndWait();
        }
    }

    private void showReclamationContent(Reclamation reclamation) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GestionReponse.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            GestionReponseController controller = fxmlLoader.getController();
            controller.setReclamationId(reclamation.getId());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editReclamationAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateOrEditReclamation.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            CreateOrEditReclamation controller = fxmlLoader.getController();
            controller.initializeData(reclamationsListView.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshListAction(ActionEvent actionEvent) {
        refreshList();
    }

    private void refreshList() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        reclamationsListView.getItems().setAll(reclamations);
    }

    private class ReclamationCell extends ListCell<Reclamation> {
        private final Label descriptionLabel;
        private final Label sujetLabel;

        public ReclamationCell() {
            descriptionLabel = new Label();
            sujetLabel = new Label();

            setGraphic(createCell());
        }

        private HBox createCell() {
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(descriptionLabel, sujetLabel);
            return hbox;
        }

        @Override
        protected void updateItem(Reclamation item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                descriptionLabel.setText(item.getDescription());
                sujetLabel.setText(item.getSujet());
                setGraphic(createCell());
            }
        }
    }
}
