package com.cofii2.mysql.store;

public class CallParam {
    
    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int INOUT = 2;

    private Object name;
    private int paramType;

    public CallParam(Object name, int paramType) {
        this.name = name;
        this.paramType = paramType;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }

    
}
