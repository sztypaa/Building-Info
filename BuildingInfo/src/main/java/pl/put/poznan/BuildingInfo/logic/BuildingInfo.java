package pl.put.poznan.BuildingInfo.logic;

import pl.put.poznan.BuildingInfo.model.*;

import java.util.List;
import java.util.ArrayList;
/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class BuildingInfo {

    private final List<CompoundLocation> buildings;

    public BuildingInfo(){
        this.buildings = new ArrayList<>();
    }

    public void save(CompoundLocation building){
        buildings.add(building);
    }

    public CompoundLocation select() {
        return buildings.get(0);
    }

}
