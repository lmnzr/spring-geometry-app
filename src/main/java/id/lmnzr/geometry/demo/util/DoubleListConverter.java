package id.lmnzr.geometry.demo.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Converter
public class DoubleListConverter implements AttributeConverter<List<Double>, String> {
    public String convertToDatabaseColumn(List<Double> list) {
        if(!list.isEmpty()) {
            List<String> stringList = list.stream().map(Object::toString).collect(Collectors.toList());
            return String.join(",", stringList);
        } else{
            return null;
        }
    }

    public List<Double> convertToEntityAttribute(String joined) {
        if(joined != null){
            Pattern pattern = Pattern.compile(",");
            Double[] separated = pattern.splitAsStream(joined).map(Double::parseDouble).toArray(Double[]::new);
            return new ArrayList<>(Arrays.asList(separated));
        } else{
            return null;
        }

    }
}
