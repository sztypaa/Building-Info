package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EnergyPricesResponse {
    private List<EnergyPrice> energyPrices;
    private double averagePrice;

    public EnergyPricesResponse() {
        this(new ArrayList<>());
    }

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

    private void calculateAveragePrice() {
        averagePrice = energyPrices.stream().mapToDouble(EnergyPrice::price).average().orElse(averagePrice);
    }

    public double getAveragePrice() {
        return averagePrice;
    }
}
