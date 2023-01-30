package im.wilk.jsonitem.item.model;

import im.wilk.jsonitem.item.node.JsonPathParser;
import lombok.Data;

@Data
public class JsonItemConfig {
    private final PathParserConfig parserConfig;
    private final boolean lenientStructure;
    private final boolean lenientLists;
    private final JsonPathParser pathParser;

    public JsonItemConfig(PathParserConfig parserConfig,
                          boolean lenientStructure,
                          boolean lenientLists) {
        this.parserConfig = parserConfig;
        this.lenientStructure = lenientStructure;
        this.lenientLists = lenientLists;
        pathParser = new JsonPathParser(parserConfig);
    }
}
