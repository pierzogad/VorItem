package im.wilk.jsonitem.item.impl;

import com.google.gson.*;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import im.wilk.jsonitem.item.node.JsonPathNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static im.wilk.jsonitem.item.JsonItem.ItemType.ANY;
import static im.wilk.jsonitem.item.JsonItem.ItemType.DATA;
import static im.wilk.jsonitem.item.JsonItem.ItemType.LIST;
import static im.wilk.jsonitem.item.JsonItem.ItemType.STRUCT;

public abstract class JsonItemDelegates extends JsonItem {

    private JsonElement jsonElement;
    protected final JsonItemConfig config;
    protected final JsonPathNode nodePath;
    protected final JsonItemImpl root;


    private JsonListItemImpl listDelegate;
    private JsonDataItemImpl dataDelegate;
    private JsonStructItemImpl structDelegate;

    public JsonItemDelegates(JsonElement inputNode,
                             JsonItemConfig config,
                             JsonItemImpl root,
                             JsonPathNode nodePath) {
        this.nodePath = nodePath;
        this.jsonElement = (inputNode != null) ? inputNode : JsonNull.INSTANCE;
        this.config = config;
        this.root = root;
    }

    protected ItemType getType(JsonElement element) {
        if (element.isJsonArray()) {
            return LIST;
        } else if (element.isJsonObject()) {
            return STRUCT;
        } else if (element.isJsonPrimitive()) {
            return DATA;
        }
        return ANY;
    }

    @Override
    public ItemType getItemType() {
        return getType(jsonElement);
    }

    protected JsonListItemImpl getListDelegate() {
        if (listDelegate == null) {
            JsonArray jsonArray = (jsonElement.isJsonArray()) ? jsonElement.getAsJsonArray() : null;
            listDelegate = new JsonListItemImpl(jsonArray, config, getThis());
        }
        validateAccess(LIST);
        return listDelegate;
    }

    protected JsonDataItemImpl getDataDelegate() {
        if (dataDelegate == null) {
            JsonPrimitive jsonPrimitive = (jsonElement.isJsonPrimitive()) ? jsonElement.getAsJsonPrimitive() : null;
            dataDelegate = new JsonDataItemImpl(jsonPrimitive, getThis());
        }
        validateAccess(DATA);
        return dataDelegate;
    }

    protected JsonStructItemImpl getStructDelegate() {
        if (structDelegate == null) {
            JsonObject jsonObject = (jsonElement.isJsonObject()) ? jsonElement.getAsJsonObject() : null;
            structDelegate = new JsonStructItemImpl(jsonObject, config, getThis());
        }
        validateAccess(STRUCT);
        return structDelegate;
    }

    protected void validateAccess(ItemType expectedType) {
        if (!config.isLenientStructure()) {
            if (!jsonElement.isJsonNull()) {
                ItemType itemType = getItemType();
                if (expectedType != null && !expectedType.equals(itemType)) {
                    String error = String.format("%s has type %s and cannot be treated as node of type %s",
                            getFullPath(), itemType, expectedType.toString());
                    throw new IllegalStateException(error);
                }
            }
        }
    }

    protected void resetDelegates() {
        dataDelegate = null;
        structDelegate = null;
        listDelegate = null;
    }

    protected abstract JsonItemImpl getThis();

    @Override
    public boolean isEmptyStruct() {
        return getStructDelegate().isEmptyStruct();
    }

    @Override
    public Map<String, JsonItem> fields() {
        return getStructDelegate().fields();
    }

    @Override
    public int listSize() {
        return getListDelegate().listSize();
    }

    @Override
    public List<JsonItem> list() {
        return getListDelegate().list();
    }

    @Override
    public boolean isEmptyList() {
        return getListDelegate().isEmptyList();
    }

    @Override
    public Optional<String> optionalString() {
        return getDataDelegate().optionalString();
    }

    @Override
    public Optional<Boolean> optionalBoolean() {
        return getDataDelegate().optionalBoolean();
    }

    @Override
    public Optional<Long> optionalLong() {
        return getDataDelegate().optionalLong();
    }

    @Override
    public String getS() {
        return getDataDelegate().getS();
    }

    @Override
    public Boolean getB() {
        return getDataDelegate().getB();
    }

    @Override
    public Number getN() {
        return getDataDelegate().getN();
    }

    @Override
    public Integer getI() {
        return getDataDelegate().getI();
    }

    @Override
    public Double getD() {
        return getDataDelegate().getD();
    }

    @Override
    public Float getF() {
        return getDataDelegate().getF();
    }

    @Override
    public Long getL() {
        return getDataDelegate().getL();
    }

    @Override
    public void set(String value) {
        getDataDelegate().set(value);
    }

    @Override
    public void set(Boolean value) {
        getDataDelegate().set(value);
    }

    @Override
    public Optional<BigDecimal> optionalBigDecimal() {
        return getDataDelegate().optionalBigDecimal();
    }

    @Override
    public Optional<Number> optionalNumber() {
        return getDataDelegate().optionalNumber();
    }

    @Override
    public Optional<Integer> optionalInteger() {
        return getDataDelegate().optionalInteger();
    }

    @Override
    public Optional<Double> optionalDouble() {
        return getDataDelegate().optionalDouble();
    }

    @Override
    public Optional<Float> optionalFloat() {
        return getDataDelegate().optionalFloat();
    }

    @Override
    public BigDecimal getBd() {
        return getDataDelegate().getBd();
    }

    @Override
    public void set(Number value) {
        getDataDelegate().set(value);
    }

    @Override
    public void set(BigDecimal value) {
        getDataDelegate().set(value);
    }

    @Override
    public void remove(int index) {
        getListDelegate().remove(index);
    }

    protected JsonElement getJsonElement() {
        return jsonElement;
    }

    public void setJsonElement(JsonElement newNode) {
        jsonElement = newNode;
    }

    @Override
    public String toString() {
        return "Delegates{" +
                "listDelegate=" + listDelegate +
                ", dataDelegate=" + dataDelegate +
                ", structDelegate=" + structDelegate +
                '}';
    }
}
