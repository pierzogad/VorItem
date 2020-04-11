package im.wilk.vor.item.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.model.VorItemConfig;
import im.wilk.vor.item.node.VorPathNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class VorItemImpl extends VorItemDelegates implements VorItem {

    public VorItemImpl(JsonElement node, VorItemConfig config, VorItemImpl root, VorPathNode nodePath) {
        super(node, config, root, nodePath);
    }

    @Override
    public VorItem get(int idx) {
        return fromPathNode(VorPathNode.forIndex(idx));
    }

    @Override
    public VorItemImpl get(String path) {
        return get(config.getPathParser().toNodes(path));
    }

    @Override
    public VorItemImpl get(List<VorPathNode> nodes) {
        VorItemImpl ret = this;
        for (VorPathNode node : nodes) {
            ret = ret.fromPathNode(node);
        }
        return ret;
    }

    @Override
    public void set(String name, VorItem other) {
        VorItemImpl vorItem = get(name);
        VorItemBaseImpl itemBase = vorItem.selectTypeToSet(other.getItemType());
        itemBase.setValue((VorItemImpl) other);
    }

    @Override
    public String getFullPath() {
        LinkedList<VorPathNode> nodes = new LinkedList<>();
        VorItem vorItem = this;

        while (vorItem != null) {
            nodes.push(vorItem.getPathNode());
            vorItem = vorItem.getContainer();
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

    private void remove(VorPathNode nodePath) {
        if (nodePath.getNodeName() != null) {
            getStructDelegate().remove(nodePath.getNodeName());
        } else {
            getListDelegate().remove(nodePath.getNodeIndex());
        }
    }

    private VorItemBaseImpl selectTypeToSet(ItemType otherType) {

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
    public void set(int idx, VorItem other) {
        getListDelegate().setInList(idx, other);
    }

    @Override
    public void add(VorItem other) {
        getListDelegate().addToList(other);
    }

    @Override
    public void set(VorItem other) {
        set(other.name(), other);
    }

    @Override
    public void set(String path, String value) {
        VorItemImpl vorItem = get(path);
        vorItem.getDataDelegate().set(value);
    }

    @Override
    public void set(String path, Boolean value) {
        VorItemImpl vorItem = get(path);
        vorItem.set(value);
    }

    @Override
    public void set(String path, Number value) {
        VorItemImpl vorItem = get(path);
        vorItem.set(value);
    }

    @Override
    public void set(String path, BigDecimal value) {
        VorItemImpl vorItem = get(path);
        vorItem.set(value);
    }

    public VorItemImpl fromPathNode(VorPathNode node) {
        if (node.getNodeName() != null) {
            return getStruct(node);
        }

        return getList(node);
    }

    @Override
    public List<VorItem> select(String... paths) {

        List<VorItem> items = new ArrayList<>();
        for (String path : paths) {
            items.add(get(path));
        }

        return items;
    }

    protected VorItemImpl getStruct(VorPathNode node) {
        return getStructDelegate().get(node);
    }

    protected VorItemImpl getList(VorPathNode node) {
        return getListDelegate().get(node);
    }


    @Override
    public VorPathNode getPathNode() {
        return nodePath;
    }

    @Override
    public VorItem getContainer() {
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

    protected VorItemImpl getThis() {
        return this;
    }

    public void propagateChange(VorItemImpl modified) {
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

    public VorItemImpl makeCopy(VorItem toCopy, VorPathNode pathNode) {
        return new VorItemImpl(toCopy.asJsonElement(), config, this, pathNode);
    }

    @Override
    public String toString() {
        return "VorItemImpl{" +
                "node=" + (getJsonElement().isJsonNull() ? "(empty)" : "(" + getType(getJsonElement())+ ")") +
                ", config=" + config +
                ", nodePath=" + nodePath +
                ", " + super.toString() +
                '}';
    }
}
