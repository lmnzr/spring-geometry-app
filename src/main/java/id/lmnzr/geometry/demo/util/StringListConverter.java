package id.lmnzr.geometry.demo.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (!list.isEmpty()) {
            return String.join(",", list);
        } else {
            return null;
        }

    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if (joined != null) {
            return new ArrayList<>(Arrays.asList(joined.split(",")));
        } else {
            return new ArrayList<>();
        }
    }

}