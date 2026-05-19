package com.telemetry.ui.controllers;

import com.telemetry.model.Alert;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlertsController implements DashChildController {

    @FXML private VBox alertHistoryList;
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void addAlert(Alert alert) {
        Platform.runLater(() -> {
            VBox card = createAlertCard(alert);
            alertHistoryList.getChildren().add(0, card);
        });
    }

    private VBox createAlertCard(Alert alert) {
        VBox card = new VBox(5);
        card.getStyleClass().add("glass-card");
        card.setPadding(new Insets(12, 18, 12, 18));
        
        String color = switch(alert.getSeverity().toUpperCase()) {
            case "CRITICAL" -> "#ef4444";
            case "WARNING" -> "#f97316";
            default -> "#3b82f6";
        };
        
        // Severity Strip
        card.setStyle("-fx-border-color: " + color + "; -fx-border-width: 0 0 0 5; -fx-padding: 10 15;");

        Label msg = new Label(alert.getMessage());
        msg.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 13px; -fx-font-weight: bold;");
        
        Label time = new Label(alert.getFormattedTimestamp().toUpperCase());
        time.getStyleClass().add("label-muted");
        time.setStyle("-fx-font-size: 9px; -fx-font-weight: 800; -fx-letter-spacing: 0.5px;");

        card.getChildren().addAll(time, msg);
        return card;
    }
    
    @FXML
    public void initialize() {
        // Clear default
        alertHistoryList.getChildren().clear();
    }
}
