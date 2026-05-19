package com.telemetry.alert;

import com.telemetry.model.Alert;
import com.telemetry.model.TelemetryData;
import com.telemetry.analyzer.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AlertManager {
    private final List<Alert> alerts = new ArrayList<>();
    private final List<Analyzer> analyzers = new ArrayList<>();
    private Consumer<Alert> alertListener;

    public void addAnalyzer(Analyzer analyzer) {
        analyzers.add(analyzer);
    }

    public void setAlertListener(Consumer<Alert> listener) {
        this.alertListener = listener;
    }

    public void analyze(TelemetryData data) {
        for (Analyzer analyzer : analyzers) {
            analyzer.analyze(data);
        }
    }

    public void addAlert(String message, String severity) {
        // Prevent duplicate recent alerts
        if (!alerts.isEmpty() && alerts.get(0).getMessage().equals(message)) {
            return;
        }

        Alert alert = new Alert(message, severity);
        alerts.add(0, alert);

        if (alertListener != null) {
            alertListener.accept(alert);
        }
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
