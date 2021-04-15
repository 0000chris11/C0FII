package com.cofii2.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IUpdates;
import com.cofii2.mysql.interfaces.IConnectionException;

public class MSQLP {

    private Connection con;
    private String sql;
    private PreparedStatement ps;

    private CallableStatement cs;
    private StringBuilder sb;

    private ResultSet rs;

    public MSQLP(Connect connect) {
        try {
            con = DriverManager.getConnection(connect.URLConnection, connect.User, connect.Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MSQLP(Connect connect, IConnectionException ic) {
        try {
            con = DriverManager.getConnection(connect.URLConnection, connect.User, connect.Password);
            ic.succes();
        } catch (SQLException e) {
            ic.exception(e);
        }
    }

    // QUERYS
    private void query(IActions ac) throws SQLException {
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
        } else {
            throw new NullPointerException("IAction can't be null");
        }
    }

    private void callQuery(IActions ac) throws SQLException {
        ac.beforeQuery();
        int row = 0;
        boolean rsValue = false;
        while (rs.next()) {
            row++;
            rsValue = true;
            ac.setData(rs, row);
        }
        ac.afterQuery(sql, rsValue);
    }

    public void selectUsers(IActions ac) {
        try {
            sql = "SELECT USER FROM mysql.user";
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectDatabases(IActions ac) {
        try {
            sql = "SHOW DATABASES";
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectTables(IActions ac) {
        try {
            sql = "SHOW TABLES";
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void executeQuery(String sql, IActions ac) {
        try {
            this.sql = sql;
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    // UPDATES
    private boolean update(IUpdates iu) throws SQLException {
        ps = con.prepareStatement(sql);
        int i = ps.executeUpdate();

        if (iu != null) {
            if (i > 0) {
                iu.executeResultRowN();
            } else {
                iu.executeResult0();
            }
        }

        if (i >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean executeUpdate(String sql) {
        this.sql = sql;
        try {
            return update(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // CLOSE
    public void close() {
        try {
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
