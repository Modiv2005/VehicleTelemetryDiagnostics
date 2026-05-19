package com.telemetry.service;

public class HealthReport {

    private double engineHealth;
    private double fuelHealth;
    private double coolingHealth;
    private double batteryHealth;

    public HealthReport(double engineHealth, double fuelHealth,
                        double coolingHealth, double batteryHealth) {
        this.engineHealth = engineHealth;
        this.fuelHealth = fuelHealth;
        this.coolingHealth = coolingHealth;
        this.batteryHealth = batteryHealth;
    }

    public double getEngineHealth() { return engineHealth; }
    public double getFuelHealth() { return fuelHealth; }
    public double getCoolingHealth() { return coolingHealth; }
    public double getBatteryHealth() { return batteryHealth; }
}