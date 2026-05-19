package com.telemetry.ui.controllers;

import com.telemetry.model.TelemetryData;
import com.telemetry.model.Alert;
import com.telemetry.ui.viewmodels.TelemetryViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;

public class DashboardController implements DashChildController {

    @FXML private MetricCardController speedCardController;
    @FXML private MetricCardController rpmCardController;
    @FXML private MetricCardController tempCardController;
    @FXML private MetricCardController fuelCardController;
    @FXML private MetricCardController voltCardController;

    @FXML private LineChart<Number, Number> telemetryChart;
    @FXML private VBox alertList;
    @FXML private ScrollPane alertScrollPane;

    private XYChart.Series<Number, Number> speedSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> tempSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> rpmSeries = new XYChart.Series<>();
    private int timeIndex = 0;

    private MainController mainController;

    @FXML
    public void initialize() {
        speedSeries.setName("SPEED");
        tempSeries.setName("TEMP");
        rpmSeries.setName("RPM");
        telemetryChart.getData().addAll(speedSeries, tempSeries, rpmSeries);

        // Initialize cards with parameters
        speedCardController.init("Speed", "km/h", 200.0);
        rpmCardController.init("Engine Load", "RPM", 8000.0);
        tempCardController.init("Engine Temp", "°C", 110.0);
        fuelCardController.init("Fuel Level", "%", 100.0);
        voltCardController.init("Battery", "V", 16.0);
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void update(TelemetryData data) {
        Platform.runLater(() -> {
            // Update Charts
            addDataPoint(speedSeries, data.getSpeed());
            addDataPoint(tempSeries, data.getTemperature());
            addDataPoint(rpmSeries, data.getRpm() / 40.0); // Scaled for chart visibility

            // Update Cards
            speedCardController.setValue(data.getSpeed(), getStatus(data.getSpeed(), 120, 140));
            rpmCardController.setValue(data.getRpm(), getStatus(data.getRpm(), 5000, 7000));
            tempCardController.setValue(data.getTemperature(), getStatus(data.getTemperature(), 95, 105));
            fuelCardController.setValue(data.getFuelLevel(), getStatusInverse(data.getFuelLevel(), 15, 5));
            voltCardController.setValue(data.getBatteryVoltage(), "NORMAL");
        });
    }

    public void addAlert(Alert alert) {
        Platform.runLater(() -> {
            VBox alertCard = createAlertCard(alert);
            alertList.getChildren().add(0, alertCard);
        });
    }

    private void addDataPoint(XYChart.Series<Number, Number> series, double value) {
        series.getData().add(new XYChart.Data<>(timeIndex++, value));
        if (series.getData().size() > 60) {
            series.getData().remove(0);
        }
    }

    private VBox createAlertCard(Alert alert) {
        VBox card = new VBox(4);
        card.getStyleClass().add("glass-card");
        card.setPadding(new javafx.geometry.Insets(12, 16, 12, 16));
        
        String color = switch(alert.getSeverity().toUpperCase()) {
            case "CRITICAL" -> "#ef4444";
            case "WARNING" -> "#f97316";
            default -> "#3b82f6";
        };
        
        // Severity Strip
        card.setStyle("-fx-border-color: " + color + "; -fx-border-width: 0 0 0 5; -fx-padding: 10 15;");

        Label msg = new Label(alert.getMessage());
        msg.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: bold;");
        msg.setWrapText(true);
        
        Label time = new Label(alert.getFormattedTimestamp().toUpperCase());
        time.getStyleClass().add("label-muted");
        time.setStyle("-fx-font-size: 9px; -fx-font-weight: 800; -fx-letter-spacing: 0.5px;");

        card.getChildren().addAll(time, msg);
        return card;
    }

    private String getStatus(double val, double warn, double crit) {
        if (val > crit) return "CRITICAL";
        if (val > warn) return "WARNING";
        return "NORMAL";
    }

    private String getStatusInverse(double val, double warn, double crit) {
        if (val < crit) return "CRITICAL";
        if (val < warn) return "WARNING";
        return "NORMAL";
    }
}
