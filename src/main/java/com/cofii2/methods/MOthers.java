/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import com.cofii2.myInterfaces.ThreadAction;
import com.cofii2.myInterfaces.ThreadLoopAction;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author C0FII
 */
public class MOthers {

      private MOthers() {
            throw new IllegalStateException("Private Constructor");
      }

      // +++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Get a random number between the given parameters. If match-Random return 0.0
       * (the minimum value) then it'll return the min parameter value, and if
       * Math.random return 1.0 (the max value) then it'll return the max value.
       * 
       * @param min lowest in the range to return
       * @param max highest in the range to return
       * @return random value between min and max
       */
      public static int getRandomNumber(int min, int max) {
            return (int) ((Math.random() * (max - min)) + min);
      }

      private static String getStringAdjust(String text) {
            text = text.toUpperCase().trim();
            if (text.contains("_")) {
                  text = text.replaceAll("_", " ");
            }
            /*
             * if(text.contains(" ")){ text = text.replaceAll(" ", ""); }
             */
            return text;
      }

      public static boolean getContainMatchFromStringToList(String text, ArrayList<String> list, boolean caseSensitive,
                  boolean trim, boolean adjust) {
            // System.out.println("getContainMatchFromStringToList");
            boolean returnValue = false;

            if (adjust) {
                  text = getStringAdjust(text);
            }
            if (!caseSensitive) {
                  text = text.toUpperCase();
            }
            if (trim) {
                  text = text.trim();
            }

            for (String x : list) {
                  String cont;
                  if (adjust) {
                        cont = getStringAdjust(x);
                  } else if (!caseSensitive) {
                        cont = x.toUpperCase();
                  } else {
                        cont = x;
                  }
                  if (trim) {
                        cont.trim();
                  }

                  // System.out.println("\ttext: " + text);
                  // System.out.println("\tcont: " + cont);
                  if (text.contains(cont)) {
                        returnValue = true;
                  }
            }
            return returnValue;
      }

      public static boolean getEqualMatchFromStringToList(String text, ArrayList<String> list, int minus,
                  boolean trim) {
            // System.out.println("getEqualMatchFromStringToList+++++++++++");
            // System.out.println("List size: " + list.size());
            boolean returnValue = false;
            int count = 0;
            text = getStringAdjust(text);
            if (trim) {
                  text = text.trim();
            }
            // System.out.println("\tText: " + text);
            for (String x : list) {
                  // System.out.println("\tX: " + x);
                  String cont = getStringAdjust(x);
                  if (trim) {
                        cont = cont.trim();
                  }
                  if (count != minus && minus >= 0) {
                        if (text.equalsIgnoreCase(cont) && !text.isEmpty()) {
                              returnValue = true;
                        }
                  } else if (minus < 0) {
                        if (text.equalsIgnoreCase(cont) && !text.isEmpty()) {
                              returnValue = true;
                        }
                  }
                  count++;
            }
            // System.out.println("\t\tRETURN VALUE: " + returnValue);
            return returnValue;
      }

      public static boolean getEqualMatchFromStringToArray(String text, String[] array) {
            boolean returnValue = false;
            text = getStringAdjust(text);
            for (String x : array) {
                  String cont = getStringAdjust(x);
                  if (text.equalsIgnoreCase(cont)) {
                        returnValue = true;
                  }
            }
            return returnValue;
      }

      public static boolean getContainMatchFromArrayToList(String[] array, ArrayList<String> list) {
            boolean returnValue = false;
            for (int a = 0; a < array.length; a++) {
                  String text = getStringAdjust(array[a]);

                  for (int b = 0; b < list.size(); b++) {
                        String cont = getStringAdjust(list.get(b));
                        if (text.contains(cont)) {
                              returnValue = true;
                        }
                  }
            }
            return returnValue;
      }

      public static boolean getContainMatchFromArrayToList(JTextField[] array, ArrayList<String> list) {
            boolean returnValue = false;
            for (int a = 0; a < array.length; a++) {
                  String text = getStringAdjust(array[a].getText());

                  for (int b = 0; b < list.size(); b++) {
                        String cont = getStringAdjust(list.get(b));
                        if (text.contains(cont)) {
                              returnValue = true;
                        }
                  }
            }
            return returnValue;
      }

      /**
       * Get equal match from the same array (to detect no repited elements)
       *
       * @param array String array to search on
       * @param trim  trim elements
       * @return elements match
       */
      public static boolean getEqualsMatchFromArray(String[] array, boolean trim) {
            boolean returnValue = false;
            for (int a = 0; a < array.length; a++) {
                  String text = getStringAdjust(array[a]);
                  if (trim == true) {
                        text = text.trim();
                  }
                  for (int b = 0; b < array.length; a++) {
                        if (a != b) {
                              String cont = getStringAdjust(array[b]).trim();
                              if (trim == true) {
                                    cont = cont.trim();
                              }
                              if (text.equalsIgnoreCase(cont) && !text.isEmpty()) {
                                    returnValue = true;
                              }
                        }
                  }
            }
            return returnValue;
      }

