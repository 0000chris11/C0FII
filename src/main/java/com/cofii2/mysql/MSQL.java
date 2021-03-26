/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.mysql;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.myInterfaces.IUpdates;
import com.cofii2.stores.CC;
import com.cofii2.stores.IntString;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author COFII
 */
public class MSQL {

      private Logger logger = Logger.getLogger(MSQL.class.getName());
      private boolean blog = true;

      private Connection con;
      private String sql;
      private Statement stt;
      private PreparedStatement pstt;
      private ResultSet rs;

      public MSQL(String urlConnection, String user, String passw) {
            try {
                  //this.urlConnection = urlConnection;
                  //this.user = user;
                  //this.passw = passw;
                  con = DriverManager.getConnection(
                          urlConnection, user, passw);
            } catch (SQLException ex) {
                  Logger.getLogger(MSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      public MSQL(Connect c) {
            try {
                  //this.urlConnection = urlConnection;
                  //this.user = user;
                  //this.passw = passw;
                  con = DriverManager.getConnection(
                          c.URLConnection, c.User, c.Password);
            } catch (SQLException ex) {
                  Logger.getLogger(MSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public void setLogger(boolean b) {
            blog = b;
      }

      private void logging() {
            if (blog == false) {
                  logger.setLevel(Level.OFF);
            }
      }

      //QUERYS++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      private boolean actionsQuery(IActions ac) {
            if (ac == null) {

                  throw new NullPointerException("IAction can't be null");
            } else {
                  return true;
            }
      }

      private void querySP(ResultSet rs, IActions ac, String query) throws SQLException {
            if (ac != null) {
                  ac.beforeQuery();

                  boolean rsValue = false;
                  int row = 0;
                  while (rs.next()) {
                        row++;
                        rsValue = true;
                        ac.setData(rs, row);
                  }

                  ac.afterQuery(query, rsValue);
            } else {
                  throw new NullPointerException("IAction can't be null");
            }
      }

      private void query(IActions ac) throws SQLException {
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

      //QUERYS+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public void selectDataBases(IActions ac) {
            if (actionsQuery(ac)) {
                  try {
                        sql = "SHOW DATABASES";
                        query(ac);

                  } catch (SQLException ex) {
                        ac.exception(ex, sql);
                  }
            }
      }

      /**
       * SelectTables from <b>Database</b> (SHOW TABLES)
       *
       * @param ac IAction interface required
       */
      public void selectTables(IActions ac) {
            if (actionsQuery(ac)) {
                  logging();
                  logger.fine("MakeCon ++++ SelectTables");
                  try {
                        sql = "SHOW TABLES";
                        query(ac);

                  } catch (SQLException ex) {
                        ac.exception(ex, sql);
                  }

            }
      }

      /**
       * Self Explained selecte a row from a table
       *
       * @param table Table to use
       * @param whereCol column to search on
       * @param whereValue string to match
       * @param ac IAction interface required
       */
      public void selectRowFromTable(String table, String whereCol, Object whereValue, IActions ac) {
            if (actionsQuery(ac)) {
                  try {
                        if (whereValue instanceof String) {
                              sql = "SELECT * FROM " + table + " WHERE " + whereCol + " = \""
                                      + whereValue.toString() + "\"";
                        } else {
                              sql = "SELECT * FROM " + table + " WHERE " + whereCol + " = "
                                      + whereValue;
                        }

                        query(ac);

                  } catch (SQLException ex) {
                        ac.exception(ex, sql);
                  }
            }
      }

      public void selectRowFromTable_TEST(String table, String whereCol, Object whereValue) {
            if (whereValue instanceof String) {
                  sql = "SELECT * FROM " + table + " WHERE " + whereCol + " = \""
                          + whereValue.toString() + "\"";
            } else {
                  sql = "SELECT * FROM " + table + " WHERE " + whereCol + " = "
                          + whereValue;
            }
            System.out.println("TEST selectRowFromTable_TEST: " + sql);
      }

      public void selectRowFromTable(String table, int limit, IActions ac) {
            if (actionsQuery(ac)) {
                  try {
                        sql = "SELECT * FROM " + table + " LIMIT " + limit;
                        query(ac);

                  } catch (SQLException ex) {
                        ac.exception(ex, sql);
                  }
            }
      }

      public Object selectValueFromTable(String table, String col, String colWhere, Object objWhere) {
            Object returnValue = "ERROR";
            try {
                  stt = con.createStatement();
                  if (objWhere instanceof String) {
                        rs = stt.executeQuery("SELECT " + col + " FROM " + table
                                + " WHERE " + colWhere + " = \"" + objWhere.toString() + "\"");
                  } else {
                        rs = stt.executeQuery("SELECT " + col + " FROM " + table
                                + " WHERE " + colWhere + " = " + objWhere);
                  }
                  boolean rsValue = false;
                  while (rs.next()) {
                        rsValue = true;
                        returnValue = rs.getObject(1);
                  }

                  if (rsValue) {
                        return returnValue;
                  } else {
                        return null;
                  }

            } catch (SQLException ex) {
                  ex.printStackTrace();
                  return "EXCEPTION";
            }
      }

      public void selectDistinctColumn(String table, String col, IActions ac) {
            if (actionsQuery(ac)) {
                  try {
                        sql = "SELECT DISTINCT " + col + " FROM " + table + " ORDER BY " + col;
                        query(ac);
                  } catch (SQLException ex) {
                        ac.exception(ex, sql);
                  }
            }
      }

      public void selectDistinctColumns(String table, String[] cols, String orderCol, IActions ac) {
            try {
                  sql = "SELECT DISTINCT ";
                  for (int a = 0; a < cols.length; a++) {
                        sql += cols[a];
                        if (a != cols.length - 1) {
                              sql += ", ";
                        }
                  }

                  sql += " FROM " + table;
                  if (orderCol != null) {
                        sql += " ORDER BY " + orderCol;
                  }
                  query(ac);
            } catch (SQLException ex) {
                  ac.exception(ex, sql);
            }
      }

      /**
       * Select Column ffom a Table
       *
       * @param table Name of the Table
       * @param ac IActions to implement
       */
      public void selectColumns(String table, IActions ac) {
            logging();
            logger.fine("MakeCon ++++ SelectColumns");
            logger.fine("\tTable: " + table);
            try {
                  sql = "SHOW COLUMNS FROM " + table.replaceAll(" ", "_");
                  query(ac);

            } catch (SQLException ex) {
                  ac.exception(ex, sql);

            }
      }

      public void selectIndexs(String table, IActions ac) {
            try {
                  sql = "SHOW INDEXES FROM " + table.replaceAll(" ", "_");
                  query(ac);
            } catch (SQLException ex) {
                  ac.exception(ex, sql);
            }
      }

      public void selectData(String table, IActions ac) {
            logging();
            logger.fine("MakeCon ++++ SelectData");
            logger.fine("\tTable: " + table);

            try {
                  sql = "SELECT * FROM " + table;
                  query(ac);

            } catch (SQLException ex) {
                  ac.exception(ex, sql);
            }
      }

      public void selectData(String table, String colOrder, IActions ac) {
            logging();
            logger.fine("MakeCon ++++ SelectData");
            logger.fine("\tTable: " + table);
            try {
                  sql = "SELECT * FROM " + table + " ORDER BY " + colOrder;
                  query(ac);

            } catch (SQLException ex) {
                  ac.exception(ex, sql);
            }
      }

      public void selectUsers(IActions ac) {
            try {
                  sql = "SELECT USER FROM mysql.user";
                  query(ac);

            } catch (SQLException ex) {
                  ac.exception(ex, sql);
            }
      }

      /**
       * Get the Column Count from a Table
       *
       * @param table Name of the Table
       * @return how many columns
       */
      public int getColumnCount(String table) {
            logging();
            logger.fine("MakeCon ++++ getColumnCount");
            logger.fine("\tTable: " + table);

            int returnValue = -1;
            try {
                  sql = "SELECT COUNT(*) FROM " + table;
                  stt = con.createStatement();
                  rs = stt.executeQuery(sql);

                  while (rs.next()) {
                        returnValue = rs.getInt(1);
                  }
            } catch (SQLException ex) {
                  ex.printStackTrace();
                  returnValue = -1;
            }
            return returnValue;
      }

      public void doesThisTableExist(String table, IActions ac) {
            //boolean rsValues = false;
            sql = "getTables from DatabaseMetaData";
            try {
                  DatabaseMetaData dmd = con.getMetaData();
                  rs = dmd.getTables(null, null, table, null);
                  //ResultSet rs, IActions ac, String query
                  querySP(rs, ac, sql);

            } catch (SQLException ex) {
                  ac.exception(ex, sql);
                  //Logger.getLogger(MSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            //return rsValues;
      }

      public boolean doesThisTableExist(String table) {
            boolean returnValue = false;
            sql = "getTables from DatabaseMetaData";
            try {
                  DatabaseMetaData dmd = con.getMetaData();
                  rs = dmd.getTables(null, null, table, null);
                  String rsTable = "";
                  while (rs.next()) {
                        rsTable = rs.getString(1);
                        System.out.println("TEST MSQL doesThisTableExist RS VALUE: " + rsTable);
                  }

                  if (table.equals(rsTable)) {
                        returnValue = true;
                  }

            } catch (SQLException ex) {
                  ex.printStackTrace();
            }
            return returnValue;
      }

      //UPDATED+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      private void update(IUpdates iu) throws SQLException {
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

      //+++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Create Table using custom conection (by the this Constructor)
       *
       * @param table Name of the Table
       * @param columnsNames Columns Names
       * @param columnsTypes the Types of the columns
       * @param columnsNulls Allow nulls for the columns
       * @param primaryKey which column is the primary key (null for none)
       * @param foreignKey which column is the foreign key (null for none)
       * @param defaults wich columns have defaults Values
       * @param extra Column index in which there is AUTO_INCREMENT OR IDENTITY
       * (0 for none)
       * @param iu IActions Interface required
       *
       *
       *
       */
      public void createTable(String table, String[] columnsNames, String[] columnsTypes, boolean[] columnsNulls,
              String primaryKey, String foreignKey, IntString[] defaults, IntString extra, IUpdates iu) {
            System.out.println(CC.YELLOW + "Create Table" + CC.RESET);
            try {
                  int extraCol = 0;
                  String extraValue = null;
                  if (extra != null) {
                        extraCol = extra.index;
                        extraValue = extra.string;
                  }

                  table = table.replaceAll(" ", "_");

                  sql = "CREATE TABLE IF NOT EXISTS " + table + " (";
                  //colNames.length == types.length == nulls.length
                  for (int a = 0; a < columnsNames.length; a++) {
                        columnsNames[a] = columnsNames[a].replaceAll(" ", "_");
                        sql += columnsNames[a] + " " + columnsTypes[a];

                        if (columnsNulls[a] == false) {
                              sql += " NOT NULL";
                        }
                        if (extraCol == (a + 1) && extraValue != null) {
                              sql += " " + extraValue;
                        }//TEST IF YOU CAN HAVE EXTRA AND DEFAULT AT THE SAME TIME
                        if (a != columnsNames.length - 1) {
                              sql += ", ";
                        }
                  }

                  if (primaryKey != null) {
                        sql += ", PRIMARY KEY(" + primaryKey + ")";
                  }
                  sql += ") ENGINE=INNODB";

                  System.out.println(sql);
                  update(iu);

            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      //NOT FINISH YET!!!!!!!!!!!!!!!!!!!!!!!!!!!
      public void createTable(String table, String[] colNames, String[] types, boolean[] nulls,
              IntString extra, String primaryKey, IntString defaults, IUpdates iu) {
            System.out.println(CC.YELLOW + "Create Table" + CC.RESET);
            try {
                  int extraCol = extra.index;
                  String extraValue = extra.string;
                  if (defaults != null) {
                        int defaultCol = defaults.index;
                        String defaultValue = defaults.string;
                  }
                  table = table.replaceAll(" ", "_");

                  sql = "CREATE TABLE IF NOT EXISTS " + table + " (";
                  //colNames.length == types.length == nulls.length
                  for (int a = 0; a < colNames.length; a++) {
                        colNames[a] = colNames[a].replaceAll(" ", "_");
                        sql += colNames[a] + " " + types[a];

                        if (nulls[a] == false) {
                              sql += " NOT NULL";
                        }
                        if (extraCol == (a + 1) && extraValue != null) {
                              sql += " " + extraValue;
                        }//TEST IF YOU CAN HAVE EXTRA AND DEFAULT AT THE SAME TIME
                        if (a != colNames.length - 1) {
                              sql += ", ";
                        }
                  }

                  if (primaryKey != null) {
                        sql += ", PRIMARY KEY(" + primaryKey + ")";
                  }
                  sql += ") ENGINE=INNODB";

                  System.out.println(sql);
                  update(iu);

            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void deleteTable(String table, IUpdates iu) {
            try {
                  sql = "DROP TABLE " + table;
                  update(iu);
            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void deleteTable_TEST(String table) {
            sql = "DROP TABLE " + table;
            System.out.println("SQL: " + sql);
      }

      public void renameTable(String table, String newTable, IUpdates iu) {
            try {
                  sql = "RENAME TABLE " + table + " TO " + newTable;
                  //RENAME TABLE table_name TO new_table_name
                  update(iu);
            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      /**
       *
       * @param table Table to insert into
       * @param columns which columns are to be inserted on
       * @param newValues values corresponding to each column
       * @param autoCol which column is either auto_increment or identity (0 to
       * ignore)
       * @param iu IUpdates to implement
       */
      public void insert(String table, String[] columns, Object[] newValues, int autoCol, IUpdates iu) {
            System.out.println(CC.YELLOW + "Insert" + CC.RESET);
            System.out.println("\tTable: " + table);
            try {
                  boolean errors = false;
                  sql = "INSERT INTO "
                          + table
                          + " (";

                  if (autoCol > 0) {
                        //IF AUTOCOL IS GREATER THAN 0 THEN THE VALUE (ON NEWVALUES) SHOULD BE NULL
                        if (newValues[autoCol - 1] != null) {//THE COLUMN THAT HAS EXTYPE NEEDS TO HAVE IT'S VALUE NULL
                              errors = true;
                              System.out.println("ArrayStoreException");
                              throw new ArrayStoreException("the value of the column that has"
                                      + "AUTO_INCREMENT or IDENTITY must be NULL");
                        } else {
                              errors = false;
                        }
                  } else {
                        errors = false;
                  }

                  //COLUMNS NAMES+++++++++++++++++++++++
                  if (!errors) {
                        for (int a = 0; a < columns.length; a++) {
                              if ((a + 1) != autoCol) {//WHICH COLUMN DOESN'T HAVE EXTRATYPE AND SHOULDN'T BE IGNORE
                                    sql += columns[a];
                                    if ((a + 1) != columns.length) {//COMA FOR ALL EXCEPT THE LAST ONE
                                          sql += ", ";
                                    }
                              }

                        }

                        sql += ") VALUES (";
                        //VALUES+++++++++++++++++++++++++++++++++
                        for (int a = 0; a < newValues.length; a++) {
                              if ((a + 1) != autoCol) {
                                    if (newValues[a] instanceof String) {
                                          sql += "\"" + newValues[a] + "\"";
                                    } else {
                                          sql += newValues[a];
                                    }
                                    if ((a + 1) != columns.length) {
                                          sql += ", ";
                                    }
                              }
                        }

                        sql += ")";
                        //++++++++++++++++++++++++++++++++
                        System.out.println("INSERT SQL TEST: " + sql);
                        //++++++++++++++++++++++++++++++++++++++++++++
                        update(iu);
                  }
            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void updateRow(String table, String[] colsNames, Object[] newValues,
              String colID, Object valID, IUpdates iu) {
            System.out.println(CC.YELLOW + "MakeCon ++++ UpdateRow" + CC.RESET);

            if (colsNames.length == newValues.length) {
                  try {
                        //UPDATE tablename SET main_photo = 'yes' LIMIT 1;
                        //+++++++++++++++++++++++++++++++++++++++++++++
                        String upd = "UPDATE " + table + " SET ";
                        String end;
                        if (valID instanceof String) {
                              end = " WHERE " + colID + " = \"" + valID + "\"";
                        } else { //INT AND BOOLEAN
                              end = " WHERE " + colID + " = " + valID;
                        }
                        for (int a = 0; a < colsNames.length; a++) {//LOOPS N OF COL(CELL) CHANGED
                              if (a > 0) {
                                    upd += ", ";
                              }

                              if (newValues[a] instanceof String) {
                                    upd += colsNames[a] + " = \"" + newValues[a] + "\"";
                              } else {
                                    upd += colsNames[a] + " = " + newValues[a];
                              }

                        }
                        upd += end;
                        System.out.println("\tUPD: " + upd);
                        //+++++++++++++++++++++++++++++++++++++++++++++
                        update(iu);

                  } catch (SQLException ex) {
                        if (iu == null) {
                              ex.printStackTrace();
                        }
                        iu.exception(ex);
                  }
            } else {
                  System.out.println("\tColumn and newValues don't match");
            }
      }

      public void updateRow(String table, String[] colsNames, Object[] newValues,
              int limit, IUpdates iu) {
            //UPDATE tablename SET main_photo = 'yes' LIMIT 1;
            try {
                  sql = "UPDATE " + table + " SET ";
                  for (int a = 0; a < colsNames.length; a++) {//LOOPS N OF COL(CELL) CHANGED
                        if (a > 0) {
                              sql += ", ";
                        }

                        if (newValues[a] instanceof String) {
                              sql += colsNames[a] + " = \"" + newValues[a] + "\"";
                        } else {
                              sql += colsNames[a] + " = " + newValues[a];
                        }

                  }
                  sql += " LIMIT " + limit;
                  update(iu);

            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void updateRow(String table, String colName, Object newValue,
              String colID, Object valID, IUpdates iu) {
            System.out.println(CC.YELLOW + "MakeCon ++++ UpdateRow[]" + CC.RESET);

            try {
                  /*
                  con = DriverManager.getConnection(
                          urlConnection, user, passw);
                   */
                  //+++++++++++++++++++++++++++++++++++++++++++++

                  if (newValue instanceof String) {
                        sql = "UPDATE " + table + " SET " + colName + " = \"" + newValue + "\"";
                  } else {
                        sql = "UPDATE " + table + " SET " + colName + " = " + newValue + "";
                  }
                  if (valID instanceof String) {
                        sql += " WHERE " + colID + " = \"" + valID + "\"";
                  } else {
                        sql += " WHERE " + colID + " = " + valID;
                  }

                  System.out.println("\tUPD: " + sql);
                  //+++++++++++++++++++++++++++++++++++++++++++++
                  update(iu);

            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void deleteRow(String table, String col, Object where, IUpdates iu) {
            try {
                  sql = "DELETE FROM " + table + " WHERE " + col + " = ";
                  if (where instanceof String) {
                        sql += "\"" + where + "\"";
                  } else {
                        sql += where;
                  }
                  sql += " LIMIT 1";

                  update(iu);

            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void deleteRow(String table, String[] cols, Object[] values, IUpdates iu) {
            try {
                  int cl = cols.length;
                  int vl = values.length;
                  if (cl > 0 && vl > 0) {
                        if (cl == vl) {
                              sql = "DELETE FROM " + table + " WHERE ";
                              for (int a = 0; a < cl; a++) {
                                    if (values[a] instanceof String) {
                                          sql += cols[a] + " = \"" + values[a].toString() + "\"";
                                    } else {
                                          sql += cols[a] + " = " + values[a];
                                    }
                                    if (a != cl - 1) {
                                          sql += " AND ";
                                    }
                              }
                              sql += " LIMIT 1";
                              update(iu);
                        } else {
                              throw new IllegalArgumentException(
                                      "The column and value Array need to have the same length");
                        }
                  } else {
                        throw new IllegalArgumentException(
                                "The column and values list should have at least 1 element each");
                  }
            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void deleteRow_TEST(String table, String[] cols, Object[] values) {
            int cl = cols.length;
            int vl = values.length;
            if (cl > 0 && vl > 0) {
                  if (cl == vl) {
                        sql = "DELETE FROM " + table + " WHERE ";
                        for (int a = 0; a < cl; a++) {
                              if (values[a] instanceof String) {
                                    sql += cols[a] + " = \"" + values[a].toString() + "\"";
                              } else {
                                    sql += cols[a] + " = " + values[a];
                              }
                              if (a != cl - 1) {
                                    sql += " AND ";
                              }
                        }
                        sql += " LIMIT 1";
                        System.out.println("SQL: " + sql);
                  } else {
                        throw new IllegalArgumentException(
                                "The column and value Array need to have the same length");
                  }
            } else {
                  throw new IllegalArgumentException(
                          "The column and values list should have at least 1 element each");
            }
      }

      public void deleteRow(String table, int limit, IUpdates iu) {
            try {
                  sql = "DELETE FROM " + table + " LIMIT " + limit;
                  update(iu);

            } catch (SQLException ex) {
                  if (iu == null) {
                        ex.printStackTrace();
                  }
                  iu.exception(ex);
            }
      }

      public void deleteColumn(String table, String column, IUpdates iu) {
            //ALTER TABLE table_name DROP COLUMN col_name
            try {
                  sql = "ALTER TABLE " + table + " DROP COLUMN " + column;
                  update(iu);
            } catch (SQLException ex) {
                  iu.exception(ex);
                  Logger.getLogger(MSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public Connection getConnection() {
            return con;
      }
}
