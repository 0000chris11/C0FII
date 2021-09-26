package com.cofii2.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MSQLB {

    private Connection con;
    private String sql;
    private PreparedStatement ps;
    //-----------------------------------------------
    public MSQLB(Connect connect){
        try {
            con = DriverManager.getConnection(connect.URLConnection, connect.User, connect.Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------------
    
}
