package api.dto.datahubs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MetaDeserializer extends JsonDeserializer<Meta> {
    private static final Map<String, Class<?>> knownTypes = Map.of(
            "dmon.storage.datahub", DataHub.class,
            "dmon.drvcapmeta.mappingDH", MappingDataHub.class
    );

    @Override
    public Meta deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ObjectNode node = mapper.readTree(p);

        Meta meta = new Meta();

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();

            if (knownTypes.containsKey(key) && value.isArray()) {
                Class<?> clazz = knownTypes.get(key);
                Object list = mapper.readValue(value.traverse(mapper), mapper.getTypeFactory().constructCollectionType(List.class, clazz));
                meta.put(key, list);
            } else {
                Object unknown = mapper.treeToValue(value, Object.class);
                meta.put(key, unknown);
            }
        }
        return meta;
    }
}
