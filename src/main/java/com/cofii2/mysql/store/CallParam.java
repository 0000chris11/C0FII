package com.cofii2.mysql.store;

public class CallParam {
    
    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int INOUT = 2;

    private Object name;
    private int paramType;
    private Object returnValue;

    /**
     * By Default this constructor acts as the return Value (OUT Paramater)
     * @param name OUT Parameter
     */
    public CallParam(Object name){
        this.name = name;
        this.paramType = OUT;
    }
    /**
     * Construct a parameter with it's name and parameter type (IN, OUT, INOUT)
     * @param name name of the parameter
     * @param paramType type of the parameter (IN, OUT, INOUT)
     */
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

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    
}
