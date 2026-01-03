package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>EnergyPricesResponse</code> is a wrapper class for <code>{@link EnergyPricesFetcher}</code> response.
 *
 * @version %I% %D%
 */
public class EnergyPricesResponse {
    /**
     * used to store date received from external API
     */
    private List<EnergyPrice> energyPrices;

    /**
     * Constructs a new <code>EnergyPricesResponse</code> with empty list and average price set to 0.
     */
    public EnergyPricesResponse() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a new <code>EnergyPricesResponse</code> with given list and average price set to 0.
     */
    public EnergyPricesResponse(List<EnergyPrice> value) {
        this.energyPrices = value;
    }

    @JsonProperty("value")
    public void setEnergyPrices(List<EnergyPrice> energyPrices) {
        this.energyPrices = energyPrices;
    }

    public List<EnergyPrice> getEnergyPrices() {
        return energyPrices;
    }
}
