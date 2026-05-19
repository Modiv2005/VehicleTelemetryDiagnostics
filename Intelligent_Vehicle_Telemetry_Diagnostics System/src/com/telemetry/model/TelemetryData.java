package com.telemetry.model;

import java.time.LocalDateTime;

public class TelemetryData {

    private double speed;
    private double temperature;
    private double rpm;
    private double fuelLevel;
    private double batteryVoltage;
    private LocalDateTime timestamp;

    public TelemetryData(double speed, double temperature, double rpm, double fuelLevel, double batteryVoltage) {
        this.speed = speed;
        this.temperature = temperature;
        this.rpm = rpm;
        this.fuelLevel = fuelLevel;
        this.batteryVoltage = batteryVoltage;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public double getSpeed() {
        return speed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRpm() {
        return rpm;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public double getBatteryVoltage() {
        return batteryVoltage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}