package im.wilk.vor.item.node;


import im.wilk.vor.item.util.matchers.VorPathNodeMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VorPathNodeTest {

    @Test
    public void whenUsingBuilders_validDataIsCreated() {
        VorPathNode node = VorPathNode.forName("field");
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith("field", null, null));

        node = VorPathNode.forName("field", "alias");
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith("field", null, "alias"));

        node = VorPathNode.forName("field", null);
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith("field", null, null));

        node = VorPathNode.forName("field", "");
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith("field", null, null));

        node = VorPathNode.forIndex(27);
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith(null, 27, null));

        node = VorPathNode.forIndex(27, "alias");
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith(null, 27, "alias"));

        node = VorPathNode.forIndex(27, "");
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith(null, 27, null));

        node = VorPathNode.forIndex(27, null);
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith(null, 27, null));
    }

    @Test
    public void whenUpdatingIndex_itWorks() {
        VorPathNode node = VorPathNode.forIndex(23);
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith(null, 23, null));

        node.setNodeIndex(77);
        MatcherAssert.assertThat(node, VorPathNodeMatcher.isNodeWith(null, 77, null));

        VorPathNode pathNode = VorPathNode.forName("field");
        Assertions.assertThrows(IllegalStateException.class, () -> pathNode.setNodeIndex(77));
    }

    @Test
    public void whenSettingAlias_itWorks() {
        VorPathNode nodeOne = VorPathNode.forName("one", "alias_one");
        VorPathNode nodeTwo = VorPathNode.forName("two", "alias_two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, VorPathNodeMatcher.isNodeWith("one", null, "alias_two"));

        nodeOne = VorPathNode.forName("one", "alias_one");
        nodeTwo = VorPathNode.forName("two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, VorPathNodeMatcher.isNodeWith("one", null, "alias_one"));

        nodeOne = VorPathNode.forName("one");
        nodeTwo = VorPathNode.forName("two", "alias_two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, VorPathNodeMatcher.isNodeWith("one", null, "alias_two"));

        nodeOne = VorPathNode.forName("one");
        nodeTwo = VorPathNode.forName("two");

        nodeOne.syncAlias(nodeTwo);
        MatcherAssert.assertThat(nodeOne, VorPathNodeMatcher.isNodeWith("one", null, null));
    }
}