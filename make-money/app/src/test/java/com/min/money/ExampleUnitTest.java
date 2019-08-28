package com.min.money;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

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

        Set<Integer> hasClickSet = new HashSet<>();
        hasClickSet.add(1);
        System.out.println(hasClickSet.contains(2));
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