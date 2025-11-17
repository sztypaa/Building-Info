package pl.put.poznan.BuildingInfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.BuildingInfo.logic.BuildingInfo;
import java.util.Map;

import java.util.Arrays;


@RestController
@RequestMapping("/")
public class BuildingInfoController {

    @PostMapping(consumes = "application/json")
    public Map<String, Object> post(@RequestBody Map<String, Object> json) {
        BuildingInfo buildingInfo = new BuildingInfo();
        buildingInfo.save(json);
        Map<String, Object> json2 = buildingInfo.select();
        return json2;
    }

}


