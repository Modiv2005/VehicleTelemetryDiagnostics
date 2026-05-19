package com.telemetry.ui.controllers;

import com.telemetry.model.Vehicle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleSelectionController implements DashChildController {

    @FXML private TilePane vehicleGrid;
    private MainController mainController;

    @FXML
    public void initialize() {
        populateVehicles();
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void populateVehicles() {
        vehicleGrid.getChildren().clear();
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("Tesla", "Model 3 Performance", "Sedan", 100, 82, 530, 4.4, 510, "/resources/images/tesla.png", true));
        vehicles.add(new Vehicle("Ford", "Mustang Mach-E GT", "SUV", 100, 91, 500, 3.7, 480, "/resources/images/mach_e.png", true));
        vehicles.add(new Vehicle("Rivian", "R1T Adventure", "Truck", 100, 135, 600, 3.3, 835, "/resources/images/rivian.png", true));

        for (Vehicle v : vehicles) {
            vehicleGrid.getChildren().add(createSaaSCard(v));
        }
    }

    private VBox createSaaSCard(Vehicle v) {
        VBox card = new VBox(15);
        card.getStyleClass().add("glass-card");
        card.setPrefWidth(350);
        card.setAlignment(Pos.TOP_LEFT);

        // 1. Top Image + Overlay Category
        StackPane topArea = new StackPane();
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(v.getImagePath())));
        img.setFitWidth(310);
        img.setPreserveRatio(true);
        
        Label badge = new Label(v.getType().toUpperCase());
        badge.setStyle("-fx-background-color: rgba(37, 99, 235, 0.1); -fx-text-fill: #2563eb; -fx-padding: 4px 10px; -fx-background-radius: 8px; -fx-font-weight: bold; -fx-font-size: 10px;");
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        
        topArea.getChildren().addAll(img, badge);

        // 2. Body Info
        VBox body = new VBox(5);
        Label brand = new Label(v.getBrand().toUpperCase());
        brand.getStyleClass().add("label-muted");
        brand.setStyle("-fx-font-weight: bold; -fx-letter-spacing: 1px;");

        Label model = new Label(v.getModelName());
        model.getStyleClass().add("card-title");

        body.getChildren().addAll(brand, model);

        // 3. Spec Grid (2x2)
        GridPane specGrid = new GridPane();
        specGrid.setHgap(10);
        specGrid.setVgap(10);
        
        specGrid.add(createSpecBox("BATTERY", v.getBatteryCapacity() + " kWh"), 0, 0);
        specGrid.add(createSpecBox("RANGE", v.getRange() + " mi"), 1, 0);
        specGrid.add(createSpecBox("CHARGE", "40 min"), 0, 1);
        specGrid.add(createSpecBox("POWER", v.getHorsepower() + " hp"), 1, 1);

        // 4. Action Button
        Button openBtn = new Button("OPEN DASHBOARD");
        openBtn.getStyleClass().add("btn-primary");
        openBtn.setMaxWidth(Double.MAX_VALUE);
        openBtn.setOnAction(e -> mainController.setVehicle(v));

        card.getChildren().addAll(topArea, body, specGrid, openBtn);
        return card;
    }

    private VBox createSpecBox(String label, String value) {
        VBox box = new VBox(2);
        box.getStyleClass().add("spec-mini-card");
        box.setPrefWidth(145);
        
        Label lbl = new Label(label);
        lbl.getStyleClass().add("label-muted");
        lbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");
        
        Label val = new Label(value);
        val.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        
        box.getChildren().addAll(lbl, val);
        return box;
    }
}
