package im.wilk.vor.item.impl;

import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.model.VorItemBase;

public abstract class VorItemBaseImpl implements VorItemBase<VorItem> {

    abstract void setValue(VorItemImpl other);
    abstract String describe();
}
