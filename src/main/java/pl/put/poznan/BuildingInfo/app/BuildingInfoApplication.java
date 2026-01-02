package pl.put.poznan.BuildingInfo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.put.poznan.BuildingInfo.logic.BuildingInfo;
import pl.put.poznan.BuildingInfo.other.EnergyPricesFetcher;
import pl.put.poznan.BuildingInfo.other.EnergyPricesResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.BuildingInfo.rest", "pl.put.poznan.BuildingInfo.logic"})
@EnableScheduling
public class BuildingInfoApplication {
    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoApplication.class);

    @Autowired
    private BuildingInfo buildingInfo;

    public static void main(String[] args) {
        SpringApplication.run(BuildingInfoApplication.class, args);
    }

    //TODO repeat for each day, not every 5 seconds
    @Scheduled(cron = "0/5 * * * * ?", zone = "Europe/Warsaw")
    public void fetchEnergyPrices() {
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
