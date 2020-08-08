package com.heller.bytecode;

public class Adder {

    private int a;

    public int add(int b, int c) {
        a = b + c;
        return a;
    }

    public int getResult() {
        return a;
    }

}

/**
    使用 javap -v xxx.class  即可生成对应的字节码文件
 */
