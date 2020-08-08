# A Guide to Java Bytecode Manipulation with ASM
https://www.baeldung.com/java-asm

笔记：
ASM 使用了访问者模式（Vistor）

## ASM API Basics
The ASM API provides two styles of interacting with Java classes for transformation and generation: event-based and tree-based.

1. Event-based API
    This API is heavily based on the Visitor pattern and is similar in feel to the SAX parsing model of processing XML documents. 
    It is comprised, at its core, of the following components:

    - ClassReader – helps to read class files and is the beginning of transforming a class
    - ClassVisitor – provides the methods used to transform the class after reading the raw class files
    - ClassWriter – is used to output the final product of the class transformation

    It's in the ClassVisitor that we have all the visitor methods that we'll use to touch 
    the different components (fields, methods, etc.) of a given Java class. 
    We do this by providing a subclass of ClassVisitor to implement any changes in a given class.
    
    Due to the need to preserve the integrity of the output class concerning Java conventions and the resulting bytecode, this class requires a strict order in which its methods should be called to generate correct output.
    
    The ClassVisitor methods in the event-based API are called in the following order:
    
    ```
    visit
    visitSource?
    visitOuterClass?
    ( visitAnnotation | visitAttribute )*
    ( visitInnerClass | visitField | visitMethod )*
    visitEnd
    ```

2. Tree-based API
    This API is a more object-oriented API and is analogous to the JAXB model of processing XML documents.
    
    It's still based on the event-based API, but it introduces the ClassNode root class. 
    This class serves as the entry point into the class structure.


