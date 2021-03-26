/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.mysql;

import com.cofii2.myInterfaces.IActions;
import com.cofii2.stores.DString;
import com.cofii2.stores.DStringObject;
import com.cofii2.stores.StringObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author C0FII
 */
public class MSQL1 extends SQLInit {

      private ArrayList<String> listC = new ArrayList<String>();
      private ArrayList<DString> listDC = new ArrayList<DString>();

      private String table;
      private DString tableD;

      private int join = -1;
      public static final int JOIN = 0;
      public static final int LEFT_JOIN = 1;
      public static final int RIGHT_JOIN = 2;
      private String tableJ;
      private DString tableJD;

      private DString match1;
      private DString match2;
      private String[] matchs;

      private StringObject[] where;
      private DStringObject[] whereD;

      private String order;
      private DString orderD;
      
      public MSQL1(Connect msc){
            super(msc);
      }

      public MSQL1(String table, Connect msc) {
            super(msc);
            this.table = table;

      }

      public MSQL1(DString table, Connect msc) {
            super(msc);
            this.tableD = table;
      }

      //+++++++++++++++++++++++++++++++++++++++++++
      public void addColumn(String column) {
            if (listDC.isEmpty()) {
                  listC.add(column);
            } else {
                  throw new IllegalArgumentException("Only adding to one type of list is allowed");
            }
      }

      public void addColumn(DString column) {
            if (listC.isEmpty()) {
                  listDC.add(column);
            } else {
                  throw new IllegalArgumentException("Only adding to one type of list is allowed");
            }
      }

      public void addColumns(String[] columns) {
            if (listDC.isEmpty()) {
                  for (String x : columns) {
                        listC.add(x);
                  }
            }
      }

      public void addColumns(DString[] columns) {
            if (listC.isEmpty()) {
                  for (DString x : columns) {
                        listDC.add(x);
                  }
            }
      }

      public void setJoin(int join, String tableJ) {
            this.join = join;
            this.tableJ = tableJ;
      }

      public void setJoin(int join, DString tableJ) {
            this.join = join;
            this.tableJD = tableJ;
      }

      public void setOn(DString match1, DString match2) {
            this.match1 = match1;
            this.match2 = match2;
      }

      public void setUsing(String[] matchs) {
            this.matchs = matchs;
      }

      public void setWhere(StringObject where) {
            this.where = new StringObject[]{where};
      }

      public void setWheres(StringObject[] wheres) {
            this.where = wheres;
      }

      public void setWhere(DStringObject whereD) {
            this.whereD = new DStringObject[]{whereD};
      }

      public void setWhere(DStringObject[] whereDs) {
            this.whereD = whereDs;
      }

      public void setOrder(String order) {
            this.order = order;
      }

      public void setOrder(DString order) {
            this.orderD = order;
      }

      //+++++++++++++++++++++++++++++++++++++++++++
      public void select(IActions ac) {
            try {
                  sql = "SELECT ";
                  //+++++++++++++++++++++++++++++++
                  columnNames();
                  //+++++++++++++++++++++++++++++++
                  if (table != null) {
                        sql += " FROM " + table;
                  } else {
                        sql += " FROM " + tableD.string1 + "." + tableD.string2;
                  }
                  //+++++++++++++++++++++++++++++++
                  join();
                  //+++++++++++++++++++++++++++++++
                  onUsing();
                  //+++++++++++++++++++++++++++++++
                  where();
                  //+++++++++++++++++++++++++++++++
                  if (order != null) {
                        sql += " ORDER BY " + order;
                  } else if (orderD != null) {
                        sql += " ORDER BY " + orderD.string1 + "." + orderD.string2;
                  }
                  //+++++++++++++++++++++++++++++++
                  query(ac);
            } catch (SQLException ex) {
                  ac.exception(ex, sql);
            }
      }

      public void select(String query, IActions ac) {
            try {
                  sql = query;
                  query(ac);
            }catch(SQLException ex){
                  ac.exception(ex, sql);
            }
      }

      public void selectTEST() {
            sql = "SELECT ";
            //+++++++++++++++++++++++++++++++
            columnNames();
            //+++++++++++++++++++++++++++++++
            if (table != null) {
                  sql += " FROM " + table;
            } else {
                  sql += " FROM " + tableD.string1 + "." + tableD.string2;
            }
            //+++++++++++++++++++++++++++++++
            join();
            //+++++++++++++++++++++++++++++++
            onUsing();
            //+++++++++++++++++++++++++++++++
            where();
            //+++++++++++++++++++++++++++++++
            if (order != null) {
                  sql += " ORDER BY " + order;
            } else if (orderD != null) {
                  sql += " ORDER BY " + orderD.string1 + "." + orderD.string2;
            }
            //+++++++++++++++++++++++++++++++
            System.out.println(sql);

      }
      //+++++++++++++++++++++++++++++++++++++++++++

      private void columnNames() {
            int columnSisze = listC.size();
            int columnSizeD = listDC.size();
            if (columnSisze != 0) {
                  for (int a = 0; a < columnSisze; a++) {
                        sql += listC.get(a);

                        if (a != listC.size() - 1) {
                              sql += ", ";
                        }
                  }
            } else if (columnSizeD != 0) {
                  for (int a = 0; a < columnSizeD; a++) {
                        sql += listDC.get(a).string1 + "." + listDC.get(a).string2;

                        if (a != listDC.size() - 1) {
                              sql += ", ";
                        }
                  }
            } else {
                  sql += "*";
            }
      }

      private void where() {
            if (where != null) {
                  sql += " WHERE ";
                  for (int a = 0; a < where.length; a++) {
                        sql += where[a].string + " = ";
                        if (where[a].object instanceof String) {
                              sql += "\"" + where[a].object.toString() + "\"";
                        } else {
                              sql += where[a].object;
                        }

                        if (a != where.length - 1) {
                              sql += " AND ";
                        }
                  }
            } else if (whereD != null) {
                  sql += " WHERE ";
                  for (int a = 0; a < whereD.length; a++) {
                        sql += whereD[a].string1 + "." + whereD[a].string2 + " = ";
                        if (whereD[a].object instanceof String) {
                              sql += "\"" + whereD[a].object.toString() + "\"";
                        } else {
                              sql += whereD[a].object;
                        }

                        if (a != whereD.length - 1) {
                              sql += " AND ";
                        }
                  }
            }
      }

      private void join() {
            if (join == JOIN) {
                  sql += " JOIN ";
            } else if (join == LEFT_JOIN) {
                  sql += " LEFT JOIN ";
            } else if (join == RIGHT_JOIN) {
                  sql += " RIGHT JOIN ";
            }
            if (tableJ != null) {
                  sql += tableJ;
            } else if (tableJD != null) {
                  sql += tableJD.string1 + "." + tableJD.string2;
            }
      }

      private void onUsing() {
            if (match1 != null && match2 != null) {
                  sql += " ON " + match1.string1 + "." + match1.string2
                          + " = " + match2.string2 + "." + match2.string2;
            } else if (matchs != null) {
                  sql += " USING(";
                  for (int a = 0; a < matchs.length; a++) {
                        sql += matchs[a];
                        if (a != matchs.length - 1) {
                              sql += ", ";
                        }
                  }
            }
      }
}
