package id.lmnzr.geometry.demo.util;

import javax.persistence.AttributeConverter;
import java.math.BigDecimal;

public class DecimalDoubleConverter implements AttributeConverter<Double, BigDecimal> {
    public BigDecimal convertToDatabaseColumn(Double number) {
        if(number!=null){
            return BigDecimal.valueOf(number);
        } else{
            return null;
        }
    }

    public Double convertToEntityAttribute(BigDecimal decimal) {
        if(decimal!=null){
            return decimal.doubleValue();
        } else{
            return null;
        }
    }
}
