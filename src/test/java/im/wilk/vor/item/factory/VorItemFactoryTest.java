package im.wilk.vor.item.factory;

import com.google.gson.JsonElement;
import im.wilk.vor.item.VorItem;
import im.wilk.vor.item.model.VorItemConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VorItemFactoryTest {

    private VorItemConfig config = mock(VorItemConfig.class);
    private VorItemFactory factory = new VorItemFactory(config);

    @Test
    public void whenCallingFrom_validObjectIsCreated() {
        JsonElement jsonElement = mock(JsonElement.class);
        when(jsonElement.deepCopy()).thenReturn(jsonElement);

        VorItem vorItem = factory.from(jsonElement);
        assertThat(vorItem, is(notNullValue()));
        assertThat(vorItem.asJsonElement(), equalTo(jsonElement));

        assertThat(factory.getVorItemConfig(), equalTo(config));
    }

    @Test
    public void whenCallingEmpty_validObjectIsCreated() {

        VorItem vorItem = factory.empty();

        assertThat(vorItem, is(notNullValue()));
        assertThat(vorItem.asJsonElement().isJsonNull(), is(true));
    }
}