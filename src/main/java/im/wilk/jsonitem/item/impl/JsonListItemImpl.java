package im.wilk.jsonitem.item.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import im.wilk.jsonitem.item.model.JsonListItem;
import im.wilk.jsonitem.item.node.JsonPathNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonListItemImpl extends JsonItemBaseImpl implements JsonListItem<JsonItem> {

    private JsonArray jsonArray;
    private final JsonItemConfig config;
    private final JsonItemImpl root;
    private final List<JsonItemImpl> itemList;

    public JsonListItemImpl(JsonArray jsonArray,
                            JsonItemConfig config,
                            JsonItemImpl root) {
        this.jsonArray = jsonArray;
        this.config = config;
        this.root = root;
        itemList = new ArrayList<>(listSize());
    }

    @Override
    public boolean isEmptyList() {
        return !exists() || jsonArray.size() == 0;
    }

    @Override
    public int listSize() {
        return exists() ? jsonArray.size() : 0;
    }

    @Override
    public List<JsonItem> list() {
        List<JsonItem> items = new ArrayList<>();
        for (int i = 0; i < listSize(); i++) {
            items.add(get(JsonPathNode.forIndex(i)));
        }
        return Collections.unmodifiableList(items);
    }

    public void addToList(JsonItem item) {
        setInList(listSize(), item);
    }

    public void setInList(int idx, JsonItem item) {

        JsonItemImpl jsonItem = root.makeCopy(item, JsonPathNode.forIndex(idx));
        while (itemList.size() <= idx) {
            itemList.add(null);
        }
        itemList.set(idx, jsonItem);

        root.propagateChange(jsonItem);
    }

    private boolean exists() {
        return jsonArray != null;
    }

    public JsonItemImpl get(JsonPathNode node) {
        int idx = node.getNodeIndex();

        if (idx >= itemList.size()) {
            if (!config.isLenientLists()) {
                if ((!exists() && idx > 0) || (jsonArray != null && idx > jsonArray.size())) {
                    throw new IndexOutOfBoundsException("Attempt to get item with index " + idx
                            + " from " + describe());
                }
            }
            while (idx >= itemList.size()) {
                itemList.add(null);
            }
        }

        JsonItemImpl ionItem = itemList.get(idx);
        if (ionItem == null) {
            JsonElement ionValue = null;
            if (exists() && idx < listSize()) {
                ionValue = jsonArray.get(idx);
            }
            ionItem = new JsonItemImpl(ionValue, config, root, node);
            itemList.set(idx, ionItem);
        }
        ionItem.nodePath.syncAlias(node);
        return ionItem;
    }

    public boolean updateList(Integer index, JsonElement ionValue) {
        boolean updated = false;
        if (jsonArray == null) {
            jsonArray = new JsonArray();
            root.setJsonElement(jsonArray);
            updated = true;
        }
        if (!config.isLenientLists()) {
            if (index > jsonArray.size()) {
                throw new IndexOutOfBoundsException("Attempt to add item with index " + index + " to " + describe());
            }
        }
        while (jsonArray.size() <= index) {
            jsonArray.add(JsonNull.INSTANCE);
        }
        jsonArray.set(index, ionValue);
        return updated;
    }

    @Override
    void setValue(JsonItemImpl other) {
        this.jsonArray = other.asJsonElement().getAsJsonArray();
        itemList.clear();
        root.setJsonElement(jsonArray);
        root.propagateChange(null);
    }

    @Override
    String describe() {
        return "List: " + root.getFullPath() + " (" + listSize() + " items)";
    }

    @Override
    public void remove(int index) {
        if (!exists()) {
            return;
        }
        if (index < jsonArray.size()) {
            jsonArray.remove(index);
        }
        if (index < itemList.size()) {
            itemList.remove(index);
            for (int idx = index; idx < itemList.size(); idx++) {
                JsonItemImpl jsonItem = itemList.get(idx);
                if (jsonItem != null) {
                    jsonItem.getPathNode().setNodeIndex(idx);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "JsonListItemImpl{" +
                "List=" + ((jsonArray == null) ? "(empty)" : "(present)") +
                describe() +
                '}';
    }
}
