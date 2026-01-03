package pl.put.poznan.BuildingInfo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.BuildingInfo.rest", "pl.put.poznan.BuildingInfo.logic"})
@EnableScheduling
public class BuildingInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuildingInfoApplication.class, args);
    }
}
