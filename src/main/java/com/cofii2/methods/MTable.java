/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import com.cofii2.stores.DInt;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author C0FII
 */
public class MTable {

      private MTable() {
            throw new IllegalStateException("Private Constructor");
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++

      public static int getMayorId(JTable jt, int col) {
            Object data;
            int valor = -1;
            int mayor = 0;

            for (int a = 0; a < jt.getRowCount(); a++) {
                  data = jt.getValueAt(a, col);
                  if (data instanceof String) {
                        valor = Integer.parseInt((String) data);
                  } else if (data instanceof Integer) {
                        valor = (Integer) data;
                  } else {
                        System.out.println("no String or Integer");
                  }
                  //valor = (Integer) data;
                  if (valor > mayor) {
                        mayor = valor;
                  }
            }
            return mayor;
      }

      /**
       * Change each element (column) in a row for a String or int instead of an
       * Object value.
       *
       * @param JT Table to be changed
       * @param list Updates List-String with each column value
       * @param row Which row to convert
       */
      public static void typeConvert(JTable JT, ArrayList<Object> list, int row) {
            list.clear();
            Object oo;
            String aaa;
            int bbb;
            for (int a = 0; a < JT.getColumnCount(); a++) {
                  oo = JT.getValueAt(row, a);
                  if (oo instanceof String) {
                        aaa = (String) oo;
                        list.add(aaa);
                  } else if (oo instanceof Integer) {
                        bbb = (int) oo;
                        list.add(Integer.toString(bbb));
                  }
            }
      }

      /**
       * Change each element (column) in a row for a String or int instead of an
       * Object value
       *
       * @param JT Table to be changed
       * @param row Which row to convert
       */
      public static void typeConvert(JTable JT, int row) {
            Object oo;

            String stringValue;
            int intValue;
            for (int a = 0; a < JT.getColumnCount(); a++) {
                  oo = JT.getValueAt(row, a);
                  if (oo instanceof String) {
                        stringValue = (String) oo;
                        JT.setValueAt(stringValue, row, a);
                  } else if (oo instanceof Integer) {
                        intValue = (int) oo;
                        JT.setValueAt(intValue, row, a);
                  }
            }
      }

      /**
       * Print Info about the TableModel of JTable
       *
       * @param tm The TableModel to use...
       */
      public static void printTableModelInfo(TableModel tm) {
            System.out.println("\nprintTableModelInfo");
            System.out.println("\tClass: " + tm.getClass().toString());
            System.out.println("\tColumn Count: " + tm.getColumnCount());
            System.out.println("\tRow Count: " + tm.getRowCount());
            for (int a = 0; a < tm.getColumnCount(); a++) {
                  System.out.println("\t\tClass Column " + a + ": " + tm.getColumnClass(a).toString());
            }
      }
      //+++++++++++++++++++++++++++++++++++++++++++++++++++
      public static DInt getMatchFromTable(String text, JTable jt){
            DInt returnVlaue = null;
            for(int row = 0; row < jt.getRowCount(); row++){
                  for(int col = 0; col < jt.getColumnCount(); col++){
                        if(text.equals(jt.getValueAt(row, col).toString())){
                              returnVlaue = new DInt(row, col);
                        }
                  }
            }
            return returnVlaue;
      }
}
