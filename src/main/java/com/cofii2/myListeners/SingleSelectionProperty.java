/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myInterfaces.SingleSelectionIAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;

/**
 *
 * @author C0FII
 */
public class SingleSelectionProperty <T extends AbstractButton> implements PropertyChangeListener{

      private SingleSelectionIAction sa;
      private T[] array;
      
      public SingleSelectionProperty(T[] array, SingleSelectionIAction sa){
            this.array = array;
            this.sa = sa;
      }
      
      @Override
      public void propertyChange(PropertyChangeEvent e) {
            int num = MComp.getSelectedOnAButtonGroup(e, array);
            if(num != -1){
                  num = num - 1;
            }
            sa.action(e, num);
      }
      
}
