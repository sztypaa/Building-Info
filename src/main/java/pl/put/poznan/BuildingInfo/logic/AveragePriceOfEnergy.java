package pl.put.poznan.BuildingInfo.logic;

import org.springframework.stereotype.Component;

/**
 * <code>AveragePriceOfEnergy</code> is a wrapper class that stores average price of energy.
 *
 * @version %I% %D%
 */
@Component
public class AveragePriceOfEnergy {
    /**
     * wrapped value
     */
    private double energyPrice;

    public void set(double averagePrice) {
        this.energyPrice = averagePrice;
    }

    public double get() {
        return energyPrice;
    }
}
