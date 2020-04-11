package im.wilk.vor.item.model;

import java.util.Map;

public interface VorStructItem<T extends VorStructItem<T>> extends VorItemBase<T> {
    boolean isEmptyStruct();
    Map<String, T> fields();
}
