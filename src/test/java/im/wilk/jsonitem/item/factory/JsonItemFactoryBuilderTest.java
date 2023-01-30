package im.wilk.jsonitem.item.factory;

import im.wilk.jsonitem.item.model.PathParserConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class JsonItemFactoryBuilderTest {

    @Test
    public void whenUsingStaticConstructors_validSettingsAreUsed() {
        JsonItemFactoryBuilder builder = JsonItemFactoryBuilder.standard();

        assertThat(builder.isLenientLists(), is(true));
        assertThat(builder.isLenientStructure(), is(true));
        assertThat(builder.getParserConfig(), is(notNullValue()));
        assertThat(builder.getParserConfig(), equalTo(PathParserConfig.standard()));

        // build non-lenient JsonItems
        builder = JsonItemFactoryBuilder.strict();

        assertThat(builder.isLenientLists(), is(false));
        assertThat(builder.isLenientStructure(), is(false));
        assertThat(builder.getParserConfig(), is(notNullValue()));
        assertThat(builder.getParserConfig(), equalTo(PathParserConfig.standard()));
    }
}