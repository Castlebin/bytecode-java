package com.heller.asm.demo1;

public class TestCustomClassWriterMain {
    public static void main(String[] args) {
        CustomClassWriter ccw = new CustomClassWriter();
        ccw.publicizeMethod();
    }
}
