package im.wilk.jsonitem.item.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

class JsonItemConfigTest {

    public static final boolean LENIENT_STRUCTURE_SET = true;
    public static final boolean LENIENT_LISTS_SET = true;
    public static final boolean LENIENT_STRUCTURE_UNSET = false;
    public static final boolean LENIENT_LISTS_UNSET = false;

    @Test
    public void whenCreated_AllItemsAreSet() {
        PathParserConfig parserConfig = mock(PathParserConfig.class);

        JsonItemConfig config = new JsonItemConfig(parserConfig, LENIENT_STRUCTURE_SET, LENIENT_LISTS_UNSET);
        assertThat(config.getParserConfig(), equalTo(parserConfig));
        assertThat(config.isLenientLists(), equalTo(LENIENT_LISTS_UNSET));
        assertThat(config.isLenientStructure(), equalTo(LENIENT_STRUCTURE_SET));
        assertThat(config.getPathParser(), is(notNullValue()));

        config = new JsonItemConfig(parserConfig, LENIENT_STRUCTURE_UNSET, LENIENT_LISTS_SET);
        assertThat(config.isLenientLists(), equalTo(LENIENT_LISTS_SET));
        assertThat(config.isLenientStructure(), equalTo(LENIENT_STRUCTURE_UNSET));

        assertThat(config.toString(), is(notNullValue()));
    }
}