module tn.esprit.pidev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens tn.esprit.pidev to javafx.fxml;
    exports tn.esprit.pidev;
    exports tn.esprit.pidev.controllers;
    exports tn.esprit.pidev.models;
    exports tn.esprit.pidev.services;
    opens tn.esprit.pidev.models;
    opens tn.esprit.pidev.services;
    opens tn.esprit.pidev.controllers;
}