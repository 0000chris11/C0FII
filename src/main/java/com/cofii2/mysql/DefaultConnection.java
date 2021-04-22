package com.cofii2.mysql;

public class DefaultConnection extends Connect {

    public static final String CONNECTION_PROPS_NO_DB = "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String CONNECTION_PROPS = "?allowPublicKeyRetrieval=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String URL_FULL_CONNECTION = "jdbc:mysql://localhost:3306/mysql" + CONNECTION_PROPS;
    public static final String BASIC_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER_CONNECTION = "root";
    private static final String PASSWORD_CONNECTION = null;

    public DefaultConnection() {
        super(URL_FULL_CONNECTION, USER_CONNECTION, PASSWORD_CONNECTION);
        
    }
    
}
