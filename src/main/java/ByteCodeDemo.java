public class ByteCodeDemo {
    private int a = 1;

    public int add() {
        int b = 2;
        int c = a + b;
        System.out.println(c);
        return c;
    }
}


/**
 使用 javap -v xxx.class  即可生成对应的字节码文件
 */
