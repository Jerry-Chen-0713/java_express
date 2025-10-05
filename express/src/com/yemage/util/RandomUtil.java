package com.yemage.util;

import java.util.Random;


public class RandomUtil {
    private static Random random = new Random();

    public static int getCode() {
        return random.nextInt(900000)+100000;
    }
    
}
