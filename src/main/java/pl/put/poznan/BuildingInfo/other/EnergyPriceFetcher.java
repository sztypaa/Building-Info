package pl.put.poznan.BuildingInfo.other;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class EnergyPriceFetcher {
    private static RestClient restClient;
    private static Calendar calendar;
    private static DateFormat dateFormat;

    private EnergyPriceFetcher() {
        restClient = RestClient.create();
        calendar = new GregorianCalendar();
        String pattern = "yyyy-MM-dd";
        dateFormat = new SimpleDateFormat(pattern);
    }

    private static void updateCalendar() {
        calendar.setTime(new Date());
    }

    private static Date getDate1DayAgo() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    private static Date getDate30DaysAgo() {
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        return calendar.getTime();
    }

    public static List<Float> fetch() {
        ParameterizedTypeReference<Map<String, List<Map<String, Float>>>> typeReference = new ParameterizedTypeReference<>() {};

        Map<String, List<Map<String, Float>>> response = restClient
                .get()
                .uri("https://api.raporty.pse.pl/api/rce-pln?" +
                        "$select=rce_pln&" +
                        "$filter=dtime gt '" + dateFormat.format(getDate30DaysAgo()) + "T00:00:00' and " +
                        "dtime lt '" + dateFormat.format(getDate1DayAgo()) + "T00:00:00' &" +
                        "$first=2880")
                .retrieve()
                .body(typeReference);
        if (response == null) {
            return null;
        }
        List<Map<String, Float>> listOfJson = response.get("value");
        return listOfJson.stream().map(Map::values).flatMap(Collection::stream).toList();
    }
}
