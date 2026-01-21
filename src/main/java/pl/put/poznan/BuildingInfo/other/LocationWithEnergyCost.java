package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import pl.put.poznan.BuildingInfo.model.Location;

@JsonView(LocationView.EnergyCost.class)
public record LocationWithEnergyCost(@JsonUnwrapped Location location, float energyCost) {
}