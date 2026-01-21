package pl.put.poznan.BuildingInfo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompoundLocationMockTest {

    @Mock
    private Location child1;

    @Mock
    private Location child2;

    private CompoundLocation compoundLocation;

    @BeforeEach
    void setUp() {
        compoundLocation = new CompoundLocation("TestFloor", Arrays.asList(child1, child2));
    }

    @Test
    void testGetAreaCallsChildren() {
        when(child1.getArea()).thenReturn(10);
        when(child2.getArea()).thenReturn(20);

        int result = compoundLocation.getArea();

        assertEquals(30, result);
        verify(child1, times(1)).getArea();
        verify(child2, times(1)).getArea();
    }

    @Test
    void testGetCubeCallsChildren() {
        when(child1.getCube()).thenReturn(100);
        when(child2.getCube()).thenReturn(200);

        int result = compoundLocation.getCube();

        assertEquals(300, result);
        verify(child1, times(1)).getCube();
        verify(child2, times(1)).getCube();
    }

    @Test
    void testGetHeatingCallsChildren() {
        when(child1.getHeating()).thenReturn(50.0f);
        when(child2.getHeating()).thenReturn(150.0f);

        float result = compoundLocation.getHeating();

        assertEquals(200.0f, result);
        verify(child1, times(1)).getHeating();
        verify(child2, times(1)).getHeating();
    }

    @Test
    void testGetLightingCallsChildren() {
        when(child1.getLighting()).thenReturn(30.0f);
        when(child2.getLighting()).thenReturn(70.0f);

        float result = compoundLocation.getLighting();

        assertEquals(100.0f, result);
        verify(child1, times(1)).getLighting();
        verify(child2, times(1)).getLighting();
    }

    @Test
    void testInfoCallsChildren() {
        when(child1.info(anyString(), anyBoolean(), anyBoolean())).thenReturn("Info1");
        when(child2.info(anyString(), anyBoolean(), anyBoolean())).thenReturn("Info2");

        compoundLocation.info();

        verify(child1, times(1)).info(anyString(), anyBoolean(), anyBoolean());
        verify(child2, times(1)).info(anyString(), anyBoolean(), anyBoolean());
    }
}