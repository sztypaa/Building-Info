package pl.put.poznan.BuildingInfo.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class EnergyPriceFetcher {
    private static final RestClient restClient = RestClient.create();
    private static final Calendar calendar = new GregorianCalendar();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static List<Double> prices = new ArrayList<>();
    private static double averagePrice = 0;
    private static final Logger logger = LoggerFactory.getLogger(EnergyPriceFetcher.class);

    private EnergyPriceFetcher() {}

    private static void updateCalendar() {
        calendar.setTime(new Date());
        logger.info("Updated calendar, current date is: {}", dateFormat.format(calendar.getTime()));
    }

    private static Date getDate1DayAgo() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date date1DayAgo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return date1DayAgo;
    }

    private static Date getDate31DaysAgo() {
        calendar.add(Calendar.DAY_OF_MONTH, -31);
        Date date31DaysAgo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 31);
        return date31DaysAgo;
    }

    private static void calculateAveragePrice() {
        averagePrice = prices.stream().mapToDouble(Double::doubleValue).average().orElse(averagePrice);
    }

    public static double getAveragePrice() {
        return averagePrice;
    }

    public static void fetch() {
        logger.info("Fetching prices from last 30 days");
        updateCalendar();

        ParameterizedTypeReference<Map<String, List<Map<String, Double>>>> typeReference = new ParameterizedTypeReference<>() {};

        Map<String, List<Map<String, Double>>> response = restClient
                .get()
                .uri("https://api.raporty.pse.pl/api/rce-pln?" +
                        "$select=rce_pln&" +
                        "$filter=dtime ge '" + dateFormat.format(getDate31DaysAgo()) + "T00:00:00' and " +
                        "dtime lt '" + dateFormat.format(getDate1DayAgo()) + "T00:00:00' &" +
                        "$first=2880")
                .retrieve()
                .body(typeReference);
        if (response != null) {
            List<Map<String, Double>> listOfJson = response.get("value");
            prices = listOfJson.stream().map(Map::values).flatMap(Collection::stream).toList();
            calculateAveragePrice();
            logger.info("Fetch success");
        } else {
            logger.debug("Fetch response was null");
        }
    }
}
