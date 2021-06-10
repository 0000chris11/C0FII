package com.cofii2.stores;

public class IntDString {

    private int index;
    private String string1;
    private String string2;
    //-------------------------------------------------
    public IntDString(int index, String string1, String string2) {
        this.index = index;
        this.string1 = string1;
        this.string2 = string2;
    }
    //-------------------------------------------------
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getString1() {
        return string1;
    }
    public void setString1(String string1) {
        this.string1 = string1;
    }
    public String getString2() {
        return string2;
    }
    public void setString2(String string2) {
        this.string2 = string2;
    }

    
}
