package im.wilk.vor.item.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.model.VorListItem;
import im.wilk.vor.item.model.VorItemConfig;
import im.wilk.vor.item.node.VorPathNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VorListItemImpl extends VorItemBaseImpl implements VorListItem<VorItem> {

    private JsonArray jsonArray;
    private final VorItemConfig config;
    private final VorItemImpl root;
    private final List<VorItemImpl> itemList;

    public VorListItemImpl(JsonArray jsonArray,
                           VorItemConfig config,
                           VorItemImpl root) {
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
    public List<VorItem> list() {
        List<VorItem> items = new ArrayList<>();
        for (int i = 0; i < listSize(); i++) {
            items.add(get(VorPathNode.forIndex(i)));
        }
        return Collections.unmodifiableList(items);
    }

    public void addToList(VorItem item) {
        setInList(listSize(), item);
    }

    public void setInList(int idx, VorItem item) {

        VorItemImpl vorItem = root.makeCopy(item, VorPathNode.forIndex(idx));
        while (itemList.size() <= idx) {
            itemList.add(null);
        }
        itemList.set(idx, vorItem);

        root.propagateChange(vorItem);
    }

    private boolean exists() {
        return jsonArray != null;
    }

    public VorItemImpl get(VorPathNode node) {
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

        VorItemImpl ionItem = itemList.get(idx);
        if (ionItem == null) {
            JsonElement ionValue = null;
            if (exists() && idx < listSize()) {
                ionValue = jsonArray.get(idx);
            }
            ionItem = new VorItemImpl(ionValue, config, root, node);
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
    void setValue(VorItemImpl other) {
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
                VorItemImpl vorIonItem = itemList.get(idx);
                if (vorIonItem != null) {
                    vorIonItem.getPathNode().setNodeIndex(idx);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "VorListItemImpl{" +
                "List=" + ((jsonArray == null) ? "(empty)" : "(present)") +
                describe() +
                '}';
    }
}
