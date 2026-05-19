package com.telemetry.analyzer;

import com.telemetry.model.TelemetryData;

public interface Analyzer {
    void analyze(TelemetryData data);
}