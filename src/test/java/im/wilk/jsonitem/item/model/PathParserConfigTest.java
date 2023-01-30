package im.wilk.jsonitem.item.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PathParserConfigTest {

    public static final char PART_SEPARATOR = '~';
    public static final char INDEX_OPEN = 'o';
    public static final char INDEX_CLOSE = 'c';
    public static final char ALIAS_SEPARATOR = 'a';

    @Test
    public void whenCreatedCustomConfigWithIndexClose_parametersArePassed() {
        PathParserConfig config = new PathParserConfig(
                PART_SEPARATOR, ALIAS_SEPARATOR, INDEX_OPEN, INDEX_CLOSE);

        assertThat(config.getPartSeparator(), equalTo(PART_SEPARATOR));
        assertThat(config.getAliasSeparator(), equalTo(ALIAS_SEPARATOR));
        assertThat(config.getIndexOpen(), equalTo(INDEX_OPEN));
        assertThat(config.getIndexClose(), equalTo(INDEX_CLOSE));
    }

    @Test
    public void whenCreatedCustomConfig_parametersArePassed() {
        PathParserConfig config = new PathParserConfig(
                PART_SEPARATOR, ALIAS_SEPARATOR, INDEX_OPEN);

        assertThat(config.getPartSeparator(), equalTo(PART_SEPARATOR));
        assertThat(config.getAliasSeparator(), equalTo(ALIAS_SEPARATOR));
        assertThat(config.getIndexOpen(), equalTo(INDEX_OPEN));
        assertThat(config.getIndexClose(), equalTo((char) 0));
    }

    @Test
    public void whenSameCharacterIsUsed_thenExceptionIsThrown() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new PathParserConfig('1', '1', '3', '4')
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new PathParserConfig('1', '2', '1', '4')
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new PathParserConfig('1', '2', '3', '1')
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new PathParserConfig('1', '2', '2', '4')
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new PathParserConfig('1', '2', '3', '2')
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new PathParserConfig('1', '2', '3', '3')
        );
    }
}