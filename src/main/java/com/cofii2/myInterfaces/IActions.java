/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myInterfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author C0FII
 */
public interface IActions {
      public void beforeQuery();
      public void setData(ResultSet rs, int row) throws SQLException;
      public void afterQuery(String query, boolean rsValue);
      public void exception(SQLException ex, String query);
}
