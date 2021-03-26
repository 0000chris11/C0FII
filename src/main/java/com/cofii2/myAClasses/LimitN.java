/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myAClasses;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Class for limiting key input to only numbers
 *
 * @author C0FII
 */
public class LimitN extends DocumentFilter {
      
      private int option = 1;
      private int range1 = -1;
      private int range2 = -1;
      
      public static final int ALL_NUMBERS = 1;
      public static final int ONE_TO_ = 2;

      public LimitN(int option) {
            this.option = option;
      }
      
      public LimitN(int range1, int range2){   
            option = 3;
            this.range1 = range1;
            this.range2 = range2;
      }

      
/*
      @Override
      public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            System.out.println("\nREMOVE");
            int alength = fb.getDocument().getLength();
            System.out.println("length: " + alength);
            if(alength > 1){
                  System.out.println("\tremoving");
                  System.out.println("\t\toffset: " + offset);
                  System.out.println("\t\tlength: " + length);
                  fb.remove(offset, length);
            }
            
      }
      */
      @Override
      public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            //System.out.println("text: " + text);
            String atext = fb.getDocument().getText(0, fb.getDocument().getLength());
            //System.out.println("document lenght: " + fb.getDocument().getLength());
            //System.out.println("atext: " + atext);
            if (test(text)) {
                  //System.out.println("\tinteger");
                  String newText = atext + text;
                  int n = Integer.parseInt(newText);
                  if (option == ALL_NUMBERS) {
                        //System.out.println("\t\tOPTION 1");
                        insert(fb, offset, length, text, attrs);
                  } else if (option == ONE_TO_) {
                        //System.out.println("\t\tOPTION 2");
                        if (n > 0) {
                              insert(fb, offset, length, text, attrs);
                        }
                  }else if(option == 3){
                        //System.out.println("\t\tOPTION 3");
                        if(n >= range1 && n <= range2) {
                             // System.out.println("\t\t\tWORK");
                              insert(fb, offset, length, text, attrs);
                        }
                  }
            }
      }

      @Override
      public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
              throws BadLocationException {

      }

      private void insert(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) {
            try {
                  fb.replace(offset, length, text, attr);
            } catch (BadLocationException ex) {
                  ex.printStackTrace();
            }
      }

      private boolean test(String text) {
            try {
                  Integer.parseInt(text);
                  return true;
            } catch (NumberFormatException e) {
                  return false;
            }
      }
}
