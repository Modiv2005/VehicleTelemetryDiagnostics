package com.telemetry.analyzer;

import com.telemetry.model.TelemetryData;
import com.telemetry.alert.AlertManager;

public class TemperatureAnalyzer implements Analyzer {
    private final AlertManager alertManager;

    public TemperatureAnalyzer(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @Override
    public void analyze(TelemetryData data) {
        if (data.getTemperature() > 105) {
            alertManager.addAlert("CRITICAL: Engine Overheating!", "CRITICAL");
        } else if (data.getTemperature() > 95) {
            alertManager.addAlert("WARNING: Temperature Rising", "WARNING");
        }
    }
}