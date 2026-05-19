package com.telemetry.analyzer;

import com.telemetry.model.TelemetryData;
import com.telemetry.alert.AlertManager;

public class BatteryAnalyzer implements Analyzer {
    private final AlertManager alertManager;

    public BatteryAnalyzer(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @Override
    public void analyze(TelemetryData data) {
        if (data.getBatteryVoltage() < 11.5) {
            alertManager.addAlert("CRITICAL: Battery Voltage Very Low!", "CRITICAL");
        } else if (data.getBatteryVoltage() < 12.0) {
            alertManager.addAlert("WARNING: Low Battery Voltage", "WARNING");
        }
    }
}
