package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayloadUtil implements PayloadProccessing{

    public  JsonObject getJsonObjectFromFile(String jsonFileName) {
        String result = "unable to read data from json file";
        try {
            StringBuffer paths = new StringBuffer();
            paths.append(System.getProperty("user.dir"));
            paths.append(File.separator);
            paths.append("data");
            paths.append(File.separator);

            paths.append(jsonFileName);
            byte[] content = Files.readAllBytes(Paths.get(paths.toString()));
            result = new String(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonParser.parseString(result).getAsJsonObject();
    }

    public JsonObject replacePropertyValue(JsonObject jsonObject, String property, Object value) {
        jsonObject.remove(property);
        if (value instanceof Number) {
            jsonObject.addProperty(property, (Number) value);
        } else if (value instanceof String) {
            jsonObject.addProperty(property, (String) value);
        } else if (value instanceof Boolean) {
            jsonObject.addProperty(property, (Boolean) value);
        } else if (value instanceof Character) {
            jsonObject.addProperty(property, (Character) value);
        }
        return jsonObject;
    }

    public JsonObject replaceMultiplePropertyValue(JsonObject jsonObject, HashMap<String, Object> newValue){
        for(Map.Entry<String, Object> set: newValue.entrySet()){
            if(set.getValue() instanceof Number){
                jsonObject.remove(set.getKey());
                jsonObject.addProperty(set.getKey(), (Number) set.getValue());
            }else if(set.getValue() instanceof String){
                jsonObject.remove(set.getKey());
                jsonObject.addProperty(set.getKey(), (String) set.getValue());
            }else if(set.getValue() instanceof Boolean){
                jsonObject.remove(set.getKey());
                jsonObject.addProperty(set.getKey(), (Boolean) set.getValue());
            }else if(set.getValue() instanceof Character) {
                jsonObject.remove(set.getKey());
                jsonObject.addProperty(set.getKey(), (Character) set.getValue());
            }
        }
        return jsonObject;
    }

    public JsonObject removeMultipleKey(JsonObject jsonObject,List<String> keys){
        for(String key:keys){
            jsonObject.remove(key);
        }
        return jsonObject;
    }

    public Object parsingJSONObject(String json, Object data){
        Gson gson = new Gson();
        Object classParser = gson.fromJson(json, Object.class);

        return classParser;
    }


}
