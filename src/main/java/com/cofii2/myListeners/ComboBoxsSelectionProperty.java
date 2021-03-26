/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myInterfaces.ComboBoxsAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComboBox;

/**
 *
 * @author C0FII
 */
public class ComboBoxsSelectionProperty implements PropertyChangeListener{

      private JComboBox[] array;
      private ComboBoxsAction ca;
      
      public ComboBoxsSelectionProperty(JComboBox[] array, ComboBoxsAction ca){
            this.array = array;
            this.ca = ca;
      }
      
      @Override
      public void propertyChange(PropertyChangeEvent e) {
            ca.action(e, MComp.getSelectedItems(e, array, true));
      }
      
}
