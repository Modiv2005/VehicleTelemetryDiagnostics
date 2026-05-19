package com.telemetry.service;

import com.telemetry.model.TelemetryData;

public class HealthCalculator {

    public HealthReport calculate(TelemetryData data) {
        // Engine Health: Impacts from extreme Temp and RPM
        double engineHealth = 100 
            - (data.getTemperature() > 100 ? (data.getTemperature() - 100) * 2 : 0)
            - (data.getRpm() > 5000 ? (data.getRpm() - 5000) / 100 : 0);

        // Fuel Health: System pressure/status (simulated by level and rpm demand)
        double fuelHealth = data.getFuelLevel();
        if (data.getFuelLevel() < 10) fuelHealth *= 0.8;

        // Cooling Health: Ability to maintain temp
        double coolingHealth = 100 - Math.abs(data.getTemperature() - 85) * 1.5;

        // Battery Health: Voltage stability (ideal 12.6V - 14.4V)
        double batteryHealth = 0;
        if (data.getBatteryVoltage() >= 12.0 && data.getBatteryVoltage() <= 14.8) {
            batteryHealth = 100 - Math.abs(13.4 - data.getBatteryVoltage()) * 10;
        } else {
            batteryHealth = 50 - Math.abs(12.0 - data.getBatteryVoltage()) * 20;
        }

        return new HealthReport(
            clamp(engineHealth), 
            clamp(fuelHealth),
            clamp(coolingHealth), 
            clamp(batteryHealth)
        );
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(100, value));
    }
}