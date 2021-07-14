package com.cofii2.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IDataTooLong;
import com.cofii2.myInterfaces.ISQL;
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
    private ISQL isql;
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
    // ----------------------------------------------
    public static final int AFTER = 0;
    public static final int BEFORE = 1;
    private int optionWhere;
    // COLUMN---------------------------------------------------
    private Object defaultValue;
    private boolean nullValue = true;
    private boolean extraValue = false;

    private String constraint = null;

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

        System.out.println("ps: " + ps);
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

    public void setSQLException(ISQL isql) {
        this.isql = isql;
    }

    private boolean update(IUpdates iu, String queryType) throws SQLException {
        try {
            if (queryType.equals("SQL")) {
                ps = con.prepareStatement(sql);
            } else if (queryType.equals("SB")) {
                ps = con.prepareStatement(sb.toString());
            }
            System.out.println("ps update: " + ps);
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
            // isql = null;
            defaultValue = null;
            nullValue = true;
            extraValue = false;
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Data too long for column")) {
                if (idata != null) {
                    idata.exception(e);
                } else {
                    e.printStackTrace();
                }
            } else {
                if (isql != null) {
                    isql.exception(e, null);
                }
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
            sb.append(")");
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
            System.out.println(sql);
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
                    "SELECT t.table_schema, t.table_name, t.constraint_name, k.ORDINAL_POSITION, k.column_name, k.referenced_table_schema, k.REFERENCED_TABLE_NAME, k.REFERENCED_COLUMN_NAME FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name, table_schema, table_name)"
                            + " WHERE (t.constraint_type = 'PRIMARY KEY' OR t.constraint_type = 'FOREIGN KEY') AND (");

            whereSet("t.table_schema", " OR ", databases, false);
            sb.append(")");

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

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // TABLE=================================================
    public boolean renameTable(String oldTable, String toTable) {
        try {
            sql = "RENAME TABLE " + oldTable + " TO " + toTable;
            return update(iu, "SQL");
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

    // COLUMN=============================================
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setNullValue(boolean nullValue) {
        this.nullValue = nullValue;
    }

    public void setExtraValue(boolean addColumnExtra) {
        this.extraValue = addColumnExtra;
    }

    public void setAfterOrBeforeColumn(int optionWhere) {
        this.optionWhere = optionWhere;
    }

    // ------------
    private void defaultAddColumn(String table, String newColumn, String type) {
        sb = new StringBuilder("ALTER TABLE ").append(table).append(" ");
        sb.append("ADD COLUMN").append(" ").append(newColumn).append(" ");
        sb.append(type);// PASS ALSO LENGTH
        sb.append(nullValue ? " " : " NOT NULL");
        if (defaultValue != null) {
            sb.append(" DEFAULT ");
            sb.append(defaultValue instanceof String ? "'" + defaultValue + "'" : defaultValue);
        }
        sb.append(extraValue ? " AUTO_INCREMENT" : "");
    }

    public boolean addColumn(String table, String newColumn, String type) {
        try {
            defaultAddColumn(table, newColumn, type);
            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addColumn(String table, String newColumn, String type, String afterBeforeColumn) {
        try {
            // ALTER TABLE table_name ADD COLUMN col_name type AFTER col_name
            defaultAddColumn(table, newColumn, type);
            sb.append(optionWhere == AFTER ? " AFTER" : " BEFORE").append(" ").append(afterBeforeColumn);

            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dropColumn(String table, String column) {
        try {
            // ALTER TABLE table_name DROP COLUMN col_name
            sb = new StringBuilder("ALTER TABLE ").append(table);
            sb.append(" DROP COLUMN ").append(column);

            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------------------------------
    public boolean renameColumn(String table, String oldColumn, String newColumn) {
        try {
            // ALTER TABLE table_name RENAME COLUMN orig_col_name TO new_col_name;
            sql = "ALTER TABLE " + table + " RENAME COLUMN " + oldColumn + " TO " + newColumn;

            return update(iu, "SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean changeType(String table, String column, String type) {
        try {
            // ALTER TABLE table_name MODIFY COLUMN col_name col_type
            sb = new StringBuilder("ALTER TABLE ").append(table).append(" MODIFY COLUMN ").append(column).append(" ")
                    .append(type);
            sb.append(nullValue ? "" : " NOT NULL");
            sb.append(extraValue ? " AUTO_INCREMENT" : "");

            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addDefaultValue(String table, String column, Object defaultValue) {
        try {
            // ALTER TABLE table_name ALTER col_name SET DEFAULT col_value;
            sql = "ALTER TABLE " + table + " ALTER " + column + " "
                    + (defaultValue != null ? "SET DEFAULT " : "DROP DEFAULT");
            if (defaultValue != null) {
                sql += (defaultValue instanceof String ? "'" + defaultValue.toString() + "'" : defaultValue.toString());
            }

            return update(iu, "SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PK===================================================
    public boolean dropPrimaryKey(String table) {
        try {
            // ALTER TABLE table_name DROP PRIMARY KEY;
            sql = "ALTER TABLE " + table + " DROP PRIMARY KEY";
            return update(iu, "SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPrimaryKey(String table, String... columns) {
        try {
            // ALTER TABLE table_name ADD PRIMARY KEY (col_name);
            sb = new StringBuilder("ALTER TABLE ").append(table).append(" ADD PRIMARY KEY (");
            List<String> cols = Arrays.asList(columns);
            cols.forEach(col -> sb.append(col).append(","));
            sb.deleteCharAt(sb.length() - 1);// TEST
            sb.append(")");
            System.out.println("TEST: " + sb.toString());
            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FK====================================================
    public boolean dropForeignKey(String table, String constraint) {
        try {
            // ALTER TABLE table_name DROP FOREIGN KEY fk_name;
            sb = new StringBuilder("ALTER TABLE ").append(table).append(" DROP FOREIGN KEY ").append(constraint);
            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * for adding a foreign key
     * 
     * @param constraint this key constraint name
     */
    public void setConstraintName(String constraint) {
        this.constraint = constraint;
    }

    public boolean addForeignKey(String table, String[] columns, String tableReference, String[] columnsReference) {
        // ALTER TABLE table_name ADD FOREIGN KEY(column_name) REFERENCES table_name
        // ALTER TABLE table_name ADD CONSTRAINT fk_name FOREIGN KEY(column_name)
        // REFERENCES table_name
        if (columns.length == columnsReference.length) {
            try {
                sb = new StringBuilder("ALTER TABLE ").append(table).append(" ADD ");
                sb.append(constraint != null ? "CONSTRAINT " + constraint : "");
                sb.append(" FOREIGN KEY(");
                Arrays.asList(columns).forEach(c -> sb.append(c).append(","));
                sb.deleteCharAt(sb.length() - 1).append(")");

                sb.append(" REFERENCES ").append(tableReference).append("(");
                Arrays.asList(columnsReference).forEach(cr -> sb.append(cr).append(","));
                sb.deleteCharAt(sb.length() - 1).append(")");

                System.out.println(sb.toString());
                return update(iu, "SB");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    // ROW==================================================
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

    public boolean updateRow(String table, String columnWhere, Object valueWhere, String columnSet, Object valueSet) {
        try {
            sb = new StringBuilder("UPDATE " + table + " SET " + columnSet + " = ");
            if (valueSet instanceof Integer) {
                sb.append(valueSet);
            } else {
                sb.append("'" + valueSet + "'");
            }
            sb.append(" WHERE " + columnWhere + " = ");
            if (valueWhere instanceof Integer) {
                sb.append(valueWhere);
            } else {
                sb.append("'" + valueWhere + "'");
            }

            return update(iu, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update a singel cell where a row match all valuesWhere values
     * 
     * @param table       table in wich the update will happen
     * @param valuesWhere an entire row to match the where values
     * @param column      column to set the new value
     * @param newValue    the set for the cell to be updated
     */
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

                // return update(iu, "SB");
                ps.executeUpdate();
                return true;
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
