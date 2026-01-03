package pl.put.poznan.BuildingInfo.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <code>EnergyPricesFetcher</code> is and utility class that allows fetching energy prices data from external API.
 *
 * @version %I% %D%
 */
public final class EnergyPricesFetcher {
    /**
     * used to perform HTTP request
     */
    private static final RestClient restClient = RestClient.create();
    /**
     * format of date used in external API
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = LoggerFactory.getLogger(EnergyPricesFetcher.class);

    /**
     * Used to prevent instantiation.
     */
    private EnergyPricesFetcher() {}

    /**
     * Fetches energy prices from <a href="https://api.raporty.pse.pl/api/">https://api.raporty.pse.pl/api/</a> whose
     * timestamps fit in specified interval.
     * @param from left side of interval
     * @param to right side of interval
     * @return <code>{@link EnergyPricesResponse}</code> or null if request was not successful
     */
    public static EnergyPricesResponse fetchPricesFromDateTimeInterval(Date from, Date to) {
        long diffInMillis = Math.abs(to.getTime() - from.getTime());
        long diffInMinutes = TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS);

        EnergyPricesResponse response = restClient
                .get()
                .uri("https://api.raporty.pse.pl/api/rce-pln?" +
                        "$select=dtime,rce_pln&" +
                        "$filter=dtime ge '" + dateFormat.format(from) + "T00:00:00' and " +
                        "dtime lt '" + dateFormat.format(to) + "T00:00:00' &" +
                        "$first=" + diffInMinutes / 15)
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
