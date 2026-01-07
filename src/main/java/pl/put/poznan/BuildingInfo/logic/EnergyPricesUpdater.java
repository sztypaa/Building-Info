package pl.put.poznan.BuildingInfo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.put.poznan.BuildingInfo.other.EnergyPrice;
import pl.put.poznan.BuildingInfo.other.EnergyPricesFetcher;
import pl.put.poznan.BuildingInfo.other.EnergyPricesResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <code>EnergyPricesUpdater</code> is a helper class that periodically updates energy prices in all buildings.
 *
 * @version %I% %D%
 */
@Component
public class EnergyPricesUpdater {
    private static final Logger logger = LoggerFactory.getLogger(EnergyPricesUpdater.class);

    /**
     * used to store average price of energy
     */
    private final AveragePriceOfEnergy energyPrice;

    /**
     * Constructs a new <code>EnergyPricesUpdater</code> with injected <code>{@link AveragePriceOfEnergy}</code>
     * @param averagePrice price to update
     */
    public EnergyPricesUpdater(AveragePriceOfEnergy averagePrice) {
        this.energyPrice = averagePrice;
    }

    /**
     * Fetches energy prices from last full 30 days using @see
     * <a href="https://api.raporty.pse.pl/api/">https://api.raporty.pse.pl/api/</a>.
     * Then calculates average price and updates buildings. If response was null or had no energy prices then leaves old
     * price.
     */
    //TODO repeat for each day, not every 5 seconds
    @Scheduled(cron = "0/5 * * * * ?", zone = "Europe/Warsaw")
    public void updateEnergyPrices() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date date1DayAgo = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date date31DaysAgo = calendar.getTime();

        EnergyPricesResponse energyPricesResponse = EnergyPricesFetcher
                .fetchPricesFromDateTimeInterval(date31DaysAgo, date1DayAgo);
        if(energyPricesResponse != null) {
            energyPrice.set(energyPricesResponse.getEnergyPrices().stream()
                    .mapToDouble(EnergyPrice::price)
                    .average()
                    .orElse(energyPrice.get()));
            logger.info("New energy price is: {}", energyPrice.get());
        }
    }
}
