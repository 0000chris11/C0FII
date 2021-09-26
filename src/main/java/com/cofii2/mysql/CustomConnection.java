package com.cofii2.mysql;

public class CustomConnection extends Connect {

    public static final String CONNECTION_PROPS = "?allowPublicKeyRetrieval=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String URL_FULL_CONNECTION = "jdbc:mysql://localhost:3306/%DATABASE" + CONNECTION_PROPS;
    private static final String USER_CONNECTION = "root";
    private static final String PASSWORD_CONNECTION = "";

    public CustomConnection(String databaseName) {
        super(URL_FULL_CONNECTION.replace("%DATABASE", databaseName), USER_CONNECTION, PASSWORD_CONNECTION);
    }

}
