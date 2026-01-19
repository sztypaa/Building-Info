package pl.put.poznan.BuildingInfo.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.BuildingInfo.model.CompoundLocation;
import pl.put.poznan.BuildingInfo.model.Location;

import java.util.ArrayList;

class BuildingInfoTest {

    private BuildingInfo buildingInfo;

    @BeforeEach
    void setUp() {
        buildingInfo = new BuildingInfo();
    }

    /**
     * StubLocation implementuje wszystkie metody abstrakcyjne Location.
     * Ustawia ID ręcznie, korzystając z dostępu do pola protected 'id'.
     */
    static class StubLocation extends Location {
        public StubLocation(int id, String name) {
            super(name);
            this.id = id;
        }

        @Override public int getArea() { return 100; }
        @Override public int getCube() { return 300; }
        @Override public float getHeating() { return 50.0f; }
        @Override public float getLighting() { return 100.0f; }
        @Override public String info() { return "StubInfo"; }
        @Override protected String info(String indent, boolean last, boolean start) { return "StubInfo"; }
        @Override public String allInfo() { return "StubAllInfo"; }
        @Override protected String allInfo(String indent, boolean last, boolean start) { return "StubAllInfo"; }
    }

    /**
     * StubCompoundLocation rozszerza CompoundLocation, aby ułatwić dodawanie obiektów podrzędnych
     * i ustawianie ID w testach.
     */
    static class StubCompoundLocation extends CompoundLocation {
        public StubCompoundLocation(int id, String name) {
            super(name);
            this.id = id;
        }

        public void addChild(Location loc) {
            if (this.getChildren() == null) {
                this.setChildren(new ArrayList<>());
            }
            this.getChildren().add(loc);
        }
    }

    @Test
    void testGetLocationByIdInEmptyList() {
        Location result = buildingInfo.getLocationById(1);
        Assertions.assertNull(result, "Should return null for empty repository");
    }

    @Test
    void testSaveAndRetrieveRootLocation() {
        StubLocation loc = new StubLocation(10, "Room1");
        buildingInfo.save(loc);

        Location result = buildingInfo.getLocationById(10);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.getId());
    }

    @Test
    void testGetLocationByIdNotFound() {
        buildingInfo.save(new StubLocation(10, "Room1"));
        Location result = buildingInfo.getLocationById(999);
        Assertions.assertNull(result);
    }

    @Test
    void testGetNestedLocationFound() {
        StubCompoundLocation root = new StubCompoundLocation(1, "Floor1");
        StubLocation child = new StubLocation(2, "Room2");
        root.addChild(child);
        buildingInfo.save(root);

        Location result = buildingInfo.getLocationById(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getId());
    }

    @Test
    void testGetDeeplyNestedLocation() {
        StubCompoundLocation level1 = new StubCompoundLocation(1, "Building");
        StubCompoundLocation level2 = new StubCompoundLocation(2, "Floor");
        StubLocation level3 = new StubLocation(3, "Room");

        level1.addChild(level2);
        level2.addChild(level3);

        buildingInfo.save(level1);

        Location result = buildingInfo.getLocationById(3);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getId());
    }

    @Test
    void testSearchAcrossMultipleRoots() {
        buildingInfo.save(new StubLocation(100, "LocA"));
        buildingInfo.save(new StubLocation(200, "LocB"));

        Assertions.assertNotNull(buildingInfo.getLocationById(100));
        Assertions.assertNotNull(buildingInfo.getLocationById(200));
    }

    @Test
    void testNestedStructureNotFound() {
        StubCompoundLocation root = new StubCompoundLocation(1, "Root");
        root.addChild(new StubLocation(2, "Child"));
        buildingInfo.save(root);

        Assertions.assertNull(buildingInfo.getLocationById(3));
    }

    @Test
    void testMultipleChildrenInCompound() {
        StubCompoundLocation root = new StubCompoundLocation(1, "Root");
        root.addChild(new StubLocation(2, "C1"));
        root.addChild(new StubLocation(3, "C2"));
        buildingInfo.save(root);

        Assertions.assertEquals(2, buildingInfo.getLocationById(2).getId());
        Assertions.assertEquals(3, buildingInfo.getLocationById(3).getId());
    }

    @Test
    void testReturnTypeConsistency() {
        StubCompoundLocation root = new StubCompoundLocation(5, "Root");
        buildingInfo.save(root);

        Location result = buildingInfo.getLocationById(5);
        Assertions.assertInstanceOf(CompoundLocation.class, result);
    }
}