package im.wilk.jsonitem.item.factory;

import com.google.gson.JsonElement;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.impl.JsonItemImpl;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import im.wilk.jsonitem.item.node.JsonPathNode;
import lombok.Getter;

/**
 * JsonItemFactory is the only mechanism to create instance of JsonItem .
 *
 * JsonItemFactory itself must be created using JsonItemFactoryBuilder that
 * allows setting path parser format ("a/b/c:n" vs "a.b.c[n]"),
 * and JsonItem behaviour (leniency)
 */
@Getter
public class JsonItemFactory {
    private final JsonItemConfig jsonItemConfig;

    /*package local - it should be created with JsonItemFactoryBuilder */
    JsonItemFactory(JsonItemConfig jsonItemConfig) {
        this.jsonItemConfig = jsonItemConfig;
    }

    /**
     * Create JsonItem based on existing Json data.
     *
     * @param element - JSonElement.
     *              Data passed here will:
     *              * not be modified by read-only access
     *                 (attempt to read from item that does not exist will not modify Json)
     *              * WILL be modified data when using set(), remove(), add()..
     * @return JsonItem referencing to root of element data  hierarchy.
     */
    public JsonItem from(JsonElement element) {
        return new JsonItemImpl(element, jsonItemConfig, null, JsonPathNode.forName(""));
    }

    /**
     * Create JsonItem containing no data.
     *
     *  Data can be modified data when using set(), remove(), add()..
     *
     *  asJsonElement() method will return data in Json format.
     *
     * @return JsonItem containing no data.
     */
    public JsonItem empty() {
        return new JsonItemImpl(null, jsonItemConfig, null, JsonPathNode.forName(""));
    }
}
