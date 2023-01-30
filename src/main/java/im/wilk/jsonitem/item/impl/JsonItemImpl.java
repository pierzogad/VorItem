package im.wilk.jsonitem.item.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.factory.JsonItemFactory;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import im.wilk.jsonitem.item.node.JsonPathNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonItemImpl extends JsonItemDelegates {

    public JsonItemImpl(JsonElement node, JsonItemConfig config, JsonItemImpl root, JsonPathNode nodePath) {
        super(node, config, root, nodePath);
    }

    @Override
    public JsonItem get(int idx) {
        return fromPathNode(JsonPathNode.forIndex(idx));
    }

    @Override
    public JsonItemImpl get(String path) {
        return get(config.getPathParser().toNodes(path));
    }

    @Override
    public JsonItemImpl get(List<JsonPathNode> nodes) {
        JsonItemImpl ret = this;
        for (JsonPathNode node : nodes) {
            ret = ret.fromPathNode(node);
        }
        return ret;
    }

    @Override
    public void set(String name, JsonItem other) {
        JsonItemImpl jsonItem = get(name);
        JsonItemBaseImpl itemBase = jsonItem.selectTypeToSet(other.getItemType());
        itemBase.setValue((JsonItemImpl) other);
    }

    @Override
    public String getFullPath() {
        LinkedList<JsonPathNode> nodes = new LinkedList<>();
        JsonItem jsonItem = this;

        while (jsonItem != null) {
            nodes.push(jsonItem.getPathNode());
            jsonItem = jsonItem.getContainer();
        }

        return config.getPathParser().buildPath(nodes);
    }

    @Override
    public void setNull() {
        setJsonElement(JsonNull.INSTANCE);
        resetDelegates();
        if (root != null) {
            root.propagateChange(this);
        }
    }

    @Override
    public void remove() {
        if (root != null) {
            root.remove(nodePath);
        } else {
            setNull();
        }
    }

    private void remove(JsonPathNode nodePath) {
        if (nodePath.getNodeName() != null) {
            getStructDelegate().remove(nodePath.getNodeName());
        } else {
            getListDelegate().remove(nodePath.getNodeIndex());
        }
    }

    private JsonItemBaseImpl selectTypeToSet(ItemType otherType) {

        switch (otherType) {
            case LIST:
                return getListDelegate();

            case STRUCT:
                return getStructDelegate();

            default:
                return getDataDelegate();
        }
    }

    @Override
    public void set(int idx, JsonItem other) {
        getListDelegate().setInList(idx, other);
    }

    @Override
    public void add(JsonItem other) {
        getListDelegate().addToList(other);
    }

    @Override
    public void set(JsonItem other) {
        set(other.name(), other);
    }

    @Override
    public void set(String path, String value) {
        JsonItemImpl jsonItem = get(path);
        jsonItem.getDataDelegate().set(value);
    }

    @Override
    public void set(String path, Boolean value) {
        JsonItemImpl jsonItem = get(path);
        jsonItem.set(value);
    }

    @Override
    public void set(String path, Number value) {
        JsonItemImpl jsonItem = get(path);
        jsonItem.set(value);
    }

    @Override
    public void set(String path, BigDecimal value) {
        JsonItemImpl jsonItem = get(path);
        jsonItem.set(value);
    }

    public JsonItemImpl fromPathNode(JsonPathNode node) {
        if (node.getNodeName() != null) {
            return getStruct(node);
        }

        return getList(node);
    }

    @Override
    public List<JsonItem> select(String... paths) {

        List<JsonItem> items = new ArrayList<>();
        for (String path : paths) {
            items.add(get(path));
        }

        return items;
    }

    protected JsonItemImpl getStruct(JsonPathNode node) {
        return getStructDelegate().get(node);
    }

    protected JsonItemImpl getList(JsonPathNode node) {
        return getListDelegate().get(node);
    }


    @Override
    public JsonPathNode getPathNode() {
        return nodePath;
    }

    @Override
    public JsonItem getContainer() {
        return root;
    }

    @Override
    public String name() {
        if (nodePath.getNodeAlias() != null) {
            return nodePath.getNodeAlias();
        }
        if (nodePath.getNodeName() != null) {
            return nodePath.getNodeName();
        }
        return root.name();
    }

    @Override
    public JsonElement asJsonElement() {
        return getJsonElement().deepCopy();
    }

    @Override
    public boolean exists() {
        return !getJsonElement().isJsonNull();
    }

    protected JsonItemImpl getThis() {
        return this;
    }

    public void propagateChange(JsonItemImpl modified) {
        boolean needPropagate = true;
        if (modified != null) {
            if (modified.getPathNode().getNodeName() != null) {
                needPropagate = getStructDelegate()
                        .updateStruct(modified.getPathNode().getNodeName(), modified.getJsonElement());
            } else if (modified.getPathNode().getNodeIndex() != null) {
                needPropagate = getListDelegate()
                        .updateList(modified.getPathNode().getNodeIndex(), modified.getJsonElement());
            }
        }
        if (needPropagate && root != null) {
            root.propagateChange(this);
        }
    }

    public JsonItemImpl makeCopy(JsonItem toCopy, JsonPathNode pathNode) {
        return new JsonItemImpl(toCopy.asJsonElement(), config, this, pathNode);
    }

    @Override
    public String toString() {
        return "JsonItemImpl{" +
                "node=" + (getJsonElement().isJsonNull() ? "(empty)" : "(" + getType(getJsonElement())+ ")") +
                ", config=" + config +
                ", nodePath=" + nodePath +
                ", " + super.toString() +
                '}';
    }
}
