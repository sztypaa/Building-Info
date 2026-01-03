package pl.put.poznan.BuildingInfo.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.BuildingInfo.app.BuildingInfoApplication;
import pl.put.poznan.BuildingInfo.logic.BuildingInfo;
import pl.put.poznan.BuildingInfo.model.Location;
import pl.put.poznan.BuildingInfo.other.LocationDeserializer;
import pl.put.poznan.BuildingInfo.other.LocationView;

/**
 * <code>BuildingInfoController</code> class specifies REST mappings in
 * <code>{@link BuildingInfoApplication}</code>.
 *
 * @version %I% %D%
 */
@RestController
@RequestMapping("/")
public class BuildingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);

    /**
     * used to store and access buildings
     */
    @Autowired
    private BuildingInfo buildingInfo;
    /**
     * used to serialize/deserialize objects to/from JSON
     */
    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * used to register serializers and deserializers
     */
    private final SimpleModule module = new SimpleModule();
    /**
     * register custom deserializer <code>{@link LocationDeserializer}</code> for <code>{@link Location}</code> class
     */
    @PostConstruct
    public void initialize() {
        module.addDeserializer(Location.class, new LocationDeserializer());
        mapper.registerModule(module);
        logger.debug("BuildingInfoController initialized");
    }

    /**
     * Deserializes JSON to building, saves it in {@link #buildingInfo} and returns JSON response with re-serialized
     * building.
     * @param json  JSON from request
     * @return      building
     */
    @JsonView(LocationView.All.class)
    @PostMapping(value = "createBuilding", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> createBuilding(@RequestBody ObjectNode json) {
        logger.info("Received POST request to create building");

        Location location = mapper.convertValue(json, Location.class);
        buildingInfo.save(location);

        logger.debug("Building structure created successfully with root ID: {}", location.getId());
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    /**
     * Returns JSON response representing all information about location with matching id
     * @param id    id to search for
     * @return      location with matching id
     */
    @JsonView(LocationView.All.class)
    @GetMapping(value = "getAll", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getAll(@RequestParam("id") int id) {
        logger.info("Received GET request: getAll for ID {}", id);
        Location location = buildingInfo.getLocationById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    /**
     * Returns response containing tree of short information about location with matching id
     * @param id    id to search for
     * @return      string that represents tree with short information about location with matching id
     */
    @GetMapping(value = "print", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> print(@RequestParam("id") int id) {
        logger.info("Received GET request: print (info) for ID {}", id);
        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location.info(), HttpStatus.OK);
    }

    /**
     * Returns response containing tree of all information about location with matching id
     * @param id    id to search for
     * @return      string that represents tree with all information about location with matching id
     */
    @GetMapping(value = "printAll", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> printAll(@RequestParam("id") int id) {
        logger.info("Received GET request: printAll (allInfo) for ID {}", id);
        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location.allInfo(), HttpStatus.OK);
    }

    /**
     * Returns JSON response representing id, name and cubature of location with matching id
     * @param id    id to search for
     * @return      location with matching id
     */
    @JsonView(LocationView.Cube.class)
    @GetMapping(value = "getCube", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getCube(@RequestParam("id") int id) {
        logger.info("Received GET request: getCube for ID {}", id);
        return new ResponseEntity<>(buildingInfo.getLocationById(id), HttpStatus.OK);
    }

    /**
     * Returns JSON response representing id, name and area of location with matching id
     * @param id    id to search for
     * @return      location with matching id
     */
    @JsonView(LocationView.Area.class)
    @GetMapping(value = "getArea", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getArea(@RequestParam("id") int id) {
        logger.info("Received GET request: getArea for ID {}", id);
        return new ResponseEntity<>(buildingInfo.getLocationById(id), HttpStatus.OK);
    }


    /**
     * Returns JSON response representing id, name and average heating energy of location with matching id
     * @param id    id to search for
     * @return      location with matching id
     */
    @JsonView(LocationView.Heating.class)
    @GetMapping(value = "calculateHeating", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> calculateHeating(@RequestParam("id") int id) {
        logger.info("Received GET request: calculateHeating for ID {}", id);
        Location loc = buildingInfo.getLocationById(id);
        if (loc != null) loc.calculateHeatingEnergy();
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }

    /**
     * Returns JSON response representing id, name and average lighting power of location with matching id
     * @param id    id to search for
     * @return      location with matching id
     */
    @JsonView(LocationView.Lighting.class)
    @GetMapping(value = "calculateLighting")
    @ResponseBody
    public ResponseEntity<Location> calculateLighting(@RequestParam("id") int id) {
        logger.info("Received GET request: calculateLighting for ID {}", id);
        Location loc = buildingInfo.getLocationById(id);
        if (loc != null) loc.calculateLightingPower();
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }
}