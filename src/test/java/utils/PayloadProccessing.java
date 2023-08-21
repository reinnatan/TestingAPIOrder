package utils;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

public interface PayloadProccessing {
    public JsonObject getJsonObjectFromFile(String jsonFileName);
    public JsonObject replacePropertyValue(JsonObject jsonObject, String property, Object value);
    public JsonObject replaceMultiplePropertyValue(JsonObject jsonObject, HashMap<String, Object> newValue);
    public JsonObject removeMultipleKey(JsonObject jsonObject, List<String> keys);
    public Object parsingJSONObject(String json, Object data);
}
