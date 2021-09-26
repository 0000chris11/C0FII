/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.mysql;

import com.cofii2.myInterfaces.IUpdates;
import com.cofii2.stores.CC;
import com.cofii2.stores.DInt;
import com.cofii2.stores.IntBoolean;
import com.cofii2.stores.IntString;
import com.cofii2.stores.TString;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author C0FII
 */
public class MSQLCreate extends SQLInit {

      private String table;

      private ArrayList<DInt> listWidth = new ArrayList<>();
      private ArrayList<IntBoolean> listNulls = new ArrayList<>();
      private ArrayList<IntString> listDefaults = new ArrayList<>();

      private String extraType = "";
      private TInt extra = null;

      private ArrayList<String> listPK = new ArrayList<>();
      private ArrayList<TString> listFK = new ArrayList<>();// COLUMN NAME AND TABLE.COLUMN REFERENCE
      // +++++++++++++++++++++++++++++++++++++++++++

      public MSQLCreate(Connect conn) {
            super(conn);
      }

      // +++++++++++++++++++++++++++++++++++++++++++
      public boolean createTable(String table, String[] columnsNames, String[] columnsTypes, IUpdates iu) {
            try {
                  this.table = table;
                  sb = new StringBuilder("CREATE TABLE " + table.replace(" ", "_") + "(");
                  // sql = "CREATE TABLE " + table.replaceAll(" ", "_") + "(";

                  int length = columnsNames.length;
                  int removeTW = 0;
                  int removeDF = 0;
                  int removeNLL = 0;
                  for (int a = 0; a < length; a++) {
                        sb.append(columnsNames[a] + " " + columnsTypes[a]);

                        typeWidths(removeTW, a);
                        defaults(removeDF, a);
                        nulls(removeNLL, a);

                        extra(a);
                        if (a != length - 1) {
                              sb.append(", ");
                        }
                  }

                  primaryKeys();
                  foreignKeys();

                  sb.append(")");
                  System.out.println(CC.CYAN + "\nSQL: " + sb.toString() + CC.RESET);
                  update(iu);

                  return true;
            } catch (SQLException ex) {
                  iu.exception(ex, sb.toString());
                  return false;
            }

      }

