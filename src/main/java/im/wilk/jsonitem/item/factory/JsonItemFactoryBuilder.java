package im.wilk.jsonitem.item.factory;

import im.wilk.jsonitem.item.model.PathParserConfig;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import lombok.Getter;

@Getter
public final class JsonItemFactoryBuilder {

    private PathParserConfig parserConfig;
    private boolean lenientStructure;
    private boolean lenientLists;

    private JsonItemFactoryBuilder() {
    }

    public static JsonItemFactoryBuilder standard() {
        return new JsonItemFactoryBuilder()
                .withPathParserConfig(PathParserConfig.standard())
                .withLenientStructure(true)
                .withLenientLists(true);
    }

    public static JsonItemFactoryBuilder strict() {
        return new JsonItemFactoryBuilder()
                .withPathParserConfig(PathParserConfig.standard())
                .withLenientStructure(false)
                .withLenientLists(false);

    }

    public JsonItemFactoryBuilder withLenientLists(boolean lenientLists) {
        this.lenientLists = lenientLists;
        return this;
    }

    public JsonItemFactoryBuilder withLenientStructure(boolean lenientStructure) {
        this.lenientStructure = lenientStructure;
        return this;
    }

    public JsonItemFactoryBuilder withPathParserConfig(PathParserConfig parserConfig) {
        this.parserConfig = parserConfig;
        return this;
    }

    public JsonItemFactory build() {
        JsonItemConfig jsonItemConfig = new JsonItemConfig(parserConfig, lenientStructure, lenientLists);

        return new JsonItemFactory(jsonItemConfig);
    }
}
