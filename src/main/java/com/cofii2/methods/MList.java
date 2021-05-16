/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import com.cofii2.stores.CC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;

/**
 *
 * @author C0FII
 */
public class MList {

      private MList() {
            throw new IllegalStateException("Private Constructor");
      }

      // +++++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Obsolete method. Used for old project (TimeStamp)
       * 
       * @param lst  for display
       * @param list elements
       * @param b    re-add
       * @deprecated
       */
      public static void add_SetDefaultListModel(JList<String> lst, ArrayList<String> list, boolean b) {
            DefaultListModel<String> dlm = new DefaultListModel<String>();
            lst.setModel(dlm);
            if (b == true) {
                  for (int a = 0; a < list.size(); a++) {
                        dlm.addElement(list.get(a));
                  }
            }
      }

      /**
       * Obsolete method. Used for old project (TimeStamp)
       * 
       * @param cb   to set them on
       * @param list the elements
       * @deprecated
       */
      public static void add_SetListModel(JComboBox cb, ArrayList<String> list) {
            cb.removeAllItems();
            for (int a = 0; a < list.size(); a++) {
                  cb.addItem(list.get(a));
            }

      }

      /**
       * Mix two list into one; concated by conn variable
       * 
       * @param list  left elements
       * @param list2 right elements
       * @param conn  concat between list and list2
       * @deprecated
       */
      public static List<String> mixRowList(List<String> list, List<String> list2, String conn) {
            List<String> list3 = new ArrayList<>();
            for (int c = 0; c < list.size(); c++) {
                  list3.add(list.get(c) + conn + list2.get(c));
            }
            return list3;
      }

      public static void unTagList(ArrayList<String> list) {
            // Separate elements with ; (TAG)
            String sub1;
            String sub2;
            // System.out.println("MList Size Before: " + list.size());
            for (int a = 0; a < list.size(); a++) {
                  // System.out.println("- - - - - - " + a + " >" + list.get(a));
                  if (list.get(a).contains(";")) {
                        sub1 = list.get(a).substring(0, list.get(a).indexOf(";"));
                        sub2 = list.get(a).substring(list.get(a).indexOf(";") + 2, list.get(a).length());

                        list.add(sub1);
                        list.add(sub2);

                        list.remove(a);

                        a--;
                  }
            }
            // ++++++++++++++++++++++++++++++++++++++++++++++
            ArrayList<String> copy = new ArrayList<String>();
            copy.addAll(list);

            Set<String> set = new LinkedHashSet<>(copy);

            copy = new ArrayList<String>(set);
            Collections.sort(copy);

            list.clear();
            list.addAll(copy);
      }

      public static List<String> removeDuplicates(List<String> list) {
            // int minus = 0;
            /**
             * AAAA BBBB CCCC DDDD AAAA --- EEEE
             */
            for (int a = 0; a < list.size(); a++) {
                  String element = list.get(a);
                  for (int b = a; b < list.size(); b++) {
                        String element2 = list.get(b);
                        if (a != b) {
                              if (element.equals(element2)) {
                                    list.remove(b);
                                    // minus++;
                              }
                        }
                  }
            }
            return list;
      }

      // +++++++++++++++++++++++++++++++++++++++++++++++++++++
      public static void printFor(List<String> list) {
            System.out.println(CC.GREEN + "\nPrinting for..." + CC.RESET);
            if (list.isEmpty()) {
                  System.out.println("\tthe list has no elements");
            } else {
                  for (int a = 0; a < list.size(); a++) {
                        System.out.println("\telement " + a + ": [" + list.get(a) + "]");
                  }
            }
      }

      public static void printFor(ArrayList<String> list) {
            System.out.println(CC.GREEN + "\nPrinting for..." + CC.RESET);
            if (list.isEmpty()) {
                  System.out.println("\tthe list has no elements");
            } else {
                  for (int a = 0; a < list.size(); a++) {
                        System.out.println("\telement " + a + ": " + list.get(a));
                  }
            }
      }

