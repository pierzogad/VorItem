package im.wilk.vor.item.factory;

import im.wilk.vor.item.model.PathParserConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class VorItemFactoryBuilderTest {

    @Test
    public void whenUsingStaticConstructors_validSettingsAreUsed() {
        VorItemFactoryBuilder builder = VorItemFactoryBuilder.standard();

        assertThat(builder.isLenientLists(), is(true));
        assertThat(builder.isLenientStructure(), is(true));
        assertThat(builder.getParserConfig(), is(notNullValue()));
        assertThat(builder.getParserConfig(), equalTo(PathParserConfig.standard()));

        // build non-lenient VorItems
        builder = VorItemFactoryBuilder.strict();

        assertThat(builder.isLenientLists(), is(false));
        assertThat(builder.isLenientStructure(), is(false));
        assertThat(builder.getParserConfig(), is(notNullValue()));
        assertThat(builder.getParserConfig(), equalTo(PathParserConfig.standard()));
    }
}