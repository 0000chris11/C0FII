/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;

/**
 *
 * @author C0FII
 */
public class MText {
      
      private MText() {
            throw new IllegalStateException("Private Constructor");
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public static boolean getMatch(String text, String comp, int pattern) {
            Pattern pat = Pattern.compile(Pattern.quote(text), pattern);
            Matcher match = pat.matcher(comp);
            return match.find();
      }

      public static void shortenLB(JLabel lb) {
            if (lb.getText().length() >= 10) {
                  String somet = lb.getText().substring(0, 10);
                  lb.setText(somet + "...");
            }
      }

      public static String filterTextName(String text, String op) {
            //System.out.println(CC.PURPLE + "FilterTableName" + CC.RESET);
            //System.out.println("\tText: " + text);
            if (op.equals("ADD")) {
                  if (text.contains(" ")) {
                        //table.replaceAll(" ", "_");
                        text = text.replaceAll(" ", "_");
                  }
            } else if (op.equals("TAKE")) {
                  if (text.contains("_")) {
                        //table.replaceAll(" ", "_");
                        text = text.replaceAll("_", " ");
                  }
            }
            //System.out.println("\tTable: " + table);
            return text;
      }
}
