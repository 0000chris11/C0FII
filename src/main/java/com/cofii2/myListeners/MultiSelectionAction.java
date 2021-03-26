/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myInterfaces.IMultiSelection;
import com.cofii2.myListeners.preClass.MultiSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

/**
 *
 * @author C0FII
 */
public class MultiSelectionAction extends MultiSelection implements ActionListener{

      public MultiSelectionAction(AbstractButton[] array, IMultiSelection ma) {
            super(array, ma);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
            ma.action(e, MComp.getSelectedButtons(e, array, true));
      }
      
}
