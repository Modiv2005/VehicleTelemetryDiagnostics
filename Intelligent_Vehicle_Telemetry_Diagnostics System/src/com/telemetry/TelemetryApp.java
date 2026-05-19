package com.telemetry;

import com.telemetry.simulator.*;
import com.telemetry.analyzer.*;
import com.telemetry.alert.AlertManager;
import com.telemetry.service.HealthCalculator;
import com.telemetry.ui.controllers.*;
import com.telemetry.ui.viewmodels.TelemetryViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelemetryApp extends Application {

    private TelemetrySimulator simulator;
    private AlertManager alertManager;
    private HealthCalculator healthCalculator;
    private TelemetryViewModel viewModel;
    private ScheduledExecutorService executor;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Initialize Backend
        simulator = new TelemetrySimulator();
        alertManager = new AlertManager();
        healthCalculator = new HealthCalculator();
        viewModel = new TelemetryViewModel();

        // Analyzers
        alertManager.addAnalyzer(new SpeedAnalyzer(alertManager));
        alertManager.addAnalyzer(new TemperatureAnalyzer(alertManager));
        alertManager.addAnalyzer(new FuelAnalyzer(alertManager));
        alertManager.addAnalyzer(new BatteryAnalyzer(alertManager));

        // 2. Load UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();

        // 3. Connect UI components to ViewModel if needed
        alertManager.setAlertListener(alert -> {
             Platform.runLater(() -> {
                 viewModel.addAlert(alert);
                 // Direct push to controllers for real-time update
                 DashboardController dash = (DashboardController) mainController.getController("Dashboard");
                 if (dash != null) dash.addAlert(alert);
                 
                 AlertsController history = (AlertsController) mainController.getController("Alerts");
                 if (history != null) history.addAlert(alert);
             });
        });

        // 4. Start Simulation Loop
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (mainController.getCurrentVehicle() != null) {
                var data = simulator.generateData(mainController.getCurrentVehicle());
                var health = healthCalculator.calculate(data);
                alertManager.analyze(data);
                
                Platform.runLater(() -> {
                    viewModel.update(data, health);
                    
                    // Propagate to active controllers
                    DashboardController dash = (DashboardController) mainController.getController("Dashboard");
                    if (dash != null) dash.update(data);
                    
                    PerformanceController perf = (PerformanceController) mainController.getController("Performance");
                    if (perf != null) perf.update(data);
                    
                    DiagnosticsController diag = (DiagnosticsController) mainController.getController("Diagnostics");
                    if (diag != null) diag.updateHealth(health);
                });
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        // 5. Build Scene
        Scene scene = new Scene(root, 1280, 800);
        
        // Ensure Global Styling
        String stylePath = getClass().getResource("/resources/style.css").toExternalForm();
        scene.getStylesheets().add(stylePath);

        primaryStage.setTitle("VEHICLE INTELLIGENCE PLATFORM - MOTORSPORT CORE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
