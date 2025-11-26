package pl.put.poznan.BuildingInfo.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; /*TODO*/
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.BuildingInfo.logic.BuildingInfo;
import pl.put.poznan.BuildingInfo.model.Location;
import pl.put.poznan.BuildingInfo.other.LocationDeserializer;


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

    @PostMapping(value = "createBuilding", consumes = "application/json")
    public String createBuilding(@RequestBody ObjectNode json) {
        Location location = mapper.convertValue(json, Location.class);

        buildingInfo.save(location);

        return location.printAll() + "\n";
    }

    @GetMapping(value = "getVolume")
    @ResponseBody
    public String getVolume(@RequestParam int locationId) {
        return buildingInfo.getLocationById(locationId).getVolume() + "\n";
    }

    @GetMapping(value = "calculateHeating")
    @ResponseBody
    public String calculateHeating(@RequestParam int locationId) {
        return buildingInfo.getLocationById(locationId).calculateHeatingEnergy() + "\n";
    }

}


