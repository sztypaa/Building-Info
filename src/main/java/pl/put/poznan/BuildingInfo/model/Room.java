package pl.put.poznan.BuildingInfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import pl.put.poznan.BuildingInfo.other.LocationView;

public class Room extends Location{
    private int height;
    private int area;
    private int cube;
    private float heating;
    private int lighting;

    public Room() {
        this("");
    }

    public Room(String name) {
        this(name, 0, 0, 0, 0);
    }

    public Room(String name, int area, int height, float heating, int lighting) {
        super(name);
        this.area = area;
        this.height = height;
        this.cube = height * area;
        this.heating = heating;
        this.lighting = lighting;
    }

    @JsonView(LocationView.All.class)
    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
        this.cube = height * area;
    }

    public void setHeight(int height) {
        this.height = height;
        this.cube = height * area;
    }

    @Override
    @JsonView({LocationView.Cube.class, LocationView.All.class})
    public int getCube() {
        return cube;
    }

    @Override
    @JsonProperty("heating")
    @JsonView(LocationView.All.class)
    public float getTotalHeating() {
        return heating;
    }

    public void setHeating(float heating) {
        this.heating = heating;
    }

    @JsonView(LocationView.All.class)
    public int getLighting() {
        return lighting;
    }

    public void setLighting(int lighting) {
        this.lighting = lighting;
    }

    @Override
    public String print() {
        return this.print("", true, true);
    }

    @Override
    protected String print(String indent, boolean last, boolean start) {
        String elbow = "└──";
        String tee = "├──";

        if(!start) {
            indent += (last ? elbow : tee);
        }

        return indent + "Id: " + id;
    }

    @Override
    public String printAll() {
        return this.printAll("", true, true);
    }

    @Override
    protected String printAll(String indent, boolean last, boolean start) {
        String elbow = "└──";
        String tee = "├──";

        if(!start) {
            indent += (last ? elbow : tee);
        }

        return indent
               + "Id: " + id
               + " Name: " + name
               + " Area: " + area
               + " Cube: " + cube
               + " Heating: " + heating
               + " Lighting: " + lighting;
    }
}
