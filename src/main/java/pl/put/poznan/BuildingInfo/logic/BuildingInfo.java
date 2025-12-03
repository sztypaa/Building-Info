package pl.put.poznan.BuildingInfo.logic;

import pl.put.poznan.BuildingInfo.model.*;

import java.util.List;
import java.util.ArrayList;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class BuildingInfo {

    private final List<Location> locations;

    public BuildingInfo(){
        this.locations = new ArrayList<>();
    }

    public void save(Location location) {
        locations.add(location);
    }

    private Location getLocationById(Location location, int id) {
        if(location.getId() == id) {
            return location;
        } else {
           if (location instanceof CompoundLocation compoundLocation) {
                for (Location sublocation : compoundLocation.getChildren()) {
                    Location temp = getLocationById(sublocation, id);
                    if(temp != null) {
                        return temp;
                    }
                }
            }
        }
        return null;
    }

    public Location getLocationById(int id) {
        for(Location location : locations) {
            Location temp = getLocationById(location, id);
            if(temp != null) {
                return temp;
            }
        }
        return null;
    }

}
