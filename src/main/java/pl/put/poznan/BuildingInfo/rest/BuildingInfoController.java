package pl.put.poznan.BuildingInfo.rest;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.BuildingInfo.logic.BuildingInfo;
import pl.put.poznan.BuildingInfo.model.Location;
import pl.put.poznan.BuildingInfo.other.LocationDeserializer;
import pl.put.poznan.BuildingInfo.other.LocationView;

/**
 * <code>BuildingInfoController</code> class specifies REST mappings in
 * <code>{@link pl.put.poznan.BuildingInfo.app.BuildingInfoApplication}</code>.
 */
@RestController
@RequestMapping("/")
public class BuildingInfoController {
    /**
     * used to store and access buildings
     */
    private final BuildingInfo buildingInfo = new BuildingInfo();
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
        Location location = mapper.convertValue(json, Location.class);

        buildingInfo.save(location);

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
    public ResponseEntity<Location> getAll(@RequestParam int id) {
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
    public ResponseEntity<String> print(@RequestParam int id) {
        Location location = buildingInfo.getLocationById(id);
        return new ResponseEntity<>(location.info(), HttpStatus.OK);
    }

    /**
     * Returns response containing tree of all information about location with matching id
     * @param id    id to search for
     * @return      string that represents tree with all information about location with matching id
     */
    @GetMapping(value = "printAll", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> printAll(@RequestParam int id) {
        Location location = buildingInfo.getLocationById(id);
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
    public ResponseEntity<Location> getCube(@RequestParam int id) {
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
    public ResponseEntity<Location> getArea(@RequestParam int id) {
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
    public ResponseEntity<Location> calculateHeating(@RequestParam int id) {
        return new ResponseEntity<>(buildingInfo.getLocationById(id), HttpStatus.OK);
    }

    /**
     * Returns JSON response representing id, name and average lighting power of location with matching id
     * @param id    id to search for
     * @return      location with matching id
     */
    @JsonView(LocationView.Lighting.class)
    @GetMapping(value = "calculateLighting")
    @ResponseBody
    public ResponseEntity<Location> calculateLighting(@RequestParam int id) {
        return new ResponseEntity<>(buildingInfo.getLocationById(id), HttpStatus.OK);
    }

}


