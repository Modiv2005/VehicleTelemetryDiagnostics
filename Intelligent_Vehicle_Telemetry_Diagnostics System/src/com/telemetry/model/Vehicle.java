package com.telemetry.model;

public class Vehicle {
    private final String brand;
    private final String modelName;
    private final String type;
    private final int batteryLevel;
    private final int batteryCapacity;
    private final int range;
    private final double acceleration;
    private final int horsepower;
    private final String imagePath;
    private final boolean isElectric;

    public Vehicle(String brand, String modelName, String type, 
                   int batteryLevel, int batteryCapacity, int range, 
                   double acceleration, int horsepower, String imagePath,
                   boolean isElectric) {
        this.brand = brand;
        this.modelName = modelName;
        this.type = type;
        this.batteryLevel = batteryLevel;
        this.batteryCapacity = batteryCapacity;
        this.range = range;
        this.acceleration = acceleration;
        this.horsepower = horsepower;
        this.imagePath = imagePath;
        this.isElectric = isElectric;
    }

    // Getters
    public String getBrand() { return brand; }
    public String getModelName() { return modelName; }
    public String getType() { return type; }
    public int getBatteryLevel() { return batteryLevel; }
    public int getBatteryCapacity() { return batteryCapacity; }
    public int getRange() { return range; }
    public double getAcceleration() { return acceleration; }
    public int getHorsepower() { return horsepower; }
    public String getImagePath() { return imagePath; }
    public boolean isElectric() { return isElectric; }

    @Override
    public String toString() {
        return brand + " " + modelName;
    }
}
