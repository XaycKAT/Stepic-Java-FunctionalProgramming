import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Tests {
    @Test
    public void bifun() {
        Assert.assertEquals((Object) 0L, Main.bifun.apply(0L, 1L));
        Assert.assertEquals((Object) 2L, Main.bifun.apply(2L, 2L));
        Assert.assertEquals((Object) 24L, Main.bifun.apply(1L, 4L));
        Assert.assertEquals((Object) 54486432000L, Main.bifun.apply(5L, 15L));


    }

    @Test
    public void fun() {
        List<String> list1 = Arrays.asList(new String[]{"java", "scala", "java", "clojure", "clojure"});
        List<String> list2 = Arrays.asList(new String[]{"clojure", "java", "scala"});
        Assert.assertEquals(list2, Main.fun.apply(list1));
    }

    @Test
    public void Acc() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Assert.assertEquals(Arrays.asList(2,4,6),Main.filter(numbers, number -> number % 2 == 0));
    }


}
