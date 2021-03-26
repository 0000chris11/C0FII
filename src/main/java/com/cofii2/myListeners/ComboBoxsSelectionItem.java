/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myInterfaces.ComboBoxsAction;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author C0FII
 */
public class ComboBoxsSelectionItem implements ItemListener {

      private JComboBox[] array;
      private ComboBoxsAction ca;

      public ComboBoxsSelectionItem(JComboBox[] array, ComboBoxsAction ca) {
            this.array = array;
            this.ca = ca;
      }

      @Override
      public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                  ca.action(e, MComp.getSelectedItems(e, array, true));
            }
      }

}
