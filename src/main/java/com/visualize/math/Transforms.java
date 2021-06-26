package com.visualize.math;

public class Transforms {

    public static double melTrans(double freq) {
        return 1125 * Math.log(1 + freq / 700.0); // or 2595 * log10(1 + f / 700)
    }

    public static double iMelTrans(double freq) {
        return 700 * (Math.pow(Math.E, freq / 1125.0) - 1); // or 700 * (10^(f / 2595) - 1)
    }

}
