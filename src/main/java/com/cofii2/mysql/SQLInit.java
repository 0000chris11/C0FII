/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.mysql;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IUpdates;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author C0FII
 */
class SQLInit {
      public Connection con;
      public String sql;
      public Statement stt;
      public PreparedStatement pstt;
      public ResultSet rs;

     SQLInit(Connect msc) {
            try {
                  con = DriverManager.getConnection(
                          msc.URLConnection, msc.User, msc.Password);
            } catch (SQLException ex) {
                  Logger.getLogger(SQLInit.class.getName()).log(Level.SEVERE, null, ex);
            }

      }
      
      public void query(IActions ac) throws SQLException {
            stt = con.createStatement();
            rs = stt.executeQuery(sql);

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
      
      public void update(IUpdates iu) throws SQLException{
            stt = con.createStatement();
            int i = stt.executeUpdate(sql);

            if (iu != null) {
                  if (i > 0) {
                        iu.executeResultRowN();
                  } else {
                        iu.executeResult0();
                  }
            }
      }
      
      public Connection getConnection() {
            return con;
      }
}
