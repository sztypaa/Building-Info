package pl.put.poznan.BuildingInfo.model;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class CompoundLocation implements Location {
    private UUID id;
    private String name;
    private List<Location> children = new ArrayList<>();

    public CompoundLocation(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void add(Location location) {
        children.add(location);
    }

    public void print() {
        System.out.println(name);
        for(Location child : children) {
            System.out.print("-");
            child.print();
        }
    }

    @Override
    public void printAll() {
        System.out.println(id + " " + name);
        for(Location child : children) {
            System.out.print("-");
            child.printAll();
        }
    }
}
