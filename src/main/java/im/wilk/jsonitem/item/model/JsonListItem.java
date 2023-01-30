package im.wilk.jsonitem.item.model;

import java.util.List;

public interface JsonListItem<T extends JsonListItem<T>> extends JsonItemBase<T> {
    boolean isEmptyList();
    int listSize();
    List<T> list();
    void remove(int idx);
}
