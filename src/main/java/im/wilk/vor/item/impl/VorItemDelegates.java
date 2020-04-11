package im.wilk.vor.item.impl;

import com.google.gson.*;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.model.VorItemConfig;
import im.wilk.vor.item.node.VorPathNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static im.wilk.vor.item.VorItem.ItemType.ANY;
import static im.wilk.vor.item.VorItem.ItemType.DATA;
import static im.wilk.vor.item.VorItem.ItemType.LIST;
import static im.wilk.vor.item.VorItem.ItemType.STRUCT;

public abstract class VorItemDelegates implements VorItem {

    private JsonElement jsonElement;
    protected final VorItemConfig config;
    protected final VorPathNode nodePath;
    protected final VorItemImpl root;


    private VorListItemImpl listDelegate;
    private VorDataItemImpl dataDelegate;
    private VorStructItemImpl structDelegate;

    public VorItemDelegates(JsonElement inputNode,
                            VorItemConfig config,
                            VorItemImpl root,
                            VorPathNode nodePath) {
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

    protected VorListItemImpl getListDelegate() {
        if (listDelegate == null) {
            JsonArray jsonArray = (jsonElement.isJsonArray()) ? jsonElement.getAsJsonArray() : null;
            listDelegate = new VorListItemImpl(jsonArray, config, getThis());
        }
        validateAccess(LIST);
        return listDelegate;
    }

    protected VorDataItemImpl getDataDelegate() {
        if (dataDelegate == null) {
            JsonPrimitive jsonPrimitive = (jsonElement.isJsonPrimitive()) ? jsonElement.getAsJsonPrimitive() : null;
            dataDelegate = new VorDataItemImpl(jsonPrimitive, getThis());
        }
        validateAccess(DATA);
        return dataDelegate;
    }

    protected VorStructItemImpl getStructDelegate() {
        if (structDelegate == null) {
            JsonObject jsonObject = (jsonElement.isJsonObject()) ? jsonElement.getAsJsonObject() : null;
            structDelegate = new VorStructItemImpl(jsonObject, config, getThis());
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

    protected abstract VorItemImpl getThis();

    @Override
    public boolean isEmptyStruct() {
        return getStructDelegate().isEmptyStruct();
    }

    @Override
    public Map<String, VorItem> fields() {
        return getStructDelegate().fields();
    }

    @Override
    public int listSize() {
        return getListDelegate().listSize();
    }

    @Override
    public List<VorItem> list() {
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
