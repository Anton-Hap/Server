package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public abstract class JSONUtils {

    public static void fromObjectToFile(String path, Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(path), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fromObjectToJSON(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        String json = "";

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object fromFileToObject(String path, Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(path), object.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object fromJSONToObject(String json, Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, object.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
