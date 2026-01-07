package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>EnergyPrice</code> is used to store information received in <code>{@link EnergyPricesResponse}</code>
 * @param timestamp date and time of price generation
 * @param price RCE in PLN
 *
 * @version %I% %D%
 */
public record EnergyPrice(Date timestamp, double price) {
    /**
     * Constructs a new <code>EnergyPrice</code> with timestamp converted from string and price.
     */
    public EnergyPrice(@JsonProperty("dtime") String timestamp, @JsonProperty("rce_pln") double price)
            throws ParseException {
        this(convertTimestamp(timestamp), price);
    }

    /**
     * Converts <code>timestamp</code> from received format to <code>{@link Date}</code>
     * @param timestamp  date and time of price generation
     * @return converted timestamp
     * @throws ParseException If timestamp format is different from <code>yyyy-MM-dd HH:mm:ss</code>
     */
    private static Date convertTimestamp(String timestamp)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(timestamp);
    }
}
