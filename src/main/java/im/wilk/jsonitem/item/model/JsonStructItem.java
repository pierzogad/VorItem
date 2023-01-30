package im.wilk.jsonitem.item.model;

import java.util.Map;

public interface JsonStructItem<T extends JsonStructItem<T>> extends JsonItemBase<T> {
    boolean isEmptyStruct();
    Map<String, T> fields();
}
