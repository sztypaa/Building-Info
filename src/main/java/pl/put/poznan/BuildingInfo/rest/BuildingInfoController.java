package pl.put.poznan.BuildingInfo.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);

    private final BuildingInfo buildingInfo = new BuildingInfo();
    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleModule module = new SimpleModule();

    @PostConstruct
    public void initialize() {
        module.addDeserializer(Location.class, new LocationDeserializer());
        mapper.registerModule(module);
        logger.debug("BuildingInfoController initialized");
    }

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

    @JsonView(LocationView.All.class)
    @GetMapping(value = "getAll", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getAll(@RequestParam int id) {
        logger.info("Received GET request: getAll for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping(value = "print", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> print(@RequestParam int id) {
        logger.info("Received GET request: print for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(location.print(), HttpStatus.OK);
    }

    @GetMapping(value = "printAll", produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> printAll(@RequestParam int id) {
        logger.info("Received GET request: printAll for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(location.printAll(), HttpStatus.OK);
    }

    @JsonView(LocationView.Cube.class)
    @GetMapping(value = "getCube", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getCube(@RequestParam int id) {
        logger.info("Received GET request: getCube for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.debug("Returning cube for location ID {}: {}", id, location.getCube());
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @JsonView(LocationView.Area.class)
    @GetMapping(value = "getArea", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> getArea(@RequestParam int id) {
        logger.info("Received GET request: getArea for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.debug("Returning area for location ID {}: {}", id, location.getArea());
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @JsonView(LocationView.Heating.class)
    @GetMapping(value = "calculateHeating", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Location> calculateHeating(@RequestParam int id) {
        logger.info("Received GET request: calculateHeating for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        float result = location.calculateHeatingEnergy();
        logger.debug("Heating energy calculated for ID {}: {}", id, result);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @JsonView(LocationView.Lighting.class)
    @GetMapping(value = "calculateLighting")
    @ResponseBody
    public ResponseEntity<Location> calculateLighting(@RequestParam int id) {
        logger.info("Received GET request: calculateLighting for ID {}", id);

        Location location = buildingInfo.getLocationById(id);
        if (location == null) {
            logger.error("Location with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        float result = location.calculateLightingPower();
        logger.debug("Lighting power calculated for ID {}: {}", id, result);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}