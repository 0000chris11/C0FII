package com.cofii2.mysql;

public class DefaultConnection extends Connect {

    private static final String CONNECTION_PROPS = "?allowPublicKeyRetrieval=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/rootconfig" + CONNECTION_PROPS;
    private static final String USER_CONNECTION = "root";
    private static final String PASSWORD_CONNECTION = "ccfmps00112";

    public DefaultConnection() {
        super(URL_CONNECTION, USER_CONNECTION, PASSWORD_CONNECTION);
    }

}
