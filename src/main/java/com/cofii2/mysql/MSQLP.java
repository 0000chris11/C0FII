package com.cofii2.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IDataTooLong;
import com.cofii2.myInterfaces.ISQL;
import com.cofii2.myInterfaces.IUpdates;
import com.cofii2.mysql.enums.QueryResult;
import com.cofii2.mysql.interfaces.IConnectionException;
import com.cofii2.mysql.interfaces.IQueryResult;
import com.cofii2.mysql.interfaces.IResultAndException;
import com.cofii2.mysql.store.KeyPassword;
import com.cofii2.stores.CC;

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
    // private ExceptionAction exceptionAction;DELETE & CHANGE THE NAME OF ISQL FOR
    // THIS
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
    private boolean insertIgnore = false;

    private boolean batchIgnore = false;

    private String constraint = null;
    // ROW--------------------------------------
    private KeyPassword keyPassword;

    // CONSTRUCTORS-------------------------------------------------------
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

    // QUERYS/UPDATES DEFAULT ACTION-----------------------------------
    private void queryIAction(IActions ac, int sqlOp) throws SQLException {
        if (sqlOp == SQL) {
            ps = con.prepareStatement(sql);
        } else if (sqlOp == STRING_BUILDER) {
            ps = con.prepareStatement(sb.toString());
        }

        System.out.println(CC.YELLOW + "ps: " + ps + CC.RESET);
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

            ac = null;
        }
    }

    private void queryQRAction(QueryResult qr, int sqlOp) throws SQLException {
        if (sqlOp == SQL) {
            ps = con.prepareStatement(sql);
        } else if (sqlOp == STRING_BUILDER) {
            ps = con.prepareStatement(sb.toString());
        }

        System.out.println(CC.YELLOW + "ps: " + ps + CC.RESET);
        rs = ps.executeQuery();
        qr.setValue(QueryResult.VALUES);
        qr.setResultSet(rs);
    }

    private void queryRSAction(RSAction rsa, int sqlOp) throws SQLException {
        if (sqlOp == SQL) {
            ps = con.prepareStatement(sql);
        } else if (sqlOp == STRING_BUILDER) {
            ps = con.prepareStatement(sb.toString());
        }

        System.out.println(CC.YELLOW + "ps: " + ps + CC.RESET);
        rs = ps.executeQuery();
        if (rsa != null) {
            rsa.actionRS(rs);
        }
    }

    private void query4tAttemp(IResultAndException irae, int sqlOp) {
        try {
            if (sqlOp == SQL) {
                ps = con.prepareStatement(sql);
            } else if (sqlOp == STRING_BUILDER) {
                ps = con.prepareStatement(sb.toString());
            }

            System.out.println(CC.YELLOW + "ps: " + ps + CC.RESET);
            rs = ps.executeQuery();

            int row = 0;
            boolean rsValue = false;
            while (rs.next()) {
                row++;
                rsValue = true;
                irae.action(rs, true, null);
            }

            if (!rsValue) {
                irae.action(null, false, null);
            }
        } catch (SQLException ex) {
            try {
                irae.action(null, false, ex);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            } else if (queryType.equals("SB")) {
                ps = con.prepareStatement(sb.toString());
            }
            // System.out.println("ps update: " + ps);
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

    // SETTERS----------------------------------
    public void setSQLException(ISQL isql) {
        this.isql = isql;
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

    // RE-USABLE METHODS---------------------------
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

    // ========================================================================
    // QUERYS--------------------------------------------------
    public void selectUsers(IActions ac) {
        try {
            sql = "SELECT USER FROM mysql.user";
            queryIAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);

        }
    }

    public void selectDatabases(IActions ac) {
        try {
            sql = "SHOW DATABASES";
            queryIAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectTables(IActions ac) {
        try {
            sql = "SHOW TABLES";
            queryIAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectTables(IResultAndException irae) {
        sql = "SHOW TABLES";
        query4tAttemp(irae, SQL);
        try {
            irae.action(rs, false, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // ROWS------------------------------------
    public void selectData(String table, IActions ac) {
        try {
            sql = "SELECT * FROM " + table;
            queryIAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    public void selectData(String table, IQueryResult iqr) {
        QueryResult qr = new QueryResult();
        try {
            sql = "SELECT * FROM " + table;
            queryQRAction(qr, SQL);
        } catch (SQLException e) {
            qr.setValue(QueryResult.EXCEPTION);
            qr.setSqlException(e);
        } finally {
            iqr.queryAction(qr);
        }
    }

    public void selectData(String table, IResultAndException irae) {
        sql = "SELECT * FROM " + table;
        query4tAttemp(irae, SQL);
    }

    public void selectDataWhere(String table, String column, Object valueWhere, IActions ac) {
        try {
            sql = "SELECT * FROM " + table + " WHERE " + column + " = ";
            if (valueWhere instanceof String) {
                sql += " \"" + valueWhere.toString() + "\"";
            } else if (valueWhere instanceof Integer) {
                sql += valueWhere.toString();
            }

            queryIAction(ac, SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectDataWhere(String table, String column, Object valueWhere, IQueryResult iqr) {
        QueryResult qr = new QueryResult();
        try {
            sql = "SELECT * FROM " + table + " WHERE " + column + " = ";
            if (valueWhere instanceof String) {
                sql += " \"" + valueWhere.toString() + "\"";
            } else if (valueWhere instanceof Integer) {
                sql += valueWhere.toString();
            }

            queryQRAction(qr, SQL);
        } catch (SQLException e) {
            qr.setValue(QueryResult.EXCEPTION);
            qr.setSqlException(e);
        } finally {
            iqr.queryAction(qr);
        }
    }

    public void selectDataWhere(String table, String columnWhere, Object valueWhere, IResultAndException irae) {
        sql = "SELECT * FROM " + table + " WHERE " + columnWhere + " = ";
        if (valueWhere instanceof String) {
            sql += " \"" + valueWhere.toString() + "\"";
        } else if (valueWhere instanceof Integer) {
            sql += valueWhere.toString();
        }

        query4tAttemp(irae, SQL);

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

    /**
     * Select an undetermine amount values from the given table and column
     * 
     * @param table        table to search on
     * @param columnSelect column to pick the values from
     * @param columnWhere  column where
     * @param valueWhere   value where
     * @return values that match this query
     */
    public Object[] selectValues(String table, String columnSelect, String columnWhere, Object valueWhere) {
        try {
            sb = new StringBuilder("SELECT ").append(columnSelect).append(" FROM ").append(table);
            sb.append(" WHERE ").append(columnWhere).append(" = ?");

            ps = con.prepareStatement(sb.toString());
            if (valueWhere instanceof String) {
                ps.setString(1, valueWhere.toString());
            } else if (valueWhere instanceof Integer) {
                ps.setInt(1, (int) valueWhere);
            }

            rs = ps.executeQuery();
            int columnType = rs.getMetaData().getColumnType(1);
            List<Object> returnValue = new ArrayList<>();
            while (rs.next()) {
                if (columnType == Types.INTEGER) {
                    returnValue.add(rs.getInt(1));
                } else if (columnType == Types.CHAR || columnType == Types.VARCHAR) {
                    returnValue.add(rs.getString(1));
                }
            }

            queryIAction(null, SQL);
            return returnValue.toArray(new Object[returnValue.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0];
        }
    }

    public Object[] selectValues(String table, String columnSelect, int limit) {
        try {
            sb = new StringBuilder();
            sb.append("SELECT ").append(columnSelect).append(" FROM ").append(table);
            sb.append(" LIMIT ").append(limit);

            ps = con.prepareStatement(sb.toString());
            System.out.println(CC.YELLOW + "ps: " + ps.toString() + CC.RESET);
            ps.execute();

            rs = ps.getResultSet();
            List<Object> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getObject(1));
            }

            return list.toArray(new Object[list.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0];
        }
    }

    // COLUMNS---------------------------------------
    public void selectColumns(String table, IActions ac) {
        try {
            sql = "SHOW COLUMNS FROM " + table;
            queryIAction(ac, SQL);
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
            // System.out.println(sql);
            queryIAction(ac, SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectDistinctColumn(String table, String column, RSAction rsa) {
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
            // System.out.println(sql);
            queryRSAction(rsa, SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectDistinctColumn(String table, String column, IResultAndException irae){
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

        query4tAttemp(irae, SQL);
    }
    // KEYS-------------------------------------------
    public void selectKeysInDatabase(String database, IActions ac) {
        try {
            sql = "SELECT t.table_name, t.constraint_type, k.ORDINAL_POSITION, k.column_name, k.REFERENCED_TABLE_NAME, k.REFERENCED_COLUMN_NAME FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name)"
                    + "WHERE (t.constraint_type='PRIMARY KEY' OR t.constraint_type= 'FOREIGN KEY') "
                    + "AND t.table_schema ='" + database + "' " + "ORDER BY t.table_name";

            queryIAction(ac, SQL);
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

            queryIAction(ac, NONE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // ADD DATABASE ATRIBUTTE DATABASE
    }

    // PASSWORD--------------------------------------
    public boolean selectCorrectPassword(String table, String column, String user, String password) {
        try {
            // SELECT CAST(AES_DECRYPT(column_name, “passw”) as CHAR) FROM table_name
            sb = new StringBuilder("SELECT CAST(AES_DECRYPT(").append(column).append(", ?) AS CHAR) FROM ")
                    .append(table);
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, password);

            rs = ps.executeQuery();

            boolean findUser = false;
            while (rs.next()) {
                String possibleUser = rs.getString(1);
                if (user.equals(possibleUser)) {
                    findUser = true;
                    break;
                }
            }
            return findUser;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // STRING-------------------------------------------
    public void executeQuery(String sql, IActions ac) {
        try {
            this.sql = sql;
            queryIAction(ac, SQL);
        } catch (SQLException e) {
            ac.exception(e, sql);
        }
    }

    // UPDATES================================================================
    // DATABASES-----------------------------------------------
    public void use(String database) {
        try {
            sql = "USE " + database;
            queryIAction(null, SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createDatabase(String database) {
        try {
            sb = new StringBuilder("CREATE DATABASE ");
            sb.append(database);
            return update(null, "SB");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // TABLE-----------------------------------------------
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

    // COLUMN------------------------------------------------
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

                // System.out.println(sb.toString());
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
     * Insert on the given table
     * 
     * @param table  table to insert on
     * @param values the new values for the table (only String and Integer allowed)
     * @return true if query succed it
     */
    public boolean insert(String table, Object[] values) {
        try {
            sb = new StringBuilder();
            sb.append("INSERT ").append(insertIgnore ? "IGNORE INTO " : "INTO ");
            sb.append(table).append(" VALUES(");
            for (int a = 0; a < values.length; a++) {
                if (keyPassword != null) {
                    if (keyPassword.getColumnNumber() == (a + 1)) {
                        sb.append("AES_ENCRYPT(\"");
                        sb.append(keyPassword.getKeyName()).append("\",\"");
                        sb.append(keyPassword.getPassword()).append("\")");
                    } else {
                        sb.append("?");
                    }
                } else {
                    sb.append("?");
                }

                if (a != values.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            ps = con.prepareStatement(sb.toString());
            int keyColumn = -1;
            int forSize = values.length;
            for (int a = 0; a < forSize; a++) {
                if (keyPassword != null) {
                    if (keyPassword.getColumnNumber() == (a + 1)) {
                        keyColumn = a;
                        --forSize;
                        if (a >= forSize) {
                            break;
                        }
                    }
                }

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
            keyPassword = null;
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            insertIgnore = false;
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
                if (isql != null) {
                    isql.exception(e, sb.toString());
                }
                e.printStackTrace();
                return false;
            }
        } else {
            throw new IllegalArgumentException(
                    "C0FII: Both valuesWhere and newValues array need to have the same size");
        }
    }

    public boolean executeStringUpdate(String sql) {
        this.sql = sql;
        try {

            return update(null, "SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //BATCH------------------------------------------
    public void addBatch(){

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
    // GET & SETTERS ------------------------------------

    public KeyPassword getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(KeyPassword keyPassword) {
        this.keyPassword = keyPassword;
    }

    public boolean isInsertIgnore() {
        return insertIgnore;
    }

    public void setInsertIgnore(boolean insertIgnore) {
        this.insertIgnore = insertIgnore;
    }

}
