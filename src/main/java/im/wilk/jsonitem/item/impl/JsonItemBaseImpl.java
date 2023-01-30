package im.wilk.jsonitem.item.impl;

import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.model.JsonItemBase;

public abstract class JsonItemBaseImpl implements JsonItemBase<JsonItem> {

    abstract void setValue(JsonItemImpl other);
    abstract String describe();
}
