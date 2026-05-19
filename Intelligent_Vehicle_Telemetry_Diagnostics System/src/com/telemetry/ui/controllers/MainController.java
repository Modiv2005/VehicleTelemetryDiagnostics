package com.telemetry.ui.controllers;

import com.telemetry.model.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML private StackPane contentArea;
    @FXML private Label brandLabel;
    @FXML private VBox identityPanel;

    private final Map<String, Node> views = new HashMap<>();
    private final Map<String, Object> controllers = new HashMap<>();
    private Vehicle currentVehicle;

    @FXML
    public void initialize() {
        // Pre-load core views
        loadView("VehicleSelection", "/resources/fxml/VehicleSelection.fxml");
        loadView("Dashboard", "/resources/fxml/Dashboard.fxml");
        loadView("Performance", "/resources/fxml/Performance.fxml");
        loadView("Diagnostics", "/resources/fxml/Diagnostics.fxml");
        loadView("Alerts", "/resources/fxml/Alerts.fxml");
        
        showView("VehicleSelection");
    }

    @FXML
    private void handleNav(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String text = btn.getText();

        switch (text) {
            case "Vehicle Models" -> showView("VehicleSelection");
            case "Live Dashboard" -> showView("Dashboard");
            case "Analytics" -> showView("Performance");
            case "Diagnostics" -> showView("Diagnostics");
            case "Alert Logs" -> showView("Alerts");
        }
    }

    public void setVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        brandLabel.setText(vehicle.getBrand().toUpperCase());
        
        // Jump to dashboard once a vehicle is selected
        showView("Dashboard");
    }

    public void showView(String name) {
        Node nextView = views.get(name);
        if (nextView == null || contentArea.getChildren().contains(nextView)) {
            return;
        }

        // Smooth Transition
        javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(javafx.util.Duration.millis(250), contentArea);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            contentArea.getChildren().setAll(nextView);
            javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(javafx.util.Duration.millis(300), contentArea);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    private void loadView(String name, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            views.put(name, view);
            
            Object controller = loader.getController();
            controllers.put(name, controller);
            
            if (controller instanceof DashChildController child) {
                child.setMainController(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getController(String name) { return controllers.get(name); }
    public Vehicle getCurrentVehicle() { return currentVehicle; }
}
