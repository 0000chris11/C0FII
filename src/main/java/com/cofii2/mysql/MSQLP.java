package com.cofii2.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cofii2.myInterfaces.IActions;

public class MSQLP {

    private Connection con;
    private String sql;
    private PreparedStatement ps;
    private ResultSet rs;

    public MSQLP(Connect connect) {
        try {
            con = DriverManager.getConnection(connect.URLConnection, connect.User, connect.Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void query(IActions ac) throws SQLException{
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (ac != null) {
            ac.beforeQuery();
            int row = 0;
            boolean rsValue = false;
            while (rs.next()) {
                row++;
                rsValue = true;
                ac.setData(rs, row);
            }
            ac.afterQuery(sql, rsValue);
        }else{
            throw new NullPointerException("IAction can't be null");
        }
    }

    public void selectUsers(IActions ac){
        try {
            sql = "SELECT USER FROM mysql.user";
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }
}
