package im.wilk.jsonitem.item.node;


import im.wilk.jsonitem.item.util.matchers.JsonPathNodeMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonPathNodeTest {

    @Test
    public void whenUsingBuilders_validDataIsCreated() {
        JsonPathNode node = JsonPathNode.forName("field");
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith("field", null, null));

        node = JsonPathNode.forName("field", "alias");
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith("field", null, "alias"));

        node = JsonPathNode.forName("field", null);
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith("field", null, null));

        node = JsonPathNode.forName("field", "");
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith("field", null, null));

        node = JsonPathNode.forIndex(27);
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith(null, 27, null));

        node = JsonPathNode.forIndex(27, "alias");
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith(null, 27, "alias"));

        node = JsonPathNode.forIndex(27, "");
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith(null, 27, null));

        node = JsonPathNode.forIndex(27, null);
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith(null, 27, null));
    }

    @Test
    public void whenUpdatingIndex_itWorks() {
        JsonPathNode node = JsonPathNode.forIndex(23);
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith(null, 23, null));

        node.setNodeIndex(77);
        MatcherAssert.assertThat(node, JsonPathNodeMatcher.isNodeWith(null, 77, null));

        JsonPathNode pathNode = JsonPathNode.forName("field");
        Assertions.assertThrows(IllegalStateException.class, () -> pathNode.setNodeIndex(77));
    }

    @Test
    public void whenSettingAlias_itWorks() {
        JsonPathNode nodeOne = JsonPathNode.forName("one", "alias_one");
        JsonPathNode nodeTwo = JsonPathNode.forName("two", "alias_two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, JsonPathNodeMatcher.isNodeWith("one", null, "alias_two"));

        nodeOne = JsonPathNode.forName("one", "alias_one");
        nodeTwo = JsonPathNode.forName("two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, JsonPathNodeMatcher.isNodeWith("one", null, "alias_one"));

        nodeOne = JsonPathNode.forName("one");
        nodeTwo = JsonPathNode.forName("two", "alias_two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, JsonPathNodeMatcher.isNodeWith("one", null, "alias_two"));

        nodeOne = JsonPathNode.forName("one");
        nodeTwo = JsonPathNode.forName("two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, JsonPathNodeMatcher.isNodeWith("one", null, null));
    }
}