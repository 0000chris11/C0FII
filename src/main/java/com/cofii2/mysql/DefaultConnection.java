package com.cofii2.mysql;

public class DefaultConnection extends Connect {

    private static final String URL = "jdbc:mysql://localhost:3306/rootcofig";
    private static final String USER = "root";
    private static final String PASSWORD = "ccfmps00112";

    public DefaultConnection() {
        super(URL, USER, PASSWORD);
    }

}
