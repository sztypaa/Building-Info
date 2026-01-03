package pl.put.poznan.BuildingInfo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
     * used to store and access buildings
     */
    @Autowired
    private BuildingInfo buildingInfo;

    /**
     * Fetches energy prices from last full 30 days using @see
     * <a href="https://api.raporty.pse.pl/api/">https://api.raporty.pse.pl/api/</a>.
     * Then calculates average price and updates buildings.
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
            buildingInfo.updateEnergyPrices(energyPricesResponse.getAveragePrice());
            logger.info("New energy price is: {}", energyPricesResponse.getAveragePrice());
        }
    }
}
