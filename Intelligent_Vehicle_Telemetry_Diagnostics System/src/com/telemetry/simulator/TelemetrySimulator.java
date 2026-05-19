package com.telemetry.simulator;

import com.telemetry.model.TelemetryData;
import com.telemetry.model.Vehicle;
import java.util.Random;

public class TelemetrySimulator {

    private final Random random = new Random();
    private double currentSpeed = 0;
    private double currentRpm = 800;
    private double currentTemp = 75;
    private double fuelLevel = 100;
    private Vehicle currentVehicle = null;

    public TelemetryData generateData(Vehicle vehicle) {
        // Reset if vehicle changed (Using ModelName as unique ID for now)
        if (currentVehicle == null || !currentVehicle.getModelName().equals(vehicle.getModelName())) {
            this.currentVehicle = vehicle;
            this.currentSpeed = 0;
            this.currentRpm = vehicle.isElectric() ? 0 : 800;
            this.currentTemp = 70;
            this.fuelLevel = 100;
        }

        // Logic (Dynamic)
        currentSpeed += (random.nextDouble() - 0.4) * 8; 
        if (currentSpeed < 0) currentSpeed = 0;
        if (currentSpeed > 280) currentSpeed = 280;

        if (vehicle.isElectric()) {
            currentRpm = currentSpeed * 60; // Simulate motor rpm
        } else {
            currentRpm = 800 + (currentSpeed * 45) + (random.nextDouble() * 200);
        }
        
        currentTemp += (currentRpm / 6000.0) + (random.nextDouble() - 0.5);
        if (currentTemp < 70) currentTemp = 70;
        if (currentTemp > 115) currentTemp = 115;

        fuelLevel -= (vehicle.isElectric() ? 0.02 : 0.01) + (currentRpm / 150000.0);
        if (fuelLevel < 0) fuelLevel = 0;

        double batteryVoltage = (vehicle.isElectric() ? 350 : 12.5) + (random.nextDouble() * 2.0);

        return new TelemetryData(currentSpeed, currentTemp, currentRpm, fuelLevel, batteryVoltage);
    }
}