/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.textControl;

import com.cofii2.stores.Int_StringList;
import java.util.ArrayList;
import java.util.EventObject;

/**
 *
 * @author C0FII
 */
public abstract class AKeyMatchActions {
      
      /**
       * Override this method for establishing an action before implementing the action of the matches
       * @param e 
       */
      public void beforeActions(EventObject e) {}
      //++++++++++++++++++++++++++++++++++++++++++++
      public abstract void listsAction(EventObject e, boolean[] matches);
      //++++++++++++++++++++++++++++++++++++++++++++
      public Int_StringList getUpdatedList(int listID, ArrayList<String> list) {
            return new Int_StringList(listID, list);
      }
      
}
