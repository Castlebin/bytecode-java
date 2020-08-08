package com.heller.bytecode;

import java.util.function.Consumer;

public class TestLambda {
    public static void main(String[] args) {
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("testStrForLambda");
    }
}
