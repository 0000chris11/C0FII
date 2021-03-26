/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myListeners.preClass.MultiSelection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;
import com.cofii2.myInterfaces.IMultiSelection;

/**
 *
 * @author C0FII
 */
public class MultiSelectionProperty extends MultiSelection implements PropertyChangeListener{
      
      public MultiSelectionProperty(AbstractButton[] array, IMultiSelection ma) {
            super(array, ma);
      }

      @Override
      public void propertyChange(PropertyChangeEvent e) {
            ma.action(e, MComp.getSelectedButtons(e, array, true));
      }
      
}
