package pl.put.poznan.BuildingInfo.logic;

import org.springframework.stereotype.Component;
import pl.put.poznan.BuildingInfo.model.*;

import java.util.List;
import java.util.ArrayList;

/**
 * <code>BuildingInfo</code> is class that stores trees of <code>{@link Location}</code> objects and allows storing and
 * accessing them during current <code>{@link pl.put.poznan.BuildingInfo.app.BuildingInfoApplication}</code> runtime.
 * Trees of <code>Location</code> objects are meant to represent buildings and their structure. All stored information
 * is lost after application is shut down.
 *
 * @version %I% %D%
 */
@Component
public class BuildingInfo {
    /**
     * list containing trees of locations
     */
    private final List<Location> locations;

    /**
     * Constructs a new <code>BuildingInfo</code> with no trees of locations.
     */
    public BuildingInfo() {
        this.locations = new ArrayList<>();
    }

    /**
     * Save specified <code>Location</code> tree for the duration of the application runtime.
     * @param location location to save
     */
    public void save(Location location) {
        locations.add(location);
    }

    /**
     * Search tree of <code>Location</code> objects for one with matching id. <code>location</code> should be provided
     * by {@link #getLocationById(int)}
     * @param location  tree or subtree of <code>Location</code> objects to traverse during search
     * @param id        id to look for in a tree
     * @return          location with matching id or null if such location wasn't found
     */
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

    /**
     * Search stored trees of locations for location with matching id.
     * @param id    id to look for in stored trees
     * @return      location with matching id or null if such location wasn't found
     */
    public Location getLocationById(int id) {
        for(Location location : locations) {
            Location temp = getLocationById(location, id);
            if(temp != null) {
                return temp;
            }
        }
        return null;
    }

    private void updateEnergyPrices(Location location, double price) {
        if(location instanceof CompoundLocation compoundLocation) {
            for (Location sublocation : compoundLocation.getChildren()) {
                updateEnergyPrices(sublocation, price);
            }
        } else if (location instanceof Room room){
            room.setEnergyPrice(price);
        }
    }

    public void updateEnergyPrices(double price) {
        for(Location location : locations) {
            updateEnergyPrices(location, price);
        }
    }
}
