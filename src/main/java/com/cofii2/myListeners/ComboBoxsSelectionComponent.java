/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myInterfaces.ComboBoxsAction;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComboBox;

/**
 *
 * @author C0FII
 */
public class ComboBoxsSelectionComponent extends ComponentAdapter{
      
      private JComboBox[] array;
      private ComboBoxsAction ca;
      
      public ComboBoxsSelectionComponent(JComboBox[] array, ComboBoxsAction ca){
            this.array = array;
            this.ca = ca;
      }

      
      
      @Override
      public void componentShown(ComponentEvent e) {
            action(e);
      }

      @Override
      public void componentHidden(ComponentEvent e) {
            action(e);
      }

      public void action(ComponentEvent e){
            ca.action(e, MComp.getSelectedItems(e, array, true));
      }
      
}
