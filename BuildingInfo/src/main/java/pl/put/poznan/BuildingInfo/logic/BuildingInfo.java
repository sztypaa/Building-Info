package pl.put.poznan.BuildingInfo.logic;

import pl.put.poznan.BuildingInfo.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class BuildingInfo {

    private final List<CompoundLocation> buildings;

    public BuildingInfo(){
        this.buildings = new ArrayList<>();
    }

    public CompoundLocation parse(Map<String, Object> json) {
        String name = json.get("name").toString();
        return new CompoundLocation(name);
    }

    public Map<String, Object> parse(CompoundLocation building) {
       Map<String, Object> json = new HashMap<>();
       json.put("id", building.getId().toString());
       json.put("name", building.getName());
       json.put("children", new ArrayList<>());
       return json;
    }

    public void save(Map<String, Object> json){
        buildings.add(parse(json));
    }

    public Map<String, Object> select() {
        return parse(buildings.get(0));
    }

}