      public static Object[] getEqualsMatchAndPositionFromArray(String[] array, boolean trim) {
            boolean returnValue = false;
            int A = -1;
            int B = -1;
            for (int a = 0; a < array.length; a++) {
                  String text = getStringAdjust(array[a]);
                  if (trim == true) {
                        text = text.trim();
                  }
                  for (int b = 0; b < array.length; b++) {
                        if (a != b) {
                              String cont = getStringAdjust(array[b]).trim();
                              if (trim == true) {
                                    cont = cont.trim();
                              }
                              if (text.equalsIgnoreCase(cont) && !text.isEmpty()) {
                                    returnValue = true;
                                    A = a;
                                    B = b;
                              }
                        }
                  }
            }

            return new Object[] { returnValue, A, B };
      }

      // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public static void setThreadAction(int milis, ThreadAction ta) {
            new Thread(new Runnable() {
                  @Override
                  public void run() {
                        ta.before();
                        try {
                              Thread.sleep(milis);
                              ta.after();
                        } catch (InterruptedException ex) {
                              Logger.getLogger(MOthers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                  }

            }).start();
      }

      public static void setLoopThreadAction(int milis, ThreadLoopAction ta) {
            new Thread(new Runnable() {

                  @Override
                  public void run() {
                        int c = 0;
                        while (true) {
                              ta.action();
                              try {
                                    Thread.sleep(milis);
                              } catch (InterruptedException ex) {
                                    Logger.getLogger(MOthers.class.getName()).log(Level.SEVERE, null, ex);
                              }
                              if (ta.keep(++c)) {
                                    break;
                              }
                        }
                  }

            }).start();
      }

      public static TimerTask getCustomTimerTask(Label label) {
            return new TimerTask() {
                  int msu = 0;
                  int su = 0;
                  int sd = 0;
                  int mu = 0;

                  @Override
                  public void run() {
                              msu++;
                              // MILISECOND
                              if (msu == 10) {
                                    su++;
                                    msu = 0;
                              }
                              // SECOND U
                              if (su == 10) {
                                    sd++;
                                    su = 0;
                              }
                              // SECOND D
                              if (sd == 6) {
                                    mu++;
                                    sd = 0;
                              }
                              // MINUTE U
                              if (mu == 10) {
                                    msu = 0;
                                    su = 0;
                                    sd = 0;
                                    mu = 0;
                              }
                              label.setText((mu) + ":" + (sd) + (su) + ":" + (msu));
                        
                  }
            };
      }

      // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public static class Result {

            public boolean match;
            public int a;
            public int b;

            public Result(boolean match, int a, int b) {
                  this.match = match;
                  this.a = a;
                  this.b = b;
            }
      }

      /**
       *
       * @param array JTextFields list
       * @param trim  trim elements
       * @return Object containing boolean (match) and two Array that determine the
       *         position when they match
       */
      public static ArrayList<Result> getEqualsMatchAndPositionFromArray(JTextField[] array, boolean trim) {
            boolean match = false;

            ArrayList<Result> res = new ArrayList<Result>();
            // ####################
            List<JTextField> lis = Arrays.asList(array);
            ArrayList<JTextField> list = new ArrayList<JTextField>(lis);
            // System.out.println("array length: " + array.length);
            // System.out.println("list size: " + list.size());

            for (int a = 0; a < array.length; a++) {
                  // System.out.println("Element " + (a + 1) + ": " + array[a].getText());
                  if (!array[a].isVisible()) {
                        list.remove(a);

                  }
            }
            // ####################

            for (int a = 0; a < list.size(); a++) {
                  String text = getStringAdjust(list.get(a).getText());
                  if (trim == true) {
                        text = text.trim();
                  }
                  for (int b = 0; b < list.size(); b++) {
                        if (a != b) {
                              String cont = getStringAdjust(list.get(b).getText()).trim();
                              if (trim == true) {
                                    cont = cont.trim();
                              }

                              // System.out.println("####Comparing " + (a + 1) + " & " + (b + 1));
                              // System.out.println("\t####text " + (a + 1) + ": " + text);
                              // System.out.println("\t####cont " + (b + 1) + ": " + cont);
                              if (!text.isEmpty()) {
                                    if (text.equalsIgnoreCase(cont)) {
                                          // System.out.println("\t\tMATCH");
                                          match = true;
                                          res.add(new Result(true, a, b));
                                    } else {
                                          if (match != true)
                                                ;
                                          // System.out.println("\t\tUNMATCH");
                                          res.add(new Result(false, a, b));
                                    }
                              } else {
                                    // System.out.println("\t\tUNMATCH");
                                    match = false;
                                    res.add(new Result(false, a, b));
                              }
                        }
                  }
            }

            return res;
      }
}
