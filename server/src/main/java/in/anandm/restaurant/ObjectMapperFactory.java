package in.anandm.restaurant;

import org.bson.types.ObjectId;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class ObjectMapperFactory {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule("restaurants-api", new Version(
                1, 0, 0, ""));
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    public static final ObjectMapper getObjectMapper() {

        return OBJECT_MAPPER;
    }
}
