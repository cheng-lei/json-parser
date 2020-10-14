package com.cxycds;

/**
 * Created by leicheng on 2020/6/5.
 */
public class Token {

    public static final byte NUMBER = 0;
    public static final byte STRING = 1;
    public static final byte OBJECT = 2;
    public static final byte ARRAY = 3;
    public static final byte TRUE = 4;
    public static final byte FALSE = 5;
    public static final byte NULL = 6;

    public byte type;
    public Object o;

    public Token() {
    }

    public void set(byte type,Object o){
        this.type = type;
        this.o = o;
    }
}
