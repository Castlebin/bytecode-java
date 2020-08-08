package com.heller.rt.demo;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * 缺少 attach 包，编译报错的话，手动添加 JDK 中 tools.jar 到编译路径即可
 */
public class Attacher {
    public static void main(String[] args) throws AttachNotSupportedException,
            IOException, AgentLoadException, AgentInitializationException {
        // 传入目标 JVM pid
        VirtualMachine vm = VirtualMachine.attach("17300");
        vm.loadAgent("asm-basic/TestAgent.jar");
    }
}
