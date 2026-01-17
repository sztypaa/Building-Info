package pl.put.poznan.BuildingInfo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.put.poznan.BuildingInfo.logic.AveragePriceOfEnergy;
import pl.put.poznan.BuildingInfo.model.Location;
import pl.put.poznan.BuildingInfo.other.LocationWithEnergyCost;

import java.util.Objects;

class BuildingInfoControllerTest {

    private BuildingInfoController controller;
    private AveragePriceOfEnergy price;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        price = new AveragePriceOfEnergy();
        price.set(1.23);
        controller = new BuildingInfoController(price);
        controller.initialize();
        mapper = new ObjectMapper();
    }

    private ObjectNode createBuildingJson(String name) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id", 0);
        node.put("name", name);
        return node;
    }

    private int createAndGetId(String name) {
        ObjectNode json = createBuildingJson(name);
        ResponseEntity<Location> response = controller.createBuilding(json);
        if (response.getBody() != null) {
            return response.getBody().getId();
        }
        throw new RuntimeException("Failed to create building in test setup");
    }

    @Test
    void testCreateBuildingSuccess() {
        ObjectNode json = createBuildingJson("TestBuilding");
        ResponseEntity<Location> response = controller.createBuilding(json);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().getId() > 0);
    }

    @Test
    void testGetAllFound() {
        int id = createAndGetId("BuildingA");

        ResponseEntity<Location> response = controller.getAll(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetAllNotFound() {
        ResponseEntity<Location> response = controller.getAll(99999);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void testPrintFound() {
        int id = createAndGetId("BuildingB");
        ResponseEntity<String> response = controller.print(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testPrintNotFound() {
        ResponseEntity<String> response = controller.print(99999);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Location not found", response.getBody());
    }

    @Test
    void testPrintAllFound() {
        int id = createAndGetId("BuildingC");
        ResponseEntity<String> response = controller.printAll(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testPrintAllNotFound() {
        ResponseEntity<String> response = controller.printAll(88888);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetCube() {
        int id = createAndGetId("CubeCheck");
        ResponseEntity<Location> response = controller.getCube(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetArea() {
        int id = createAndGetId("AreaCheck");
        ResponseEntity<Location> response = controller.getArea(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(id, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void testCalculateHeating() {
        int id = createAndGetId("HeatCheck");
        ResponseEntity<Location> response = controller.calculateHeating(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testCalculateLighting() {
        int id = createAndGetId("LightCheck");
        ResponseEntity<Location> response = controller.calculateLighting(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCalculateEnergyCost() {
        price.set(5.0);
        int id = createAndGetId("CostCheck");

        ResponseEntity<LocationWithEnergyCost> response = controller.calculateEnergyCost(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testInitializationLog() {
        Assertions.assertDoesNotThrow(() -> controller.initialize());
    }
}