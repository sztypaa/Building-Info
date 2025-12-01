package pl.put.poznan.BuildingInfo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import pl.put.poznan.BuildingInfo.other.LocationView;

import java.util.concurrent.atomic.AtomicInteger;

@JsonIgnoreProperties(value={ "id", "volume" }, ignoreUnknown = true, allowGetters = true)
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

    @JsonView(LocationView.Basic.class)
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(LocationView.Basic.class)
    public String getName() {
        return name;
    }

    public abstract int getArea();

    @JsonView(LocationView.Cube.class)
    public abstract int getCube();

    public abstract float getTotalHeating();

    @JsonProperty("average heating energy")
    @JsonView(LocationView.Heating.class)
    public float calculateHeatingEnergy() {
        if (getCube() == 0) {
            return 0;
        }
        return getTotalHeating() / getCube();
    }

    public abstract float getTotalLighting();

    @JsonProperty("average lighting power")
    @JsonView(LocationView.Lighting.class)
    public float calculateLightingPower() {
        if (getArea() == 0) {
            return 0f;
        }
        return getTotalLighting() / getArea();
    }

    public abstract String print();

    protected abstract String print(String indent, boolean last, boolean start);

    public abstract String printAll();

    protected abstract String printAll(String indent, boolean last, boolean start);
}
