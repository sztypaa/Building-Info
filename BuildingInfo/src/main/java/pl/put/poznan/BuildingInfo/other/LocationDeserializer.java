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

public class LocationDeserializer extends StdDeserializer<Location> {
    public LocationDeserializer() {
        this(null);
    }

    protected LocationDeserializer(Class<?> vc) {
        super(vc);
    }

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
