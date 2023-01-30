import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import im.wilk.jsonitem.item.JsonItem;
import im.wilk.jsonitem.item.factory.JsonItemFactory;
import im.wilk.jsonitem.item.factory.JsonItemFactoryBuilder;

import java.util.ArrayList;
import java.util.List;

public class SimpleTest {

    public static void main(String[] args) {

        JsonItem item = JsonItem.from("{\"a\":[1, 2]}");


        List<JsonItem> list = item.get("a").list();
        list.stream().forEach(x -> System.out.println("x is " + x.getS()));
        String a = item.get("a").get(0).getS();
        System.out.println("a is " + a);

        item.set("b.c[6].qq.txt", "abba");
        item.get("b.c").list().forEach(e -> e.set("qq.val", 177));
        System.out.println("now: " + item.asJsonElement());

        x c = new x(2);
        c.add(new x(77));
        System.out.println("one: " + c.get().val);
        System.out.println("one: " + c.get(0).val);
    }

    private static class x extends ArrayList<x> {
        public x(int ss) {
            val = ss;
        }

        x get() {
            return get(0);
        }

        public x get(int idx) {
            return (size() == 0) ? this : super.get(idx);
        }
        int val;
    }

}
