package com.northcoders.record_shop_api_v2.model;

import java.util.Arrays;

public enum Genre {
    ROCK,
    POP,
    RAP,
    ELECTRONIC,
    JAZZ,
    METAL,
    ALTERNATIVE,
    CLASSICAL,
    SOUL,
    HIPHOP;

    public static boolean isValid(String name) {
        return Arrays.stream(values()).anyMatch(e -> e.name().equals(name));
    }
}
