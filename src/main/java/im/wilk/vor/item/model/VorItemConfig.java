package im.wilk.vor.item.model;

import im.wilk.vor.item.node.VorPathParser;
import lombok.Data;

@Data
public class VorItemConfig {
    private final PathParserConfig parserConfig;
    private final boolean lenientStructure;
    private final boolean lenientLists;
    private final VorPathParser pathParser;

    public VorItemConfig(PathParserConfig parserConfig,
                         boolean lenientStructure,
                         boolean lenientLists) {
        this.parserConfig = parserConfig;
        this.lenientStructure = lenientStructure;
        this.lenientLists = lenientLists;
        pathParser = new VorPathParser(parserConfig);
    }
}
