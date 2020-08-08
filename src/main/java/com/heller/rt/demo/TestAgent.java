package com.heller.rt.demo;

import java.lang.instrument.Instrumentation;

public class TestAgent {
    /**
     * 定义Agent，并在其中实现AgentMain方法
     * 该方法将作为 Agent 的入口
     */
    public static void agentmain(String args, Instrumentation inst) {
        //指定我们自己定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new TestTransformer(), true);
        try {
            //重定义类并载入新的字节码
            inst.retransformClasses(Base.class);
            System.out.println("Agent Load Done.");
        } catch (Exception e) {
            System.out.println("agent load failed!");
        }
    }
}
