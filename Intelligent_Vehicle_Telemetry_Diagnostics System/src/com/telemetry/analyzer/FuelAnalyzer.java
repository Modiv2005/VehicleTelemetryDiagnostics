package com.telemetry.analyzer;

import com.telemetry.model.TelemetryData;
import com.telemetry.alert.AlertManager;

public class FuelAnalyzer implements Analyzer {
    private final AlertManager alertManager;

    public FuelAnalyzer(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @Override
    public void analyze(TelemetryData data) {
        if (data.getFuelLevel() < 5) {
            alertManager.addAlert("CRITICAL: Fuel Level Extremely Low!", "CRITICAL");
        } else if (data.getFuelLevel() < 15) {
            alertManager.addAlert("INFO: Low Fuel Warning", "INFO");
        }
    }
}
