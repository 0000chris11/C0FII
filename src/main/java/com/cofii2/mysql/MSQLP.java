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

    //CALLABLE
    /**
     * pickCall method calls and existing procedure in the database 
     * pass in the constructor of this class
     * 
     * @param name name of the procedure
     * @param procedureName parameters of the procedure
     * @return return false if is an update or true if CallableStatement return a ResultTest
     */
    public boolean pickCall(String name, Object... procedureName){
        boolean returnValue = false;
        try{
            //BUILDING CALLABLE STATEMENT
            sql = "{CALL " + name + "(";
            StringBuilder sb = new StringBuilder(sql);
            int length = procedureName.length;
            if(length != 0){
                for(int a = 0; a < length; a++){
                    sb.append("?");
                    if(a != length - 1){
                        sb.append(", ");
                    }
                }
                sb.append(")}");
            }else{
                sb.append(")}");
            }
            //SET THE CALLABLESTATEMENT
            
            CallableStatement cs = con.prepareCall(sb.toString());
            for(int a = 0; a < length; a++){
                if(procedureName[a] instanceof String){
                    cs.setString((a + 1), procedureName[a].toString());
                }else if(procedureName[a] instanceof Integer){
                    cs.setInt((a + 1), (Integer) procedureName[a]);
                }
            }
            returnValue = cs.execute();
            cs.close();

        }catch(SQLException ex){
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