      public void use(String database) {
            try {
                sql = "USE " + database;
                update(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      public boolean createTable(String table, String[] columnsNames, String[] columnsTypes) {
            try {
                  table = table.replace(" ", "_");
                  this.table = table;
                  sb = new StringBuilder("CREATE TABLE " + table + "(");
                  // sql = "CREATE TABLE " + table.replaceAll(" ", "_") + "(";

                  int length = columnsNames.length;
                  int removeTW = 0;
                  int removeDF = 0;
                  int removeNLL = 0;
                  for (int a = 0; a < length; a++) {
                        sb.append(columnsNames[a] + " " + columnsTypes[a]);

                        typeWidths(removeTW, a);
                        defaults(removeDF, a);
                        nulls(removeNLL, a);

                        extra(a);
                        if (a != length - 1) {
                              sb.append(", ");
                        }
                  }

                  primaryKeys();
                  foreignKeys();

                  sb.append(")");
                  System.out.println(CC.CYAN + "\nSQL: " + sb.toString() + CC.RESET);
                  update(null);

                  return true;
            } catch (SQLException ex) {
                  ex.printStackTrace();
                  return false;
            }
      }

      // +++++++++++++++++++++++++++++++++++++++++++
      private void typeWidths(int removeTW, int a) {
            if (!listWidth.isEmpty()) {
                  for (DInt x : listWidth) {
                        if (x != null) {
                              int index = x.index1;
                              int width = x.index2;
                              if (index == (a + 1) && width > 0) {
                                    sb.append("(" + width + ")");
                                    break;
                              }
                        }
                  }
                  listWidth.remove(removeTW++);
            }
      }

      private void defaults(int removeNLL, int a) {
            if (!listDefaults.isEmpty()) {
                  for (IntString x : listDefaults) {
                        if (x != null) {
                              int index = x.index;
                              String string = x.string;
                              try {// TEST FOR DEFAULT VALUE HAVING QUOTES
                                    Integer.parseInt(string);
                                    if (index == (a + 1)) {
                                          sb.append(" DEFAULT " + string);
                                          break;
                                    }
                              } catch (NumberFormatException ex) {
                                    if (index == (a + 1)) {
                                          sb.append(" DEFAULT \"" + string + "\"");
                                          break;
                                    }
                              }
                              ;
                        }
                  }
                  listDefaults.remove(removeNLL++);
            }
      }

      private void nulls(int removeNLL, int a) {
            if (!listNulls.isEmpty()) {
                  for (IntBoolean x : listNulls) {
                        int index = x.index;
                        boolean bool = x.bool;
                        if (index == (a + 1) && !bool) {
                              sb.append(" NOT NULL");
                              break;
                        }
                  }
                  listNulls.remove(removeNLL++);
            }
      }

      private void extra(int a) {
            if (extra != null) {
                  int col = extra.index1;
                  if (extraType.equals("AUTO_INCREMENT")) {
                        if (col == a + 1) {
                              sb.append(" AUTO_INCREMENT");
                        }
                  } else if (extraType.equals("IDENTITY")) {
                        int iden1 = extra.index2;
                        int iden2 = extra.index3;
                        if (col == a + 1) {
                              sb.append(" IDENTITY(" + iden1 + ", " + iden2 + ")");
                        }
                  }
            }
      }

      private void primaryKeys() {
            if (!listPK.isEmpty()) {
                  sb.append(", PRIMARY KEY(");
                  for (int a = 0; a < listPK.size(); a++) {
                        sb.append(listPK.get(a));

                        if (a != listPK.size() - 1) {
                              sb.append(", ");
                        }
                  }
                  sb.append(")");
            }
      }

      private void foreignKeys() {
            if (!listFK.isEmpty()) {
                  String databseR = listFK.get(0).string2;
                  String tableR = listFK.get(0).string3;
                  sb.append(", CONSTRAINT fk_" + table + "__" + databseR + "_" + tableR  + " FOREIGN KEY(");
                  // TEST CONSTRAINTS
                  for (int a = 0; a < listFK.size(); a++) {
                        sb.append(listFK.get(a).string1);

                        if (a != listFK.size() - 1) {
                              sb.append(", ");
                        }
                  }
                  sb.append(") REFERENCES " + tableR.substring(0, tableR.indexOf("__")) + "(");
                  sb.append(tableR.replaceAll("[A-Za-z][A-Za-z0-9_]+__([A-Za-z_]+)", "$1"));
                  sb.append(")");
                  /*
                  for (int a = 0; a < listFK.size(); a++) {
                        sb.append(tableR.substring(tableR.indexOf("__" + 2, tableR.length() - 1)));

                        if (a != listFK.size() - 1) {
                              sb.append(", ");
                        }
                  }
                  */
            }
      }

      // +++++++++++++++++++++++++++++++++++++++++++
      public void addTypesWidth(DInt width) {
            listWidth.add(width);
      }

      public void addAllTypesWidths(DInt[] widths) {
            listWidth.clear();
            for (DInt x : widths) {
                  listWidth.add(x);
            }
      }

      public void addAllowsNull(IntBoolean nullColumn) {
            listNulls.add(nullColumn);
      }

      /**
       * Needs to be fix.
       * 
       * @deprecated (Unnecesary IntBollean Array)
       * @param nullsColumns boolean indicating if the column allows null values
       */
      public void addAllAllowsNulls(IntBoolean[] nullsColumns) {
            listNulls.clear();
            for (IntBoolean x : nullsColumns) {
                  listNulls.add(x);
            }
      }

      public void addDefault(IntString defaultt) {
            listDefaults.add(defaultt);
      }

      public void addAllDefaults(IntString[] defaultt) {
            listDefaults.clear();
            for (IntString x : defaultt) {
                  listDefaults.add(x);
            }
      }

      public void setAutoIncrement(int col) {
            extraType = "AUTO_INCREMENT";
            extra = new TInt(col, 0, 0);
      }

      public void setIdentity(int col, DInt idenValues) {
            extraType = "IDENTITY";
            extra = new TInt(col, idenValues.index1, idenValues.index2);
      }

      public void addPrimaryKey(String pk) {
            if (pk != null) {
                  listPK.add(pk);
            }
      }

      public void addAllPrimaryKeys(String[] pks) {
            listPK.clear();
            for (String x : pks) {
                  listPK.add(x);
            }
      }

      public void addAllPrimaryKeys(List<String> pks) {
            listPK.clear();
            for (String x : pks) {
                  listPK.add(x);
            }
      }

      public void addForeignKey(TString FK) {
            if (listFK.isEmpty()) {
                  listFK.add(FK);
            } else {
                  String tableR = FK.string2;
                  boolean match = false;
                  for (int a = 0; a < listFK.size(); a++) {
                        String tableRC = listFK.get(a).string2;
                        if (!tableR.equals(tableRC)) {
                              match = false;
                              throw new IllegalArgumentException("All Foreign keys must reference the same table");
                        } else {
                              match = true;
                        }
                  }
                  if (match) {
                        listFK.add(FK);
                  }
            }
      }

      public void addAllForeignKeys(TString[] FKS) {
            listFK.clear();
            for (TString x : FKS) {
                  if (listFK.isEmpty()) {
                        listFK.add(x);
                  } else {
                        String tableR = x.string2;
                        boolean match = false;
                        for (int a = 0; a < listFK.size(); a++) {
                              String tableRC = listFK.get(a).string2;
                              if (!tableR.equals(tableRC)) {
                                    match = false;
                                    throw new IllegalArgumentException(
                                                "All Foreign keys must reference the same table");
                              } else {
                                    match = true;
                              }
                        }
                        if (match) {
                              listFK.add(x);
                        }
                  }
            }
      }

      public void addAllForeignKeys(List<TString> FKS) {
            listFK.clear();//ADD ALL -> RESET ALL
            for (TString fk : FKS) {
                  if (listFK.isEmpty()) {
                        listFK.add(fk);
                  } else {
                        String tableR = fk.string2;
                        boolean match = false;
                        for (int a = 0; a < listFK.size(); a++) {
                              String tableRC = listFK.get(a).string2;
                              if (!tableR.equals(tableRC)) {
                                    match = false;
                                    throw new IllegalArgumentException(
                                                "All Foreign keys must reference the same table");
                              } else {
                                    match = true;
                              }
                        }
                        if (match) {
                              listFK.add(fk);
                        }
                  }
            }
      }
      // +++++++++++++++++++++++++++++++++++++++++++
}
