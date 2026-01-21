package pl.put.poznan.BuildingInfo.other;

import pl.put.poznan.BuildingInfo.model.Location;

public class LocationWithEnergyCost {
    private final Location location;
    private final double cost;

    public LocationWithEnergyCost(Location location, double cost) {
        this.location = location;
        this.cost = cost;
    }

    public Location getLocation() {
        return location;
    }

    public double getCost() {
        return cost;
    }
}