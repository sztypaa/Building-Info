package pl.put.poznan.BuildingInfo.model;

import java.util.UUID;

public class Room implements Location{
    private UUID id;
    private String name;
    private int area;
    private int volume;
    private float heating;
    private int lighting;

    public Room(String name) {
        this(name, 0, 0, 0, 0);
    }

    public Room(String name, int area, int volume, float heating, int lighting) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.area = area;
        this.volume = volume;
        this.heating = heating;
        this.lighting = lighting;
    }

    public void print() {
        System.out.println(name);
    }

    public void printAll() {
        System.out.println(id + " " + name + " " + area + " " + volume + " " + heating + " " + lighting);
    }
}
