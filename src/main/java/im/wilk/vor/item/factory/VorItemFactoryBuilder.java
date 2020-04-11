package im.wilk.vor.item.factory;

import im.wilk.vor.item.model.PathParserConfig;
import im.wilk.vor.item.model.VorItemConfig;
import lombok.Getter;

@Getter
public final class VorItemFactoryBuilder {

    private PathParserConfig parserConfig;
    private boolean lenientStructure;
    private boolean lenientLists;

    private VorItemFactoryBuilder() {
    }

    public static VorItemFactoryBuilder standard() {
        return new VorItemFactoryBuilder()
                .withPathParserConfig(PathParserConfig.standard())
                .withLenientStructure(true)
                .withLenientLists(true);
    }

    public static VorItemFactoryBuilder strict() {
        return new VorItemFactoryBuilder()
                .withPathParserConfig(PathParserConfig.standard())
                .withLenientStructure(false)
                .withLenientLists(false);

    }

    public VorItemFactoryBuilder withLenientLists(boolean lenientLists) {
        this.lenientLists = lenientLists;
        return this;
    }

    public VorItemFactoryBuilder withLenientStructure(boolean lenientStructure) {
        this.lenientStructure = lenientStructure;
        return this;
    }

    public VorItemFactoryBuilder withPathParserConfig(PathParserConfig parserConfig) {
        this.parserConfig = parserConfig;
        return this;
    }

    public VorItemFactory build() {
        VorItemConfig vorItemConfig = new VorItemConfig(parserConfig, lenientStructure, lenientLists);

        return new VorItemFactory(vorItemConfig);
    }
}
