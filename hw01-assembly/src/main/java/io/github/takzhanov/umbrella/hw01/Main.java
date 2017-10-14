package io.github.takzhanov.umbrella.hw01;

import com.google.common.base.MoreObjects;

public class Main {
    public static void main(String[] args) {
        final String s = "This project hw01-assembly";
        MoreObjects.toStringHelper(s);
        System.out.println(s);
    }
}
