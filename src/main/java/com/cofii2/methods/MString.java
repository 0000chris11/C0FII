/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import com.cofii2.stores.DInt;
import java.util.ArrayList;

/**
 *
 * @author C0FII
 */
public class MString {

      private static final String[] formatted = {"%fslash%", "bslash", "%lbracket%", "%gbracket%", "%question%", "%colon%",
            "%asterisk%", "%dquotes%", "%pipe%"};
      private static final String[] normal = {"/", "\\", "<", ">", "?", ":", "*", "\"", "|"};

      private MString() {
            throw new IllegalStateException("Private Constructor");

      }

      //+++++++++++++++++++++++++++++++++++++++++
      public static String getCustomFormattedString(String text) {
            if (text.contains("/")) {
                  text = text.replaceAll("/", "%Slash%");
            }
            if (text.contains("<")) {
                  text = text.replaceAll("<", "%Bracket%");
            }
            if (text.contains("?")) {
                  text = text.replace("?", "%Question%");
            }
            if (text.contains(": ")) {
                  text = text.replaceAll(": ", "%2d%");

            }
            return text;
      }

      public static String getRemoveCustomFormattedString(String text) {
            for (int a = 0; a < formatted.length; a++) {
                  if (text.contains(formatted[a])) {
                        text = text.replaceAll(formatted[a], normal[a]);
                  }
            }
            return text;
      }

      public static String[] getTags(String text, int limit) {
            String[] array = new String[limit];
            boolean happens = false;
            for (int a = 0; a < limit; a++) {
                  if (text.contains("; ")) {
                        array[a] = text.substring(0, text.indexOf(";"));
                        text = text.replace(array[a] + "; ", "");

                        //System.out.println("array[a]: " + array[a]);
                        //System.out.println("Text: " + text);
                        happens = true;
                  } else {
                        if (happens) {
                              array[a] = text;
                        } else {
                              if (a == 0) {
                                    array[a] = text;
                              } else {
                                    array[a] = null;
                              }
                        }
                  }
            }

            ArrayList<String> list = new ArrayList<String>();
            for (String x : array) {
                  if (x != null) {
                        list.add(x);
                  }
            }
            return MList.getListToArray(String.class, list);
      }

      public static String getCustomDistTagFormat(ArrayList<Integer> list) {
            String value = "NONE";
            if (!list.isEmpty()) {
                  int size = list.size();
                  value = "X" + size + ": ";
                  for (int a = 0; a < size; a++) {
                        int v = list.get(a);
                        value += v;
                        if (a != size - 1) {
                              value += "_";
                        }
                  }
            }
            return value;
      }

      public static String getCustomDist2Format(ArrayList<Integer> list) {
            String value = "NONE";
            if (!list.isEmpty()) {
                  int size = list.size();
                  value = "X" + size + ": ";
                  for (int a = 0; a < size; a++) {
                        int v1 = list.get(a);
                        int v2 = list.get(a) + 1;
                        value += v1 + "-" + v2;
                        if (a != size - 1) {
                              value += "_";
                        }
                  }
            }
            return value;
      }

      public static int[] getCustomDistTagSelected(String value) {
            if (!value.equals("NONE")) {
                  int size = Character.getNumericValue(value.charAt(1));
                  int[] array = new int[size];
                  int c = 4;
                  for (int a = 0; a < size; a++) {
                        array[a] = Character.getNumericValue(value.charAt(c));
                        c += 2;
                  }
                  return array;
            } else {
                  return null;
            }
      }

      public static DInt[] getCustomDist2Selected(String value) {
            if (!value.equals("NONE")) {
                  int size = Character.getNumericValue(value.charAt(1));
                  DInt[] array = new DInt[size];
                  int c = 4;
                  for (int a = 0; a < size; a++) {
                        int value1 = Character.getNumericValue(value.charAt(c));
                        c += 2;
                        int value2 = Character.getNumericValue(value.charAt(c));
                        array[a] = new DInt(value1, value2);
                        c += 2;
                  }
                  return array;
            } else {
                  return null;
            }
      }
      
      public static String getCustomClearTableFormat(String table){
            return table.toLowerCase().trim().replaceAll(" ", "_");
      }
}
