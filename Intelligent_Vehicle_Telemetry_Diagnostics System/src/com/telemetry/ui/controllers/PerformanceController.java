package com.telemetry.ui.controllers;

import com.telemetry.model.TelemetryData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PerformanceController implements DashChildController {

    @FXML private Label avgSpeedLabel;
    @FXML private Label fuelConsLabel;
    @FXML private Label distanceLabel;
    @FXML private Label peakRpmLabel;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void update(TelemetryData data) {
        // Mocking some stats for performance view
        avgSpeedLabel.setText(String.format("%.1f", data.getSpeed() * 0.9));
        peakRpmLabel.setText(String.format("%.0f", data.getRpm()));
    }
}
