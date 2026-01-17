package pl.put.poznan.BuildingInfo.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AveragePriceOfEnergyTest {

    private AveragePriceOfEnergy energyPrice;

    @BeforeEach
    void setUp() {
        energyPrice = new AveragePriceOfEnergy();
    }

    @Test
    void testDefaultValueIsZero() {
        Assertions.assertEquals(0.0, energyPrice.get());
    }

    @Test
    void testSetAndGetStandardValue() {
        energyPrice.set(2000);
        Assertions.assertEquals(2000, energyPrice.get());
    }

    @Test
    void testUpdateValue() {
        energyPrice.set(1.0);
        Assertions.assertEquals(1.0, energyPrice.get());

        energyPrice.set(2.5);
        Assertions.assertEquals(2.5, energyPrice.get());
    }

    @Test
    void testSetNegativeValue() {
        energyPrice.set(-0.5);
        Assertions.assertEquals(-0.5, energyPrice.get());
    }
}