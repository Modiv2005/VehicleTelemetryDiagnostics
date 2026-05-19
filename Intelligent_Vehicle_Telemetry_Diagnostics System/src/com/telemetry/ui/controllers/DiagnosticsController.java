package com.telemetry.ui.controllers;

import com.telemetry.service.HealthReport;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class DiagnosticsController implements DashChildController {

    @FXML private ProgressBar engineBar;
    @FXML private ProgressBar batteryBar;
    @FXML private ProgressBar coolingBar;

    @FXML private Label engineLabel;
    @FXML private Label batteryLabel;
    @FXML private Label coolingLabel;
    @FXML private Label recommendationLabel;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void updateHealth(HealthReport report) {
        updateBar(engineBar, engineLabel, report.getEngineHealth());
        updateBar(batteryBar, batteryLabel, report.getBatteryHealth());
        updateBar(coolingBar, coolingLabel, report.getCoolingHealth());

        if (report.getEngineHealth() < 70 || report.getBatteryHealth() < 70) {
            recommendationLabel.setText("System detected maintenance requirement in propulsion or energy storage. Schedule service soon.");
            recommendationLabel.setStyle("-fx-text-fill: #f59e0b; -fx-font-style: italic;");
        } else {
            recommendationLabel.setText("All systems operating within optimal flight parameters. No maintenance required.");
            recommendationLabel.setStyle("-fx-text-fill: #22c55e; -fx-font-style: italic;");
        }
    }

    private void updateBar(ProgressBar bar, Label label, double val) {
        bar.setProgress(val / 100.0);
        label.setText(String.format("%.0f%%", val));
        
        // Match CSS classes (though we can directly style here for precise control)
        if (val < 40) {
            bar.setStyle("-fx-accent: #ef4444;");
            label.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
        } else if (val < 75) {
            bar.setStyle("-fx-accent: #f59e0b;");
            label.setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
        } else {
            bar.setStyle("-fx-accent: #22c55e;");
            label.setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
        }
    }
}
