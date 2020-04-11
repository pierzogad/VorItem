package im.wilk.vor.item.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.model.VorStructItem;
import im.wilk.vor.item.model.VorItemConfig;
import im.wilk.vor.item.node.VorPathNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VorStructItemImpl extends VorItemBaseImpl implements VorStructItem<VorItem> {

    private JsonObject jsonObject;
    private final VorItemConfig config;
    private final VorItemImpl root;
    private final Map<String, VorItemImpl> itemMap;

    public VorStructItemImpl(JsonObject jsonObject,
                             VorItemConfig config,
                             VorItemImpl root) {
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
    public Map<String, VorItem> fields() {
        if (isNull()) {
            return Collections.emptyMap();
        }
        Map<String, VorItem> result = new HashMap<>();
        jsonObject.keySet().forEach(key -> result.put(key,
                get(VorPathNode.forName(key))));
        return Collections.unmodifiableMap(result);
    }

    private boolean isNull() {
        return jsonObject == null;
    }

    public VorItemImpl get(VorPathNode node) {
        String name = node.getNodeName();
        itemMap.computeIfAbsent(name, key -> {
            JsonElement element = jsonObject != null ? jsonObject.get(key) : null;
            return new VorItemImpl(element, config, root, node);
        });
        VorItemImpl vorItem = itemMap.get(name);
        vorItem.nodePath.syncAlias(node);
        return vorItem;
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
    void setValue(VorItemImpl other) {
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
        return "VorStructItemImpl{" +
                "Struct=" + (jsonObject == null ? "(empty)" : "(present)") + " " +
                describe() +
                '}';
    }
}
