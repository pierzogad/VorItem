package im.wilk.vor.item.factory;

import com.google.gson.JsonElement;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.impl.VorItemImpl;
import im.wilk.vor.item.model.VorItemConfig;
import im.wilk.vor.item.node.VorPathNode;
import lombok.Getter;

/**
 * VorItemFactory is the only mechanism to create instance of VorItem .
 *
 * VorItemFactory itself must be created using VorItemFactoryBuilder that
 * allows setting path parser format ("a/b/c:n" vs "a.b.c[n]"),
 * and VorItem behaviour (leniency)
 */
@Getter
public class VorItemFactory {
    private final VorItemConfig vorItemConfig;

    /*package local - it should be created with VorItemFactoryBuilder */
    VorItemFactory(VorItemConfig vorItemConfig) {
        this.vorItemConfig = vorItemConfig;
    }

    /**
     * Create VorItem based on existing Json data.
     *
     * @param element - JSonElement.
     *              Data passed here will:
     *              * not be modified by read-only access
     *                 (attempt to read from item that does not exist will not modify Json)
     *              * WILL be modified data when using set(), remove(), add()..
     * @return VorItem referencing to root of element data  hierarchy.
     */
    public VorItem from(JsonElement element) {
        return new VorItemImpl(element, vorItemConfig, null, VorPathNode.forName(""));
    }

    /**
     * Create VorItem containing no data.
     *
     *  Data can be modified data when using set(), remove(), add()..
     *
     *  asJsonElement() method will return data in Json format.
     *
     * @return VorItem containing no data.
     */
    public VorItem empty() {
        return new VorItemImpl(null, vorItemConfig, null, VorPathNode.forName(""));
    }
}
