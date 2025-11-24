package pl.put.poznan.BuildingInfo.model;

import java.util.concurrent.atomic.AtomicInteger;


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

    public abstract String print();

    protected abstract String print(String indent, boolean last, boolean start);

    public abstract String printAll();

    protected abstract String printAll(String indent, boolean last, boolean start);
}
