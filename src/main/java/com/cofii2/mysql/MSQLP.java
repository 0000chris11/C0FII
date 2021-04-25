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

    private IUpdates iu;

    public MSQLP(String url) {
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

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

    public void selectColumns(String table, IActions ac) {
        try {
            sql = "SHOW COLUMNS FROM " + table;
            query(ac);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectData(String table, IActions ac) {
        try {
            sql = "SELECT * FROM " + table;
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
    private void update(IUpdates iu, String queryType) throws SQLException {
        if (queryType.equals("SQL")) {
            ps = con.prepareStatement(sql);
        }  

        int i = ps.executeUpdate();

        if (iu != null) {
            if (i > 0) {
                iu.executeResultRowN();
            } else {
                iu.executeResult0();
            }
        }
        sb = null;
    }

    /**
     * Set the IUpdate for the next update statement
     * 
     * @param iu IUpdates interface required
     */
    private void setIUpdates(IUpdates iu) {
        this.iu = iu;
    }

    /**
     * Insert on the given table
     * 
     * @param table  table to insert on
     * @param values the new values for the table (only String and Integer allowed)
     */
    public boolean insert(String table, Object[] values) {
        try {
            sb = new StringBuilder("INSERT INTO " + table + " VALUES(");
            for (int a = 0; a < values.length; a++) {
                sb.append("?");
                if (a != values.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            ps = con.prepareStatement(sb.toString());
            for (int a = 0; a < values.length; a++) {
                if (values[a] != null) {
                    if (values[a] instanceof String) {
                        ps.setString((a + 1), values[a].toString());
                    } else if (values[a] instanceof Integer) {
                        ps.setInt((a + 1), (int) values[a]);
                    } else {
                        throw new IllegalArgumentException("C0FII: Only String and Integer");
                    }
                } else {
                    ps.setObject((a + 1), null);
                }
            }

            update(iu, "SB");
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a row from a table
     * 
     * @param table      table where the row will be deleted
     * @param column     Where this column...
     * @param valueWhere where the value match on the given column
     * @return true if row has been deleted succesfully
     */
    public boolean deleteRow(String table, String column, Object valueWhere) {
        try {
            sql = "DELETE FROM " + table + " WHERE " + column + " = ";
            if (valueWhere instanceof String) {
                sql += "\"" + valueWhere.toString() + "\"";
            } else if (valueWhere instanceof Integer) {
                sql += (int) valueWhere;
            }

            update(iu, "SQL");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRow(String table, Object[] valuesWhere) {
        try {
            sql = "SHOW COLUMNS FROM " + table;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int length = valuesWhere.length;
            sb = new StringBuilder("DELETE FROM " + table + " WHERE ");
            while (rs.next()) {
                String column = rs.getString(1);
                sb.append(column + " = ?");
                if (length != rs.getRow()) {
                    sb.append(" AND ");
                }
            }
            System.out.println("TEST AT DELETEROW SQL: " + sb.toString());
            ps = con.prepareStatement(sb.toString());
            for (int a = 0; a < length; a++) {
                if (valuesWhere[a] != null) {
                    if (valuesWhere[a] instanceof String) {
                        ps.setString((a + 1), valuesWhere[a].toString());
                    } else if (valuesWhere[a] instanceof Integer) {
                        ps.setInt((a + 1), (int) valuesWhere[a]);
                    } else {
                        throw new IllegalArgumentException("C0FII: Only String or Integer allowed");
                    }
                } else {
                    ps.setObject((a + 1), null);
                }
            }

            update(iu, "SB");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean executeUpdate(String sql) {
        this.sql = sql;
        try {
            update(null, "SQL");
            return true;
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
