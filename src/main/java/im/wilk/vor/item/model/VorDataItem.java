package im.wilk.vor.item.model;

import java.math.BigDecimal;
import java.util.Optional;

public interface VorDataItem<T extends VorDataItem<T>> extends VorItemBase<T> {
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
