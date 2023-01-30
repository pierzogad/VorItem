package im.wilk.jsonitem.item.factory;

import com.google.gson.JsonElement;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.model.JsonItemConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JsonItemFactoryTest {

    private JsonItemConfig config = mock(JsonItemConfig.class);
    private JsonItemFactory factory = new JsonItemFactory(config);

    @Test
    public void whenCallingFrom_validObjectIsCreated() {
        JsonElement jsonElement = mock(JsonElement.class);
        when(jsonElement.deepCopy()).thenReturn(jsonElement);

        JsonItem jsonItem = factory.from(jsonElement);
        assertThat(jsonItem, is(notNullValue()));
        assertThat(jsonItem.asJsonElement(), equalTo(jsonElement));

        assertThat(factory.getJsonItemConfig(), equalTo(config));
    }

    @Test
    public void whenCallingEmpty_validObjectIsCreated() {

        JsonItem jsonItem = factory.empty();

        assertThat(jsonItem, is(notNullValue()));
        assertThat(jsonItem.asJsonElement().isJsonNull(), is(true));
    }
}