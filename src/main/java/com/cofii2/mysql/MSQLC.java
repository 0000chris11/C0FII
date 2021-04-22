package com.cofii2.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.PriorityQueue;
import java.util.Queue;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.ISQL;
import com.cofii2.myInterfaces.IUpdates;
import com.cofii2.mysql.interfaces.IConnectionException;

/**
 * Class for the creation of CallableStatement (UNFINISHED)
 * 
 * @author C0FII
 */
public class MSQLC {
    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int INOUT = 2;
    // --------------------------------------------------
    private Connection con;
    private String sql;

    private CallableStatement cs;
    private StringBuilder sb;
    private Queue<Integer> outParametersIndex = new PriorityQueue<>();
    private Object[] outParameters = null;
    private int[] typeParameters;

    private ResultSet rs;

    private IActions ac;
    private IUpdates iu;

    // --------------------------------------------------
    /**
     * Connect to the given Connect class
     * 
     * @param connect the url, user and password
     */
    public MSQLC(Connect connect) {
        try {
            con = DriverManager.getConnection(connect.URLConnection, connect.User, connect.Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MSQLC(Connect connect, IConnectionException ic) {
        try {
            con = DriverManager.getConnection(connect.URLConnection, connect.User, connect.Password);
            ic.succes();
        } catch (SQLException e) {
            ic.exception(e);
        }
    }

    // --------------------------------------------------
    private void builCall(int length, String name) {
        sb = new StringBuilder("{CALL " + name + "(");
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

    }

    /**
     * Set the Call to execute later. Missing INOUT
     * 
     * @param parameters parameters names
     * @throws SQLException
     */
    private void setCall(Object... parameters) throws SQLException {
        cs = con.prepareCall(sb.toString());
        for (int a = 0; a < parameters.length; a++) {
            int index = a + 1;
            if (parameters[a] instanceof String) {
                if (typeParameters[a] == IN) {
                    cs.setString(index, parameters[a].toString());
                } else if (typeParameters[a] == OUT) {
                    cs.registerOutParameter(index, Types.VARCHAR);
                    outParametersIndex.offer(index);
                } else {
                    throw new IllegalArgumentException(
                            "Type parameter \"" + typeParameters[a] + "\" not supported; IN or OUT");
                }

            } else if (parameters[a] instanceof Integer) {
                if (typeParameters[a] == IN) {
                    cs.setInt(index, (int) parameters[a]);
                } else if (typeParameters[a] == OUT) {
                    cs.registerOutParameter(index, Types.INTEGER);
                    outParametersIndex.offer(index);
                } else {
                    throw new IllegalArgumentException(
                            "Type parameter \"" + typeParameters[a] + "\" not supported; IN or OUT");
                }
            } else {
                if (parameters[a] != null) {
                    throw new IllegalArgumentException(
                            "Type not supported \"" + parameters[a].getClass() + "\"; String or Integer");
                } else {
                    throw new IllegalArgumentException("The parameters can't be null");
                }
            }
        }
    }

    private void getOutParameters(Object... parameters) throws SQLException {
        int outPSize = outParametersIndex.size();
        if (outPSize > 0) {
            outParameters = new Object[outPSize];
        }

        int countReturn = 0;
        while (!outParametersIndex.isEmpty()) {
            int index = outParametersIndex.poll();
            if (typeParameters[index - 1] == OUT) {
                Object parameter = parameters[index - 1];
                if (parameter instanceof String) {
                    outParameters[countReturn++] = cs.getString(index);
                } else if (parameter instanceof Integer) {
                    outParameters[countReturn++] = cs.getInt(index);
                } else{
                    throw new IllegalArgumentException("Only String or Integer supported for OUT parameters");
                }
            }else {
                throw new IllegalArgumentException("Only OUT supported for now");
            }
        }
    }

    public void call(String name, Object... parameters) {
        int length = parameters.length;
        try {
            builCall(length, name);
            setCall(parameters);

            cs.execute();

            getOutParameters(parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTypeParamaterOrder(int... typeParameters) {
        this.typeParameters = typeParameters;
    }

    public <T extends ISQL> void setActionCall(T action) {
        if (action instanceof IActions) {
            ac = (IActions) action;
        } else if (action instanceof IUpdates) {
            iu = (IUpdates) action;
        }
    }

    // -----------------------------------------------------------
    public Object[] getOutParameters() {
        return outParameters;
    }
}
