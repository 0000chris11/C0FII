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

/**
 *
 * @author C0FII
 */
public class MSQLCreate extends SQLInit {
      
      private String table;

      private ArrayList<DInt> listWidth = new ArrayList<DInt>();
      private ArrayList<IntBoolean> listNulls = new ArrayList<IntBoolean>();
      private ArrayList<IntString> listDefaults = new ArrayList<IntString>();

      private String extraType = "";
      private TInt extra = null;

      private ArrayList<String> listPK = new ArrayList<String>();
      private ArrayList<TString> listFK = new ArrayList<TString>();//COLUMN NAME AND TABLE.COLUMN REFERENCE

      public MSQLCreate(Connect conn) {
            super(conn);
      }

      //+++++++++++++++++++++++++++++++++++++++++++
      public void createTable(String table, String[] columnsNames, String[] columnsTypes, IUpdates iu) {
            try {
                  this.table = table;
                  sql = "CREATE TABLE " + table.replaceAll(" ", "_") + "(";

                  int length = columnsNames.length;
                  int removeTW = 0;
                  int removeDF = 0;
                  int removeNLL = 0;
                  for (int a = 0; a < length; a++) {
                        sql += columnsNames[a] + " " + columnsTypes[a];

                        typeWidths(removeTW, a);
                        defaults(removeDF, a);
                        nulls(removeNLL, a);

                        extra(a);
                        if (a != length - 1) {
                              sql += ", ";
                        }
                  }

                  primaryKeys();
                  foreignKeys();

                  sql += ")";
                  System.out.println(CC.CYAN + "\nSQL: " + sql + CC.RESET);
                  update(iu);

            } catch (SQLException ex) {
                  iu.exception(ex, sql);
            }

      }

      //+++++++++++++++++++++++++++++++++++++++++++
      private void typeWidths(int removeTW, int a) {
            if (!listWidth.isEmpty()) {
                  for (DInt x : listWidth) {
                        if (x != null) {
                              int index = x.index1;
                              int width = x.index2;
                              if (index == (a + 1)) {
                                    sql += "(" + width + ")";
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
                              try {//TEST FOR DEFAULT VALUE HAVING QUOTES
                                    Integer.parseInt(string);
                                    if (index == (a + 1)) {
                                          sql += " DEFAULT " + string;
                                          break;
                                    }
                              } catch (NumberFormatException ex) {
                                    if (index == (a + 1)) {
                                          sql += " DEFAULT \"" + string + "\"";
                                          break;
                                    }
                              };
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
                              sql += " NOT NULL";
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
                              sql += " AUTO_INCREMENT";
                        }
                  } else if (extraType.equals("IDENTITY")) {
                        int iden1 = extra.index2;
                        int iden2 = extra.index3;
                        if (col == a + 1) {
                              sql += " IDENTITY(" + iden1 + ", " + iden2 + ")";
                        }
                  }
            }
      }

      private void primaryKeys() {
            if (!listPK.isEmpty()) {
                  sql += ", PRIMARY KEY(";
                  for (int a = 0; a < listPK.size(); a++) {
                        sql += listPK.get(a);

                        if (a != listPK.size() - 1) {
                              sql += ", ";
                        }
                  }
                  sql += ")";
            }
      }

      private void foreignKeys() {
            if (!listFK.isEmpty()) {
                  String tableR = listFK.get(0).string2;
                  sql += ", CONSTRAINT fk_" + table + "__" + tableR + " FOREIGN KEY(";
                  //TEST CONSTRAINTS
                  for (int a = 0; a < listFK.size(); a++) {
                        sql += listFK.get(a).string1;

                        if (a != listFK.size() - 1) {
                              sql += ", ";
                        }
                  }
                  sql += ") REFERENCES " + tableR + "(";
                  for (int a = 0; a < listFK.size(); a++) {
                        sql += listFK.get(a).string3;

                        if (a != listFK.size() - 1) {
                              sql += ", ";
                        }
                  }
                  sql += ")";
            }
      }

      //+++++++++++++++++++++++++++++++++++++++++++
      public void addTypesWidth(DInt width) {
            listWidth.add(width);
      }

      public void setAllTypesWidths(DInt[] widths) {
            listWidth.clear();
            for (DInt x : widths) {
                  listWidth.add(x);
            }
      }

      public void addAllowsNull(IntBoolean nullColumn) {
            listNulls.add(nullColumn);
      }

      public void setAllAllowsNulls(IntBoolean[] nullsColumns) {
            listNulls.clear();
            for (IntBoolean x : nullsColumns) {
                  listNulls.add(x);
            }
      }

      public void addDefault(IntString defaultt) {
            listDefaults.add(defaultt);
      }

      public void setAllDefaults(IntString[] defaultt) {
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

      public void addPrimaryKey(String PK) {
            listPK.add(PK);
      }

      public void addAllPrimaryKeys(String[] PKS) {
            listPK.clear();
            for (String x : PKS) {
                  listPK.add(x);
            }
      }

      public void addAllPrimaryKeys(ArrayList<String> PKS) {
            listPK.clear();
            for (String x : PKS) {
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
                                    throw new IllegalArgumentException("All Foreign keys must reference the same table");
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
      
      public void addAllForeignKeys(ArrayList<TString> FKS) {
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
                                    throw new IllegalArgumentException("All Foreign keys must reference the same table");
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
      //+++++++++++++++++++++++++++++++++++++++++++
}
