package com.cofii2.mysql.store;

public class KeyPassword {
    
    private int columnNumber;
    private String keyName;
    private String password;

    public KeyPassword(int columnNumber, String keyName, String password) {
        this.columnNumber = columnNumber;
        this.keyName = keyName;
        this.password = password;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
