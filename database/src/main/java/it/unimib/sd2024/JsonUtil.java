package it.unimib.sd2024;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {

    // Method to convert JSON array to List of JsonObject
    public static List<JsonObject> getJsonArray(JsonObject jsonObject, String key) {
        List<JsonObject> list = new ArrayList<>();
        if (jsonObject.containsKey(key)) {
            JsonArray jsonArray = jsonObject.getJsonArray(key);
            for (JsonValue jsonValue : jsonArray) {
                list.add(jsonValue.asJsonObject());
            }
        }
        return list;
    }

    // Method to convert List of JsonObject to JSON array
    public static JsonArray toJsonArray(List<JsonObject> list) {
        return Json.createArrayBuilder(list).build();
    }

    // Method to parse JSON string to JsonObject
    public static JsonObject parseJsonObject(String json) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
            return jsonReader.readObject();
        }
    }
}