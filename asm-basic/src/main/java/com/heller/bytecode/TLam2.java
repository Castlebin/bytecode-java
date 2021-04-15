package com.heller.bytecode;

import java.util.function.Consumer;
import java.util.function.Function;

public class TLam2 {

    public void accept() {
        Consumer<String> consumer = s -> System.out.println(s);

        Function<String, Long> strToLong = numStr -> Long.parseLong(numStr);
    }

}
