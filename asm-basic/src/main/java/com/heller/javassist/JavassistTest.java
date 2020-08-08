package com.heller.javassist;

import com.heller.asm.demo3.Base;
import javassist.*;

import java.io.IOException;

public class JavassistTest {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("com.heller.asm.demo3.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
        cc.writeFile("asm-basic/target/classes/com/heller/asm/demo3/");
        Base h = (Base)c.newInstance();
        h.process();
    }
}
