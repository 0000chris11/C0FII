package com.cofii2.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IUpdates;

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
    //QUERYS
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

    public void selectDatabases(IActions ac){
        try {
            sql = "SHOW DATABASES";
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectTables(IActions ac){
        try {
            sql = "SHOW TABLES";
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }
    public void executeQuery(String sql, IActions ac){
        try {
            this.sql = sql;
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }
    //UPDATES
    public void executeUpdate(String sql, IUpdates iu){
        //NOT YET IMPLEMENTED
    }
    //CLOSE
    public void close(){
        try {
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
