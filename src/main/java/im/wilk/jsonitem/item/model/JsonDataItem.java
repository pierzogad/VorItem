package im.wilk.jsonitem.item.model;

import java.math.BigDecimal;
import java.util.Optional;

public interface JsonDataItem<T extends JsonDataItem<T>> extends JsonItemBase<T> {
    Optional<String> optionalString();
    Optional<Boolean> optionalBoolean();
    Optional<Long> optionalLong();
    Optional<BigDecimal> optionalBigDecimal();
    Optional<Integer> optionalInteger();
    Optional<Double> optionalDouble();
    Optional<Float> optionalFloat();
    Optional<Number> optionalNumber();


    String getS();
    Boolean getB();
    Number getN();
    Long getL();
    Integer getI();
    Double getD();
    Float getF();
    BigDecimal getBd();

    void set(String value);
    void set(Boolean value);
    void set(Number value);
    void set(BigDecimal value);
}
