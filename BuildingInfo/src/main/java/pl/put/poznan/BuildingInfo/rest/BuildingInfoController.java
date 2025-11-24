package pl.put.poznan.BuildingInfo.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.BuildingInfo.model.Location;
import pl.put.poznan.BuildingInfo.other.LocationDeserializer;


@RestController
@RequestMapping("/")
public class BuildingInfoController {

    @PostMapping(value = "createBuilding", consumes = "application/json")
    public String createBuilding(@RequestBody ObjectNode json) {
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(Location.class, new LocationDeserializer());
        mapper.registerModule(module);

        Location location = mapper.convertValue(json, Location.class);
        return location.printAll();
    }

}


