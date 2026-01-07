package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import pl.put.poznan.BuildingInfo.model.Location;

/**
 * <code>LocationWithEnergyCost</code> is a wrapper class of <code>{@link Location}</code> used in
 * <code>{@link pl.put.poznan.BuildingInfo.rest.BuildingInfoController#calculateEnergyCost(int)}</code>.
 *
 * @version %I% %D%
 */
@JsonView(LocationView.EnergyCost.class)
public record LocationWithEnergyCost(@JsonUnwrapped Location location,
                                     float energyCost) {
}