      public static void printFor(ArrayList<String> list, String message) {
            System.out.println("\n" + message);
            if (list.isEmpty()) {
                  System.out.println("\tthe list has no elements");
            } else {
                  for (int a = 0; a < list.size(); a++) {
                        System.out.println("\telement " + a + ": " + list.get(a));
                  }
            }
      }

      public static void printForInteger(ArrayList<Integer> list) {
            System.out.println(CC.GREEN + "\nPrinting for..." + CC.RESET);
            if (list.isEmpty()) {
                  System.out.println("\tthe list has no elements");
            } else {
                  for (int a = 0; a < list.size(); a++) {
                        System.out.println("\telement " + a + ": " + list.get(a));
                  }
            }
      }

      public static <T> void printFor(T[] array) {
            System.out.println();
            if (array instanceof JComponent[]) {
                  for (int a = 0; a < array.length; a++) {
                        JComponent jc = (JComponent) array[a];
                        String name = "";
                        if (jc.getName() != null) {
                              name = " (" + jc.getName() + ")";
                        }
                        System.out.println((a + 1) + ": " + jc.getClass().toString() + name);
                  }
            } else {
                  for (int a = 0; a < array.length; a++) {
                        if (array[a] != null) {
                              System.out.println((a + 1) + ": " + array[a].toString());
                        } else {
                              System.out.println((a + 1) + ": null");
                        }
                  }
            }
      }

      public static <T> void printFor(T[] array, String message) {
            System.out.println("\n" + message);
            if (array instanceof JComponent[]) {
                  for (int a = 0; a < array.length; a++) {
                        JComponent jc = (JComponent) array[a];
                        String name = "";
                        if (jc.getName() != null) {
                              name = " (" + jc.getName() + ")";
                        }
                        System.out.println((a + 1) + ": " + jc.getClass().toString() + name);
                  }
            } else {
                  for (int a = 0; a < array.length; a++) {
                        if (array[a] != null) {
                              System.out.println((a + 1) + ": " + array[a].toString());
                        } else {
                              System.out.println((a + 1) + ": null");
                        }
                  }
            }
      }

      public static void printFor(boolean[] array) {
            System.out.println(CC.GREEN + "\nPrinting for..." + CC.RESET);
            if (array.length == 0) {
                  System.out.println("\tthe list has no elements");
            } else {
                  for (int a = 0; a < array.length; a++) {
                        System.out.println("\telement " + a + ": " + array[a]);
                  }
            }
      }

      public static void printFor(boolean[] array, String message) {
            System.out.println("\n" + message);
            if (array.length == 0) {
                  System.out.println("\tthe list has no elements");
            } else {
                  for (int a = 0; a < array.length; a++) {
                        System.out.println("\telement " + (a + 1) + ": " + array[a]);
                  }
            }
      }

