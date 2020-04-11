package im.wilk.vor.item.node;

import lombok.ToString;

@ToString
public class VorPathNode {

    private final String nodeName;
    private Integer nodeIndex;
    private String nodeAlias;

    public VorPathNode(String nodeName, Integer nodeIndex, String nodeAlias) {
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

    public void syncAlias(VorPathNode other) {
        if (other.getNodeAlias() != null) {
            nodeAlias = other.getNodeAlias();
        }
    }

    public static VorPathNode forName(String name) {
        return new VorPathNode(name, null, null);
    }

    public static VorPathNode forName(String name, String alias) {
        String aliasToUse = (alias == null || alias.isEmpty()) ? null : alias;
        return new VorPathNode(name, null, aliasToUse);
    }

    public static VorPathNode forIndex(int index) {
        return new VorPathNode(null, index, null);
    }

    public static VorPathNode forIndex(int index, String alias) {
        String aliasToUse = (alias == null || alias.isEmpty()) ? null : alias;
        return new VorPathNode(null, index, aliasToUse);
    }
}
