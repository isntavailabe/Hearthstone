package client.utils;

import java.util.Random;

public class RandomNumber {
    private static Random random = new Random(System.nanoTime());

    public static int getRandom(int bound) {
        return random.nextInt(bound);
    }
}
