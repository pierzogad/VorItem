package im.wilk.jsonitem.item.node;

import im.wilk.jsonitem.item.model.PathParserConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonPathParser {

    private final char partSeparator;
    private final char indexOpen;
    private final char indexClose;
    private final char aliasSeparator;
    private final boolean useIndexClose;

    public JsonPathParser(PathParserConfig config) {
        partSeparator = config.getPartSeparator();
        indexOpen = config.getIndexOpen();
        indexClose = config.getIndexClose();
        aliasSeparator = config.getAliasSeparator();
        useIndexClose = indexClose != PathParserConfig.NO_INDEX_CLOSE;
    }

    public List<JsonPathNode> toNodes(String path) {

        List<JsonPathNode> nodes = new ArrayList<>();

        char[] chars = path.toCharArray();

        StringBuilder name = new StringBuilder();
        StringBuilder alias = new StringBuilder();
        StringBuilder index = new StringBuilder();

        StringBuilder current = name;

        for (char c : chars) {
            if (c == partSeparator) {
                if (useIndexClose && current == index) {
                    throw new PathParsingException("Index opened but not closed in " + path);
                }
                addNode(nodes, name, alias, index);
                current = name;
            } else if (c == indexOpen) {
                if (useIndexClose && current == index) {
                    throw new PathParsingException("Index opening separator without closing previous one in " + path);
                }
                addNode(nodes, name, alias, index);
                current = index;
            } else if (c == indexClose) {
                if (current != index) {
                    throw new PathParsingException("Index closing separator without opening one in " + path);
                }
                current = null;
            } else if (c == aliasSeparator) {
                if (useIndexClose && current == index) {
                    throw new PathParsingException("Index opened but not closed in " + path);
                }
                current = alias;
            } else {
                if (current == null) {
                    throw new PathParsingException("Unexpected character past index closing separator in " + path);
                } else if (current == index) {
                    if (c < '0' || c > '9') {
                        throw new PathParsingException("Index can contain digits only in " + path);
                    }
                }
                current.append(c);
            }
        }
        addNode(nodes, name, alias, index);
        return nodes;
    }

    public String buildPath(Collection<JsonPathNode> forNodes) {
        StringBuilder sb = new StringBuilder();
        for (JsonPathNode forNode : forNodes) {
            if (forNode.getNodeName() != null) {
                if (sb.length() > 0) {
                    sb.append(partSeparator);
                }
                sb.append(forNode.getNodeName());
            }
            if (forNode.getNodeIndex() != null) {
                sb.append(indexOpen);
                sb.append(forNode.getNodeIndex());
                if (useIndexClose) {
                    sb.append(indexClose);
                }
            }
            if (forNode.getNodeAlias() != null) {
                sb.append(aliasSeparator);
                sb.append(forNode.getNodeAlias());
            }
        }

        return sb.toString();
    }

    private void addNode(List<JsonPathNode> nodes, StringBuilder name, StringBuilder alias, StringBuilder index) {
        if (name.length() > 0) {
            nodes.add(JsonPathNode.forName(name.toString(), alias.toString()));
            name.setLength(0);
        }
        if (index.length() > 0) {
            int indexValue = Integer.parseInt(index.toString());
            nodes.add(JsonPathNode.forIndex(indexValue, alias.toString()));
            index.setLength(0);
        }
        alias.setLength(0);
    }
}
