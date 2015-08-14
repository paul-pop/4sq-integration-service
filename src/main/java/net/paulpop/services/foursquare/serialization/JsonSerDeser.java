package net.paulpop.services.foursquare.serialization;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * Utility class used for serialization and deserialization via Gson.
 * Uses generics so we can make this reusable and avoid duplicating code.
 *
 * Created by popp on 28/07/15.
 */
@Component
public class JsonSerDeser<T> {

    // We just need this one instance of GSON for everything
    private final Gson gson = new Gson();

    /**
     * Serialize a given object to a JSON string.
     *
     * @param source
     * @return
     */
    public String serialize(T source) {
        return gson.toJson(source);
    }

    /**
     * Deserialize to a specific object based on the given JSON string.
     *
     * @param source
     * @param clz
     * @return
     */
    public T deserialize(String source, Class<T> clz) {
        return gson.fromJson(source, clz);
    }

}
