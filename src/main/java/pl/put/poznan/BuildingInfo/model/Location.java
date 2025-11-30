package pl.put.poznan.BuildingInfo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.concurrent.atomic.AtomicInteger;

@JsonIgnoreProperties(value={ "id", "volume" }, ignoreUnknown = true)
public abstract class Location {
    protected static final AtomicInteger counter = new AtomicInteger(0);
    protected int id;
    protected String name;

    public Location() {
        this("");
    }

    public Location(String name) {
        this.id = counter.incrementAndGet();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getVolume();

    public abstract float getTotalHeating();

    public float calculateHeatingEnergy() {
        if (getVolume() == 0) {
            return 0;
        }
        return getTotalHeating() / getVolume();
    }

    public abstract String print();

    protected abstract String print(String indent, boolean last, boolean start);

    public abstract String printAll();

    protected abstract String printAll(String indent, boolean last, boolean start);
}
