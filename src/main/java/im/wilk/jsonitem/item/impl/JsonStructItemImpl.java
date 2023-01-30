package im.wilk.jsonitem.item.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.model.JsonStructItem;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import im.wilk.jsonitem.item.node.JsonPathNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonStructItemImpl extends JsonItemBaseImpl implements JsonStructItem<JsonItem> {

    private JsonObject jsonObject;
    private final JsonItemConfig config;
    private final JsonItemImpl root;
    private final Map<String, JsonItemImpl> itemMap;

    public JsonStructItemImpl(JsonObject jsonObject,
                              JsonItemConfig config,
                              JsonItemImpl root) {
        this.jsonObject = jsonObject;
        this.config = config;
        this.root = root;
        itemMap = new HashMap<>();
    }

    @Override
    public boolean isEmptyStruct() {
        return isNull() || jsonObject.size() == 0;
    }

    @Override
    public Map<String, JsonItem> fields() {
        if (isNull()) {
            return Collections.emptyMap();
        }
        Map<String, JsonItem> result = new HashMap<>();
        jsonObject.keySet().forEach(key -> result.put(key,
                get(JsonPathNode.forName(key))));
        return Collections.unmodifiableMap(result);
    }

    private boolean isNull() {
        return jsonObject == null;
    }

    public JsonItemImpl get(JsonPathNode node) {
        String name = node.getNodeName();
        itemMap.computeIfAbsent(name, key -> {
            JsonElement element = jsonObject != null ? jsonObject.get(key) : null;
            return new JsonItemImpl(element, config, root, node);
        });
        JsonItemImpl jsonItem = itemMap.get(name);
        jsonItem.nodePath.syncAlias(node);
        return jsonItem;
    }

    public boolean updateStruct(String name, JsonElement jsonElement) {
        if (isNull()) {
            jsonObject = new JsonObject();
            root.setJsonElement(jsonObject);
        }
        boolean modified = !jsonObject.has(name);
        jsonObject.add(name, jsonElement);

        return modified;
    }

    @Override
    void setValue(JsonItemImpl other) {
        jsonObject = other.asJsonElement().getAsJsonObject();
        itemMap.clear();
        root.setJsonElement(jsonObject);
        root.propagateChange(null);
    }

    public void remove(String name) {
        itemMap.remove(name);
        jsonObject.remove(name);
    }

    @Override
    String describe() {
        return "Struct: " + root.getFullPath();
    }

    @Override
    public String toString() {
        return "JsonStructItemImpl{" +
                "Struct=" + (jsonObject == null ? "(empty)" : "(present)") + " " +
                describe() +
                '}';
    }
}
