package com.min.money;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        int from =3;
//        int to = 6;
//        Random random = new Random();
//        System.out.println(from + random.nextInt(to - from + 1));
//        sleepRandomSecond(5);
//        sleepRandomSecond(5,10);

//        Set<Integer> hasClickSet = new HashSet<>();
//        hasClickSet.add(1);
//        System.out.println(hasClickSet.contains(2));
        String s = "1我们23我们";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }

    }

    /**
     * 随机休眠
     */
    public static void sleepRandomSecond(int from) {
        long coreSecond = from * 1000;
        long randomSecond = 1 * 1000;
        System.out.println(coreSecond + (int) (Math.random() * randomSecond));
    }

    /**
     * 随机休眠
     */
    public static void sleepRandomSecond(int from, int to) {
        long coreSecond = from * 1000;
        long randomSecond = (to - from) * 1000;
        if (randomSecond < 0) {
            randomSecond = 0;
        }
        System.out.println(coreSecond + (int) (Math.random() * randomSecond));
    }
}