package com.telemetry.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class MetricCardController {

    @FXML private Label titleLabel;
    @FXML private Label valueLabel;
    @FXML private Label unitLabel;
    @FXML private Label statusLabel;
    @FXML private ProgressBar progressBar;

    private double maxVal = 100.0;

    public void init(String title, String unit, double max) {
        titleLabel.setText(title.toUpperCase());
        unitLabel.setText(unit);
        this.maxVal = max;
    }

    public void setValue(double val, String status) {
        valueLabel.setText(String.format("%.1f", val));
        progressBar.setProgress(val / maxVal);
        
        statusLabel.setText(status.toUpperCase());
        
        progressBar.getStyleClass().removeAll("progress-normal", "progress-warning", "progress-critical");
        switch (status.toUpperCase()) {
            case "WARNING" -> {
                progressBar.getStyleClass().add("progress-warning");
                statusLabel.setStyle("-fx-text-fill: #f97316; -fx-font-size: 10px; -fx-font-weight: 800;");
            }
            case "CRITICAL" -> {
                progressBar.getStyleClass().add("progress-critical");
                statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 10px; -fx-font-weight: 800;");
            }
            default -> {
                progressBar.getStyleClass().add("progress-normal");
                statusLabel.setStyle("-fx-text-fill: #22c55e; -fx-font-size: 10px; -fx-font-weight: 800;");
            }
        }
    }
}
