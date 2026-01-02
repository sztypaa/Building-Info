package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public record EnergyPrice(Date timestamp, double price) {
    public EnergyPrice(@JsonProperty("dtime") String timestamp, @JsonProperty("rce_pln") double price)
            throws ParseException {
        this(convertTimestamp(timestamp), price);
    }

    private static Date convertTimestamp(String timestamp)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(timestamp);
    }
}
