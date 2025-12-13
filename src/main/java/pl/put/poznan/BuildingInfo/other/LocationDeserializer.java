package pl.put.poznan.BuildingInfo.other;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.put.poznan.BuildingInfo.model.CompoundLocation;
import pl.put.poznan.BuildingInfo.model.Location;
import pl.put.poznan.BuildingInfo.model.Room;

import java.io.IOException;

/**
 * <code>LocationDeserializer</code> is a custom deserializer for <code>{@link Location}</code> class. It ignores id,
 * cube and unknown properties.
 */
public class LocationDeserializer extends StdDeserializer<Location> {
    /**
     * Constructs a new <code>LocationDeserializer</code>.
     */
    public LocationDeserializer() {
        this(null);
    }

    /**
     * Constructs a new <code>LocationDeserializer</code>.
     * @param vc type of values this deserializer handles
     */
    protected LocationDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Deserializes JSON to instance of class inheriting from <code>Location</code> based on JSON data
     * @param parser        used to parse JSON
     * @param context       context for the deserialization process
     * @return              instance of class inheriting from <code>Location</code>
     * @throws IOException  if I/O exception occurred
     */
    @Override
    public Location deserialize(JsonParser parser, DeserializationContext context)
    throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final ObjectMapper mapper = (ObjectMapper)parser.getCodec();
        if(node.has("children")) {
            return mapper.convertValue(node, CompoundLocation.class);
        } else {
            return mapper.convertValue(node, Room.class);
        }
    }
}
