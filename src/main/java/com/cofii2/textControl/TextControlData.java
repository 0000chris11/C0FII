/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.textControl;

import com.cofii2.stores.StringList_Int;
import java.util.ArrayList;

/**
 * Super class containg the necessary data for the TextControl class
 * 
 * @author C0FII
 */
public class TextControlData {

      /**
       * Determines if the text of the keyListener contains any char from the list
       */
      public static final int CONTAIN_MATCH = 1;
      /**
       * Determines if the text of the keyListener equals any elements of the list
       */
      public static final int EQUAL_MATCH = 2;
      /**
       * If there are duplicated elements on this list, it will throw true
       */
      public static final int DUPLICATED_ELEMENTS = 3;
     
      /**
       * Option choice for TextControl class's constructor for a key being typed
       */
      public static final int KEY_TYPED = 11;
      /**
       * Option choice for TextControl class's constructor for a key being pressed
       */
      public static final int KEY_PRESSED = 12;
      /**
       * Option choice for TextControl class's constructor for a key being released
       */
      public static final int KEY_RELEASED = 13;
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Each value on this list contains a list and the options given by TextControlData static final fields
       */
      public ArrayList<StringList_Int> Lists = new ArrayList<StringList_Int>();
      public static ArrayList<Integer> list_MI = new ArrayList<Integer>();
      public AKeyMatchActions ac;
      //+++++++++++++++++++++++++++++++++++++++++++
      public void addList(ArrayList<String> list, int option) {
            Lists.add(new StringList_Int(list, option));
            list_MI.add(-1);
      }

      public void setMinusIndex(int listID, int minusIndex) {
            list_MI.set(listID, minusIndex);
      }
}
