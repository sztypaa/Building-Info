package pl.put.poznan.BuildingInfo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.put.poznan.BuildingInfo.other.EnergyPricesFetcher;
import pl.put.poznan.BuildingInfo.other.EnergyPricesResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.BuildingInfo.rest"})
@EnableScheduling
public class BuildingInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildingInfoApplication.class, args);
    }

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
    }
}
