package pl.put.poznan.BuildingInfo.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class EnergyPricesFetcher {
    private static final RestClient restClient = RestClient.create();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = LoggerFactory.getLogger(EnergyPricesFetcher.class);

    private EnergyPricesFetcher() {}

    public static EnergyPricesResponse fetchPricesFromDateTimeInterval(Date from, Date to) {
        ParameterizedTypeReference<Map<String, List<Map<String, Double>>>> typeReference = new ParameterizedTypeReference<>() {};

        EnergyPricesResponse response = restClient
                .get()
                .uri("https://api.raporty.pse.pl/api/rce-pln?" +
                        "$select=dtime,rce_pln&" +
                        "$filter=dtime ge '" + dateFormat.format(from) + "T00:00:00' and " +
                        "dtime lt '" + dateFormat.format(to) + "T00:00:00' &" +
                        "$first=2880")
                .retrieve()
                .body(EnergyPricesResponse.class);
        if (response != null) {
            logger.info("Fetch success");
            return response;
        } else {
            logger.debug("Fetch response was null");
            return null;
        }
    }
}
