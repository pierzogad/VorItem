package im.wilk.jsonitem.item.node;

import lombok.ToString;

@ToString
public class JsonPathNode {

    private final String nodeName;
    private Integer nodeIndex;
    private String nodeAlias;

    public JsonPathNode(String nodeName, Integer nodeIndex, String nodeAlias) {
        this.nodeName = nodeName;
        this.nodeAlias = nodeAlias;
        this.nodeIndex = nodeIndex;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Integer getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int idx) {
        if (nodeName != null) {
            throw new IllegalStateException("cannot set index on " + this);
        }
        nodeIndex = idx;
    }

    public String getNodeAlias() {
        return nodeAlias;
    }

    public void syncAlias(JsonPathNode other) {
        if (other.getNodeAlias() != null) {
            nodeAlias = other.getNodeAlias();
        }
    }

    public static JsonPathNode forName(String name) {
        return new JsonPathNode(name, null, null);
    }

    public static JsonPathNode forName(String name, String alias) {
        String aliasToUse = (alias == null || alias.isEmpty()) ? null : alias;
        return new JsonPathNode(name, null, aliasToUse);
    }

    public static JsonPathNode forIndex(int index) {
        return new JsonPathNode(null, index, null);
    }

    public static JsonPathNode forIndex(int index, String alias) {
        String aliasToUse = (alias == null || alias.isEmpty()) ? null : alias;
        return new JsonPathNode(null, index, aliasToUse);
    }
}
