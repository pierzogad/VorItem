package im.wilk.vor.item.model;

import java.util.List;

public interface VorListItem<T extends VorListItem<T>> extends VorItemBase<T> {
    boolean isEmptyList();
    int listSize();
    List<T> list();
    void remove(int idx);
}
