package com.telemetry.analyzer;

import com.telemetry.model.TelemetryData;
import com.telemetry.alert.AlertManager;

public class SpeedAnalyzer implements Analyzer {
    private final AlertManager alertManager;

    public SpeedAnalyzer(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @Override
    public void analyze(TelemetryData data) {
        if (data.getSpeed() > 140) {
            alertManager.addAlert("CRITICAL: Extreme Speed Detected!", "CRITICAL");
        } else if (data.getSpeed() > 120) {
            alertManager.addAlert("WARNING: High Speed Alert", "WARNING");
        }
    }
}