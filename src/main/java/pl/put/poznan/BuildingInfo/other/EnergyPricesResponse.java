package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>EnergyPricesResponse</code> is an wrapper class for <code>{@link EnergyPricesFetcher}</code> response.
 *
 * @version %I% %D%
 */
public class EnergyPricesResponse {
    /**
     * used to store date received from external API
     */
    private List<EnergyPrice> energyPrices;
    /**
     * used to store average price of energy; calculated using <code>{@link #calculateAveragePrice()}</code> when new list of energy prices is set
     */
    private double averagePrice;

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
        averagePrice = 0;
        calculateAveragePrice();
    }

    @JsonProperty("value")
    public void setEnergyPrices(List<EnergyPrice> energyPrices) {
        this.energyPrices = energyPrices;
        calculateAveragePrice();
    }

    public List<EnergyPrice> getEnergyPrices() {
        return energyPrices;
    }

    /**
     * Calculates and updates average price of energy. If price is null leaves old value.
     */
    private void calculateAveragePrice() {
        averagePrice = energyPrices.stream().mapToDouble(EnergyPrice::price).average().orElse(averagePrice);
    }

    public double getAveragePrice() {
        return averagePrice;
    }
}
