/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import com.cofii2.myInterfaces.IMultiSelection;

/**
 *
 * @author C0FII
 */
public class MultiSelectionComponent <T extends AbstractButton> extends ComponentAdapter{
      
      private T[] array;
      private IMultiSelection ma;
      
      public MultiSelectionComponent(T[] array, IMultiSelection ma){
            this.array = array;
            this.ma = ma;
      }
      
      @Override
      public void componentHidden(ComponentEvent e) {
            hiddenShown(e);
      }

      @Override
      public void componentShown(ComponentEvent e) {
            hiddenShown(e);
      }
      
      private void hiddenShown(ComponentEvent e){
            ArrayList<Integer> list = MComp.getSelectedButtons(e, array, true);
            ma.action(e, list);
      }
}
