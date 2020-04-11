package im.wilk.vor.item.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.factory.VorItemFactory;
import im.wilk.vor.item.factory.VorItemFactoryBuilder;
import im.wilk.vor.item.util.matchers.VorPathNodeMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class VorItemImplTest {


    private VorItemFactory factory = VorItemFactoryBuilder.standard().build();

    @Test
    public void emptyItemCanPerformAllOperations() {
        VorItem item = factory.empty();
        String path = "somePath";

        assertThat(item.get(path).exists(), is(false));
        assertThat(item.get(path).get(0).exists(), is(false));
        assertThat(item.get(path).get("next").exists(), is(false));
        assertThat(item.get(path).getFullPath(), is(equalTo(path)));
        assertThat(item.get(path).getContainer(), equalTo(item));
        assertThat(item.get(path).getItemType(), equalTo(VorItem.ItemType.ANY));
        MatcherAssert.assertThat(item.get(path).getPathNode(), VorPathNodeMatcher.isNodeWith(path, null, null));

        assertThat(item.get(path).list(), is(empty()));
        assertThat(item.get(path).listSize(), equalTo(0));

        assertThat(item.get(path).fields(), is(anEmptyMap()));

        assertThat(item.get(path).optionalString(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBoolean(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBigDecimal(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalLong(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalDouble(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalFloat(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalNumber(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalInteger(), equalTo(Optional.empty()));

        assertThat(item.get(path).getS(), is(nullValue()));
        assertThat(item.get(path).getL(), is(nullValue()));
        assertThat(item.get(path).getBd(), is(nullValue()));
        assertThat(item.get(path).getD(), is(nullValue()));
        assertThat(item.get(path).getF(), is(nullValue()));
        assertThat(item.get(path).getN(), is(nullValue()));
        assertThat(item.get(path).getI(), is(nullValue()));
        assertThat(item.get(path).getB(), is(false));
    }

    @Test
    public void existingListItemCanPerformAllOperations() {
        JsonElement jsonElement = parseSemiJson("{'somePath':[1,2,3]}");
        VorItem item = factory.from(jsonElement);
        String path = "somePath";

        assertThat(item.get(path).exists(), is(true));
        assertThat(item.get(path).get(0).exists(), is(true));
        assertThat(item.get(path).get("next").exists(), is(false));
        assertThat(item.get(path).getFullPath(), is(equalTo(path)));
        assertThat(item.get(path).getContainer(), equalTo(item));
        assertThat(item.get(path).getItemType(), equalTo(VorItem.ItemType.LIST));
        MatcherAssert.assertThat(item.get(path).getPathNode(), VorPathNodeMatcher.isNodeWith(path, null, null));

        assertThat(item.get(path).list(), is(not(empty())));
        assertThat(item.get(path).listSize(), equalTo(3));

        assertThat(item.get(path).fields(), is(anEmptyMap()));

        assertThat(item.get(path).optionalString(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBoolean(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBigDecimal(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalLong(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalDouble(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalFloat(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalNumber(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalInteger(), equalTo(Optional.empty()));

        assertThat(item.get(path).getS(), is(nullValue()));
        assertThat(item.get(path).getL(), is(nullValue()));
        assertThat(item.get(path).getBd(), is(nullValue()));
        assertThat(item.get(path).getD(), is(nullValue()));
        assertThat(item.get(path).getF(), is(nullValue()));
        assertThat(item.get(path).getN(), is(nullValue()));
        assertThat(item.get(path).getI(), is(nullValue()));
        assertThat(item.get(path).getB(), is(false));
    }

    private JsonElement parseSemiJson(String semiJson) {
        return JsonParser.parseString(semiJson.replace('\'', '"'));
    }

    @Test
    public void existingStructItemCanPerformAllOperations() {
        JsonElement jsonElement = parseSemiJson("{'somePath':{'x':1}}");
        VorItem item = factory.from(jsonElement);
        String path = "somePath";

        assertThat(item.get(path).exists(), is(true));
        assertThat(item.get(path).get(0).exists(), is(false));
        assertThat(item.get(path).get("next").exists(), is(false));
        assertThat(item.get(path).getFullPath(), is(equalTo(path)));
        assertThat(item.get(path).getContainer(), equalTo(item));
        assertThat(item.get(path).getItemType(), equalTo(VorItem.ItemType.STRUCT));
        MatcherAssert.assertThat(item.get(path).getPathNode(), VorPathNodeMatcher.isNodeWith(path, null, null));

        assertThat(item.get(path).list(), is(empty()));
        assertThat(item.get(path).listSize(), equalTo(0));

        assertThat(item.get(path).fields(), is(not(anEmptyMap())));

        assertThat(item.get(path).optionalString(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBoolean(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBigDecimal(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalLong(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalDouble(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalFloat(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalNumber(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalInteger(), equalTo(Optional.empty()));

        assertThat(item.get(path).getS(), is(nullValue()));
        assertThat(item.get(path).getL(), is(nullValue()));
        assertThat(item.get(path).getBd(), is(nullValue()));
        assertThat(item.get(path).getD(), is(nullValue()));
        assertThat(item.get(path).getF(), is(nullValue()));
        assertThat(item.get(path).getN(), is(nullValue()));
        assertThat(item.get(path).getI(), is(nullValue()));
        assertThat(item.get(path).getB(), is(false));
    }

    @Test
    public void existingStringItemCanPerformAllOperations() {
        JsonElement jsonElement = parseSemiJson("{'somePath':'value'}");
        VorItem item = factory.from(jsonElement);
        String path = "somePath";

        assertThat(item.get(path).exists(), is(true));
        assertThat(item.get(path).get(0).exists(), is(false));
        assertThat(item.get(path).get("next").exists(), is(false));
        assertThat(item.get(path).getFullPath(), is(equalTo(path)));
        assertThat(item.get(path).getContainer(), equalTo(item));
        assertThat(item.get(path).getItemType(), equalTo(VorItem.ItemType.DATA));
        MatcherAssert.assertThat(item.get(path).getPathNode(), VorPathNodeMatcher.isNodeWith(path, null, null));

        assertThat(item.get(path).list(), is(empty()));
        assertThat(item.get(path).listSize(), equalTo(0));

        assertThat(item.get(path).fields(), is(anEmptyMap()));

        assertThat(item.get(path).optionalString(), equalTo(Optional.of("value")));
        assertThat(item.get(path).optionalBoolean(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalBigDecimal(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalLong(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalDouble(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalFloat(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalNumber(), equalTo(Optional.empty()));
        assertThat(item.get(path).optionalInteger(), equalTo(Optional.empty()));

        assertThat(item.get(path).getS(), equalTo("value"));
        assertThat(item.get(path).getL(), is(nullValue()));
        assertThat(item.get(path).getBd(), is(nullValue()));
        assertThat(item.get(path).getD(), is(nullValue()));
        assertThat(item.get(path).getF(), is(nullValue()));
        assertThat(item.get(path).getN(), is(nullValue()));
        assertThat(item.get(path).getI(), is(nullValue()));
        assertThat(item.get(path).getB(), is(false));
    }

    @Test
    public void readAllDataAndOverrideTypes() {
        JsonElement jsonElement = parseSemiJson("{'data':{" +
                "'string':'string_value'," +
                "'int':77, 'num':23.2, 'bool':true" +
                "}}");
        VorItem item = factory.from(jsonElement);

        assertThat(item.get("data/string").getS(), equalTo("string_value"));
        assertThat(item.get("data/string").optionalString(), equalTo(Optional.of("string_value")));

        assertThat(item.get("data/int").getS(), equalTo("77"));
        assertThat(item.get("data/num").getS(), equalTo("23.2"));
        assertThat(item.get("data/bool").getS(), equalTo("true"));

        assertThat(item.get("data/int").getL(), equalTo(77L));
        assertThat(item.get("data/int").optionalLong(), equalTo(Optional.of(77L)));

        assertThat(item.get("data/num").getBd(), equalTo(BigDecimal.valueOf(23.2)));
        assertThat(item.get("data/num").optionalBigDecimal(), equalTo(Optional.of(BigDecimal.valueOf(23.2))));

        assertThat(item.get("data/bool").getB(), is(true));
        assertThat(item.get("data/bool").optionalBoolean(), equalTo(Optional.of(true)));

        // re-setting
        item.set("data/string", "new_string_value");
        assertThat(item.get("data/string").getS(), equalTo("new_string_value"));

        item.set("data/int", 34L);
        assertThat(item.get("data/int").getL(), equalTo(34L));

        item.set("data/num", BigDecimal.valueOf(234.7));
        assertThat(item.get("data/num").getBd(), equalTo(BigDecimal.valueOf(234.7)));

        item.set("data/bool", false);
        assertThat(item.get("data/bool").getB(), is(false));

        JsonElement updatedjsonElement = parseSemiJson("{'data':{" +
                "'string':'new_string_value'," +
                "'int':34, 'num':234.7, 'bool':false" +
                "}}");

        assertThat(item.asJsonElement(), equalTo(updatedjsonElement));
    }

    @Test
    public void converdionBetweenNumericTypesWork() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'number_1':77," +
                "'number_2':23.2" +
                "}");
        VorItem item = factory.from(jsonElement);

        assertThat(item.get("number_1").getS(), equalTo("77"));
        assertThat(item.get("number_1").getI(), equalTo(77));
        assertThat(item.get("number_1").getL(), equalTo(77L));
        assertThat(item.get("number_1").getF(), equalTo((float)77.0));
        assertThat(item.get("number_1").getD(), equalTo(77.0));
        assertThat(item.get("number_1").getN().byteValue(), equalTo((byte)77));

        assertThat(item.get("number_2").getS(), equalTo("23.2"));
        assertThat(item.get("number_2").getI(), equalTo(23));
        assertThat(item.get("number_2").getL(), equalTo(23L));
        assertThat(item.get("number_2").getF(), equalTo((float)23.2));
        assertThat(item.get("number_2").getD(), equalTo(23.2));
        assertThat(item.get("number_2").getN().byteValue(), equalTo((byte)23));
    }

    @Test
    public void canAccessNestedItem() {
        JsonElement jsonElement = parseSemiJson("{'one':{" +
                "'list':['symbol', {'struct_elem':6}, [ 1, 2, 3]]}," +
                "'two':{'three':99}}");
        VorItem item = factory.from(jsonElement);

        assertThat(item.get("one/list:0").getS(), equalTo("symbol"));
        assertThat(item.get("one/list:1/struct_elem").getL(), equalTo(6L));
        assertThat(item.get("one/list:2:0").getL(), equalTo(1L));
        assertThat(item.get("one/list:2:1").getL(), equalTo(2L));
        assertThat(item.get("one/list:2:2").getL(), equalTo(3L));
        assertThat(item.get("two/three").getL(), equalTo(99L));
    }

    @Test
    public void canAddAliasesToItem() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'one':{'list':[1, 2, 3]}" +
                "}");
        VorItem item = factory.from(jsonElement);

        VorItem list = item.get("one/list:0");
        VorItem list_aliased = item.get("one/list:1=second");

        assertThat(list.getL(), equalTo(1L));
        assertThat(list.name(), equalTo("list"));
        assertThat(list.getFullPath(), equalTo("one/list:0"));

        assertThat(list_aliased.getL(), equalTo(2L));
        assertThat(list_aliased.name(), equalTo("second"));
        assertThat(list_aliased.getFullPath(), equalTo("one/list:1=second"));
    }

    @Test
    public void canSetSimpleData() {
        VorItem item = factory.empty();

        item.set("string", "string_value");
        item.set("bool", true);
        item.set("long", 46L);
        item.set("num", BigDecimal.valueOf(12.3));

        JsonElement jsonElement = item.asJsonElement();
        JsonElement expected = parseSemiJson("{" +
                "'string':'string_value'," +
                "'bool':true, " +
                "'long':46, " +
                "'num': 12.3" +
                "}");

        assertThat(jsonElement, equalTo(expected));
    }

    @Test
    public void settinFromOtherVorIon_MakesACopyOfData() {
        VorItem item = factory.empty();
        VorItem otherValue = factory.empty();
        VorItem otherStruct = factory.empty();
        VorItem otherList = factory.empty();

        otherValue.set("original_value");
        otherStruct.set("struct", "original_value");
        otherList.set(":0", "original_value");

        item.set("val", otherValue);
        item.set("st", otherStruct);
        item.set("l", otherList);

        otherValue.set("updated_value");
        otherStruct.set("struct", "updated_value");
        otherList.set("list:0", "updated_value");

        JsonElement expected = parseSemiJson("{" +
                "'val':'original_value'," +
                "'st':{'struct':'original_value'}," +
                "'l':['original_value']" +
                "}");

        assertThat(item.asJsonElement(), equalTo(expected));
    }

    @Test
    public void callingAsJsonElement_MakesACopyOfData() {
        VorItem item = factory.empty();

        item.set("data", "test_value");
        JsonElement jsonElement = item.asJsonElement();

        // modify item
        item.set("extra", "more_values");

        JsonElement initial = parseSemiJson("{'data':'test_value'}");
        JsonElement modified = parseSemiJson("{'data':'test_value', 'extra':'more_values'}");

        assertThat(jsonElement, equalTo(initial));
        assertThat(item.asJsonElement(), equalTo(modified));
    }

    @Test
    public void canCreateJsonFromPath() {
        VorItem item = factory.empty();

        item.set("one/two:1/three/four", "string_value");

        JsonElement jsonElement = item.asJsonElement();
        JsonElement expected = parseSemiJson("{" +
                "'one':{'two':[null, {'three':{'four':'string_value'}}]}" +
                "}");

        assertThat(jsonElement, equalTo(expected));

        item.set("one/two:1/three/four", "updated");
        expected = parseSemiJson("{" +
                "'one':{'two':[null, {'three':{'four':'updated'}}]}" +
                "}");

        jsonElement = item.asJsonElement();
        assertThat(jsonElement, equalTo(expected));
    }

    @Test
    public void singlePathCanBeReplacedWithChaining() {
        VorItem item = factory.empty();

        item.set("one/two:1/three/four:0", "set_with_path");
        item.get("one").get("two").get(1).get("three").get("four").get(1).set("set_with_chain");

        JsonElement jsonElement = item.asJsonElement();
        JsonElement expected = parseSemiJson("{" +
                "'one':{'two':[null, {'three':{'four':['set_with_path','set_with_chain']}}]}" +
                "}");

        assertThat(jsonElement, equalTo(expected));
    }

    @Test
    public void itemMayBeAccessedAsJson() {
        VorItem item = factory.empty();

        assertThat(item.asJsonElement().isJsonNull(), is(true));
        item.set(true);
        assertThat(item.asJsonElement().isJsonPrimitive(), equalTo(true));
        assertThat(item.asJsonElement().getAsBoolean(), equalTo(true));
    }

    @Test
    public void toStringShowsContentOnlyForDataItems() {

        // to prevent from adding megabytes of data to logs toString()
        // does NOT show content of List or Struct
        JsonElement jsonElement = parseSemiJson("{" +
                "'list':['list_content'], " +
                "'struct':{'struct_content':'struct_content'}," +
                "'string':'string_content'" +
                "}");

        VorItem item = factory.from(jsonElement);
        assertThat(item.toString(), not(containsString("string_content")));
        assertThat(item.get("list:0").getS(), equalTo("list_content"));
        assertThat(item.get("list").toString(), not(containsString("list_content")));
        assertThat(item.get("struct/struct_content").getS(), equalTo("struct_content"));
        assertThat(item.get("struct").toString(), not(containsString("struct_content")));
        assertThat(item.get("struct/struct_content").toString(), containsString("struct_content"));
        assertThat(item.get("string").getS(), equalTo("string_content"));
        assertThat(item.get("string").toString(), containsString("string_content"));
    }

    @Test
    public void canSetFieldsFromOtherVorItem_UsingSelect() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'list':['list_1', 'list_2'], " +
                "'struct':{'struct_1':'some struct_1_content', 'struct_2':'struct_2_content'}," +
                "'ignored':{value:33}," +
                "'string':'some_string'" +
                "}");

        VorItem existing = factory.from(jsonElement);
        VorItem copy = factory.empty();
        existing.select(
                "string",
                "struct=copy_of_struct",
                "struct/struct_1=struct_1_content",
                "list",
                "list:1=elem1")
                .forEach(copy::set);

        assertThat(copy.get("string").getS(), equalTo("some_string"));
        assertThat(copy.get("elem1").getS(), equalTo("list_2"));
        assertThat(copy.get("struct_1_content").getS(), equalTo("some struct_1_content"));
        assertThat(copy.get("list").listSize(), equalTo(2));
        assertThat(copy.get("list:0").getS(), equalTo("list_1"));
        assertThat(copy.get("list").get(1).getS(), equalTo("list_2"));

        assertThat(copy.get("copy_of_struct/struct_1").getS(), equalTo("some struct_1_content"));
        assertThat(copy.get("copy_of_struct/struct_2").getS(), equalTo("struct_2_content"));

        assertThat(copy.get("ignored").exists(), is(false));
    }

    @Test
    public void canSetFieldsFromOtherVorItem_UsingSet() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'string':'some_string'" +
                "}");

        VorItem existing = factory.from(jsonElement);

        VorItem copy = factory.empty();
        copy.set(existing.get("string"));
        assertThat(copy.get("string").getS(), equalTo("some_string"));

        copy.set("other_name", existing.get("string"));
        assertThat(copy.get("other_name").getS(), equalTo("some_string"));

        copy.set(existing.get("string=some_alias"));
        assertThat(copy.get("some_alias").getS(), equalTo("some_string"));
    }

    @Test
    public void canModifyList() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'list':['value_1', 'value_2', 'value_3', 'value_4']" +
                "}");
        VorItem item = factory.from(jsonElement);
        VorItem list = item.get("list");

        assertThat(list.listSize(), equalTo(4));
        list.get(1).set("struct", 5L);

        JsonElement expectedStruct = parseSemiJson("{'struct':5}");
        assertThat(list.get(":1").asJsonElement(), equalTo(expectedStruct));

        list.remove(2);
        assertThat(list.listSize(), equalTo(3));
        assertThat(list.get(2).getS(), equalTo("value_4"));

        VorItem added = factory.empty();
        added.set("added_value");

        list.add(added);
        assertThat(list.listSize(), equalTo(4));
        assertThat(list.get(3).getS(), equalTo("added_value"));

        added.set("updated_value");
        list.set(0, added);
        assertThat(list.listSize(), equalTo(4));
        assertThat(list.get(0).getS(), equalTo("updated_value"));
        assertThat(list.get(3).getS(), equalTo("added_value"));


        list.get(5).set("skipped_one");
        assertThat(list.listSize(), equalTo(6));
        assertThat(list.get(4).getS(), is(nullValue()));
        assertThat(list.get(5).getS(), equalTo("skipped_one"));

        list.get(4).set("now_set");
        assertThat(list.listSize(), equalTo(6));
        assertThat(list.get(4).getS(), equalTo("now_set"));
    }
    @Test
    public void canAddToNonExistingList() {
        VorItem item = factory.empty();

        VorItem forList = factory.empty();
        item.set("sample text");

        forList.get("list").set(0, item);
        assertThat(forList.asJsonElement(), equalTo(parseSemiJson("{'list':['sample text']}")));
    }
    
    @Test
    public void canSetValuesToNull() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'list':['list_1', 'list_2'], " +
                "'struct':{'struct_1':'some struct_1_content', 'struct_2':'struct_2_content'}," +
                "'ignored':{'value':33}," +
                "'string':'some_string'" +
                "}");

        VorItem item = factory.from(jsonElement);
        item.get("list:0").setNull();
        assertThat(item.get("list").asJsonElement(), equalTo(parseSemiJson("[null, 'list_2']")));

        item.get("list").setNull();
        item.get("struct").setNull();
        item.get("string").setNull();
        item.get("not_existing").setNull();

        JsonElement expected = parseSemiJson("{" +
                "'list':null, " +
                "'struct':null," +
                "'ignored':{'value':33}," +
                "'string':null," +
                "'not_existing':null" +
                "}");

        assertThat(item.asJsonElement(), equalTo(expected));

        item.setNull();
        assertThat(item.asJsonElement().isJsonNull(), is(true));
    }

    @Test
    public void canRemoveValues() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'list':['list_1', 'list_2'], " +
                "'struct':{'struct_1':'some struct_1_content', 'struct_2':'struct_2_content'}," +
                "'ignored':{value:33}," +
                "'string':'some_string'" +
                "}");

        VorItem item = factory.from(jsonElement);
        item.get("list:0").remove();
        item.get("list:10").remove();
        assertThat(item.get("list").asJsonElement(), equalTo(parseSemiJson("['list_2']")));

        item.get("list").remove();
        item.get("struct").remove();
        item.get("string").remove();

        item.get("not_existing").remove();

        // setup it as data
        item.get("not_existing_data").getS();
        item.get("not_existing_data").remove();

        // setup it as struct
        item.get("not_existing_struct/field").getS();
        item.get("not_existing_struct").remove();

        // setup it as list
        item.get("not_existing_list:0").getS();
        item.get("not_existing_list").remove(10);
        item.get("not_existing_list").remove();

        JsonElement expected = parseSemiJson("{" +
                "'ignored':{'value':33}" +
                "}");

        assertThat(item.asJsonElement(), equalTo(expected));

        item.remove();
        assertThat(item.asJsonElement().isJsonNull(), is(true));
    }

    @Test
    public void listSpecificMethods() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'list':['list_1', 'list_2']," +
                "'other_list':[] " +
                "}");

        VorItem item = factory.from(jsonElement);
        assertThat(item.get("list").isEmptyList(), is(false));
        assertThat(item.get("other_list").isEmptyList(), is(true));
        assertThat(item.get("missing_list").isEmptyList(), is(true));

        // list is read-only
        List<VorItem> list = item.get("list").list();
        assertThat(list, hasSize(2));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.remove(0));

        // only direct removal is allowed
        item.get("list").remove(0);
        List<VorItem> updatedList = item.get("list").list();
        assertThat(updatedList, hasSize(1));

        // but you can modify list items
        updatedList.get(0).set("updated");
        assertThat(item.get("list:0").getS(), equalTo("updated"));

        item.get("list:5").set("added as 5");
        updatedList = item.get("list").list();
        assertThat(updatedList, hasSize(6));

        item.get("list").remove(2);
        item.get("list").remove(2);
        updatedList = item.get("list").list();
        assertThat(updatedList, hasSize(4));
        assertThat(updatedList.get(3).getS(), equalTo("added as 5"));
        assertThat(updatedList.get(3).getFullPath(), equalTo("list:3"));
    }

    @Test
    public void structSpecificMethods() {
        JsonElement jsonElement = parseSemiJson("{" +
                "'struct':{'struct_1':'some struct_1_content', 'struct_2':'struct_2_content'}," +
                "'other_struct':{}" +
                "}");

        VorItem item = factory.from(jsonElement);
        assertThat(item.get("struct").isEmptyStruct(), is(false));
        assertThat(item.get("other_struct").isEmptyStruct(), is(true));
        assertThat(item.get("missing_struct").isEmptyStruct(), is(true));

        // map is read-only
        Map<String, VorItem> itemMap = item.get("struct").fields();
        assertThat(itemMap.keySet(), containsInAnyOrder("struct_1", "struct_2"));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> itemMap.remove("struct_1"));

        // it has to be removed directly
        item.get("struct/struct_1").remove();
        Map<String, VorItem> updatedMap = item.get("struct").fields();
        assertThat(updatedMap.keySet(), containsInAnyOrder("struct_2"));

        updatedMap.get("struct_2").set("updated_struct");
        assertThat(item.get("struct/struct_2").getS(), equalTo("updated_struct"));
    }

    @Test
    public void testStrictBehavior_forLists() {

        VorItemFactory nonLenientFactory = VorItemFactoryBuilder.strict().build();
        VorItem lenient = factory.empty();
        VorItem nonLenient = nonLenientFactory.empty();

        // list leniency means that you cannot 'skip' item.
        lenient.set("list:3", "skipped");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                nonLenient.set("list:3", "skipped"));

        lenient.get("list:3");

        // can access only one by one
        nonLenient.get("list").get(0);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                nonLenient.get("list").get(1));
        nonLenient.get("list").get(0).set("anything");
        nonLenient.get("list").get(1);


        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                nonLenient.get("list:3"));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                nonLenient.get("list").set(3, lenient.get("list:3")));

    }

    @Test
    public void testStrictBehavior_forJsonStructure_afterSettingValue() {

        VorItemFactory nonLenientFactory = VorItemFactoryBuilder.strict().build();
        VorItem lenient = factory.empty();
        VorItem nonLenient = nonLenientFactory.empty();

        // struct leniency means that you cannot change type of data once it's set.
        // before it is set behaviour is the same
        assertThat(lenient.get("missing/field").exists(), is(false));
        assertThat(lenient.get("missing:0").exists(), is(false));
        assertThat(nonLenient.get("missing/field").exists(), is(false));
        assertThat(nonLenient.get("missing:0").exists(), is(false));

        // after setting e.g. to struct

        lenient.set("missing/field", "some value");
        nonLenient.set("missing/field", "some value");

        assertThat(lenient.get("missing/field").exists(), is(true));
        assertThat(lenient.get("missing:0").exists(), is(false));
        assertThat(nonLenient.get("missing/field").exists(), is(true));
        // missing is a struct, so you cannot refer to it as list
        Assertions.assertThrows(IllegalStateException.class, () ->
                nonLenient.get("missing:0").exists());
    }

    @Test
    public void testStrictBehavior_forJsonStructure_OnExistingValues() {

        VorItemFactory nonLenientFactory = VorItemFactoryBuilder.strict().build();

        JsonElement jsonElement = parseSemiJson("{'list':[1,2,3],'data':2}");

        VorItem lenient = factory.from(jsonElement.deepCopy());
        VorItem nonLenient = nonLenientFactory.from(jsonElement);

        // no issue here
        lenient.set("list", "replaced");
        assertThat(lenient.asJsonElement(), equalTo(parseSemiJson("{'list':'replaced','data':2}")));
        lenient.set("list/data", "replaced");
        assertThat(lenient.asJsonElement(), equalTo(parseSemiJson("{'list':{'data':'replaced'},'data':2}")));


        // can change data of same kind - e.g. int to String
        nonLenient.set("data", "replaced");
        assertThat(nonLenient.get("data").getS(), equalTo("replaced"));

        // cannot change list to data or struct
        Assertions.assertThrows(IllegalStateException.class, () ->
                nonLenient.set("list", "replaced"));
        Assertions.assertThrows(IllegalStateException.class, () ->
                nonLenient.set("list/data", "replaced"));
        // or data to other
        Assertions.assertThrows(IllegalStateException.class, () ->
                nonLenient.set("data/field", "replaced"));
        Assertions.assertThrows(IllegalStateException.class, () ->
                nonLenient.set("data:0", "replaced"));

    }

}