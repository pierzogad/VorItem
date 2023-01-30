package im.wilk.jsonitem.item.impl;

import com.google.gson.JsonPrimitive;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.model.JsonDataItem;

import java.math.BigDecimal;
import java.util.Optional;

public class JsonDataItemImpl extends JsonItemBaseImpl implements JsonDataItem<JsonItem> {

    private JsonPrimitive jsonPrimitive;
    private final JsonItemImpl root;

    public JsonDataItemImpl(JsonPrimitive jsonPrimitive,
                            JsonItemImpl root) {
        this.jsonPrimitive = jsonPrimitive;
        this.root = root;
    }

    @Override
    public Optional<String> optionalString() {
        return Optional.ofNullable(getS());
    }

    @Override
    public Optional<Boolean> optionalBoolean() {
        if (isNull() || !jsonPrimitive.isBoolean()) {
            return Optional.empty();
        }
        return Optional.of(getB());
    }

    @Override
    public Optional<Long> optionalLong() {
        return Optional.ofNullable(getL());
    }

    @Override
    public Optional<BigDecimal> optionalBigDecimal() {
        return Optional.ofNullable(getBd());
    }

    @Override
    public Optional<Integer> optionalInteger() {
        return Optional.ofNullable(getI());
    }

    @Override
    public Optional<Double> optionalDouble() {
        return Optional.ofNullable(getD());
    }

    @Override
    public Optional<Float> optionalFloat() {
        return Optional.ofNullable(getF());
    }

    @Override
    public Optional<Number> optionalNumber() {
        return Optional.ofNullable(getN());
    }

    @Override
    public String getS() {
        if (isNull()) {
            return null;
        }
        if (jsonPrimitive.isString()) {
            return jsonPrimitive.getAsString();
        }

        return jsonPrimitive.toString();
    }

    @Override
    public Boolean getB() {
        if (isNull() || !jsonPrimitive.isBoolean()) {
            return false;
        }

        return jsonPrimitive.getAsBoolean();
    }

    @Override
    public Long getL() {
        if (isNull() || !jsonPrimitive.isNumber()) {
            return null;
        }

        return jsonPrimitive.getAsLong();
    }

    @Override
    public Integer getI() {
        if (isNull() || !jsonPrimitive.isNumber()) {
            return null;
        }

        return jsonPrimitive.getAsInt();
    }

    @Override
    public Double getD() {
        if (isNull() || !jsonPrimitive.isNumber()) {
            return null;
        }

        return jsonPrimitive.getAsDouble();
    }

    @Override
    public Float getF() {
        if (isNull() || !jsonPrimitive.isNumber()) {
            return null;
        }

        return jsonPrimitive.getAsFloat();
    }

    @Override
    public BigDecimal getBd() {
        if (isNull() || !jsonPrimitive.isNumber()) {
            return null;
        }

        return jsonPrimitive.getAsBigDecimal();
    }

    @Override
    public Number getN() {
        if (isNull() || !jsonPrimitive.isNumber()) {
            return null;
        }
        return jsonPrimitive.getAsNumber();
    }

    @Override
    public void set(String value) {
        jsonPrimitive = new JsonPrimitive(value);
        root.setJsonElement(jsonPrimitive);
        root.propagateChange(null);
    }

    @Override
    public void set(Boolean value) {
        jsonPrimitive = new JsonPrimitive(value);
        root.setJsonElement(jsonPrimitive);
        root.propagateChange(null);
    }

    @Override
    public void set(Number value) {
        jsonPrimitive = new JsonPrimitive(value);
        root.setJsonElement(jsonPrimitive);
        root.propagateChange(null);
    }

    @Override
    public void set(BigDecimal value) {
        jsonPrimitive = new JsonPrimitive(value);
        root.setJsonElement(jsonPrimitive);
        root.propagateChange(null);
    }

    private boolean isNull() {
        return jsonPrimitive == null;
    }

    @Override
    void setValue(JsonItemImpl other) {
        jsonPrimitive = other.asJsonElement().getAsJsonPrimitive();
        root.setJsonElement(jsonPrimitive);
        root.propagateChange(null);
    }

    @Override
    String describe() {
        return "Data: " + root.getFullPath() + " = " + jsonPrimitive;
    }

    @Override
    public String toString() {
        return "JsonDataItemImpl{" +
                describe() +
                '}';
    }
}
