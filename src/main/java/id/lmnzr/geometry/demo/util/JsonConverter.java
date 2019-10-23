package id.lmnzr.geometry.demo.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonConverter {
    public Map stringToMap(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Map.class);
    }
}
