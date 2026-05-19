package com.telemetry.ui.viewmodels;

import com.telemetry.model.TelemetryData;
import com.telemetry.service.HealthReport;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.telemetry.model.Alert;

public class TelemetryViewModel {

    // Telemetry Properties
    private final DoubleProperty speed = new SimpleDoubleProperty(0);
    private final DoubleProperty temperature = new SimpleDoubleProperty(0);
    private final DoubleProperty rpm = new SimpleDoubleProperty(0);
    private final DoubleProperty fuelLevel = new SimpleDoubleProperty(0);
    private final DoubleProperty batteryVoltage = new SimpleDoubleProperty(0);

    // Health Properties
    private final DoubleProperty engineHealth = new SimpleDoubleProperty(100);
    private final DoubleProperty batteryHealth = new SimpleDoubleProperty(100);
    private final DoubleProperty coolingHealth = new SimpleDoubleProperty(100);
    private final DoubleProperty fuelHealth = new SimpleDoubleProperty(100);

    // Alerts
    private final ObservableList<Alert> alerts = FXCollections.observableArrayList();

    public void update(TelemetryData data, HealthReport health) {
        speed.set(data.getSpeed());
        temperature.set(data.getTemperature());
        rpm.set(data.getRpm());
        fuelLevel.set(data.getFuelLevel());
        batteryVoltage.set(data.getBatteryVoltage());

        if (health != null) {
            engineHealth.set(health.getEngineHealth());
            batteryHealth.set(health.getBatteryHealth());
            coolingHealth.set(health.getCoolingHealth());
            fuelHealth.set(health.getFuelHealth());
        }
    }

    public void addAlert(Alert alert) {
        alerts.add(0, alert);
    }

    // Property Getters
    public DoubleProperty speedProperty() { return speed; }
    public DoubleProperty temperatureProperty() { return temperature; }
    public DoubleProperty rpmProperty() { return rpm; }
    public DoubleProperty fuelLevelProperty() { return fuelLevel; }
    public DoubleProperty batteryVoltageProperty() { return batteryVoltage; }

    public DoubleProperty engineHealthProperty() { return engineHealth; }
    public DoubleProperty batteryHealthProperty() { return batteryHealth; }
    public DoubleProperty coolingHealthProperty() { return coolingHealth; }
    public DoubleProperty fuelHealthProperty() { return fuelHealth; }

    public ObservableList<Alert> getAlerts() { return alerts; }
}
