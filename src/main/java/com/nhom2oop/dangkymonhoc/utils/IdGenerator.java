package com.nhom2oop.dangkymonhoc.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static String generate(String prefix) {
        return prefix + String.format("%04d", counter.getAndIncrement());
    }
}