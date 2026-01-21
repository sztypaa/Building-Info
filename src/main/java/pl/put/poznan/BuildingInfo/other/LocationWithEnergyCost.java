package pl.put.poznan.BuildingInfo.other;

/**
 * <code>LocationWithEnergyCost</code> is a wrapper class of <code>{@link Location}</code> used in
 * <code>{@link pl.put.poznan.BuildingInfo.rest.BuildingInfoController#calculateEnergyCost(int)}</code>.
 *
 * @version %I% %D%
 */

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import pl.put.poznan.BuildingInfo.model.Location;

@JsonView(LocationView.EnergyCost.class)
public record LocationWithEnergyCost(@JsonUnwrapped Location location, float energyCost) {
}
