package pl.put.poznan.BuildingInfo.model;

import java.util.List;
import java.util.ArrayList;

public class CompoundLocation extends Location {
    private List<Location> children;

    public CompoundLocation() {
        this("");
    }

    public CompoundLocation(String name) {
        this(name, new ArrayList<>());
    }

    public CompoundLocation(String name, List<Location> children) {
        super(name);
        this.children = children;
    }

    public void setChildren(List<Location> children) {
        this.children = children;
    }

    public List<Location> getChildren() {
        return children;
    }

    public void add(Location location) {
        children.add(location);
    }

    @Override
    public String print() {
        return this.print("", true, true);
    }

    @Override
    protected String print(String indent, boolean last, boolean start) {
        String elbow = "└──";
        String pipe = "│  ";
        String tee = "├──";
        String blank = "   ";
        StringBuilder tree = new StringBuilder();

        tree.append(indent);

        if(!start) {
            tree.append(last ? elbow : tee);
        }

        tree.append("Id: ").append(id);

        if(!start) {
            indent += (last ? blank : pipe);
        }

        for(int i = 0; i < children.size(); i++) {
            tree.append("\n");
            tree.append(children.get(i).print(indent,i == children.size() - 1,false));
        }

        return tree.toString();
    }

    @Override
    public int getVolume() {
        return children.stream().map(Location::getVolume).reduce(0, Integer::sum);
    }

    @Override
    public float getTotalHeating() {
        return children.stream().map(Location::getTotalHeating).reduce(0f, Float::sum);
    }

    @Override
    public String printAll() {
        return this.printAll("", true, true);
    }

    protected String printAll(String indent, boolean last, boolean start) {
        String elbow = "└──";
        String pipe = "│  ";
        String tee = "├──";
        String blank = "   ";
        StringBuilder tree = new StringBuilder();

        tree.append(indent);

        if(!start) {
            tree.append(last ? elbow : tee);
        }

        tree.append("Id: ")
            .append(id)
            .append(" Name: ")
            .append(name);

        if(!start) {
            indent += (last ? blank : pipe);
        }

        for(int i = 0; i < children.size(); i++) {
            tree.append("\n");
            tree.append(children.get(i).printAll(indent,i == children.size() - 1,false));
        }

        return tree.toString();
    }
}
