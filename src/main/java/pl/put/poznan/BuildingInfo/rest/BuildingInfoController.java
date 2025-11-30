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

@RestController
@RequestMapping("/")
public class BuildingInfoController {
    private final BuildingInfo buildingInfo = new BuildingInfo();
    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleModule module = new SimpleModule();

    @PostConstruct
    public void initialize() {
        module.addDeserializer(Location.class, new LocationDeserializer());
        mapper.registerModule(module);
    }

    @JsonView(LocationView.All.class)
    @PostMapping(value = "createBuilding", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Location> createBuilding(@RequestBody ObjectNode json) {
        Location location = mapper.convertValue(json, Location.class);

        buildingInfo.save(location);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @JsonView(LocationView.All.class)
    @GetMapping(value = "getAll", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getAll(@RequestParam int id) {
        Location location = buildingInfo.getLocationById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping(value = "print", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> print(@RequestParam int id) {
        Location location = buildingInfo.getLocationById(id);
        return new ResponseEntity<>(location.print(), HttpStatus.OK);
    }

    @GetMapping(value = "printAll", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> printAll(@RequestParam int id) {
        Location location = buildingInfo.getLocationById(id);
        return new ResponseEntity<>(location.printAll(), HttpStatus.OK);
    }

    @JsonView(LocationView.Cube.class)
    @GetMapping(value = "getCube", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getCube(@RequestParam int id) {
        return new ResponseEntity<>(buildingInfo.getLocationById(id), HttpStatus.OK);
    }

    @JsonView(LocationView.Heating.class)
    @GetMapping(value = "calculateHeating", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> calculateHeating(@RequestParam int id) {
        return new ResponseEntity<>(buildingInfo.getLocationById(id), HttpStatus.OK);
    }

}


