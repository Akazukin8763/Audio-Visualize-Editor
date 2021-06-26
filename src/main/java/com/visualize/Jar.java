package com.visualize;

public class Jar {

    public static String getJarPath() {
        return new java.io.File(Jar.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
    }

}
