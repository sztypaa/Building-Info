package pl.put.poznan.BuildingInfo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.put.poznan.BuildingInfo.other.EnergyPriceFetcher;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.BuildingInfo.rest"})
@EnableScheduling
public class BuildingInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildingInfoApplication.class, args);
    }

    @Scheduled(cron = "0/5 * * * * ?", zone = "Europe/Warsaw")
    public static void fetchEnergyPrices() {
        EnergyPriceFetcher.fetch();
    }
}