      public static <T> void printForClassFields(T[] array) {
            System.out.println();
            for (int a = 0; a < array.length; a++) {
                  Field[] fields = array[a].getClass().getDeclaredFields();
                  System.out.println("\n" + (a + 1) + ": " + array[a].getClass().toString());
                  for (int f = 0; f < fields.length; f++) {
                        String name = fields[f].getName();
                        String type = fields[f].getType().toString();
                        System.out.println("\t" + (f + 1));
                        System.out.println("\tname: " + name);
                        System.out.println("\ttype: " + type);
                        try {
                              // Object value = fields[0].get(name);
                              Object value = fields[f].get(array[a]);
                              if (value != null) {
                                    System.out.println("\tvalue:" + value);
                              } else {
                                    System.out.println("\tvalue: null");
                              }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                              Logger.getLogger(MList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                  }
            }
      }

      public static <T> void printForClassFields(T[] array, String message) {
            System.out.println("\n" + message);
            for (int a = 0; a < array.length; a++) {
                  Field[] fields = array[a].getClass().getDeclaredFields();
                  System.out.println("\n" + (a + 1) + ": " + array[a].getClass().toString());
                  for (int f = 0; f < fields.length; f++) {
                        String name = fields[f].getName();
                        String type = fields[f].getType().toString();
                        System.out.println("\t" + (f + 1));
                        System.out.println("\tname: " + name);
                        System.out.println("\ttype: " + type);
                        try {
                              // Object value = fields[0].get(name);
                              Object value = fields[f].get(array[a]);
                              if (value != null) {
                                    System.out.println("\tvalue:" + value);
                              } else {
                                    System.out.println("\tvalue: null");
                              }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                              Logger.getLogger(MList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                  }
            }
      }

      public static <T> void printFor(T[][] array) {
            if (array instanceof JComponent[][]) {
                  for (int a = 0; a < array.length; a++) {
                        for (int b = 0; b < array[a].length; b++) {
                              System.out.print((a + 1) + "-" + (b + 1) + ": " + array[a][b].getClass().toString());
                              if (b != array[a].length - 1) {
                                    System.out.print(", ");
                              }
                        }
                        System.out.println();
                  }
            } else {
                  for (int a = 0; a < array.length; a++) {
                        for (int b = 0; b < array[a].length; b++) {
                              System.out.print(a + "-" + b + ": " + array[a][b]);
                              if (b != array[a].length - 1) {
                                    System.out.print(", ");
                              }
                        }
                        System.out.println();
                  }
            }
      }

      public static <T> void printFor(T[][] array, String message) {
            System.out.println("\n" + message);
            if (array instanceof JComponent[][]) {
                  for (int a = 0; a < array.length; a++) {
                        for (int b = 0; b < array[a].length; b++) {
                              System.out.print((a + 1) + "-" + (b + 1) + ": " + array[a][b].getClass().toString());
                              if (b != array[a].length - 1) {
                                    System.out.print(", ");
                              }
                        }
                        System.out.println();
                  }
            } else {
                  for (int a = 0; a < array.length; a++) {
                        for (int b = 0; b < array[a].length; b++) {
                              System.out.print(a + "-" + b + ": " + array[a][b]);
                              if (b != array[a].length - 1) {
                                    System.out.print(", ");
                              }
                        }
                        System.out.println();
                  }
            }
      }

      // ++++++++++++++++++++++++++++++++++++++++++++
      public static boolean areTheyDuplicatedElementsOnList(List<String> list) {
            boolean returnValue = false;
            Set<String> set = new HashSet<>(list);
            if (set.size() < list.size()) {
                  returnValue = true;
            }
            return returnValue;
      }

      public static boolean areTheyDuplicatedElementsOnList(ObservableList<String> list) {
            boolean returnValue = false;
            List<String> list2 = new ArrayList<>(list);
            list2.removeIf(e -> e.equals(""));
            Set<String> set = new HashSet<>(list2);
            if (set.size() < list2.size()) {
                  returnValue = true;
            }
            return returnValue;
      }

      public static boolean isOnlyOneElementCopyiedOnThisList(ArrayList<String> list) {
            boolean returnValue = true;
            String first = list.get(0);
            for (String x : list) {
                  if (!first.equals(x)) {
                        returnValue = false;
                        break;
                  }
            }
            return returnValue;
      }

      // ++++++++++++++++++++++++++++++++++++++++++++
      public static <T> T[] getListToArray(Class<T> clazz, ArrayList<T> list) {
            // T[] returnObject = (T[]) Array.newInstance(clas, doubleArray.length);
            T[] array = null;
            if (list.size() > 0) {
                  array = (T[]) Array.newInstance(clazz, list.size());
                  for (int a = 0; a < list.size(); a++) {
                        array[a] = list.get(a);
                  }
            }
            return array;
      }

      public static <T> ArrayList<T> getArrayToArrayList(T[] array) {
            ArrayList<T> list = new ArrayList<T>();
            for (T x : array) {
                  list.add(x);
            }
            return list;
      }

      public static Object[][] getDoubleArrayFromList(ArrayList<Object[]> list) {
            Object[][] array = null;
            int col = list.size();
            boolean same = isTheLengthTheSameForAll(list);

            if (same) {
                  int row = list.get(0).length;
                  array = new Object[row][col];
                  for (int r = 0; r < row; r++) {
                        for (int c = 0; c < col; c++) {
                              array[r][c] = list.get(c)[r];
                        }
                  }
            } else {
                  throw new IllegalArgumentException("All arrays need to be the same size");
            }
            return array;
      }

      private static <T> T[][] getDoubleArrayFromList(Class<T> clazz, ArrayList<T[]> list) {
            T[][] doubleArray = null;
            int col = list.size();
            boolean same = true;
            int row = 0;
            for (int a = 0; a < col; a++) {
                  if (a == 1) {
                        row = list.get(a - 1).length;
                        int l2 = list.get(a).length;
                        if (row != l2) {
                              same = false;
                        }
                  }
            }
            if (same) {

                  System.out.println("rowCount: " + doubleArray.length);
                  System.out.println("colCount: " + doubleArray[0].length);
            } else {
                  throw new IllegalArgumentException("All arrays need to be the same size");
            }
            return doubleArray;
      }

      public static <T> T[] getColumn(Class<T> clas, T[][] doubleArray, int col) {
            T[] returnObject = (T[]) Array.newInstance(clas, doubleArray.length);
            for (int a = 0; a < doubleArray.length; a++) {
                  returnObject[a] = (T) doubleArray[a][col];
            }
            return returnObject;
      }

      public static <T> int getIndexOfArray(T[] array, Object ob) {
            int returnValue = -1;
            int c = 0;
            for (T x : array) {
                  if (ob instanceof String) {
                        String string = ob.toString();
                        if (x.equals(string)) {
                              returnValue = c;
                              break;
                        }
                  }
                  if (x.equals(ob)) {
                        returnValue = c;
                        break;
                  }
                  c++;
            }
            return returnValue;
      }

      public static <T> boolean isOnThisList(T[] array, Object ob, boolean ignoreCase) {
            boolean returnValue = false;
            for (T x : array) {
                  if (!ignoreCase) {
                        if (x.equals(ob)) {
                              returnValue = true;
                              break;
                        }
                  } else if(x instanceof String && ob instanceof String){
                        if (x.toString().equalsIgnoreCase(ob.toString())) {
                              returnValue = true;
                              break;
                        }
                  }else{
                        throw new IllegalArgumentException("C0FII: not supported function");
                  }

            }
            return returnValue;
      }

      public static boolean isOnThisList(String[] array, String str, boolean ignoreCase) {
            boolean returnValue = false;
            for (String x : array) {
                  if (!ignoreCase) {
                        if (x.equals(str)) {
                              returnValue = true;
                              break;
                        }
                  } else {
                        if (x.equalsIgnoreCase(str)) {
                              returnValue = true;
                              break;
                        }
                  }

            }
            return returnValue;
      }

      public static boolean isOnThisList(List<String> list, String text, boolean ignoreCase) {
            boolean returnValue = false;
            for (String x : list) {
                  if (ignoreCase) {
                        if (x.equalsIgnoreCase(text)) {
                              returnValue = true;
                              break;
                        }
                  } else {
                        if (x.equals(text)) {
                              returnValue = true;
                              break;
                        }
                  }
            }
            return returnValue;
      }

      public static boolean isOnThisList(ObservableList<String> list, String text, boolean ignoreCase) {
            boolean returnValue = false;
            for (String x : list) {
                  if (ignoreCase) {
                        if (x.equalsIgnoreCase(text)) {
                              returnValue = true;
                              break;
                        }
                  } else {
                        if (x.equals(text)) {
                              returnValue = true;
                              break;
                        }
                  }
            }
            return returnValue;
      }

      public static <T> boolean isTheLengthTheSameForAll(ArrayList<T[]> list) {

            boolean same = true;
            int col = list.size();
            int row = 0;
            for (int a = 0; a < col; a++) {
                  if (a == 1) {
                        row = list.get(a - 1).length;
                        int l2 = list.get(a).length;
                        if (row != l2) {
                              same = false;
                        }
                  }
            }
            return same;
      }

      public static <T extends Object> boolean isThisArrayEmpty(T[] array) {
            boolean returnValue = false;
            for (int a = 0; a < array.length; a++) {
                  if (array[a] == null) {
                        returnValue = true;
                  } else {
                        returnValue = false;
                        break;
                  }
            }
            return returnValue;
      }
}
