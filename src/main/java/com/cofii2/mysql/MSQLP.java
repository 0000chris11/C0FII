package com.cofii2.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.cofii2.methods.MList;
import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.ISQL;
import com.cofii2.myInterfaces.IUpdates;
import com.cofii2.mysql.interfaces.IConnectionException;
import com.cofii2.mysql.store.CallParam;

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

    private StringBuilder builCall(int length) {
        sb = new StringBuilder(sql);
        if (length != 0) {
            for (int a = 0; a < length; a++) {
                sb.append("?");
                if (a != length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")}");
        } else {
            sb.append(")}");
        }
        return sb;
    }

    /**
     * Set all parameters for the procedure
     * 
     * @param length        of the parameters
     * @param procedureName parameters Object (CallParam)
     * @throws SQLException
     * 
     */
    private Object[] prepareCall(int length, CallParam... procedureName) throws SQLException {
        Object[] returnValue = new Object[length];

        cs = con.prepareCall(sb.toString());
        for (int a = 0; a < length; a++) {
            if (procedureName[a].getName() instanceof String) {
                if (procedureName[a].getParamType() == CallParam.IN) {
                    cs.setString((a + 1), procedureName[a].toString());
                } else if (procedureName[a].getParamType() == CallParam.OUT) {
                    cs.registerOutParameter((a + 1), Types.VARCHAR);
                    returnValue[a] = cs.getString((a + 1));// MAY THROW ERROR FOR TRYING TO GET A VALUE INMEDIATALY
                                                           // AFTER SETTING IT
                }

            } else if (procedureName[a].getName() instanceof Integer) {
                if (procedureName[a].getParamType() == CallParam.IN) {
                    cs.setInt((a + 1), (Integer) procedureName[a].getName());
                } else if (procedureName[a].getParamType() == CallParam.OUT) {
                    cs.registerOutParameter((a + 1), Types.INTEGER);
                    returnValue[a] = cs.getInt((a + 1));
                }

            }
        }
        return returnValue;
    }

    // CALLABLE
    /**
     * pickCall method calls and existing procedure in the database pass in the
     * constructor of this class
     * 
     * @param name          name of the procedure
     * @param procedureName parameters of the procedure (supported only String and
     *                      Integers for the moment)
     * @return return false if is an update or true if CallableStatement return a
     *         ResultTest
     */
    public <T extends ISQL> boolean pickCall(String name, T action, CallParam... procedureName) {
        boolean returnValue = false;
        int length = procedureName.length;
        try {
            // BUILDING CALLABLE STATEMENT
            sql = "{CALL " + name + "(";
            builCall(length);
            // SET THE CALLABLESTATEMENT
            Object[] outParameters = prepareCall(length, procedureName);
            returnValue = cs.execute();
            if (returnValue) {
                IActions ac = (IActions) action;
                if (MList.isThisArrayEmpty(outParameters)) {
                    rs = cs.getResultSet();
                    callQuery(ac);
                } else {
                    ac.getOutParameters(outParameters);
                }

            } else {
                IUpdates iu = (IUpdates) action;
                iu.callUpdate();
            }
            cs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            returnValue = false;
        }
        return returnValue;
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
