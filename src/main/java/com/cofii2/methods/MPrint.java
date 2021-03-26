/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author C0FII
 */
public class MPrint {
      
      private MPrint() {
            throw new IllegalStateException("Private Constructor");
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public static void printListSelectionListeners(JList lst) {
            System.out.println("\n" + lst.getName() + " ListSelectionListeners count: "
                    + lst.getListSelectionListeners().length);
            for (int a = 0; a < lst.getListSelectionListeners().length; a++) {
                  System.out.println("\t" + lst.getName() + " " + a + ": " + lst.getListSelectionListeners()[a].toString());
            }
      }

      public static void printListSelectionListeners(JTable jt) {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel) jt.getSelectionModel();
            System.out.println("\n" + jt.getName() + " ListSelectionListeners count: "
                    + dlsm.getListSelectionListeners().length);
            for (int a = 0; a < dlsm.getListSelectionListeners().length; a++) {
                  System.out.println("\t" + jt.getName() + " " + a + ": " + dlsm.getListSelectionListeners()[a].toString());
            }
      }

      public static void printKeyListeners(JComponent jc) {
            System.out.println("\n" + jc.getName() + " KeyListeners count: "
                    + jc.getKeyListeners().length);
            for (int a = 0; a < jc.getKeyListeners().length; a++) {
                  System.out.println("\t" + jc.getName() + " " + a + ": " + jc.getKeyListeners()[a].toString());
            }
      }

      public static void printFocusListeners(JComponent jc) {
            System.out.println("\n" + jc.getName() + " FocusListeners count: "
                    + jc.getFocusListeners().length);
            for (int a = 0; a < jc.getFocusListeners().length; a++) {
                  System.out.println("\t" + jc.getName() + " " + a + ": " + jc.getFocusListeners()[a].toString());
            }
      }

      public static void printMouseListeners(JComponent jc) {
            System.out.println("\n" + jc.getName() + " MouseListeners count: "
                    + jc.getMouseListeners().length);

            for (int a = 0; a < jc.getMouseListeners().length; a++) {
                  System.out.println("\t" + jc.getName() + " " + a + ": " + jc.getMouseListeners()[a].toString());
            }
      }

      public static void printTableModelListeners(DefaultTableModel dtm) {
            System.out.println("\nTableModelListeners count: " + dtm.getTableModelListeners().length);

            for (int a = 0; a < dtm.getTableModelListeners().length; a++) {
                  System.out.println("\t" + a + ": " + dtm.getTableModelListeners()[a].toString());
            }
      }

      public static void printActionListeners(JButton jb) {
            System.out.println("\n" + jb.getName() + " ActionListeners count: " + jb.getActionListeners().length);

            for (int a = 0; a < jb.getActionListeners().length; a++) {
                  System.out.println("\t" + a + ": " + jb.getActionListeners()[a].toString());
            }
      }
      
      public static void printTableModelListener(JTable table){
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            System.out.println("\n" + table.getName() + " TableModelListener count: " + dtm.getTableModelListeners().length);

            for (int a = 0; a < dtm.getTableModelListeners().length; a++) {
                  System.out.println("\t" + a + ": " + dtm.getTableModelListeners()[a].toString());
            }
      }
}
