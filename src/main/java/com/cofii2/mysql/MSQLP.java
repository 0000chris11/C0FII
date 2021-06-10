package com.cofii2.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IDataTooLong;
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
    private IDataTooLong idata;
    // QUERY ACTION OPTIONS-----------------------------------------
    private static final int SQL = 0;
    private static final int STRING_BUILDER = 1;
    private static final int NONE = 2;
    // ------------------------------------------
    private static final String ERROR_MESSAGE = "C0FII: Only String or Integer allowed";
    // ORDER BY------------------------------------------
    public static final int DEFAULT_ORDER = 0;
    public static final int ASC_ORDER = 1;
    public static final int DESC_ORDER = 2;
    public static final int MOST_USE_ORDER = 3;

    private int order = DEFAULT_ORDER;

    // -------------------------------------------------------
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

    // QUERYS DEFAULT ACTION-----------------------------------
    private void queryAction(IActions ac, int sqlOp) throws SQLException {
        if (sqlOp == SQL) {
            ps = con.prepareStatement(sql);
        } else if (sqlOp == STRING_BUILDER) {
            ps = con.prepareStatement(sb.toString());
        }

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

    private void callQueryAction(IActions ac) throws SQLException {
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

    private boolean update(IUpdates iu, String queryType) throws SQLException {
        try {
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
            iu = null;
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Data too long for column")) {
                if (idata != null) {
                    idata.exception(e);
                } else {
                    e.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Set the IUpdate for the next update statement
     * 
     * @param iu IUpdates interface required
     */
    public void setIUpdates(IUpdates iu) {
        this.iu = iu;
    }

    public void setIDataToLong(IDataTooLong idata) {
        this.idata = idata;
    }

    public void setDistinctOrder(int order) {
        if (order >= 0 && order < 4) {
            this.order = order;
        } else {
            throw new IllegalArgumentException("C0FII: Not supported order");
        }
    }

    private void selectColumnsWhereOnly(String table, int length) {
        try {
            sb = new StringBuilder("SHOW COLUMNS FROM " + table);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String column = rs.getString(1);
                sb.append(column + " = ?");
                if (length != rs.getRow()) {
                    sb.append(" AND ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectColumnOneSetAndWhere(String table, String column, Object newValue, int length) {
        try {
            sql = "SHOW COLUMNS FROM " + table;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            String[] columns = new String[length];
            int c = 0;
            while (rs.next()) {
                columns[c++] = rs.getString(1);
            }

            sb.append(column + " = ? WHERE ");
            for (int a = 0; a < length; a++) {
                sb.append(columns[a] + " = ?");
                if (a != length - 1) {
                    sb.append(" AND ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectColumnSetAndWhere(String table, int length) {
        try {
            sql = "SHOW COLUMNS FROM " + table;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            String[] columns = new String[length];
            int c = 0;
            while (rs.next()) {
                columns[c] = rs.getString(1);
                sb.append(columns[c] + " = ?");
                if (length != rs.getRow()) {
                    sb.append(", ");
                }
                c++;

            }

            sb.append(" WHERE ");
            for (int a = 0; a < length; a++) {
                sb.append(columns[a] + " = ?");
                if (a != length - 1) {
                    sb.append(" AND ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void whereSet(String sameColumn, String concat, Object[] columns, boolean sql) throws SQLException {
        if (!sql) {
            for (int a = 0; a < columns.length; a++) {
                sb.append(sameColumn + " = ?");
                if (a != columns.length - 1) {
                    sb.append(concat);
                }
            }

            ps = con.prepareStatement(sb.toString());
            for (int a = 0; a < columns.length; a++) {
                if (columns[a] != null) {
                    if (columns[a] instanceof String) {
                        ps.setString((a + 1), columns[a].toString());
                    } else if (columns[a] instanceof Integer) {
                        ps.setInt((a + 1), (int) columns[a]);
                    } else {
                        throw new IllegalArgumentException("C0FII: Only String and Integer");
                    }
                } else {
                    ps.setObject((a + 1), null);
                }
            }
        }
    }

    // QUERYS--------------------------------------------------
    public void selectUsers(IActions ac) {
        try {
            sql = "SELECT USER FROM mysql.user";
            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);

        }
    }

    public void selectDatabases(IActions ac) {
        try {
            sql = "SHOW DATABASES";
            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectTables(IActions ac) {
        try {
            sql = "SHOW TABLES";
            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public ResultSet selectRow(String table, String columnWhere, Object valueWhere) {
        try {
            if (valueWhere instanceof Integer) {
                sql = "SELECT * FROM " + table + " WHERE " + columnWhere + " = " + valueWhere;
            } else {
                sql = "SELECT * FROM " + table + " WHERE " + columnWhere + " = '" + valueWhere + "'";
            }
            System.out.println("selectRow: " + sql);

            Statement st = con.createStatement();
            // queryAction(null, SQL);

            return st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Object[] selectValues(String table, String columnWhere, Object valueWhere) {
        try {
            if (valueWhere instanceof Integer) {
                sql = "SELECT " + columnWhere + " FROM " + table + " WHERE " + columnWhere + " = " + valueWhere;
            } else {
                sql = "SELECT " + columnWhere + " FROM " + table + " WHERE " + columnWhere + " = '" + valueWhere + "'";
            }
            List<Object> returnValue = new ArrayList<>();
            while (rs.next()) {
                if (valueWhere instanceof Integer) {
                    returnValue.add(rs.getInt(1));
                } else if (valueWhere instanceof String) {
                    returnValue.add(rs.getString(1));
                }
            }

            queryAction(null, SQL);
            return returnValue.toArray(new String[returnValue.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0];
        }
    }

    public void selectColumns(String table, IActions ac) {
        try {
            sql = "SHOW COLUMNS FROM " + table;
            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectDistinctColumn(String table, String column, IActions ac) {
        try {
            if (order == DEFAULT_ORDER) {
                sql = "SELECT DISTINCT " + column + " FROM " + table;
            } else if (order == ASC_ORDER) {
                sql = "SELECT DISTINCT " + column + " FROM " + table + " ORDER BY " + column;
            } else if (order == DESC_ORDER) {
                sql = "SELECT DISTINCT " + column + " FROM " + table + " ORDER BY " + column + " DESC";
            } else if (order == MOST_USE_ORDER) {
                sql = "SELECT " + column + " FROM " + table + " GROUP BY(" + column + ") ORDER BY COUNT(" + column
                        + ") DESC";
            }

            queryAction(ac, SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectData(String table, IActions ac) {
        try {
            sql = "SELECT * FROM " + table;
            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectDataWhere(String table, String column, Object valueWhere, IActions ac) {
        try {
            sql = "SELECT * FROM " + table + " WHERE " + column + " = ";
            if (valueWhere instanceof String) {
                sql += " \"" + valueWhere.toString() + "\"";
            } else if (valueWhere instanceof Integer) {
                sql += valueWhere.toString();
            }

            queryAction(ac, SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectKeysInDatabase(String database, IActions ac) {
        try {
            sql = "SELECT t.table_name, t.constraint_type, k.ORDINAL_POSITION, k.column_name, k.REFERENCED_TABLE_NAME, k.REFERENCED_COLUMN_NAME FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name)"
                    + "WHERE (t.constraint_type='PRIMARY KEY' OR t.constraint_type= 'FOREIGN KEY') "
                    + "AND t.table_schema ='" + database + "' " + "ORDER BY t.table_name";

            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectKeys(String[] databases, IActions ac) {
        try {
            sb = new StringBuilder(
                    "SELECT t.table_schema, t.table_name, t.constraint_type, k.ORDINAL_POSITION, k.column_name, k.referenced_table_schema, k.REFERENCED_TABLE_NAME, k.REFERENCED_COLUMN_NAME FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name)"
                            + "WHERE (t.constraint_type='PRIMARY KEY' OR t.constraint_type= 'FOREIGN KEY') AND ");


            whereSet("t.table_schema", " OR ", databases, false);
            queryAction(ac, NONE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // ADD DATABASE ATRIBUTTE DATABASE
    }

    public void executeQuery(String sql, IActions ac) {
        try {
            this.sql = sql;
            queryAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    // UPDATES------------------------------------------------
    /**
     * Insert on the given table
     * 
     * @param table  table to insert on
     * @param values the new values for the table (only String and Integer allowed)
     * @return true if query succed it
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

            return update(iu, "SB");
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTable(String table) {
        try {
            sql = "DROP TABLE " + table;
            return update(iu, "SQL");
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

            return update(iu, "SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRow(String table, Object[] valuesWhere) {
        try {
            int length = valuesWhere.length;
            selectColumnsWhereOnly(table, length);

            sb = new StringBuilder("DELETE FROM " + table + " WHERE ");

            ps = con.prepareStatement(sb.toString());
            for (int a = 0; a < length; a++) {
                if (valuesWhere[a] != null) {
                    if (valuesWhere[a] instanceof String) {
                        ps.setString((a + 1), valuesWhere[a].toString());
                    } else if (valuesWhere[a] instanceof Integer) {
                        ps.setInt((a + 1), (int) valuesWhere[a]);
                    } else {
                        throw new IllegalArgumentException(ERROR_MESSAGE);
                    }
                } else {
                    ps.setObject((a + 1), null);
                }
            }

            return update(iu, "SB");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRow(String table, Object[] valuesWhere, String column, Object newValue) {
        try {
            sb = new StringBuilder("UPDATE " + table + " SET ");

            int length = valuesWhere.length;
            selectColumnOneSetAndWhere(table, column, newValue, length);

            ps = con.prepareStatement(sb.toString());
            for (int a = 0; a < length + 1; a++) {
                if (a == 0) {// SET-----------------
                    if (newValue != null) {
                        if (newValue instanceof String) {
                            ps.setString((a + 1), newValue.toString());
                        } else if (newValue instanceof Integer) {
                            ps.setInt((a + 1), (int) newValue);
                        } else {
                            throw new IllegalArgumentException(ERROR_MESSAGE);
                        }
                    } else {
                        ps.setObject((a + 1), null);
                    }
                } else {// WHERE----------
                    int b = a - 1;
                    if (valuesWhere[b] != null) {
                        if (valuesWhere[b] instanceof String) {
                            ps.setString((a + 1), valuesWhere[b].toString());
                        } else if (valuesWhere[b] instanceof Integer) {
                            ps.setInt((a + 1), (int) valuesWhere[b]);
                        } else {
                            throw new IllegalArgumentException(ERROR_MESSAGE);
                        }
                    } else {
                        ps.setObject((a + 1), null);
                    }
                }
            }

            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean updateRow(String table, Object[] valuesWhere, Object[] newValues) {
        int lengthVW = valuesWhere.length;
        int lengthVL = newValues.length;
        if (lengthVW == lengthVL) {
            try {
                sb = new StringBuilder("UPDATE " + table + " SET ");
                selectColumnSetAndWhere(table, lengthVW);

                ps = con.prepareStatement(sb.toString());
                for (int a = 0; a < lengthVL * 2; a++) {
                    if (a <= lengthVL - 1) {// SET---------------
                        if (newValues[a] != null) {
                            if (newValues[a] instanceof String) {
                                ps.setString((a + 1), newValues[a].toString());
                            } else if (newValues[a] instanceof Integer) {
                                ps.setInt((a + 1), (int) newValues[a]);
                            } else {
                                throw new IllegalArgumentException(ERROR_MESSAGE);
                            }
                        } else {
                            ps.setObject((a + 1), null);
                        }
                    } else {// WHERE---------------
                        int b = a - lengthVL;
                        if (valuesWhere[b] != null) {
                            if (valuesWhere[b] instanceof String) {
                                ps.setString((a + 1), valuesWhere[b].toString());
                            } else if (valuesWhere[b] instanceof Integer) {
                                ps.setInt((a + 1), (int) valuesWhere[b]);
                            } else {
                                throw new IllegalArgumentException(ERROR_MESSAGE);
                            }
                        } else {
                            ps.setObject((a + 1), null);
                        }
                    }
                }

                return update(iu, "SB");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            throw new IllegalArgumentException(
                    "C0FII: Both valuesWhere and newValues array need to have the same size");
        }
    }

    public boolean executeUpdate(String sql) {
        this.sql = sql;
        try {

            return update(null, "SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // CLOSE---------------------------------------
    public void close() {
        try {
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
